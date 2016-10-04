package com.bsunk.theredplanetmars;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.bsunk.theredplanetmars.model.Photo;
import com.bsunk.theredplanetmars.model.Photos;
import com.bsunk.theredplanetmars.rest.ApiClient;
import com.bsunk.theredplanetmars.rest.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    String TAG = MainActivity.class.getName();
    String API_KEY = "LJ2D0jeBoRBs0QoItHDcfJjQkQCWF5Nh8pHgxg65";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                makeRequest();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void makeRequest() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<Photos> call = apiService.getPhotos("2016-9-1",API_KEY);
        call.enqueue(new Callback<Photos>() {
            @Override
            public void onResponse(Call<Photos>call, Response<Photos> response) {
                List<Photo> photos = response.body().getPhotos();
                Log.d(TAG, "Number of photos received: " + photos.size());
                Log.d(TAG, "Number of photos received: " + photos.get(1).getRover().getName());
            }

            @Override
            public void onFailure(Call<Photos> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });
    }
}
