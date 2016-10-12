package com.bsunk.theredplanetmars.roverimages;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bsunk.theredplanetmars.R;
import com.bsunk.theredplanetmars.model.Photo;
import com.bsunk.theredplanetmars.model.Photos;
import com.bsunk.theredplanetmars.roverimagesdetails.RoverImagesDetails;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

/**
 * Class that displays images of selected rover. Initial rover on app open is Curiosity.
 */
public class RoverImagesFragment extends Fragment implements RoverImagesContract.View {

    public static String PHOTO_KEY = "photo";
    private RoverImagesContract.UserActionsListener mActionsListener;
    private RoverImagesAdapter mListAdapter;
    int rover=0; //int to describe which rover to load
    RecyclerView recyclerView;
    ProgressBar loadingIndicator;
    SwipeRefreshLayout refreshLayout;
    TextView noImages;
    Photos mPhotos;

    public RoverImagesFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActionsListener = new RoverImagesPresenter(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mActionsListener.loadImages(false, rover);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle args = getArguments();
        rover = args.getInt(RoverImagesPresenter.ROVER_KEY);

        View rootView = inflater.inflate(R.layout.fragment_rover_images, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.images_list);
        loadingIndicator = (ProgressBar) rootView.findViewById(R.id.loading);
        refreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.refresh_layout);
        noImages = (TextView) rootView.findViewById(R.id.no_images_textview);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshContent(); }
            });
        return rootView;
    }

    ImageItemListener mItemListener = new ImageItemListener() {
        @Override
        public void onPhotoClick(Photo clickedPhoto) {
            mActionsListener.openPhotoDetails(clickedPhoto);
        }
    };

    private void refreshContent(){
        mActionsListener.loadImages(true, rover);
    }

    @Override
    public void setRefreshIndicator(boolean active) {
        if(!active) {
            refreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void showImages(Photos photos) {
        mPhotos = photos;
        if(mPhotos.getPhotos().size()==0) {
            noImages.setVisibility(View.VISIBLE);
        }
        else {
            noImages.setVisibility(View.GONE);
        }

        if(mListAdapter==null) {
            mListAdapter = new RoverImagesAdapter(getContext(), mItemListener);
            recyclerView.setAdapter(mListAdapter);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        }
        else {
            mListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showImageDetails(Photo photo) {
        Intent intent = new Intent(getActivity(), RoverImagesDetails.class);
        Gson gson = new Gson(); //serialize data with gson
        intent.putExtra(PHOTO_KEY, gson.toJson(photo));
        startActivity(intent);
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

    @Override
    public void showListEmpty(boolean isEmpty) {
        if (isEmpty) {
            noImages.setVisibility(View.VISIBLE);
        }
        else {
            noImages.setVisibility(View.GONE);
        }
    }

    //RecyclerView Adapter for images on main screen.
    public class RoverImagesAdapter extends RecyclerView.Adapter<RoverImagesAdapter.ViewHolder> {

        private Context mContext;
        private ImageItemListener mItemListener;

        public RoverImagesAdapter(Context context, ImageItemListener itemListener) {
            mContext = context;
            mItemListener = itemListener;
        }

        @Override
        public RoverImagesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            // Inflate the custom layout
            View roverView = inflater.inflate(R.layout.image_item, parent, false);
            // Return a new holder instance
            RoverImagesAdapter.ViewHolder viewHolder = new RoverImagesAdapter.ViewHolder(roverView, mItemListener);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(RoverImagesAdapter.ViewHolder viewHolder, int position) {
            TextView textView = viewHolder.cameraItem;
            textView.setText(mPhotos.getPhotos().get(position).getCamera().getFullName());
            ImageView imageView = viewHolder.imageItem;
            Glide.with(mContext).load(mPhotos.getPhotos().get(position).getImgSrc()).placeholder(R.drawable.no_pic).into(imageView);
        }

        @Override
        public int getItemCount() {
            return mPhotos.getPhotos().size();
        }

        public Photo getItem(int position) {
            return mPhotos.getPhotos().get(position);
        }

        class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            ImageView imageItem;
            TextView cameraItem;
            private ImageItemListener mItemListener;

            ViewHolder(View itemView, ImageItemListener listener) {
                // Stores the itemView in a public final member variable that can be used
                // to access the context from any ViewHolder instance.
                super(itemView);
                mItemListener = listener;
                imageItem = (ImageView) itemView.findViewById(R.id.image_item);
                cameraItem = (TextView) itemView.findViewById(R.id.camera_item);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                int position = getAdapterPosition();
                Photo photo = getItem(position);
                mItemListener.onPhotoClick(photo);
            }

        }
    }

    public interface ImageItemListener {
        void onPhotoClick(Photo clickedPhoto);
    }

}
