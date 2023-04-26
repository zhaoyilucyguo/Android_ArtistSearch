package com.example.ArtistSearch.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ArtistSearch.R;
import com.example.ArtistSearch.adapters.FavoritesRecyclerViewAdapter;
import com.example.ArtistSearch.adapters.RecyclerViewAdapter;
import com.example.ArtistSearch.adapters.ViewPagerAdapter;
import com.example.ArtistSearch.model.Artist;
import com.example.ArtistSearch.model.Search;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.OnNoteListener{
    private List<Search> lstSearch;
    private RecyclerView recyclerView ;
    private LinearLayout spinner1;
    private LinearLayout spinner2;
    private MenuItem star;
    private MenuItem star_outline;
    private String id;
    private List<Artist> lstFavorites;
    private RecyclerView recyclerViewFavorites;
    private LinearLayout start;
    private LinearLayout search;
    private LinearLayout tabs;
    private TextView date;
    private String pageNum;
    private String prevPageNum;
    private ViewPager2 artistViewPager;
    private Toolbar toolbar;
    private ActionBar actionBar;
    private String homeTitle;
    private String tabsTitle;
    private SearchView searchView;
    private MenuItem menuItem;
    private LinearLayout no_search;
    private String searchQuery;
    private boolean showSearchQuery;


    private final String[] data = {"DETAILS", "ARTWORK"};
    private final int[] xmls = {R.drawable.alert_circle_outline, R.drawable.palette_outline};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinner1 = findViewById(R.id.progressBar1);
        spinner2 = findViewById(R.id.progressBar2);
        recyclerView = findViewById(R.id.recyclerviewid);
        recyclerViewFavorites = findViewById(R.id.favoriterecyclerviewid);
        start = findViewById(R.id.start);
        search = findViewById(R.id.search);
        tabs = findViewById(R.id.tabs);
        showPage("load", true);
        date = findViewById(R.id.date);
        artistViewPager = findViewById(R.id.artistViewPager);
        toolbar = findViewById(R.id.toolbar);
        no_search = findViewById(R.id.no_search);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        showSearchQuery = false;
//        getSupportActionBar().setHomeButtonEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setTitle("MyTitle");

        loadDate();
        loadLink();
        loadFavorites();


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                doBack();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }
    public void doBack() {
        if (pageNum.equals("search")) {
            showPage("start", false);
        }
        else if (pageNum.equals("tabs")) {
            showPage(prevPageNum, false);
        }
        else {
        }
    }


    private void showPage(String page, Boolean load) {
        if (star != null) {
            star.setVisible(false);
            star_outline.setVisible(false);
        }
        spinner1.setVisibility(View.GONE);
        spinner2.setVisibility(View.GONE);

        switch (page) {
            case "load":
                start.setVisibility(View.GONE);
                search.setVisibility(View.GONE);
                tabs.setVisibility(View.GONE);
                spinner2.setVisibility(View.VISIBLE);
                pageNum = "load";
                break;
            case "start":
                loadFavoritesAgn();
                start.setVisibility(View.VISIBLE);
                search.setVisibility(View.GONE);
                tabs.setVisibility(View.GONE);
                spinner1.setVisibility(View.GONE);
                if (menuItem != null) {
                    menuItem.setVisible(true);
                    if (showSearchQuery) {
                        menuItem.expandActionView();
                        searchView.setQuery(searchQuery, false);
                    }
                }
                toolbar.setTitle("Artist Search");
                actionBar.setDisplayHomeAsUpEnabled(false);
                pageNum = "start";
                showSearchQuery = false;
                break;
            case "search":
                start.setVisibility(View.GONE);
                search.setVisibility(View.VISIBLE);
                tabs.setVisibility(View.GONE);
                toolbar.setTitle(homeTitle);
                no_search.setVisibility(View.GONE);
                menuItem.setVisible(false);
                actionBar.setDisplayHomeAsUpEnabled(true);
                pageNum = "search";
                if (load) {
                    spinner1.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                } else {
                    spinner1.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }
                break;
            default:
                start.setVisibility(View.GONE);
                search.setVisibility(View.GONE);
                tabs.setVisibility(View.VISIBLE);
                toolbar.setTitle(tabsTitle);
                menuItem.setVisible(false);
                actionBar.setDisplayHomeAsUpEnabled(true);
                pageNum = "tabs";
                SharedPreferences mPrefs = getSharedPreferences("Favorites", MODE_PRIVATE);
                if (mPrefs.contains(id)) {
                    star.setVisible(true);
                } else {
                    star_outline.setVisible(true);
                }
                break;
        }
    }
    private void loadLink() {
        findViewById(R.id.link).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openURL = new Intent(Intent.ACTION_VIEW);
                openURL.setData(Uri.parse("https://www.artsy.net/"));
                startActivity(openURL);
            }
        });
    }
    private void loadDate(){
        long dates = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
        String dateString = sdf.format(dates);
        date.setText(dateString);
    }
    private void loadFavoritesAgn(){
        SharedPreferences mPrefs = getSharedPreferences("Favorites", MODE_PRIVATE);
        Map<String,?> keys = mPrefs.getAll();
        lstFavorites = new ArrayList<>();
        if (keys.size() == 0) {
            setuprecyclerviewfavoritesAgn(lstFavorites);
            return;
        }
        for(Map.Entry<String,?> entry : keys.entrySet()){
            String str = entry.getKey();
            String url = "https://sandiego0615.wl.r.appspot.com/artist?id="+str;
            RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                Artist artist = new Artist();
                                artist.setName(response.getString("name"));
                                artist.setNationality(response.getString("nationality"));
                                artist.setBirthday(response.getString("birthday"));
                                artist.setId(str);
                                lstFavorites.add(artist);
                                if (keys.size() == lstFavorites.size()) {
                                    setuprecyclerviewfavoritesAgn(lstFavorites);
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
            queue.add(jsonObjectRequest);
        }
    }
    private void setuprecyclerviewfavoritesAgn(List<Artist> lstFavorites) {
        FavoritesRecyclerViewAdapter myadapter = new FavoritesRecyclerViewAdapter(this, lstFavorites, this::onNoteClickFav);
        recyclerViewFavorites.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewFavorites.setAdapter(myadapter);
    }
    private void loadFavorites(){
        SharedPreferences mPrefs = getSharedPreferences("Favorites", MODE_PRIVATE);
        Map<String,?> keys = mPrefs.getAll();
        lstFavorites = new ArrayList<>();
        if (keys.size() == 0) {
            setuprecyclerviewfavorites(lstFavorites);
            showPage("start", false);
            return;
        }
        for(Map.Entry<String,?> entry : keys.entrySet()){
            String str = entry.getKey();
            String url = "https://sandiego0615.wl.r.appspot.com/artist?id="+str;
            RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                Artist artist = new Artist();
                                artist.setName(response.getString("name"));
                                artist.setNationality(response.getString("nationality"));
                                artist.setBirthday(response.getString("birthday"));
                                artist.setId(str);
                                lstFavorites.add(artist);
                                if (keys.size() == lstFavorites.size()) {
                                    setuprecyclerviewfavorites(lstFavorites);
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
            queue.add(jsonObjectRequest);
        }
    }
    private void setuprecyclerviewfavorites(List<Artist> lstFavorites) {
        FavoritesRecyclerViewAdapter myadapter = new FavoritesRecyclerViewAdapter(this, lstFavorites, this::onNoteClickFav);
        recyclerViewFavorites.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewFavorites.setAdapter(myadapter);
        showPage("start", false);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem.OnActionExpandListener onActionExpandListener = new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                return true;

            }
        };
        star = menu.findItem(R.id.star);
        star_outline = menu.findItem(R.id.star_outline);
        star.setVisible(false);
        star_outline.setVisible(false);
        star.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener(){
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                star.setVisible(false);
                star_outline.setVisible(true);
                SharedPreferences mPrefs = getSharedPreferences("Favorites", MODE_PRIVATE);
                mPrefs.edit().remove(id).commit();
                String url = "https://sandiego0615.wl.r.appspot.com/artist?id="+id;
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                        (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    Toast.makeText(MainActivity.this,response.getString("name")+" is removed from favorites", Toast.LENGTH_SHORT).show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                            }
                        });
                queue.add(jsonObjectRequest);
                return true;
            }
        });
        star_outline.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener(){
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                star.setVisible(true);
                star_outline.setVisible(false);
                SharedPreferences mPrefs = getSharedPreferences("Favorites", MODE_PRIVATE);
                SharedPreferences.Editor prefsEditor = mPrefs.edit();
                prefsEditor.putString(id, id);
                prefsEditor.commit();
                String url = "https://sandiego0615.wl.r.appspot.com/artist?id="+id;
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                        (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    Toast.makeText(MainActivity.this,response.getString("name")+" is added to favorites", Toast.LENGTH_SHORT).show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                            }
                        });
                queue.add(jsonObjectRequest);
                return true;
            }
        });
        menuItem = menu.findItem(R.id.action_search);
        menuItem.setOnActionExpandListener(onActionExpandListener);
        searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search...");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                lstSearch = new ArrayList<>();
                homeTitle = query.toUpperCase();
                searchQuery = query;
                showSearchQuery = true;
                showPage("search", true);
                jsonrequest(query);
                menuItem.collapseActionView();
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
    private void jsonrequest(String query){
        String JSON_URL = "https://sandiego0615.wl.r.appspot.com/search?name=";
        JsonArrayRequest request = new JsonArrayRequest(JSON_URL+query, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                if (response.length() == 0) {
                    spinner1.setVisibility(View.GONE);
                    no_search.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }
                else {
                    for (int i=0; i < response.length(); i++) {
                        try {
                            jsonObject = response.getJSONObject(i);
                            String og_type = jsonObject.getString("og_type");
                            if (og_type.equals("artist")) {
                                Search search = new Search();
                                search.setOg_type(og_type);
                                search.setTitle(jsonObject.getString("title"));
                                JSONObject links = jsonObject.getJSONObject("_links");
                                String imgHref = links.getJSONObject("thumbnail").getString("href");
                                Log.d("hello", "onResponse: "+imgHref.contains("missing_image.png"));
                                if (!imgHref.contains("missing_image.png")){
                                    search.setImg_href(imgHref);
                                }

                                search.setId_href(links.getJSONObject("self").getString("href"));
                                lstSearch.add(search);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }


                    setuprecyclerview(lstSearch);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(request);
    }
    private void setuprecyclerview(List<Search> lstSearch) {
        RecyclerViewAdapter myadapter = new RecyclerViewAdapter(this, lstSearch, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myadapter);
        prevPageNum = "start";
        showPage("search", false);

    }

    @Override
    public void onNoteClick(int position) {
        String url = lstSearch.get(position).getId_href();
        int ind = url.lastIndexOf("/");
        id = url.substring(ind + 1);
        tabsTitle = lstSearch.get(position).getTitle();
        prevPageNum = "search";
        showPage("tabs", true);
        loadTabs();
    }

    public void onNoteClickFav(int position) {
        id = lstFavorites.get(position).getId();
        tabsTitle = lstFavorites.get(position).getName();
        prevPageNum = "start";
        showPage("tabs", true);
        loadTabs();
    }
    private void loadTabs(){
        TabLayout artistTabLayout = findViewById(R.id.artistTabLayout);
        ViewPagerAdapter adapter = new ViewPagerAdapter(this, id);
        artistViewPager.setAdapter(adapter);


        new TabLayoutMediator(artistTabLayout, artistViewPager,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        tab.setText(data[position]);
                        tab.setIcon(xmls[position]);
                        int colorFilter = ContextCompat.getColor(MainActivity.this , R.color.orange);;
                        tab.getIcon().setColorFilter(colorFilter, PorterDuff.Mode.SRC_IN);
                    }
                }).attach();

    }
}