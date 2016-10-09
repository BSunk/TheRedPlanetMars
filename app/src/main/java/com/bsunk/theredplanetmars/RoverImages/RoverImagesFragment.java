package com.bsunk.theredplanetmars.roverimages;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bsunk.theredplanetmars.R;
import com.bsunk.theredplanetmars.model.Photos;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class RoverImagesFragment extends Fragment implements RoverImagesContract.View {

    private RoverImagesContract.UserActionsListener mActionsListener;
    private RoverImagesAdapter mListAdapter;
    RecyclerView recyclerView;
    ProgressBar loadingIndicator;
    Photos mPhotos;

    public RoverImagesFragment() {
    }

    public static RoverImagesFragment newInstance() { return new RoverImagesFragment();}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActionsListener = new RoverImagesPresenter(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mActionsListener.loadImages(false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_rover_images, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.images_list);
        loadingIndicator = (ProgressBar) rootView.findViewById(R.id.loading);
        return rootView;
    }

    @Override
    public void showImages(Photos photos) {
        mPhotos = photos;
        if(mListAdapter==null) {
            mListAdapter = new RoverImagesAdapter(getContext());
            recyclerView.setAdapter(mListAdapter);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        }
        else {
            mListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void setProgressIndicator(boolean active) {
        if(active) {
            loadingIndicator.setVisibility(View.VISIBLE);
        }
        else {
            loadingIndicator.setVisibility(View.GONE);
        }
    }

    //Adapter for images on main screen.
    public class RoverImagesAdapter extends RecyclerView.Adapter<RoverImagesAdapter.ViewHolder> {

        private Context mContext;

        public RoverImagesAdapter(Context context) {
            mContext = context;
        }

        @Override
        public RoverImagesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            // Inflate the custom layout
            View roverView = inflater.inflate(R.layout.image_item, parent, false);
            // Return a new holder instance
            RoverImagesAdapter.ViewHolder viewHolder = new RoverImagesAdapter.ViewHolder(roverView);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(RoverImagesAdapter.ViewHolder viewHolder, int position) {
            TextView textView = viewHolder.cameraItem;
            textView.setText(mPhotos.getPhotos().get(position).getCamera().getName());
            ImageView imageView = viewHolder.imageItem;
            Glide.with(mContext).load(mPhotos.getPhotos().get(position).getImgSrc()).into(imageView);
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

}
