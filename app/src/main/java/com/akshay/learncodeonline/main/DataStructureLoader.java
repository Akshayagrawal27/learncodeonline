package com.akshay.learncodeonline.main;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.akshay.learncodeonline.model.DataStructure;
import com.akshay.learncodeonline.util.QueryUtils;

import java.util.List;

/**
 * Loads a list of dataStructure questions by using an AsyncTask to perform the
 * network request to the given URL.
 */
public class DataStructureLoader extends AsyncTaskLoader<List<DataStructure>> {

    private static final String LOG_TAG = DataStructureLoader.class.getName();

    private String mUrl;

    public DataStructureLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    /**
     * This is on a background thread.
     */
    @Override
    public List<DataStructure> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of dataStructure.
        List<DataStructure> dataStructures = QueryUtils.fetchQuestionsData(mUrl);
        return dataStructures;
    }
}
