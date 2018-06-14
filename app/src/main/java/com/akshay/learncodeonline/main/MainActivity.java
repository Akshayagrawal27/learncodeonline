package com.akshay.learncodeonline.main;

import android.app.LoaderManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.akshay.learncodeonline.R;
import com.akshay.learncodeonline.model.DataStructure;
import com.akshay.learncodeonline.ui.Answer.AnswerActivity;
import com.akshay.learncodeonline.util.Constants;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<DataStructure>>,RecyclerViewAdapter.DataStructureListClickListener {

    private static final int EARTHQUAKE_LOADER_ID = 1;

    private View adBanner;

    RecyclerView dataStructureListView;
    RecyclerViewAdapter mAdapter;

    List<DataStructure> dataStructureList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataStructureListView = (RecyclerView) findViewById(R.id.recycler_ds_list);
        adBanner = (View) findViewById(R.id.main_ad_banner);

        dataStructureListView.setHasFixedSize(false);

        mAdapter = new RecyclerViewAdapter(new ArrayList<DataStructure>(), this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        dataStructureListView.setLayoutManager(mLayoutManager);
        dataStructureListView.setAdapter(mAdapter);

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, this);
        } else {
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);
            //tvOnlineData.setText(R.string.no_internet_connection);
        }

        adBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://learncodeonline.in/"));
                    startActivity(myIntent);
                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public Loader<List<DataStructure>> onCreateLoader(int id, Bundle args) {
        return new DataStructureLoader(this, Constants.DATA_STRUCTURE_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<DataStructure>> loader, List<DataStructure> data) {

        dataStructureList = data;
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        //tvOnlineData.setText(R.string.no_data);

        if (data != null && !data.isEmpty()) {
            mAdapter = new RecyclerViewAdapter((ArrayList<DataStructure>) data, this);
            dataStructureListView.setAdapter(mAdapter);
            //tvOnlineData.setVisibility(View.GONE);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<DataStructure>> loader) {
        //mAdapter.clear();
    }

    @Override
    public void onDataStructureListItemClick(int clickedItemIndex, ArrayList<DataStructure> dataStructures) {
        Intent intent = new Intent(this, AnswerActivity.class);
        intent.putParcelableArrayListExtra(Constants.KEY_DATA_STRUSTURE_LIST, dataStructures);
        intent.putExtra(Constants.KEY_CLICKED_ITEM_INDEX, clickedItemIndex);
        startActivity(intent);
    }
}
