package com.bsunk.theredplanetmars.roverfavorites;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bsunk.theredplanetmars.R;
import com.bsunk.theredplanetmars.data.RealmFavoritesRepository;
import com.bsunk.theredplanetmars.model.Camera;
import com.bsunk.theredplanetmars.model.FavoritePhoto;
import com.bsunk.theredplanetmars.model.Photo;
import com.bsunk.theredplanetmars.model.Rover;
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
    public void onResume() {
        super.onResume();
        presenter = new RoverFavoritesPresenter(this, new RealmFavoritesRepository());
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
            toolbarPhotoCount.setText(String.format(getString(R.string.title_count), count));
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
        if(mListAdapter==null) {
            mListAdapter = new RoverFavoritesAdapter(getContext(), mItemListener, mPhotos);
            recyclerView.setAdapter(mListAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }
        else {
            mListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showEmptyView(boolean isEmpty) {
        if(isEmpty) {
            noImages.setVisibility(View.VISIBLE);
        }
        else {
            noImages.setVisibility(View.GONE);
        }
    }

    public interface ImageItemListener {
        void onPhotoClick(FavoritePhoto clickedPhoto, View v);
    }
}
