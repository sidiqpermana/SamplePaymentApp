package com.nbs.samplepaymentapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.nbs.samplepaymentapp.R;
import com.nbs.samplepaymentapp.util.Util;
import com.nbs.samplepaymentapp.base.BaseActivity;
import com.nbs.samplepaymentapp.model.VeritransTransfer;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AutomaticTransferFinishActivity extends BaseActivity {

    @Bind(R.id.tv_bank_name)
    TextView tvBankName;
    @Bind(R.id.tv_bank_account_no)
    TextView tvBankAccountNo;
    @Bind(R.id.tv_top_up_amount)
    TextView tvTopUpAmount;
    @Bind(R.id.tv_description_no_a)
    TextView tvDescriptionNoA;
    @Bind(R.id.btn_submit)
    Button btnSubmit;

    private VeritransTransfer data;
    public static String VERITRANS_TRANSFER = "veritransTransfer";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_automatic_transfer_finish);

        ButterKnife.bind(this);

        getSupportActionBar().setTitle("Payment Transfer Info");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        data = getIntent().getParcelableExtra(VERITRANS_TRANSFER);

        tvBankAccountNo.setText(data.permataVaNumber);
        tvTopUpAmount.setText(Util.getCurrency(Double.parseDouble(data.grossAmount.replace(".00", "").trim())));

        String description = "Please complete the payment using m-banking or visit you nearest ATM Machine. You have 24 hours to " +
                "complete payment before this transaction expired";

        tvDescriptionNoA.setText(description);
    }

    @OnClick(R.id.btn_submit)
    public void onBtnSubmitClicked(){
        MainActivity.start(this);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public static void start(Activity context, VeritransTransfer mVeritransTransfer) {
        Intent starter = new Intent(context, AutomaticTransferFinishActivity.class);
        starter.putExtra(VERITRANS_TRANSFER, mVeritransTransfer);
        context.startActivityForResult(starter, 0);
    }
}
