package com.example.ArtistSearch;

import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.ArtistSearch.adapters.ArtworksRecyclerViewAdapter;
import com.example.ArtistSearch.model.Artworks;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ArtworksFragment extends Fragment {

    private String id;
    private List<Artworks> lstArtworks;
    private View root;
    private LinearLayout no_artwork;
    private RecyclerView artworksrecyclerviewid;
    public ArtworksFragment(String id) {
        this.id=id;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_artworks, container, false);
        no_artwork = root.findViewById(R.id.no_artwork);
        artworksrecyclerviewid = root.findViewById(R.id.artworksrecyclerviewid);
        no_artwork.setVisibility(View.GONE);
        artworksrecyclerviewid.setVisibility((View.GONE));
        jsonRequest(id);
        return root;
    }
    private void jsonRequest(String id) {
        String url = "https://pasadena0617.wl.r.appspot.com/artworks?id=";
        lstArtworks = new ArrayList<>();

        JsonArrayRequest request  = new JsonArrayRequest(url + id, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response.length() == 0) {
                    no_artwork.setVisibility(View.VISIBLE);
                }
                else {
                    artworksrecyclerviewid.setVisibility((View.VISIBLE));
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
                }}

                setuprecyclerview();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(root.getContext());
        requestQueue.add(request);
    }
    private void setuprecyclerview() {
        RecyclerView recyclerViewArtworks = root.findViewById(R.id.artworksrecyclerviewid);
        ArtworksRecyclerViewAdapter myadapter = new ArtworksRecyclerViewAdapter(root.getContext(), lstArtworks, this::onNoteClick);
        recyclerViewArtworks.setLayoutManager(new LinearLayoutManager(root.getContext()));
        recyclerViewArtworks.setAdapter(myadapter);
    }

    public void onNoteClick(int position) {
        String geneID = lstArtworks.get(position).getId();
        setGene(geneID);
    }
    private void setGene(String geneID){
        // custom dialog
        final Dialog dialog = new Dialog(root.getContext());
        dialog.setContentView(R.layout.gene_row_item);
        TextView gene_name = dialog.findViewById(R.id.gene_name);
        TextView gene_description = dialog.findViewById(R.id.gene_description);
        ImageView gene_href = dialog.findViewById(R.id.gene_href);
        TableRow gene_header =  dialog.findViewById(R.id.gene_header);
        TableRow gene_row = dialog.findViewById(R.id.gene_row);
        LinearLayout no_content = dialog.findViewById(R.id.no_content);


        gene_header.setVisibility(View.GONE);
        gene_row.setVisibility(View.GONE);
        no_content.setVisibility(View.GONE);
        String url = "https://pasadena0617.wl.r.appspot.com/genes?id=" + geneID;
        RequestQueue queue = Volley.newRequestQueue(root.getContext());
        RequestOptions option = new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape_artworks).error(R.drawable.loading_shape_artworks);

        JsonArrayRequest request  = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    if (response.length() == 0) {
                        gene_header.setVisibility(View.GONE);
                        gene_row.setVisibility(View.GONE);
                        no_content.setVisibility(View.VISIBLE);
                    }
                    else {
                        gene_header.setVisibility(View.VISIBLE);
                        gene_row.setVisibility(View.VISIBLE);
                        no_content.setVisibility(View.GONE);
                        JSONObject jsonObject = response.getJSONObject(0);
                        gene_name.setText(jsonObject.getString("name"));
                        gene_description.setText(jsonObject.getString("description"));
                        Glide.with(root.getContext()).load(jsonObject
                                .getJSONObject("_links")
                                .getJSONObject("thumbnail")
                                .getString("href")).apply(option).into(gene_href);
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
        queue.add(request);
        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();


    }
}