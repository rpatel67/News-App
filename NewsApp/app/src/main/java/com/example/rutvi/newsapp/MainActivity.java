package com.example.rutvi.newsapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.io.IOException;
import java.net.URL;
import static android.R.id.progress;
public class MainActivity extends AppCompatActivity {
    private ProgressBar progress;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progress = (ProgressBar) findViewById(R.id.progressBar);
        textView = (TextView) findViewById(R.id.text);
        getURL();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    public void getURL(){
        NetworkTask nt = new NetworkTask();;
        URL url = NetworkUtils.makeURL();
        nt.execute(url);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.search) {
            textView.setText("");
            getURL();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public class NetworkTask extends AsyncTask<URL, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.setVisibility(View.VISIBLE);
        }
        @Override
        protected String doInBackground(URL... params) {
            try{
                return NetworkUtils.getResponseFromHttpUrl(params[0]);
            }catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            textView.setText(s);
            progress.setVisibility(View.GONE);
        }
    }
}