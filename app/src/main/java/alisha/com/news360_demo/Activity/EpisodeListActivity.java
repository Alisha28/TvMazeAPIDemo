package alisha.com.news360_demo.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import alisha.com.news360_demo.Adapter.ItemAdapter;
import alisha.com.news360_demo.App.AppController;
import alisha.com.news360_demo.Model.Item;
import alisha.com.news360_demo.R;

public class EpisodeListActivity extends AppCompatActivity {
    private String TAG = EpisodeListActivity.class.getSimpleName();
    private static final String endpoint ="http://api.tvmaze.com/shows/82/episodes";
    private ArrayList<Item> itemArrayList;
    private RecyclerView recyclerView;
    private ItemAdapter mAdapter;
    private ProgressBar progressBar;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.episode_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progressBar = (ProgressBar) findViewById(R.id.circular_progress_bar);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        itemArrayList = new ArrayList<>();

        linearLayoutManager = new LinearLayoutManager(EpisodeListActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);

        mAdapter = new ItemAdapter(itemArrayList, EpisodeListActivity.this);
        recyclerView.setAdapter(mAdapter);

        //setting divider for recyclerView
        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(
                recyclerView.getContext(),
                linearLayoutManager.getOrientation()
        );
        recyclerView.addItemDecoration(mDividerItemDecoration);
        //request to API to get episode list
        fetchItems();
    }

    public void fetchItems() {
        itemArrayList.clear();
        JsonArrayRequest req = new JsonArrayRequest(endpoint,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        GsonBuilder builder = new GsonBuilder();
                        Gson mGson = builder.create();
                        List<Item> gsonArrayList = new ArrayList<Item>();
                        gsonArrayList = Arrays.asList(mGson.fromJson(String.valueOf(response), Item[].class));

                        Toast.makeText(EpisodeListActivity.this, "Refreshed!", Toast.LENGTH_SHORT).show();
                        //Setting RecyclerView attributes
                        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(linearLayoutManager);
                        mAdapter = new ItemAdapter(gsonArrayList, EpisodeListActivity.this);
                        recyclerView.setAdapter(mAdapter);
                        progressBar.setVisibility(View.GONE);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i(TAG, error.getMessage());
                Toast.makeText(getApplicationContext(),
                        "Something is wrong!Please check your Internet connection.", Toast.LENGTH_SHORT).show();
            }
        });
        // Access the RequestQueue through your singleton class.
        AppController.getInstance().addToRequestQueue(req);

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
            fetchItems();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
