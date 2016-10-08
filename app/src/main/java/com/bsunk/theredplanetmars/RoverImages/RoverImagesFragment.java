package com.bsunk.theredplanetmars.roverimages;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bsunk.theredplanetmars.R;
import com.bsunk.theredplanetmars.model.Photos;

import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class RoverImagesFragment extends Fragment implements RoverImagesContract.View {

    private RoverImagesContract.UserActionsListener mActionsListener;
    private RoverImagesAdapter mListAdapter;
    RecyclerView recyclerView;

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

        return rootView;
    }

    @Override
    public void showImages(Photos photos) {
        if(mListAdapter==null) {
            mListAdapter = new RoverImagesAdapter(getContext(), photos);
            recyclerView.setAdapter(mListAdapter);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        }
        mListAdapter.notifyDataSetChanged();
    }

}
