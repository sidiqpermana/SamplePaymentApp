package com.nbs.samplepaymentapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Sidiq on 10/03/2016.
 */
public class VeritransTransfer implements Parcelable {
    @SerializedName("status_code")
    @Expose
    public String statusCode;
    @SerializedName("status_message")
    @Expose
    public String statusMessage;
    @SerializedName("transaction_id")
    @Expose
    public String transactionId;
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
    @SerializedName("permata_va_number")
    @Expose
    public String permataVaNumber;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.statusCode);
        dest.writeString(this.statusMessage);
        dest.writeString(this.transactionId);
        dest.writeString(this.orderId);
        dest.writeString(this.grossAmount);
        dest.writeString(this.paymentType);
        dest.writeString(this.transactionTime);
        dest.writeString(this.transactionStatus);
        dest.writeString(this.permataVaNumber);
    }

    public VeritransTransfer() {
    }

    protected VeritransTransfer(Parcel in) {
        this.statusCode = in.readString();
        this.statusMessage = in.readString();
        this.transactionId = in.readString();
        this.orderId = in.readString();
        this.grossAmount = in.readString();
        this.paymentType = in.readString();
        this.transactionTime = in.readString();
        this.transactionStatus = in.readString();
        this.permataVaNumber = in.readString();
    }

    public static final Parcelable.Creator<VeritransTransfer> CREATOR = new Parcelable.Creator<VeritransTransfer>() {
        public VeritransTransfer createFromParcel(Parcel source) {
            return new VeritransTransfer(source);
        }

        public VeritransTransfer[] newArray(int size) {
            return new VeritransTransfer[size];
        }
    };
}
