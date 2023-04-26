package com.example.ArtistSearch;

import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ArtistSearch.R;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.ArtistSearch.activities.MainActivity;
import com.google.gson.Gson;

public class ArtistFragment extends Fragment {
    private String id;
    private JSONObject jsonObject;
    private View root;

    public ArtistFragment(String id) {
        this.id = id;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_artist, container, false);
        root.findViewById(R.id.progressBar3).setVisibility(View.VISIBLE);
        root.findViewById(R.id.artist_table).setVisibility(View.GONE);
        setArtist(id, root);

        return root;
    }
    private void setArtist(String id, View root){

        TextView artist_name = root.findViewById(R.id.artist_name);
        TextView artist_nationality = root.findViewById(R.id.artist_nationality);
        TextView artist_birthday = root.findViewById(R.id.artist_birthday);
        TextView artist_deathday = root.findViewById(R.id.artist_deathday);
        TextView artist_biography = root.findViewById(R.id.artist_biography);

        TextView artist_label_name = root.findViewById(R.id.artist_label_name);
        TextView artist_label_nationality = root.findViewById(R.id.artist_label_nationality);
        TextView artist_label_birthday = root.findViewById(R.id.artist_label_birthday);
        TextView artist_label_deathday = root.findViewById(R.id.artist_label_deathday);
        TextView artist_label_biography = root.findViewById(R.id.artist_label_biography);

        String url = "https://sandiego0615.wl.r.appspot.com/artist?id="+id;
        RequestQueue queue = Volley.newRequestQueue(root.getContext());

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
                            jsonObject = response;
                            root.findViewById(R.id.progressBar3).setVisibility(View.GONE);
                            root.findViewById(R.id.artist_table).setVisibility(View.VISIBLE);
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