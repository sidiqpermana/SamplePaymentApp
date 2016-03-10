package com.nbs.samplepaymentapp.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Sidiq on 10/03/2016.
 */
public class RequestTokenPayment {
    @SerializedName("code")
    public int code;

    @SerializedName("status")
    public String status;

    @SerializedName("data")
    public RequestTokenPaymentData data;
}
