package com.chonlakant.userealm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.chonlakant.userealm.adapter.MainAdapter;
import com.chonlakant.userealm.api.BaseAPI;
import com.chonlakant.userealm.model.Model;
import com.chonlakant.userealm.service.Client;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    RealmConfiguration realmConfig;
    private Realm realm;
    List<Model> listApi = new ArrayList<>();

    MainAdapter adapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listview);

        realmConfig = new RealmConfiguration.Builder(this).build();
        Realm.setDefaultConfiguration(realmConfig);
        realm = Realm.getDefaultInstance();


        RealmResults<Model> photos = realm.where(Model.class).findAll();
        if (photos.size() <= 0) {
            loadPhoto();
        } else {

            MainAdapter adapter = new MainAdapter(MainActivity.this, photos);
            listView.setAdapter(adapter);
            listView.setVisibility(View.VISIBLE);

        }
    }

    private void loadPhoto() {

        BaseAPI service = Client.getClient().create(BaseAPI.class);

        Call<List<Model>> photoList = service.getPhotoList();
        photoList.enqueue(new Callback<List<Model>>() {
            @Override
            public void onResponse(Call<List<Model>> call, Response<List<Model>> response) {


                for (int i = 0; i < response.body().size(); i++) {
                    listApi.add(response.body().get(i));
                    adapter = new MainAdapter(MainActivity.this, listApi);
                    listView.setAdapter(adapter);
                    Log.e("photo", response.body().get(i).getFoto());

                    realm.beginTransaction();
                    Model modelPhoto = realm.createObject(Model.class);
                    modelPhoto.setNama(response.body().get(i).getNama());
                    modelPhoto.setFoto(response.body().get(i).getFoto());
                    realm.commitTransaction();


                }
            }

            @Override
            public void onFailure(Call<List<Model>> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
