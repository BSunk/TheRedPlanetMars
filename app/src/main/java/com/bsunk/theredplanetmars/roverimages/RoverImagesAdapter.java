package com.bsunk.theredplanetmars.roverimages;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bsunk.theredplanetmars.R;
import com.bsunk.theredplanetmars.model.Photos;

/**
 * Created by Bharat on 10/8/2016.
 */

public class RoverImagesAdapter extends RecyclerView.Adapter<RoverImagesAdapter.ViewHolder> {

    private Photos mPhotos;
    private Context mContext;

    public RoverImagesAdapter(Context context, Photos photos) {
        mPhotos = photos;
        mContext = context;
    }

    @Override
    public RoverImagesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // Inflate the custom layout
        View roverView = inflater.inflate(R.layout.image_item, parent, false);
        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(roverView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RoverImagesAdapter.ViewHolder viewHolder, int position) {
        TextView textView = viewHolder.cameraItem;
        textView.setText(mPhotos.getPhotos().get(position).getCamera().getName());
    }

    @Override
    public int getItemCount() {
        return mPhotos.getPhotos().size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageItem;
        TextView cameraItem;

        ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            imageItem = (ImageView) itemView.findViewById(R.id.image_item);
            cameraItem = (TextView) itemView.findViewById(R.id.camera_item);
        }
    }

}