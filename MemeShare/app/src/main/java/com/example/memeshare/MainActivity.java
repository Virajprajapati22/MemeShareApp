package com.example.memeshare;

import android.app.Notification;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import org.json.JSONException;
import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
//    String Currenturl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadMeme();
    }

    private void loadMeme(){
        ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

//        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                "https://api.unsplash.com/photos/?client_id=pdOwh9Lti94Y5PgkV_MHII4rndDjcwuURnSxOzVIhKM",
                null,
                response -> {
//                    try {
//                        Currenturl = response.getString("url");
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
                    ImageView MemeImage = MainActivity.this.findViewById(R.id.MemeImage);
                    try {
                        Glide.with(MainActivity.this).load(response.getString("url")).listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(GlideException e,
                                                        Object model,
                                                        Target<Drawable> target,
                                                        boolean isFirstResource) {
                                progressBar.setVisibility(View.GONE);
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource,
                                                           Object model,
                                                           Target<Drawable> target,
                                                           DataSource dataSource,
                                                           boolean isFirstResource) {
                                progressBar.setVisibility(View.GONE);
                                return false;
                            }
                        }).into(MemeImage);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {});
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
//        queue.add(jsonObjectRequest);
    }

    public void sharememe(View view) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("Text/plain");
        startActivities(new Intent[]{Intent.createChooser(intent, "Share using...")});
    }

    public void nextmeme(View view) {
        loadMeme();
    }
}