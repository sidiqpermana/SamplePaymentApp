package com.nbs.samplepaymentapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Sidiq on 10/03/2016.
 */
public class BankDestination {
    @SerializedName("code")
    public int code;

    @SerializedName("status")
    public String status;

    @SerializedName("data")
    public ArrayList<BankItem> listItem = new ArrayList<>();
}
