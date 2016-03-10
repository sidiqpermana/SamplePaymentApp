package com.nbs.samplepaymentapp.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nbs.samplepaymentapp.R;
import com.nbs.samplepaymentapp.base.BaseActivity;
import com.nbs.samplepaymentapp.model.BankDestination;
import com.nbs.samplepaymentapp.model.BankItem;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class ManualBankTransferActivity extends BaseActivity {

    @Bind(R.id.ln_banks)
    LinearLayout lnBanks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_bank_transfer);

        ButterKnife.bind(this);

        getSupportActionBar().setTitle("Manual Transfer");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        callBankDestinationAsync();
    }

    private void callBankDestinationAsync() {
        final ProgressDialog mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Please wait....");
        mProgressDialog.show();

        Call<BankDestination> request = apiClient.getBankDestination();
        request.enqueue(new Callback<BankDestination>() {
            @Override
            public void onResponse(Response<BankDestination> response, Retrofit retrofit) {
                mProgressDialog.dismiss();

                parsingResponse(response);
            }

            @Override
            public void onFailure(Throwable t) {
                mProgressDialog.dismiss();
                Toast.makeText(ManualBankTransferActivity.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void parsingResponse(Response<BankDestination> response) {
        BankDestination res = response.body();
        if (res.code == 200){
            for (BankItem item : res.listItem){
                View mView = getLayoutInflater().inflate(R.layout.item_bank, null);
                TextView txtName = (TextView)mView.findViewById(R.id.tv_bank_name);
                TextView txtNo = (TextView)mView.findViewById(R.id.tv_bank_no);
                TextView txtHolder = (TextView)mView.findViewById(R.id.tv_bank_holder);
                TextView txtBranch = (TextView)mView.findViewById(R.id.tv_bank_branch);

                txtName.setText("Bank : "+item.bankName);
                txtBranch.setText("Branch : "+item.bankBranch);
                txtHolder.setText("Account Holder : "+item.accountName);
                txtNo.setText("Account No : "+item.accountNumber);

                lnBanks.addView(mView);
            }
        }else{
            Toast.makeText(this, res.status, Toast.LENGTH_LONG).show();
        }
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, ManualBankTransferActivity.class);
        context.startActivity(starter);
    }
}
