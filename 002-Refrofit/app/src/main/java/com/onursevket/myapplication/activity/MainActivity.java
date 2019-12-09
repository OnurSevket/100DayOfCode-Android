package com.onursevket.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Callback;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.onursevket.myapplication.R;
import com.onursevket.myapplication.adapter.CustomAdapter;
import com.onursevket.myapplication.model.RetrofitPhoto;
import com.onursevket.myapplication.network.GetDataService;
import com.onursevket.myapplication.network.RetrofitClientInstance;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private CustomAdapter adapter;
    private RecyclerView recyclerView;
    ProgressDialog progressDoalog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDoalog = new ProgressDialog(MainActivity.this);
        progressDoalog.setMessage("Loading....");
        progressDoalog.show();

        /*Create handle for the RetrofitInstance interface*/
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

        Call<List<RetrofitPhoto>> call = service.getAllPhotos();
        call.enqueue(new Callback<List<RetrofitPhoto>>() {

            @Override
            public void onResponse(Call<List<RetrofitPhoto>> call, Response<List<RetrofitPhoto>> response) {
                progressDoalog.dismiss();
                generateDataList(response.body());
            }

            @Override
            public void onFailure(Call<List<RetrofitPhoto>> call, Throwable t) {
                progressDoalog.dismiss();
                Toast.makeText(MainActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*Method to generate List of data using RecyclerView with custom adapter*/
    private void generateDataList(List<RetrofitPhoto> photoList) {
        recyclerView = findViewById(R.id.customRecyclerView);
        adapter = new CustomAdapter(this,photoList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}
