package com.example.duynguyen.movieapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.duynguyen.movieapp.Model.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerViewApdapter extends RecyclerView.Adapter<RecyclerViewApdapter.ViewHolder> {

    ArrayList<Movie> mItems;
    Context mContext;

    RecyclerViewApdapter (ArrayList<Movie> items, Context context){
        mItems = items;
        mContext = context;
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        public RelativeLayout relativeLayout;
        public ImageView imageView;
        Movie item;


        public ViewHolder(View v) {
            super(v);
            relativeLayout = (RelativeLayout) v.findViewById(R.id.relativeLayout);
            imageView = (ImageView) v.findViewById(R.id.imageView);
        }

        public void setData (Movie item) {
            this.item = item;

            Picasso.get().load("http://image.tmdb.org/t/p/w185"+item.getPoster_path()).into(imageView);
        }
    }

    @Override
    public RecyclerViewApdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.content_main, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewApdapter.ViewHolder holder, int position) {
        holder.setData(mItems.get(position));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
}
