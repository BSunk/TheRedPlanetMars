package com.bsunk.theredplanetmars.roverfavorites;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bsunk.theredplanetmars.R;
import com.bsunk.theredplanetmars.model.Camera;
import com.bsunk.theredplanetmars.model.FavoritePhoto;
import com.bsunk.theredplanetmars.model.Photo;
import com.bsunk.theredplanetmars.model.Rover;
import com.bsunk.theredplanetmars.roverimages.RoverImagesFragment;
import com.bsunk.theredplanetmars.roverimagesdetails.RoverImagesDetails;
import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.List;

import io.realm.RealmResults;

import static com.bsunk.theredplanetmars.roverimages.RoverImagesFragment.PHOTO_KEY;

/**
 * Created by Bharat on 2/13/2017.
 */

public class RoverFavoritesFragment extends Fragment implements RoverFavoritesContract.View {

    RoverFavoritesPresenter presenter;
    FastScrollRecyclerView recyclerView;
    TextView noImages;
    Button toolbarDate;
    TextView toolbarPhotoCount;
    List<FavoritePhoto> mPhotos;
    RoverFavoritesAdapter mListAdapter;


    public RoverFavoritesFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        presenter = new RoverFavoritesPresenter(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.loadImages();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_rover_favorites, container, false);
        recyclerView = (FastScrollRecyclerView) rootView.findViewById(R.id.images_list);
        noImages = (TextView) rootView.findViewById(R.id.no_images_textview);
        toolbarDate = (Button) getActivity().findViewById(R.id.toolbar_date);
        toolbarDate.setVisibility(View.INVISIBLE);
        toolbarPhotoCount = (TextView) getActivity().findViewById(R.id.toolbar_photo_count);
        return rootView;
    }

    ImageItemListener mItemListener = new ImageItemListener() {
        @Override
        public void onPhotoClick(FavoritePhoto clickedPhoto, View v) {
            showImageDetails(clickedPhoto, v);
        }
    };

    public void setToolbarPhotoCount(String count) {
        if(count.equals("")) {
            toolbarPhotoCount.setText("");
        }
        else {
            toolbarPhotoCount.setText(String.format(getString(R.string.title_count), count));
        }
    }

    public void showImageDetails(FavoritePhoto favPhoto, View v) {
        String imageTransition = getString(R.string.image_transition_string);
        Intent intent = new Intent(getActivity(), RoverImagesDetails.class);
        ImageView imageView = (ImageView) v.findViewById(R.id.image_item);
        Pair<View, String> t1 = Pair.create((View)imageView, imageTransition);

        Photo photo = new Photo();
        photo.setImgSrc(favPhoto.getImageURL());
        photo.setEarthDate(favPhoto.getPhotoDate());
        photo.setSol(favPhoto.getMartianSol());
        photo.setId(favPhoto.getId());
        Rover rover = new Rover();
        rover.setName(favPhoto.getRoverName());
        photo.setRover(rover);
        Camera camera = new Camera();
        camera.setFullName(favPhoto.getCameraName());
        photo.setCamera(camera);

        intent.putExtra(PHOTO_KEY, Parcels.wrap(photo));
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), t1);
        ActivityCompat.startActivity(getActivity(), intent, options.toBundle());
    }

    @Override
    public void showImages(RealmResults<FavoritePhoto> photos) {
        mPhotos = photos;
        if(mPhotos.isEmpty()) {
            noImages.setVisibility(View.VISIBLE);
        }
        else {
            noImages.setVisibility(View.GONE);
        }

        if(mListAdapter==null) {
            mListAdapter = new RoverFavoritesFragment.RoverFavoritesAdapter(getContext(), mItemListener);
            recyclerView.setAdapter(mListAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setHasFixedSize(true);
        }
        else {
            mListAdapter.notifyDataSetChanged();
        }
    }



    //RecyclerView Adapter for images on main screen.
    public class RoverFavoritesAdapter extends RecyclerView.Adapter<RoverFavoritesFragment.RoverFavoritesAdapter.ViewHolder>
            implements FastScrollRecyclerView.SectionedAdapter {

        private Context mContext;
        private RoverFavoritesFragment.ImageItemListener mItemListener;

        public RoverFavoritesAdapter(Context context, RoverFavoritesFragment.ImageItemListener itemListener) {
            mContext = context;
            mItemListener = itemListener;
        }

        @Override
        public RoverFavoritesFragment.RoverFavoritesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            // Inflate the custom layout
            View roverView = inflater.inflate(R.layout.favorite_item, parent, false);
            // Return a new holder instance
            RoverFavoritesFragment.RoverFavoritesAdapter.ViewHolder viewHolder = new RoverFavoritesFragment.RoverFavoritesAdapter.ViewHolder(roverView, mItemListener);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(RoverFavoritesFragment.RoverFavoritesAdapter.ViewHolder viewHolder, int position) {
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

    public interface ImageItemListener {
        void onPhotoClick(FavoritePhoto clickedPhoto, View v);
    }
}
