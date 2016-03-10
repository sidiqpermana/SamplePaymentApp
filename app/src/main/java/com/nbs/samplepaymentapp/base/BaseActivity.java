package com.nbs.samplepaymentapp.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.nbs.samplepaymentapp.api.ApiClient;
import com.nbs.samplepaymentapp.api.ApiService;

/**
 * Created by Sidiq on 10/03/2016.
 */
public class BaseActivity extends AppCompatActivity{
    public ApiClient apiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        apiClient = ApiService.createService(ApiClient.class);
    }
}
