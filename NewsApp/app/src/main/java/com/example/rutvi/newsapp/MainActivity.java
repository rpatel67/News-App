package com.example.rutvi.newsapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;

import java.net.URL;
public class MainActivity extends AppCompatActivity {
    static final String TAG = "mainactivity";
    private ProgressBar progress;
    private RecyclerView mRecyclerView;
    private NewsAdapter mNewsAdapter;
    private JsonParser jParser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progress = (ProgressBar) findViewById(R.id.progressBar);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_news);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        NetworkTask task = new NetworkTask();
        task.execute();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.search) {
            Context context = MainActivity.this;
            String message = "Search Clicked";
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void openPage(String url) {
        Uri webPage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webPage);
        if(intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public class NetworkTask extends AsyncTask<String, Void, ArrayList<NewsItem>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.setVisibility(View.VISIBLE);
        }
        @Override
        protected ArrayList<NewsItem> doInBackground(String... params) {
            ArrayList<NewsItem> mnewsData = null;

            URL newsRequestUrl = NetworkUtils.makeURL();
            try {
                String jsonNewsResponse = NetworkUtils
                        .getResponseFromHttpUrl(newsRequestUrl);
                mnewsData = jParser.parseJSON(jsonNewsResponse);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return mnewsData;
        }
        @Override
        protected void onPostExecute(final ArrayList<NewsItem> mnewsData) {
            super.onPostExecute(mnewsData);
            progress.setVisibility(View.GONE);
            if (mnewsData != null) {
                NewsAdapter adapter = new NewsAdapter(mnewsData, new NewsAdapter.ItemClickListener() {
                    @Override
                    public void OnItemClick(int clickedItem) {
                        String url = mnewsData.get(clickedItem).getUrl();
                        Log.d(TAG, String.format("Url %s", url));
                        openPage(url);
                    }

                });
                mRecyclerView.setAdapter(adapter);
            }
        }
    }
}