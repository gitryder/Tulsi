package com.realllydan.tulsi.ui.receipt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.realllydan.tulsi.R;

public class ReceiptActivity extends AppCompatActivity implements
        ReceiptActivityView{

    private static final String TAG = "ReceiptActivity";

    //ui components
    private Toolbar mToolbar;

    //vars
    private ReceiptActivityPresenter receiptActivityPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);

        receiptActivityPresenter = new ReceiptActivityPresenter(this);
        receiptActivityPresenter.initToolbar();
    }

    @Override
    public void initToolbar() {
        Log.d(TAG, "initToolbar: setting up");
        mToolbar = findViewById(R.id.mToolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
    }
}
