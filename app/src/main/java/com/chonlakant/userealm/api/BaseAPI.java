package com.chonlakant.userealm.api;

import com.chonlakant.userealm.model.Model;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public interface BaseAPI {

    @GET("dedek")
    Call<List<Model>> getPhotoList();


}
