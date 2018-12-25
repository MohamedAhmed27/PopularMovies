package com.example.mohamed.popularmovies;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import android.widget.Toast;

import com.example.mohamed.popularmovies.Adapters.TrailerAdapter;
import com.example.mohamed.popularmovies.data.MovieDbHelper;
import com.example.mohamed.popularmovies.model.movie;
import com.github.se_bastiaan.torrentstream.StreamStatus;
import com.github.se_bastiaan.torrentstream.Torrent;
import com.github.se_bastiaan.torrentstream.TorrentOptions;
import com.github.se_bastiaan.torrentstream.TorrentStream;
import com.github.se_bastiaan.torrentstream.listeners.TorrentListener;
import com.squareup.picasso.Picasso;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;

import java.util.ArrayList;

import static com.example.mohamed.popularmovies.data.MovieContract.*;

public class DetailsActivity extends AppCompatActivity implements TrailerAdapter.TrailerAdapterOnClickHandler,View.OnClickListener,TorrentListener {
    public static final String ADAPTER_POS_TAG = "clickedpos";
    public static final String ARRAY_TAG_TO_DEATIL_ACTIVITY = "array_List_of_movies";
    private movie movieItem = new movie();
    private int clickedPosition;
    private ImageView image;
    private TextView year;
    private TextView rating;
    private TextView name;
    private TextView overview;
    private RecyclerView mTrailer;
    private ImageView starButton;
    private ProgressBar bar;
    private ImageView shareButton;
    private SQLiteDatabase mDb;
    private ImageView posterImage;
    private ImageButton backButton;
    private Button button720Pstream;
    private Button button1080stream;
    private ProgressBar progressBar;
    private TorrentStream torrentStream;

    public ArrayList<movie> movies = new ArrayList<movie>();


    private TrailerAdapter mTrailerAdapter;
    private String streamUrl = "";
    private static final int TRAILER_REVIEW_LOADER_ID = 1;
    private static final String MOVIE_ID_KEY="movie_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        shareButton=(ImageView)findViewById(R.id.share);
        starButton=(ImageView)findViewById(R.id.add_to_favorite);
        bar=(ProgressBar)findViewById(R.id.loading_indicator);
        mTrailer=(RecyclerView)findViewById(R.id.trailers_tv);
        image = (ImageView) findViewById(R.id.image_tv);
        year = (TextView) findViewById(R.id.year_tv);
        rating = (TextView) findViewById(R.id.rating_tv);
        name = (TextView) findViewById(R.id.name_tv);
        overview = (TextView) findViewById(R.id.overview_tv);
        posterImage=findViewById(R.id.image_tvv);
        backButton=findViewById(R.id.action_up);
        progressBar=findViewById(R.id.progress);
        button720Pstream=findViewById(R.id.buttonStream720);
        button1080stream=findViewById(R.id.buttonStream1080);
        starButton.setOnClickListener(this);
        shareButton.setOnClickListener(this);
        backButton.setOnClickListener(this);
        button720Pstream.setOnClickListener(this);
        button1080stream.setOnClickListener(this);


        TorrentOptions torrentOptions = new TorrentOptions.Builder()
                .saveLocation(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS))
                .removeFilesAfterStop(true)
                .build();

        torrentStream = TorrentStream.init(torrentOptions);

        torrentStream.addListener(this);
        progressBar.setMax(100);



        MovieDbHelper dbHelper = new MovieDbHelper(this);
        mDb = dbHelper.getWritableDatabase();



        Intent i = getIntent();
        if (i.hasExtra(ADAPTER_POS_TAG) && i.hasExtra(ARRAY_TAG_TO_DEATIL_ACTIVITY)) {
            clickedPosition = i.getIntExtra(ADAPTER_POS_TAG, 0);

            movies = (ArrayList<movie>) i.getSerializableExtra(ARRAY_TAG_TO_DEATIL_ACTIVITY);

        } else {
            closeOnError();
        }


        movieItem = movies.get(clickedPosition);
        if (movieItem == null) {

            closeOnError();
            return;
        }




        try {
            populateUI(movieItem);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String URLforBackGround =  movieItem.getBackGround();
        String URLforPoster=movieItem.getImage();
        Picasso.with(this)
                .load(URLforBackGround)
                .error(getResources().getDrawable(R.drawable.notfound))
                .into(image);
        Picasso.with(this)
                .load(URLforPoster)
                .error(getResources().getDrawable(R.drawable.notfound))
                .into(posterImage);

        LinearLayoutManager layoutManagerForTrailer;
        layoutManagerForTrailer = new LinearLayoutManager(this ,LinearLayoutManager.HORIZONTAL,false);

        mTrailer.setLayoutManager(layoutManagerForTrailer);
        mTrailer.setHasFixedSize(false);
        mTrailerAdapter= new TrailerAdapter(this);
        mTrailerAdapter.setTrailerData(movieItem);
        mTrailer.setAdapter(mTrailerAdapter);

        int number=CheckFavorite(movieItem);
        if(number>0)
        {
            starButton.setBackground(getResources().getDrawable(R.drawable.yellowstar));
        }
        else
        {
            starButton.setBackground(getResources().getDrawable(R.drawable.greystar));

        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }
    }

    @Override
    public void onStreamPrepared(Torrent torrent) {
        Log.d("torrent", "OnStreamPrepared");
        // If you set TorrentOptions#autoDownload(false) then this is probably the place to call
        // torrent.startDownload();
    }

    @Override
    public void onStreamStarted(Torrent torrent) {
        Log.d("torrent", "onStreamStarted");
    }

    @Override
    public void onStreamError(Torrent torrent, Exception e) {
        Log.e("torrent", "onStreamError", e);

    }

    @Override
    public void onStreamReady(Torrent torrent) {
        progressBar.setProgress(100);
        Log.d("torrent", "onStreamReady: " + torrent.getVideoFile());

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(torrent.getVideoFile().toString()));
        intent.setDataAndType(Uri.parse(torrent.getVideoFile().toString()), "video/mp4");
        startActivity(intent);
    }

    @Override
    public void onStreamProgress(Torrent torrent, StreamStatus status) {
        if(status.bufferProgress <= 100 && progressBar.getProgress() < 100 && progressBar.getProgress() != status.bufferProgress) {
            Log.d("torrent", "Progress: " + status.bufferProgress);
            progressBar.setProgress(status.bufferProgress);
        }
    }

    @Override
    public void onStreamStopped() {
        Log.d("torrent", "onStreamStopped");
    }



    private void showViews() {

        mTrailer.setVisibility(View.VISIBLE);
    }


    private void HideViews() {


        mTrailer.setVisibility(View.INVISIBLE);

    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(movie movieItem) throws ParseException {
        name.setText(movieItem.getMoveName());
        overview.setText(movieItem.getOverview());
        rating.setText(movieItem.getVoteAvarage() + "/10");
        String dateFromApi = movieItem.getReleaseDate();
        year.setText(movieItem.getReleaseDate());
    }



    public static void watchYoutubeVideo(Context context, String id){
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + id));
        try {
            context.startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            context.startActivity(webIntent);
        }
    }


    @Override
    public void onClick(String Movie_id) {

        watchYoutubeVideo(this,Movie_id);
    }

    @Override
    public void onClick(View view) {

        if(view.getId()==R.id.add_to_favorite)
        {

            if(CheckFavorite(movieItem)>0)
            {
                starButton.setBackground(getResources().getDrawable(R.drawable.greystar));
                Uri uri= MovieEntry.CONTENT_URI;
                uri=uri.buildUpon().appendPath(movieItem.getMovie_id()+"").build();
                getContentResolver().delete(uri,null,null);

            }

            else {
                starButton.setBackground(getResources().getDrawable(R.drawable.yellowstar));
                AddNewMovie(movieItem);


            }
        }
        else if(view.getId()==R.id.share)
        {

            String mimeType = "text/plain";
            String title = "Learning How to Share";
            ShareCompat.IntentBuilder

                    .from(this)
                    .setType(mimeType)
                    .setChooserTitle(title)
                    .setText("Check out this movie!"+'\n'+movieItem.getMoveName()+'\n'+"http://www.youtube.com/watch?v=" + movieItem.getTrailer_id())
                    .startChooser();
        }
        else if(view.getId()==R.id.action_up)
        {
            Intent i =new Intent(this,MainActivity.class);
            startActivity(i);
        }
        else if(view.getId()==R.id.buttonStream720)
        {

            progressBar.setProgress(0);
            if(torrentStream.isStreaming()) {
                torrentStream.stopStream();
                button720Pstream.setText("Start stream in 720P");
                return;
            }

                if (movieItem.getTorrents().get(0) != null || !movieItem.getTorrents().get(1).equals("")) {
                    streamUrl = movieItem.getTorrents().get(0);
                } else {
                    Toast.makeText(this, "Movie torrent link not found", Toast.LENGTH_SHORT).show();
                }

                Log.v("yaw720",streamUrl);
            try {
                streamUrl = URLDecoder.decode(streamUrl, "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            torrentStream.startStream(streamUrl);
            button720Pstream.setText("Stop stream");
        }
        else if(view.getId()==R.id.buttonStream1080)
        {
            progressBar.setProgress(0);
            if(torrentStream.isStreaming()) {
                torrentStream.stopStream();
                button1080stream.setText("Start stream in 1080P");
                return;
            }

                if (movieItem.getTorrents().get(1)!= null || !movieItem.getTorrents().get(1).equals("")) {
                    streamUrl = movieItem.getTorrents().get(1);
                } else {
                    Toast.makeText(this, "Movie torrent link not found", Toast.LENGTH_SHORT).show();
                    return;
                }

            Log.v("yaw1080",streamUrl);
            try {
                streamUrl = URLDecoder.decode(streamUrl, "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            torrentStream.startStream(streamUrl);
            button1080stream.setText("Stop stream");

        }
        }





    public void AddNewMovie(movie movieItem)
    {
        ContentValues cv = new ContentValues();


        cv.put(MovieEntry.Movie_720P_torrent_link,movieItem.getTorrents().get(0));
        if(movieItem.getTorrents().get(1)!=null)
        cv.put(MovieEntry.Movie_1080P_torrent_link,movieItem.getTorrents().get(1));
        cv.put(MovieEntry.Movie_BackGround_Image,movieItem.getBackGround());
        cv.put(MovieEntry.Movie_Id, movieItem.getMovie_id());
        cv.put(MovieEntry.Movie_Image, movieItem.getImage());
        cv.put(MovieEntry.Movie_Name, movieItem.getMoveName());
        cv.put(MovieEntry.Movie_Overview, movieItem.getOverview());
        cv.put(MovieEntry.Movie_ReleaseDate, movieItem.getReleaseDate());
        cv.put(MovieEntry.Movie_VoteAvarage, movieItem.getVoteAvarage());
        cv.put(MovieEntry.Movie_Trailer_ID,movieItem.getTrailer_id());
        Uri uri=MovieEntry.CONTENT_URI;
        getContentResolver().insert(uri,cv);



    }

    public int CheckFavorite(movie movieItem)
    {
        Uri uri=MovieEntry.CONTENT_URI;
        uri=uri.buildUpon().appendPath(movieItem.getMovie_id()+"").build();

    Cursor cursor =getContentResolver().query(uri,null,null,null,null);
    return cursor.getCount();



    }


}












