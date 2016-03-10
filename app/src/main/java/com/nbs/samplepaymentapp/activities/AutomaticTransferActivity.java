package com.nbs.samplepaymentapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.nbs.samplepaymentapp.R;
import com.nbs.samplepaymentapp.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AutomaticTransferActivity extends BaseActivity implements View.OnClickListener{

    @Bind(R.id.rb_50k)
    Button rb50k;
    @Bind(R.id.rb_100k)
    Button rb100k;
    @Bind(R.id.rb_150k)
    Button rb150k;
    @Bind(R.id.rb_200k)
    Button rb200k;
    @Bind(R.id.rb_250k)
    Button rb250k;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_automatic_transfer);

        ButterKnife.bind(this);

        getSupportActionBar().setTitle("Automatic Transfer Veritrans");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rb100k.setOnClickListener(this);
        rb50k.setOnClickListener(this);
        rb150k.setOnClickListener(this);
        rb200k.setOnClickListener(this);
        rb250k.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int amount = 0;
        switch (v.getId()){
            case R.id.rb_50k:
                amount = 50000;
                break;

            case R.id.rb_100k:
                amount = 100000;
                break;

            case R.id.rb_150k:
                amount = 150000;
                break;

            case R.id.rb_200k:
                amount = 200000;
                break;

            case R.id.rb_250k:
                amount = 250000;
                break;
        }

        AutomaticTransferConfirmationActivity.start(this, amount);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, AutomaticTransferActivity.class);
        context.startActivity(starter);
    }
}
