package com.realllydan.tulsi.ui.receipt;

public class ReceiptActivityPresenter {

    private static final String TAG = "ReceiptActivityPresenter";

    //vars
    private ReceiptActivityView view;

    public ReceiptActivityPresenter(ReceiptActivityView view) {
        this.view = view;
    }

    public void initToolbar(){
        view.initToolbar();
    }
}
