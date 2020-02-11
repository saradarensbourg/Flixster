package com.example.flixster.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.flixster.DetailActivity;
import com.example.flixster.Models.Movie;
import com.example.flixster.R;

import org.parceler.Parcels;

import java.util.List;

import static com.example.flixster.R.id.container;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    Context context;
    List<Movie> movies;

    // Constructor
    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    // Inflate a layout from XML and return it to the holder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("MovieAdapter", "onCreateViewHolder");
        View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(movieView);
    }

    // Populates data into item via holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("MovieAdapter", "onBindViewHolder: " + position);
        Movie movie = movies.get(position);
        holder.bind(movie);
    }

    // Return Item count of list items
    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout container;
        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            container = itemView.findViewById(R.id.container);
        }

        public void bind(final Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            String imgURL;

            // if phone is portrait, then imgURL is the poster path
            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                imgURL = movie.getPosterPath();
            }
            // else, if the phone is landscape, then imgURL is backdrop path
            else {
                imgURL = movie.getBackdropPath();
            }

            RequestOptions requestOptions = new RequestOptions();
            requestOptions = requestOptions.
                    placeholder(R.drawable.movie_load);

            Glide.with(context).load(imgURL).into(ivPoster);

            // Register click listener on container (each row in rvMovies)
            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Navigate to a new activity
                    Intent i = new Intent(context, DetailActivity.class);
                    i.putExtra("movie", Parcels.wrap(movie));
                    context.startActivity(i);
                }
            });
        }
    }
}
