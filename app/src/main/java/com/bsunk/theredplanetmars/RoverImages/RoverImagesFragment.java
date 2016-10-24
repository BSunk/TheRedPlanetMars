package com.bsunk.theredplanetmars.roverimages;

import android.app.ActivityOptions;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.util.Pair;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bsunk.theredplanetmars.R;
import com.bsunk.theredplanetmars.model.Photo;
import com.bsunk.theredplanetmars.model.Photos;
import com.bsunk.theredplanetmars.roverimagesdetails.RoverImagesDetails;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

/**
 * Class that displays images of selected rover. Initial rover on app open is Curiosity.
 */
public class RoverImagesFragment extends Fragment implements RoverImagesContract.View {

    public static String PHOTO_KEY = "photo";
    private static RoverImagesContract.UserActionsListener mActionsListener;
    private RoverImagesAdapter mListAdapter;
    int rover = 0; //int to describe which rover to load
    RecyclerView recyclerView;
    SwipeRefreshLayout refreshLayout;
    TextView noImages;
    TextView toolbarTitle;
    Photos mPhotos;
    Button toolbarDate;
    TextView toolbarPhotoCount;

    public RoverImagesFragment() {
    }

    public static RoverImagesFragment newInstance(int roverid) {
        Bundle args = new Bundle();
        args.putInt(RoverImagesPresenter.ROVER_KEY, roverid);
        RoverImagesFragment fragment = new RoverImagesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mActionsListener = new RoverImagesPresenter(this);
    }

    //Convert the data into a Gson string and store in bundle.
    @Override
    public void onSaveInstanceState(Bundle bundle) {
        Gson gson = new Gson();
        bundle.putString("data", gson.toJson(mPhotos));
        super.onSaveInstanceState(bundle);
    }

    //Restore the photos objects and call the recyclerview.
    @Override
    public void onViewStateRestored(Bundle bundle) {
        super.onViewStateRestored(bundle);
        if(bundle!=null) {
            Gson gson = new Gson();
            mPhotos = gson.fromJson(bundle.getString("data"), Photos.class);
            if(mPhotos!=null) {
                setToolbarTitle(rover);
                setToolbarDate(" " + mPhotos.getPhotos().get(0).getEarthDate());
                showToolbarDate();
                mListAdapter = new RoverImagesAdapter(getContext(), mItemListener);
                recyclerView.setAdapter(mListAdapter);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new GridLayoutManager(getContext(), getResources().getInteger(R.integer.num_columns)));
            }
            else {
                final Calendar c = Calendar.getInstance();
                c.add(Calendar.DATE, -5); //Date 5 days ago
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                mActionsListener.loadImages(false, rover, year, (month+1), day);
            }
        }
    }

    @Override
    public void setToolbarTitleText(String title) {
        toolbarTitle.setText(title);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState==null) {

            final Calendar c = Calendar.getInstance();
            c.add(Calendar.DATE, -5); //Date 5 days ago
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            rover = getArguments().getInt(RoverImagesPresenter.ROVER_KEY);
            mActionsListener.loadImages(false, rover, year, (month+1), day);

        }
        setRetainInstance(true);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_rover_images, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.images_list);
        refreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.refresh_layout);
        noImages = (TextView) rootView.findViewById(R.id.no_images_textview);
        toolbarTitle = (TextView) getActivity().findViewById(R.id.toolbar_title);
        toolbarDate = (Button) getActivity().findViewById(R.id.toolbar_date);
        toolbarPhotoCount = (TextView) getActivity().findViewById(R.id.toolbar_photo_count);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshContent(); }
            });

        toolbarDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                Bundle args = new Bundle();

                String[] date = parseDate(toolbarDate.getText().toString());
                args.putInt("year", Integer.parseInt(date[0]));
                args.putInt("month", Integer.parseInt(date[1]));
                args.putInt("day", Integer.parseInt(date[2]));
                args.putInt("roverid", rover);

                newFragment.setArguments(args);
                newFragment.show(getFragmentManager(), "datePicker");
            }
        });

        return rootView;
    }

    private String[] parseDate(String date) {
        String[] dateArr = new String[3];
        dateArr[0] = date.substring(1, 5);
        dateArr[1] = date.substring(6,8);
        dateArr[2] = date.substring(9,11);
        return dateArr;
    }

    ImageItemListener mItemListener = new ImageItemListener() {
        @Override
        public void onPhotoClick(Photo clickedPhoto, View v) {
            showImageDetails(clickedPhoto, v);
        }
    };

    private void refreshContent(){
        String[] date = parseDate(toolbarDate.getText().toString());
        mActionsListener.loadImages(true, rover,  Integer.parseInt(date[0]), (Integer.parseInt(date[1])),  Integer.parseInt(date[2]));
    }

    @Override
    public void setRefreshIndicator(boolean active) {
        if(!active) {
            refreshLayout.setRefreshing(false);
        }
        else {
            refreshLayout.setRefreshing(true);
        }
    }

    @Override
    public void showImages(Photos photos) {
        mPhotos = photos;
        if(mPhotos.getPhotos().size()==0) {
            noImages.setVisibility(View.VISIBLE);
        }
        else {
            noImages.setVisibility(View.GONE);
        }

        if(mListAdapter==null) {
            mListAdapter = new RoverImagesAdapter(getContext(), mItemListener);
            recyclerView.setAdapter(mListAdapter);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), getResources().getInteger(R.integer.num_columns)));
        }
        else {
            mListAdapter.notifyDataSetChanged();
        }
    }

    public void showImageDetails(Photo photo, View v) {
        String imageTransition = getString(R.string.image_transition_string);
        String cameraTransition = getResources().getString(R.string.camera_transition_string);

        Intent intent = new Intent(getActivity(), RoverImagesDetails.class);

        ImageView imageView = (ImageView) v.findViewById(R.id.image_item);
        TextView camNameTextView = (TextView) v.findViewById(R.id.camera_item);

        Pair<View, String> t1 = Pair.create((View)imageView, imageTransition);
        Pair<View, String> t2 = Pair.create((View)camNameTextView, cameraTransition);

        Gson gson = new Gson(); //serialize data with gson
        intent.putExtra(PHOTO_KEY, gson.toJson(photo));
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), t1, t2);
        ActivityCompat.startActivity(getActivity(), intent, options.toBundle());
    }

    @Override
    public void setToolbarTitle(int title) {
        switch (title) {
            case 0:
                toolbarTitle.setText(getString(R.string.curiosity));
                break;
            case 1:
                toolbarTitle.setText(getString(R.string.opportunity));
                break;
            case 2:
                toolbarTitle.setText(getString(R.string.spirit));
                break;
        }
    }

    @Override
    public void setToolbarPhotoCount(String count) {
        if(count.equals("")) {
            toolbarPhotoCount.setText("");
        }
        else {
            toolbarPhotoCount.setText(String.format(getString(R.string.title_count), count));
        }
    }

    @Override
    public void setToolbarDate(String date) {
        toolbarDate.setText(date);
    }

    @Override
    public void hideToolbarTitle() {
        toolbarTitle.setAnimation(AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_out));
        toolbarTitle.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showToolbarDate() {
        toolbarDate.setAnimation(AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_in));
        toolbarDate.setVisibility(View.VISIBLE);
    }

    @Override
    public void showToolbarTitle() {
        toolbarTitle.setAnimation(AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_in));
        toolbarTitle.setVisibility(View.VISIBLE);
    }

    @Override
    public void showListEmpty(boolean isEmpty) {
        if (isEmpty) {
            noImages.setVisibility(View.VISIBLE);
            toolbarTitle.setText(R.string.title_name);
        }
        else {
            noImages.setVisibility(View.GONE);
        }
    }

    @Override
    public void showList() {
       recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideList() {
        recyclerView.setVisibility(View.INVISIBLE);
    }


    //RecyclerView Adapter for images on main screen.
    public class RoverImagesAdapter extends RecyclerView.Adapter<RoverImagesAdapter.ViewHolder> {

        private Context mContext;
        private ImageItemListener mItemListener;

        public RoverImagesAdapter(Context context, ImageItemListener itemListener) {
            mContext = context;
            mItemListener = itemListener;
        }

        @Override
        public RoverImagesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            // Inflate the custom layout
            View roverView = inflater.inflate(R.layout.image_item, parent, false);
            // Return a new holder instance
            RoverImagesAdapter.ViewHolder viewHolder = new RoverImagesAdapter.ViewHolder(roverView, mItemListener);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(RoverImagesAdapter.ViewHolder viewHolder, int position) {
            TextView textView = viewHolder.cameraItem;
            textView.setText(mPhotos.getPhotos().get(position).getCamera().getFullName());
            ImageView imageView = viewHolder.imageItem;
            Picasso.with(mContext).load(mPhotos.getPhotos().get(position).getImgSrc()).placeholder(R.drawable.no_pic).into(imageView);
        }

        @Override
        public int getItemCount() {
            return mPhotos.getPhotos().size();
        }

        public Photo getItem(int position) {
            return mPhotos.getPhotos().get(position);
        }

        class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            ImageView imageItem;
            TextView cameraItem;
            private ImageItemListener mItemListener;

            ViewHolder(View itemView, ImageItemListener listener) {
                // Stores the itemView in a public final member variable that can be used
                // to access the context from any ViewHolder instance.
                super(itemView);
                mItemListener = listener;
                imageItem = (ImageView) itemView.findViewById(R.id.image_item);
                cameraItem = (TextView) itemView.findViewById(R.id.camera_item);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                int position = getAdapterPosition();
                Photo photo = getItem(position);
                mItemListener.onPhotoClick(photo, v);
            }

        }
    }

    public interface ImageItemListener {
        void onPhotoClick(Photo clickedPhoto, View v);
    }

    //Date Picker class
    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        int rover;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker

            int year = getArguments().getInt("year");
            int month = getArguments().getInt("month");
            int day = getArguments().getInt("day");
            rover = getArguments().getInt("roverid");

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month-1, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            mActionsListener.loadImages(false, rover, year, month+1, day);
        }
    }

}


