package com.example.mohamed.popularmovies.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.mohamed.popularmovies.MainActivity;
import com.example.mohamed.popularmovies.R;
import com.example.mohamed.popularmovies.model.movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Mohamed on 5/4/2018.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {


    private Context context;
    public ArrayList <movie> m=new ArrayList<movie>();

    private final MovieAdapterOnClickHandler mClickHandler;


    public interface MovieAdapterOnClickHandler {
        void onClick(int adapterPosition);
    }


    public MovieAdapter(MovieAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }


    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final ImageButton image;

        public MovieAdapterViewHolder(View view) {
            super(view);
            image = (ImageButton) view.findViewById(R.id.tv_image);

            image.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();

            mClickHandler.onClick(adapterPosition);
        }
    }


    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.movie_litem_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new MovieAdapterViewHolder(view);
    }


    @Override
    public void onBindViewHolder(MovieAdapterViewHolder Holder, int position) {
        String MovieImage = m.get(position).getImage();
       Picasso.with(context)
                .load(MovieImage)
               .error(context.getResources().getDrawable(R.drawable.notfound))
                .into(Holder.image);

    }


    @Override
    public int getItemCount() {
        if (null == m) return 0;
        return m.size();
    }



    public void setMovieData(ArrayList<movie> movies) {
        m = movies;
        notifyDataSetChanged();
    }
}
