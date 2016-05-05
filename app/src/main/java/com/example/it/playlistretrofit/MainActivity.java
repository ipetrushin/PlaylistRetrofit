package com.example.it.playlistretrofit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import retrofit2.*;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.*;



public class MainActivity extends AppCompatActivity {
    public static final String API_URL = "http://194.176.114.21:8008/";

    EditText song;
    class Song {
        public String title;
        public int duration;
        public int id;
        public int artist;

        public Song(String title, int duration, int id, int artist) {
            this.title = title;
            this.duration = duration;
            this.id = id;
            this.artist = artist;
        }
    }

    interface JsonServer {
        @GET("/song{id}")
        Call<Song> getSong(@Path("id") int id);
    }

    public void onClick(View v) throws IOException
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();

        JsonServer jsonServer = retrofit.create(JsonServer.class);

        Call<Song> call = jsonServer.getSong(Integer.parseInt(song.getText().toString()));
        final Button getsong = (Button) findViewById(R.id.getsong);
        getsong.setEnabled(false);
        call.enqueue(new Callback<Song>() {
            @Override
            public void onResponse(Call<Song> call, Response<Song> response) {
                Song song = response.body();
                Toast.makeText(MainActivity.this, "Title:"+song.title, Toast.LENGTH_SHORT).show();
                Log.w("DEBUG", song.title);
                getsong.setEnabled(true);
            }

            @Override
            public void onFailure(Call<Song> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error:"+t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.w("DEBUG", t.getMessage());
                getsong.setEnabled(true);
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        song = (EditText) findViewById(R.id.song);



    }


}
