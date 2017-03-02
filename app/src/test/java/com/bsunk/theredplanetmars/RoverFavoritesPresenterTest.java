package com.bsunk.theredplanetmars;

import com.bsunk.theredplanetmars.data.RealmFavoritesRepositoryContract;
import com.bsunk.theredplanetmars.model.FavoritePhoto;
import com.bsunk.theredplanetmars.roverfavorites.RoverFavoritesContract;
import com.bsunk.theredplanetmars.roverfavorites.RoverFavoritesFragment;
import com.bsunk.theredplanetmars.roverfavorites.RoverFavoritesPresenter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Bharat on 2/26/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class RoverFavoritesPresenterTest {

    @Mock
    RealmFavoritesRepositoryContract favoritesRepository;
    @Mock
    RoverFavoritesContract.View view;

    private RoverFavoritesContract.Presenter presenter;

    @Before
    public void setUp() {
        presenter = new RoverFavoritesPresenter(view, favoritesRepository);
    }

    @Test
    public void shouldPassImagesToView() {
        ArrayList<FavoritePhoto> photosResult = new ArrayList<>();
        photosResult.add(new FavoritePhoto());
        photosResult.add(new FavoritePhoto());

        Mockito.when(favoritesRepository.getAll()).thenReturn(photosResult);
        presenter.loadImages();
        Mockito.verify(view).showImages(photosResult);
    }

    @Test
    public void shouldHandleNoImagesFound() {
        ArrayList<FavoritePhoto> photosResult = new ArrayList<>();

        Mockito.when(favoritesRepository.getAll()).thenReturn(photosResult);
        presenter.loadImages();
        Mockito.verify(view).showEmptyView(true);
    }

}
