package com.example.mohamed.popularmovies.Adapters;

import android.content.Context;
import android.graphics.Movie;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mohamed.popularmovies.R;
import com.example.mohamed.popularmovies.model.movie;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

/**
 * Created by Mohamed on 6/17/2018.
 */

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerAdapterViewHolder> {
    private Context context;
    public  movie movie=new movie();

    private final TrailerAdapterOnClickHandler mClickHandler;



    public interface TrailerAdapterOnClickHandler {
        void onClick(String Movie_id);
    }




    public TrailerAdapter(TrailerAdapter.TrailerAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }



    @Override
    public TrailerAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        int layoutIdForListItem = R.layout.trailer_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new TrailerAdapter.TrailerAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrailerAdapterViewHolder holder, int position) {
        String trailerImage = movie.getTrailer_id();
        String URL ="http://img.youtube.com/vi/"+trailerImage+"/sddefault.jpg";
        holder.trailerName.setText(movie.getMoveName()+" "+"trailer");
        Picasso.with(context)
                .load(URL)
                .error(context.getResources().getDrawable(R.drawable.notfound))
                .into(holder.image);

    }

    @Override
    public int getItemCount() {

        if(null==movie) {
            return 0;
        }
        else {return 1;}
    }

    public class TrailerAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final ImageView image;
        public final TextView trailerName;
        public TrailerAdapterViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.trailer_item_image_tv);
            trailerName=(TextView)itemView.findViewById(R.id.trailer_item_name_tv);
            image.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapter_pos=getAdapterPosition();
            mClickHandler.onClick(movie.getTrailer_id());
        }
    }
    public void setTrailerData( movie moviess) {
        movie= moviess;
        notifyDataSetChanged();
    }
}
