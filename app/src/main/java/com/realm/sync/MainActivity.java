package com.realm.sync;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.realm.sync.adapter.ListViewAdapter;
import com.realm.sync.data.RealmManager;
import com.realm.sync.model.UserObject;
import com.realm.sync.network.EndpointsEnum;

import java.util.Collections;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;

public class MainActivity extends AppCompatActivity {

    private ListViewAdapter listViewAdapter;
    private RealmSync loader;
    private Realm realm;
    private UserObject realmUser;
    private SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initDB();

        createUser();

        initUI();

        loader = (RealmSync) getLoaderManager().initLoader(R.id.string_loader_id,
                null, loaderCallbacks);
    }

    private void initDB() {
        RealmManager.getInstance(getApplicationContext());
        realm = RealmManager.getRealm();
    }

    private void initUI() {
        ListView listView = (ListView) findViewById(R.id.listView);
        listViewAdapter = new ListViewAdapter(this);
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeLayout);
        listView.setAdapter(listViewAdapter);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loader.loadNewData();
            }
        });

        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        swipeContainer.setRefreshing(true);

    }

    public LoaderManager.LoaderCallbacks<List<RealmObject>> loaderCallbacks =
            new LoaderManager.LoaderCallbacks<List<RealmObject>>() {

                public Loader<List<RealmObject>> onCreateLoader(int id, Bundle args) {
                    return new RealmSync(MainActivity.this,
                            realmUser,
                            EndpointsEnum.Endpoints.OPEN_WEATHER,
                            Request.Method.GET, null);
                }

                @Override
                public void onLoadFinished(Loader<List<RealmObject>> loader,
                                           List<RealmObject> data) {
                    if (data.size() == 0) {
                        listViewAdapter.swapData(Collections.<RealmObject>emptyList());
                        Toast.makeText(MainActivity.this, getResources().getString(R.string.refresh_error), Toast.LENGTH_LONG).show();
                    } else {
                        listViewAdapter.swapData(data);
                    }
                    swipeContainer.setRefreshing(false);
                }

                @Override
                @SuppressWarnings("all")
                public void onLoaderReset(Loader<List<RealmObject>> loader) {
                    loader = null;
                }
            };


    private void createUser() {
        realm.beginTransaction();
        realmUser = new UserObject();
        realmUser.setUserName(getResources().getString(R.string.user_name));
        realmUser.setAuthToken(getResources().getString(R.string.auth));
        realm.commitTransaction();
    }
}
