package com.bsunk.theredplanetmars.roverimagesdetails;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.bsunk.theredplanetmars.databinding.FragmentRoverImagesDetailsBinding;
import com.bsunk.theredplanetmars.model.Photo;
import com.bsunk.theredplanetmars.roverimages.RoverImagesFragment;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.bsunk.theredplanetmars.R;

import org.parceler.Parcels;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import uk.co.senab.photoview.PhotoViewAttacher;

public class RoverImagesDetailsFragment extends Fragment implements RoverImagesDetailsContract.View {

    String imageURL;
    public static final int MY_PERMISSIONS_REQUEST_WRITE_STORAGE = 1;
    FragmentRoverImagesDetailsBinding binding;
    private RoverImagesDetailsContract.UserActionsListener mActionsListener;

    public RoverImagesDetailsFragment () {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActionsListener = new RoverImagesDetailsPresenter(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        Bundle arguments = getArguments();
        if(arguments!=null) {
            Photo photo = Parcels.unwrap(arguments.getParcelable(RoverImagesFragment.PHOTO_KEY));
            mActionsListener.openDetails(photo);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_rover_images_details, container, false);
        binding = DataBindingUtil.setContentView(getActivity(), R.layout.fragment_rover_images_details);

        Animation slideUp = AnimationUtils.loadAnimation(getActivity(), R.anim.up_from_bottom );
        binding.detailsInfoPanel.startAnimation(slideUp);

        Animation slideDown = AnimationUtils.loadAnimation(getActivity(), R.anim.down_from_top);
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

        return rootView;
    }

    @Override
    public void showImage(String url) {
        imageURL = url;
        final PhotoViewAttacher attacher = new PhotoViewAttacher(binding.roverImage);

        Picasso.with(getContext()).load(imageURL)
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

    @Override
    public void setFavoritesButton(boolean isFavorite) {
        if(isFavorite) {
            binding.favoriteButton.setImageDrawable(getActivity().getDrawable(R.drawable.ic_favorite_black_24dp));
        }
        else {
            binding.favoriteButton.setImageDrawable(getActivity().getDrawable(R.drawable.ic_favorite_border_black_24dp));
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

    public void requestPermissions() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
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
        Toast toast = Toast.makeText(getContext(), getString(R.string.share_image_error), Toast.LENGTH_LONG);
        toast.show();
    }

    @Override
    public void showInfo(Photo photo) {
        binding.setPhoto(photo);
    }



}
