package com.akshay.learncodeonline.util;

import android.text.TextUtils;
import android.util.Log;

import com.akshay.learncodeonline.model.DataStructure;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public final class QueryUtils {

    private static final String LOG_TAG = "learncodeonline";

    private QueryUtils() {
    }

    /**
     * Query the dataStructure dataSet and return a list of {@link DataStructure} objects.
     */
    public static List<DataStructure> fetchQuestionsData(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
            Log.wtf(LOG_TAG, jsonResponse);
        } catch (IOException e) {
            Log.e(LOG_TAG, Constants.ERROR_MAKING_HTTP_REQUEST, e);
        }

        List<DataStructure> questions = extractQuestionsFromJson(jsonResponse);

        return questions;
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, Constants.ERROR_BUILDING_URL, e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod(Constants.HTTP_REQUEST_GET);
            urlConnection.connect();

            // If the request was successful (response code 200)
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, Constants.ERROR_RETRIEVING_JSON_RESULT, e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Return a list of {@link DataStructure} objects that has been built up from
     * parsing the given JSON response.
     */
    private static List<DataStructure> extractQuestionsFromJson(String questionJSON) {

        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(questionJSON)) {
            return null;
        }

        List<DataStructure> dataStructure = new ArrayList<>();

        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(questionJSON);

            JSONArray earthquakeArray = baseJsonResponse.getJSONArray(Constants.PROPERTY_DS_QUESTIONS);

            for (int i = 0; i < earthquakeArray.length(); i++) {

                JSONObject currentQuestions = earthquakeArray.getJSONObject(i);

                String question = currentQuestions.getString(Constants.PROPERTY_QUESTION);
                String answer = currentQuestions.getString(Constants.PROPERTY_ANSWER);

                DataStructure dataStructure1 = new DataStructure(question, answer);

                dataStructure.add(dataStructure1);
            }

        } catch (JSONException e) {
            Log.e(LOG_TAG, Constants.ERROR_PARSING_JSON_RESULT, e);
        }

        return dataStructure;
    }

}
