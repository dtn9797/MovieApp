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
    ArrayList<Movie> arrayList;
    String API_BASE_URL = "http://api.themoviedb.org";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        arrayList = new ArrayList<Movie>();

        //RETROFIT
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        Retrofit.Builder builder = new Retrofit.Builder()
                                        .baseUrl(API_BASE_URL)
                                        .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit =
                builder
                        .client(
                                httpClient.build()
                        )
                        .build();
        MovieClient client =  retrofit.create(MovieClient.class);

        Call<APIResponse> call = client.top_rated("84d34117b17f3abebe9d04a0325e21c6");
        call.enqueue(new Callback<APIResponse>() {
            @Override
            public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                arrayList = response.body().getResults();

                RecyclerViewApdapter recyclerViewApdapter = new RecyclerViewApdapter(arrayList,MainActivity.this);
                recyclerView.setAdapter(recyclerViewApdapter);

                AutoFitGridLayoutManager autoFitGridLayoutManager = new AutoFitGridLayoutManager(MainActivity.this,500);

                recyclerView.setLayoutManager(autoFitGridLayoutManager);

            }

            @Override
            public void onFailure(Call<APIResponse> call, Throwable t) {
                Log.e("Error","Error in retrofit");
            }
        });




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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
