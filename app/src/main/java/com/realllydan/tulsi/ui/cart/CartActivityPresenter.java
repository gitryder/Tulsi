package com.realllydan.tulsi.ui.cart;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.realllydan.tulsi.data.models.Food;
import com.realllydan.tulsi.ui.main.MainActivityView;

import java.util.ArrayList;
import java.util.List;


public class CartActivityPresenter {

    private static final String TAG = "MainActivityPresenter";

    //vars
    private CartActivityView view;

    public CartActivityPresenter(CartActivityView view){
        this.view = view;
    }

    public void loadData() {
        view.displayData();
    }

    public void initToolbar(){
        view.initToolbar();
    }

    public void updateTotalPrice() {
        view.updateTotalPrice();
    }
}
