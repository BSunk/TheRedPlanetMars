package com.bsunk.theredplanetmars.roverfavorites;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bsunk.theredplanetmars.R;

/**
 * Created by Bharat on 2/13/2017.
 */

public class RoverFavoritesFragment extends Fragment implements RoverFavoritesContract.View {

    RoverFavoritesPresenter presenter;

    public RoverFavoritesFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        presenter = new RoverFavoritesPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_rover_favorites, container, false);

        return rootView;
    }
}
