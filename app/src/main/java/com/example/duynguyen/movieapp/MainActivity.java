package com.example.duynguyen.movieapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.duynguyen.movieapp.Model.APIResponse;
import com.example.duynguyen.movieapp.Model.Movie;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerViewAdapter mRecyclerViewAdapter;

    final String POPULAR_TYPE ="popular";
    final String TOP_RATED_TYPE ="top rated";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerViewAdapter = new RecyclerViewAdapter();
        recyclerView.setAdapter(mRecyclerViewAdapter);
        AutoFitGridLayoutManager autoFitGridLayoutManager = new AutoFitGridLayoutManager(MainActivity.this,500);
        recyclerView.setLayoutManager(autoFitGridLayoutManager);

        loadMovieData("normal");
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
        if (id == R.id.popular_settings) {
            loadMovieData(POPULAR_TYPE);
            return true;
        }
        else if (id == R.id.top_rated_settings) {
            loadMovieData(TOP_RATED_TYPE);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void loadMovieData (String type){
        MovieClient client =  new RetrofitClient().getClient().create(MovieClient.class);
        Call<APIResponse> call;
        if (type == TOP_RATED_TYPE) {
           call = client.top_rated("84d34117b17f3abebe9d04a0325e21c6");
        }
        else {
            call = client.popular_movies("84d34117b17f3abebe9d04a0325e21c6");
        }
        call.enqueue(new Callback<APIResponse>() {
            @Override
            public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                ArrayList<Movie> movies = response.body().getResults();
                mRecyclerViewAdapter.setMoviesData(movies);
            }

            @Override
            public void onFailure(Call<APIResponse> call, Throwable t) {
                Log.e("Error","Error in retrofit");
            }
        });
    }
}
