package com.bsunk.theredplanetmars.roverfavorites;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bsunk.theredplanetmars.R;
import com.bsunk.theredplanetmars.model.FavoritePhoto;
import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Bharat on 2/27/2017.
 */

//RecyclerView Adapter for images on main screen.
public class RoverFavoritesAdapter extends RecyclerView.Adapter<RoverFavoritesAdapter.ViewHolder>
        implements FastScrollRecyclerView.SectionedAdapter {

    private Context mContext;
    private RoverFavoritesFragment.ImageItemListener mItemListener;
    private List<FavoritePhoto> mPhotos;

    public RoverFavoritesAdapter(Context context, RoverFavoritesFragment.ImageItemListener itemListener, List<FavoritePhoto> photos) {
        mContext = context;
        mItemListener = itemListener;
        mPhotos = photos;
    }

    @Override
    public RoverFavoritesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // Inflate the custom layout
        View roverView = inflater.inflate(R.layout.favorite_item, parent, false);
        // Return a new holder instance
        RoverFavoritesAdapter.ViewHolder viewHolder = new RoverFavoritesAdapter.ViewHolder(roverView, mItemListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RoverFavoritesAdapter.ViewHolder viewHolder, int position) {
        TextView textView = viewHolder.cameraItem;
        textView.setText(mPhotos.get(position).getCameraName());
        ImageView imageView = viewHolder.imageItem;
        viewHolder.date.setText(mPhotos.get(position).getPhotoDate());
        viewHolder.name.setText(mPhotos.get(position).getRoverName());
        Picasso.with(mContext).load(mPhotos.get(position).getImageURL()).placeholder(R.drawable.no_pic).into(imageView);
    }

    @NonNull
    @Override
    public String getSectionName(int position) {
        return mPhotos.get(position).getPhotoDate();
    }

    @Override
    public int getItemCount() {
        return mPhotos.size();
    }

    public FavoritePhoto getItem(int position) {
        return mPhotos.get(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageItem;
        TextView cameraItem;
        TextView name;
        TextView date;
        private RoverFavoritesFragment.ImageItemListener mItemListener;

        ViewHolder(View itemView, RoverFavoritesFragment.ImageItemListener listener) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.`
            super(itemView);
            mItemListener = listener;
            imageItem = (ImageView) itemView.findViewById(R.id.image_item);
            cameraItem = (TextView) itemView.findViewById(R.id.camera_item);
            name = (TextView) itemView.findViewById(R.id.name);
            date = (TextView) itemView.findViewById(R.id.camera_date);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            FavoritePhoto photo = getItem(position);
            mItemListener.onPhotoClick(photo, v);
        }

    }
}
