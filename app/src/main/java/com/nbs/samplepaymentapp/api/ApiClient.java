package com.nbs.samplepaymentapp.api;

import com.nbs.samplepaymentapp.model.BankDestination;
import com.nbs.samplepaymentapp.model.CreditCardPayment;
import com.nbs.samplepaymentapp.model.RequestTokenPayment;
import com.nbs.samplepaymentapp.model.VeritransTransfer;

import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by Sidiq on 10/03/2016.
 */
public interface ApiClient {
    @FormUrlEncoded
    @POST("/api/public/basic/topup/veritrans/va")
    Call<VeritransTransfer> postAutomaticVeritransTransfer(@Field("passenger_id") String id, @Field("amount") int amount);

    @GET("/api/public/basic/topup/manual_transfer")
    Call<BankDestination> getBankDestination();

    @GET("/api/public/basic/payment/veritrans/cc")
    Call<RequestTokenPayment> getTokenPayment(@Query("passenger_id") int passengerId);

    @FormUrlEncoded
    @POST("/api/public/basic/payment/veritrans/cc")
    Call<CreditCardPayment> postChargingVeritrans(@Field("passenger_id") int passengerId,
                                                  @Field("order_id") int orderId,
                                                  @Field("hash") String hash,
                                                  @Field("price") int price,
                                                  @Field("token_id") String tokenId);
}
