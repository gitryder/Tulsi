package com.realllydan.tulsi.ui.main;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.realllydan.tulsi.data.models.Food;

import java.util.ArrayList;
import java.util.List;


public class MainActivityPresenter {

    private static final String TAG = "MainActivityPresenter";

    //vars
    private MainActivityView view;
    private DatabaseReference mDb;

    public MainActivityPresenter(MainActivityView view){
        this.view = view;
    }

    public void loadData(){
        Log.d(TAG, "loadData: loading data from firebase");
        showProgressBar();

        mDb = FirebaseDatabase.getInstance().getReference();
        mDb.child("Menu").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Food> foodList = new ArrayList<>();
                foodList.clear();

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Food food = data.getValue(Food.class);
                    foodList.add(food);

                    view.displayData(foodList);
                    view.hideProgressBar();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                view.showDatabaseError(databaseError.getMessage());
            }

        });
    }

    public void initToolbar(){
        view.initToolbar();
    }

    public void showProgressBar(){
        view.showProgressBar();
    }

    public void showCartButton() {
        view.showCartButton();
    }

    public void hideCartButton() {
        view.hideCartButton();
    }

}
