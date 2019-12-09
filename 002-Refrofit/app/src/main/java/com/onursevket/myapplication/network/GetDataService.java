package com.onursevket.myapplication.network;

import com.onursevket.myapplication.model.RetrofitPhoto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetDataService {

    @GET("/photos")
    Call<List<RetrofitPhoto>> getAllPhotos();

}
