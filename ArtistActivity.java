package com.example.ArtistSearch.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ArtistSearch.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ArtistActivity extends AppCompatActivity {
    private static final String TAG = "ArtistActivity";
    private JsonRequest request;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tabs);
        Log.d(TAG, "onCreate: started.");

        getIncomingIntent();
    }
    
    private void getIncomingIntent(){
        Log.d(TAG, "getIntent: checking for incoming intents.");

        if(getIntent().hasExtra("artist")){

            Log.d(TAG, "getIncomingIntent: found intent extra");
            String url = getIntent().getStringExtra("artist");
            int ind = url.lastIndexOf("/");
            String id = url.substring(ind+1);
            setArtist(id);
        }
    }

    private void setArtist(String id){
        Log.d(TAG, "setArtist: setting artist info to widgets");

        TextView artist_name = findViewById(R.id.artist_name);
        TextView artist_nationality = findViewById(R.id.artist_nationality);
        TextView artist_birthday = findViewById(R.id.artist_birthday);
        TextView artist_deathday = findViewById(R.id.artist_deathday);
        TextView artist_biography = findViewById(R.id.artist_biography);

        TextView artist_label_name = findViewById(R.id.artist_label_name);
        TextView artist_label_nationality = findViewById(R.id.artist_label_nationality);
        TextView artist_label_birthday = findViewById(R.id.artist_label_birthday);
        TextView artist_label_deathday = findViewById(R.id.artist_label_deathday);
        TextView artist_label_biography = findViewById(R.id.artist_label_biography);

        String url = "https://sandiego0615.wl.r.appspot.com/artist?id="+id;
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        String[] strings = new String[] {"name", "nationality", "birthday", "deathday", "biography"};
                        TextView[] textViews = new TextView[] {artist_name, artist_nationality, artist_birthday, artist_deathday, artist_biography};
                        TextView[] textViewsLbls = new TextView[] {artist_label_name, artist_label_nationality, artist_label_birthday, artist_label_deathday, artist_label_biography};
                        for (int i=0; i<strings.length; i++) {
                            String str = response.getString(strings[i]);
                            if (str.equals("")) {
                                textViews[i].setVisibility(View.GONE);
                                textViewsLbls[i].setVisibility(View.GONE);
                            }
                            else {
                                textViews[i].setText(str);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);

    }


}
