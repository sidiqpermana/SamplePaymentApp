package com.nbs.samplepaymentapp.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alihafizji.library.CreditCardEditText;
import com.nbs.samplepaymentapp.R;
import com.nbs.samplepaymentapp.base.BaseActivity;
import com.nbs.samplepaymentapp.model.CreditCardPayment;
import com.nbs.samplepaymentapp.model.RequestTokenPaymentData;
import com.nbs.samplepaymentapp.model.Veritrans;
import com.nbs.samplepaymentapp.util.Constant;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import id.co.veritrans.android.api.VTDirect;
import id.co.veritrans.android.api.VTInterface.ITokenCallback;
import id.co.veritrans.android.api.VTModel.VTCardDetails;
import id.co.veritrans.android.api.VTModel.VTToken;
import id.co.veritrans.android.api.VTUtil.VTConfig;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class CreditCardPaymentActivity extends BaseActivity {

    @Bind(R.id.txt_veritrans_text)
    TextView txtVeritransText;
    @Bind(R.id.edt_cardno)
    CreditCardEditText edtCardno;
    @Bind(R.id.spn_expiration_month)
    Spinner spnExpirationMonth;
    @Bind(R.id.spn_expiration_year)
    Spinner spnExpirationYear;
    @Bind(R.id.edt_ccv_code)
    EditText edtCcvCode;
    @Bind(R.id.ln_veritrans_form)
    LinearLayout lnVeritransForm;
    @Bind(R.id.btn_confirm_payment)
    Button btnConfirmPayment;

    private ArrayList<String> years;
    private int amount = 50000;
    private int orderId = 123;
    private int passengerId = 1;
    private Veritrans veritrans;
    private RequestTokenPaymentData requestTokenPaymentData;
    public static String PAYMENT_DATA = "payment_data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_card_payment);

        ButterKnife.bind(this);

        getSupportActionBar().setTitle("Credit Card Payment");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        requestTokenPaymentData = getIntent().getParcelableExtra(PAYMENT_DATA);

        String[] months = new String[]{
                "01", "02", "03", "04", "05", "06",
                "07", "08", "09", "10", "11", "12"
        };
        years = new ArrayList<>();

        for (int i = 2018; i < 2025; i++){
            years.add(String.valueOf(i));
        }

        ArrayAdapter<String> monthAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, android.R.id.text1, months);
        ArrayAdapter<String> yearAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, android.R.id.text1, years);

        spnExpirationMonth.setAdapter(monthAdapter);
        spnExpirationYear.setAdapter(yearAdapter);

        btnConfirmPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cardNo = edtCardno.getText().toString().trim();
                String ccvCode = edtCcvCode.getText().toString().trim();
                String month = String.valueOf(spnExpirationMonth.getSelectedItemPosition() + 1);
                String year = years.get(spnExpirationYear.getSelectedItemPosition()).toString().trim();

                if (cardNo.equals("") || ccvCode.equals("")
                        || month.equals("") || year.equals("")) {
                    Toast.makeText(CreditCardPaymentActivity.this,
                            "Field harus terisi semua", Toast.LENGTH_LONG).show();
                } else {
                    veritrans = new Veritrans();
                    veritrans.setCardNo(cardNo);
                    veritrans.setCcvCode(ccvCode);
                    veritrans.setExpirationMonth(month);
                    veritrans.setExpirationYear(year);

                    postToVeritrans(veritrans);
                }
            }
        });
    }

    private void postToVeritrans(final Veritrans veritrans) {
        VTConfig.VT_IsProduction = false;
        VTConfig.CLIENT_KEY = Constant.VERITRANS_KEY;

        //Create VTDirect Object. It’s config is Based on VTConfig Static Class
        VTDirect vtDirect = new VTDirect();
        //Create VTCardDetails Object to be set to VTDirect Object
        VTCardDetails cardDetails = new VTCardDetails();
        cardDetails.setCard_number(veritrans.getCardNo().replace(" ", "").trim()); // 3DS Dummy CC
        cardDetails.setCard_cvv(veritrans.getCcvCode());
        cardDetails.setCard_exp_month(Integer.parseInt(veritrans.getExpirationMonth()));
        cardDetails.setCard_exp_year(Integer.parseInt(veritrans.getExpirationYear()));
        cardDetails.setSecure(false);
        cardDetails.setGross_amount(String.valueOf(amount));

        final ProgressDialog mProgressDialog = new ProgressDialog(CreditCardPaymentActivity.this);
        mProgressDialog.setTitle("Connect to Veritrans");
        mProgressDialog.setMessage(getString(R.string.common_message_please_wait));
        mProgressDialog.show();

        //Set VTCardDetails to VTDirect
        vtDirect.setCard_details(cardDetails);

        //Simply Call getToken function and put your callback to handle data
        vtDirect.getToken(new ITokenCallback() {
            @Override
            public void onSuccess(VTToken token) {
                mProgressDialog.dismiss();
                //use token anyhow you want, maybe send it to your server. Example here to check whether you are using 3dsecure feature or not.
                if (token.getRedirect_url() != null) {

                    //using 3d secure
                    WebView webView = new
                            WebView(CreditCardPaymentActivity.this);

                    webView.getSettings().setJavaScriptEnabled(true);
                    webView.setOnTouchListener(new View.OnTouchListener() {

                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            switch (event.getAction()) {
                                case MotionEvent.ACTION_DOWN:
                                case MotionEvent.ACTION_UP:
                                    if (!v.hasFocus()) {
                                        v.requestFocus();
                                    }
                                    break;
                            }
                            return false;
                        }
                    });
                    webView.loadUrl(token.getRedirect_url());
                }
                //print or send token
                Log.d("TOKEN_VERITRANS", "Token " + token.getToken_id());
                postChargingVeritransPayment(veritrans, token);
                //new SendTokenAsync(token.getToken_id(), String.valueOf(totalPayment), order).execute();
            }

            @Override
            public void onError(Exception e) {
                mProgressDialog.dismiss();
                Toast.makeText(CreditCardPaymentActivity.this, e != null ? e.getMessage().toString()
                        : "Couldn't connect to payment provider", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void postChargingVeritransPayment(Veritrans veritrans, VTToken token) {
        final ProgressDialog mProgressDialog = new ProgressDialog(CreditCardPaymentActivity.this);
        mProgressDialog.setTitle("Processing Payment");
        mProgressDialog.setMessage(getString(R.string.common_message_please_wait));
        mProgressDialog.show();

        Call<CreditCardPayment> postCreditCardPaymentAsync = apiClient.postChargingVeritrans(passengerId, orderId,
                requestTokenPaymentData.hash, amount,
                token.getToken_id());
        postCreditCardPaymentAsync.enqueue(new Callback<CreditCardPayment>() {
            @Override
            public void onResponse(Response<CreditCardPayment> response, Retrofit retrofit) {
                mProgressDialog.dismiss();
                parsingResponse(response);
            }

            @Override
            public void onFailure(Throwable t) {
                mProgressDialog.dismiss();
                Toast.makeText(CreditCardPaymentActivity.this, t.getMessage().toString().trim(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void parsingResponse(Response<CreditCardPayment> response) {
        CreditCardPayment res = response.body();
        if (res.status.equalsIgnoreCase("success")){
            showPaymentStatusDialog(true);
        }else{
            showPaymentStatusDialog(false);
        }
    }

    private void showPaymentStatusDialog(final boolean isSuccess){
        String title = "Payment Status";
        String message = null;
        String btnTitle = null;

        if (isSuccess){
            message = "Payment Success!";
            btnTitle = "OK";
        }else{
            message = "Payment Failed!";
            btnTitle = "Retry";
        }

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(btnTitle, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (isSuccess) {
                            finish();
                            MainActivity.start(CreditCardPaymentActivity.this);
                        } else {
                            postToVeritrans(veritrans);
                        }
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public static void start(Activity context, RequestTokenPaymentData data) {
        Intent starter = new Intent(context, CreditCardPaymentActivity.class);
        starter.putExtra(PAYMENT_DATA, data);
        context.startActivityForResult(starter, 0);
    }
}
