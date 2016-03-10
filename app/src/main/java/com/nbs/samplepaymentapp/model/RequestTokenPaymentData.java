package com.nbs.samplepaymentapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Sidiq on 10/03/2016.
 */
public class RequestTokenPaymentData implements Parcelable {
    @SerializedName("order_id")
    public int orderId;

    @SerializedName("amount")
    public int amount;

    @SerializedName("hash")
    public String hash;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.orderId);
        dest.writeInt(this.amount);
        dest.writeString(this.hash);
    }

    public RequestTokenPaymentData() {
    }

    protected RequestTokenPaymentData(Parcel in) {
        this.orderId = in.readInt();
        this.amount = in.readInt();
        this.hash = in.readString();
    }

    public static final Parcelable.Creator<RequestTokenPaymentData> CREATOR = new Parcelable.Creator<RequestTokenPaymentData>() {
        public RequestTokenPaymentData createFromParcel(Parcel source) {
            return new RequestTokenPaymentData(source);
        }

        public RequestTokenPaymentData[] newArray(int size) {
            return new RequestTokenPaymentData[size];
        }
    };
}
