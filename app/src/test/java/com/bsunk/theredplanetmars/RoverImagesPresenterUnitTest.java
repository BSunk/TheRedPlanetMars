package com.bsunk.theredplanetmars;

import com.bsunk.theredplanetmars.roverimages.RoverImagesContract;
import com.bsunk.theredplanetmars.roverimages.RoverImagesPresenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.verify;

/**
 * Created by Bharat on 11/6/2016.
 */

public class RoverImagesPresenterUnitTest {

    @Mock
    private RoverImagesContract.View mRoverImagesView;

    private RoverImagesPresenter roverImagesPresenter;

    @Before
    public void setupNotesPresenter() {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this);

        // Get a reference to the class under test
        roverImagesPresenter = new RoverImagesPresenter(mRoverImagesView);
    }

    @Test
    public void checkReceivedDataTest() {
        roverImagesPresenter.loadImages(false, 0, 2016, 10, 15);
        verify(mRoverImagesView).setRefreshIndicator(true);
        verify(mRoverImagesView).showListEmpty(false);
        verify(mRoverImagesView).hideList();
        verify(mRoverImagesView).setToolbarTitle(0);
        verify(mRoverImagesView).setToolbarPhotoCount("0");

        verify(mRoverImagesView).setToolbarDate(" 2016-10-15");
       // verify(mRoverImagesView).showToolbarDate();
      //  verify(mRoverImagesView).showList();
        verify(mRoverImagesView).setRefreshIndicator(false);
        verify(mRoverImagesView).setToolbarPhotoCount("");

    }


}
