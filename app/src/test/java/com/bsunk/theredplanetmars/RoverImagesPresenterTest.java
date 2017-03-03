package com.bsunk.theredplanetmars;

import com.bsunk.theredplanetmars.roverimages.RoverImagesContract;
import com.bsunk.theredplanetmars.roverimages.RoverImagesPresenter;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import rx.Scheduler;
import rx.android.plugins.RxAndroidPlugins;
import rx.android.plugins.RxAndroidSchedulersHook;
import rx.schedulers.Schedulers;

import static org.mockito.Mockito.verify;

/**
 * Created by Bharat on 11/6/2016.
 */

public class RoverImagesPresenterTest {

    @Mock
    private RoverImagesContract.View view;

    private RoverImagesPresenter roverImagesPresenter;

    @BeforeClass
    public static void setupClass() {
        RxAndroidPlugins.getInstance().registerSchedulersHook(new RxAndroidSchedulersHook() {
            @Override public Scheduler getMainThreadScheduler() {
                return Schedulers.immediate();
            }
        });
    }

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        roverImagesPresenter = new RoverImagesPresenter(view);

    }

    @Test
    public void shouldShowEmptyOnLoadError() {
        roverImagesPresenter.loadImages(false, 0, 2016, 10, 16);
        verify(view).showListEmpty(true);
    }

    @Test
    public void shouldPassImagesOnLoadSuccess() {
        roverImagesPresenter.loadImages(false, 0, 2016, 10, 17);
        verify(view).showList();
    }

    @After
    public void tearDown() throws Exception {
        RxAndroidPlugins.getInstance().reset();
    }
}
