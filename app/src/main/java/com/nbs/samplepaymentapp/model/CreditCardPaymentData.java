package com.nbs.samplepaymentapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Sidiq on 10/03/2016.
 */
public class CreditCardPaymentData {
    @SerializedName("status_code")
    @Expose
    public String statusCode;
    @SerializedName("status_message")
    @Expose
    public String statusMessage;
    @SerializedName("transaction_id")
    @Expose
    public String transactionId;
    @SerializedName("masked_card")
    @Expose
    public String maskedCard;
    @SerializedName("order_id")
    @Expose
    public String orderId;
    @SerializedName("gross_amount")
    @Expose
    public String grossAmount;
    @SerializedName("payment_type")
    @Expose
    public String paymentType;
    @SerializedName("transaction_time")
    @Expose
    public String transactionTime;
    @SerializedName("transaction_status")
    @Expose
    public String transactionStatus;
    @SerializedName("fraud_status")
    @Expose
    public String fraudStatus;
    @SerializedName("approval_code")
    @Expose
    public String approvalCode;
    @SerializedName("bank")
    @Expose
    public String bank;
}
