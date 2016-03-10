package com.nbs.samplepaymentapp.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Sidiq on 10/03/2016.
 */
public class CreditCardPayment {
    @SerializedName("status")
    public String status;

    @SerializedName("code")
    public int code;

    @SerializedName("data")
    public CreditCardPaymentData data;
}
