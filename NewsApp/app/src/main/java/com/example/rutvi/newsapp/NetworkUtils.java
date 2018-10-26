package com.example.rutvi.newsapp;

import android.net.Uri;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {


    public static final String TAG="t";
    public static  final String GITHUB_BASE_URL = "https://newsapi.org/v1/articles";
    public static final String PARAM_SOURCE = "source";
    public static final String source_value = "the-next-web";
    public static final String PARAM_APIKEY = "apiKey";
    public static final String PARAM_SORT = "sortBy";
    public static final String SORT = "latest";
    public static final String APIKEY = "585a44a3616445d682ba6dd02a386da3";
    public static URL makeURL() {
        Uri uri = Uri.parse(GITHUB_BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_SOURCE, source_value)
                .appendQueryParameter(PARAM_SORT, SORT)
                .appendQueryParameter(PARAM_APIKEY, APIKEY)
                .build();
        URL url = null;
        try {
            url = new URL(uri.toString());
            return url;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");
            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
