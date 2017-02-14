package com.bsunk.theredplanetmars.roverimagesdetails;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.bsunk.theredplanetmars.R;
import com.bsunk.theredplanetmars.databinding.ActivityRoverImagesDetailsBinding;
import com.bsunk.theredplanetmars.model.Photo;
import com.bsunk.theredplanetmars.roverimages.RoverImagesFragment;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import uk.co.senab.photoview.PhotoViewAttacher;

public class RoverImagesDetails extends AppCompatActivity implements RoverImagesDetailsContract.View {

    String imageURL;
    public static final int MY_PERMISSIONS_REQUEST_WRITE_STORAGE = 1;
    ActivityRoverImagesDetailsBinding binding;
    private RoverImagesDetailsContract.UserActionsListener mActionsListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_rover_images_details);

        mActionsListener = new RoverImagesDetailsPresenter(this);
        Photo photo = Parcels.unwrap(getIntent().getParcelableExtra(RoverImagesFragment.PHOTO_KEY));
        mActionsListener.openDetails(photo);

        Animation slideUp = AnimationUtils.loadAnimation(this, R.anim.up_from_bottom );
        binding.detailsInfoPanel.startAnimation(slideUp);

        Animation slideDown = AnimationUtils.loadAnimation(this, R.anim.down_from_top);
        binding.backButton.startAnimation(slideDown);
        binding.shareButton.startAnimation(slideDown);
        binding.favoriteButton.startAnimation(slideDown);

        binding.shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActionsListener.shareButtonClick();
            }
        });

        binding.favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActionsListener.favoriteButtonOnClick();
            }
        });
    }

    @Override
    public void showImage(String url) {
        imageURL = url;
        final PhotoViewAttacher attacher = new PhotoViewAttacher(binding.roverImage);

        Picasso.with(this).load(imageURL)
                .into(binding.roverImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        attacher.update();
                    }

                    @Override
                    public void onError() {
                    }
                });
    }

    // Can be triggered by a view event such as a button press
    public void onShareItem() {
        // Get access to bitmap image from view
        // Get access to the URI for the bitmap
        Uri bmpUri = getLocalBitmapUri();
        if (bmpUri != null) {
            // Construct a ShareIntent with link to image
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
            shareIntent.setType("image/*");
            // Launch sharing dialog for image
            startActivity(Intent.createChooser(shareIntent, "Share Image"));
        } else {
            showShareErrorToast();
        }
    }

    // Returns the URI path to the Bitmap displayed in specified ImageView
    public Uri getLocalBitmapUri() {
        // Extract Bitmap from ImageView drawable
        Drawable drawable = binding.roverImage.getDrawable();
        Bitmap bmp;
        if (drawable instanceof BitmapDrawable){
            bmp = ((BitmapDrawable) binding.roverImage.getDrawable()).getBitmap();
        } else {
            return null;
        }
        // Store image to default external storage directory
        Uri bmpUri = null;
        try {
            File file =  new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS), "trpm_image_" + System.currentTimeMillis() + ".png");
            file.getParentFile().mkdirs();
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            bmpUri = Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }

    @Override
    public void setFavoritesButton(boolean isFavorite) {
        if(isFavorite) {
            binding.favoriteButton.setImageDrawable(getDrawable(R.drawable.ic_favorite_black_24dp));
        }
        else {
            binding.favoriteButton.setImageDrawable(getDrawable(R.drawable.ic_favorite_border_black_24dp));
        }
    }

    public void requestPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_WRITE_STORAGE);
        }
        else {
            mActionsListener.noPermissionsNeeded();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        mActionsListener.onPermissionResultReturned(requestCode, grantResults);
    }

    public void showShareErrorToast() {
        Toast toast = Toast.makeText(this, getString(R.string.share_image_error), Toast.LENGTH_LONG);
        toast.show();
    }

    @Override
    public void showInfo(Photo photo) {
        binding.setPhoto(photo);
    }

    public void backButtonOnClick(View view) {
        onBackPressed();
    }
}
