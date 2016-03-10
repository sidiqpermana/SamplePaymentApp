package com.nbs.samplepaymentapp.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.nbs.samplepaymentapp.R;
import com.nbs.samplepaymentapp.base.BaseActivity;
import com.nbs.samplepaymentapp.model.RequestTokenPayment;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends BaseActivity {
    @Bind(R.id.lv_payment_options)
    ListView lvPaymentOptions;

    private String[] options = new String[]{
            "Transfer Manual",
            "Automatic Transfer Via Veritrans",
            "Credit Card Payment Via Veritrans",
            "[Fucking] eCash Mandiri Payment"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        getSupportActionBar().setTitle("Payment Options");

        lvPaymentOptions.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                android.R.id.text1, options));
        lvPaymentOptions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        ManualBankTransferActivity.start(MainActivity.this);
                        break;

                    case 1:
                        AutomaticTransferActivity.start(MainActivity.this);
                        break;

                    case 2:
                        callRequestCreditPaymentAsyn();
                        break;
                }
            }
        });
    }

    private void callRequestCreditPaymentAsyn() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.common_message_please_wait));
        progressDialog.show();

        Call<RequestTokenPayment> getPaymentToken = apiClient.getTokenPayment(1);
        getPaymentToken.enqueue(new Callback<RequestTokenPayment>() {
            @Override
            public void onResponse(Response<RequestTokenPayment> response, Retrofit retrofit) {
                progressDialog.dismiss();
                parsingResponse(response);
            }

            @Override
            public void onFailure(Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, t.getMessage().toString().trim(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void parsingResponse(Response<RequestTokenPayment> response) {
        RequestTokenPayment res = response.body();
        if (res.code == 200){
            CreditCardPaymentActivity.start(MainActivity.this, res.data);
        }else{
            Toast.makeText(MainActivity.this, res.status, Toast.LENGTH_LONG).show();
        }
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, MainActivity.class);
        starter.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(starter);
    }
}
