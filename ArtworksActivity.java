package com.example.ArtistSearch.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.ArtistSearch.R;
import com.example.ArtistSearch.adapters.ArtworksRecyclerViewAdapter;
import com.example.ArtistSearch.adapters.RecyclerViewAdapter;
import com.example.ArtistSearch.model.Artworks;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ArtworksActivity extends AppCompatActivity {

    private final String url = "https://sandiego0615.wl.r.appspot.com/artworks?id=";
    private JsonArrayRequest request ;
    private RequestQueue requestQueue;
    private List<Artworks> lstArtworks ;
    private RecyclerView recyclerViewArtworks ;
    private RecyclerView recyclerView ;
    private ProgressBar spinner;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs);
        lstArtworks = new ArrayList<>();
        recyclerViewArtworks = findViewById(R.id.artworksrecyclerviewid);
        recyclerView = findViewById(R.id.recyclerviewid);
        recyclerView.setVisibility(View.GONE);
        spinner = (ProgressBar)findViewById(R.id.progressBar1);
        spinner.setVisibility(View.GONE);
        getIncomingIntent();
    }
    private void getIncomingIntent(){
        if(getIntent().hasExtra("artist")){
            String url = getIntent().getStringExtra("artist");
            int ind = url.lastIndexOf("/");
            String id = url.substring(ind+1);
            jsonRequest(id);
        }
    }
    private void jsonRequest(String id) {
        request  = new JsonArrayRequest(url + id, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null ;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i) ;
                        Artworks artwork = new Artworks();
                        artwork.setTitle(jsonObject.getString("title"));
                        artwork.setId(jsonObject.getString("id"));
                        artwork.setHref(jsonObject
                                .getJSONObject("_links")
                                .getJSONObject("thumbnail")
                                .getString("href"));
                        lstArtworks.add(artwork);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                setuprecyclerview(lstArtworks);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue = Volley.newRequestQueue(ArtworksActivity.this);
        requestQueue.add(request);
    }
    private void setuprecyclerview(List<Artworks> lstArtworks) {
        /*ArtworksRecyclerViewAdapter myadapter = new ArtworksRecyclerViewAdapter(this, lstArtworks);
        recyclerViewArtworks.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewArtworks.setAdapter(myadapter);*/
    }

}
