package com.nbs.samplepaymentapp.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.nbs.samplepaymentapp.R;
import com.nbs.samplepaymentapp.util.Util;
import com.nbs.samplepaymentapp.base.BaseActivity;
import com.nbs.samplepaymentapp.model.VeritransTransfer;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class AutomaticTransferConfirmationActivity extends BaseActivity {

    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_email)
    TextView tvEmail;
    @Bind(R.id.tv_phone)
    TextView tvPhone;
    @Bind(R.id.tv_amount)
    TextView tvAmount;
    @Bind(R.id.tv_description_no_a)
    TextView tvDescriptionNoA;
    @Bind(R.id.tv_description_no_b)
    TextView tvDescriptionNoB;
    @Bind(R.id.btn_submit)
    Button btnSubmit;

    private int amount;
    public static String AMOUNT = "amount";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_automatic_transfer_confirmation);

        ButterKnife.bind(this);

        getSupportActionBar().setTitle("Top Up Confirmation");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        amount = getIntent().getIntExtra(AMOUNT, 0);

        String descriptionA = "After Clicking on the \"SUBMIT TOPUP\" button below, you will be shown JET virtual account information.";
        String descriptionB = "Please complete the transaction as started in \"Top Up Amount\" field above within 24 hours after the virtual account information is generated";

        tvDescriptionNoA.setText(descriptionA);
        tvDescriptionNoB.setText(descriptionB);
        tvAmount.setText(Util.getCurrency(amount));
    }

    @OnClick(R.id.btn_submit)
    public void onBtnSubmitClicked(){
        callVeritransTransferAsync("1", amount);
    }

    private void callVeritransTransferAsync(String userId, int amount) {
        final ProgressDialog mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("Submit Payment");
        mProgressDialog.setMessage("Please wait...");
        mProgressDialog.show();

        Call<VeritransTransfer> request = apiClient.postAutomaticVeritransTransfer(userId, amount);
        request.enqueue(new Callback<VeritransTransfer>() {
            @Override
            public void onResponse(Response<VeritransTransfer> response, Retrofit retrofit) {
                mProgressDialog.dismiss();

                parsingPaymentResponse(response);
            }

            @Override
            public void onFailure(Throwable t) {
                mProgressDialog.dismiss();
                Toast.makeText(AutomaticTransferConfirmationActivity.this,
                        t.getMessage().toString().trim() + " Please try again", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void parsingPaymentResponse(Response<VeritransTransfer> response) {
        VeritransTransfer mVeritransTransfer = response.body();
        if (Integer.parseInt(mVeritransTransfer.statusCode) == 201){
            AutomaticTransferFinishActivity.start(this, mVeritransTransfer);
            finish();
        }else{
            Toast.makeText(AutomaticTransferConfirmationActivity.this,
                    mVeritransTransfer.statusMessage, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public static void start(Activity context, int mAmount) {
        Intent starter = new Intent(context, AutomaticTransferConfirmationActivity.class);
        starter.putExtra(AMOUNT, mAmount);
        context.startActivityForResult(starter, 0);
    }
}
