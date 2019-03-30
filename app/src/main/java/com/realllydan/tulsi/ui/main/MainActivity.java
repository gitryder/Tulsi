package com.realllydan.tulsi.ui.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.realllydan.tulsi.R;
import com.realllydan.tulsi.data.adapters.FoodItemAdapter;
import com.realllydan.tulsi.data.models.Food;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        FoodItemAdapter.OnFoodItemClickListener,
        MainActivityView {

    private static final String TAG = "MainActivity";

    //ui components
    private Toolbar mToolbar;
    private ProgressBar mProgressBar;

    //vars
    private List<Food> mFoodList;
    private ArrayList<Food> mSelectedFoods = new ArrayList<>();
    private FoodItemAdapter foodItemAdapter;
    private MainActivityPresenter mainActivityPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: started");

        mProgressBar = findViewById(R.id.mProgressBar);

        mainActivityPresenter.initToolbar();
        mainActivityPresenter = new MainActivityPresenter(this);
        mainActivityPresenter.loadData();
    }

    @Override
    public void displayData(List<Food> foodList) {
        Log.d(TAG, "displayData: populating recyclerview");

        //populate the local list
        mFoodList = new ArrayList<>();
        mFoodList = foodList;

        //init the recyclerview
        RecyclerView mRecyclerView = findViewById(R.id.mRecyclerView);
        foodItemAdapter = new FoodItemAdapter(this, foodList, this);
        mRecyclerView.setAdapter(foodItemAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void initToolbar() {
        Log.d(TAG, "initToolbar: setting up");
        mToolbar = findViewById(R.id.mToolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
    }

    @Override
    public void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showDatabaseError(String errorMessage) {
        Toast.makeText(this, "Error: " + errorMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onItemAdded(int position) {
        //get what was clicked
        Food clickedFood = mFoodList.get(position);

        //update its quantity
        clickedFood.setQuantity(clickedFood.getQuantity() + 1);

        //add it to the selected foods
        mSelectedFoods.remove(clickedFood);
        mSelectedFoods.add(clickedFood);

        //notify the adapter about the change
        foodItemAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemRemoved(int position) {
        //get what was clicked
        Food clickedFood = mFoodList.get(position);

        //get its quantity
        int clickedFoodQuantity = clickedFood.getQuantity();

        //update its quantity (only if its non-zero)
        if (clickedFoodQuantity != 0) {
            clickedFoodQuantity -= 1;
            clickedFood.setQuantity(clickedFoodQuantity);
        }

        //clicked food's index in selected foods
        int clickedFoodIndex = mSelectedFoods.indexOf(clickedFood);

        //remove any previous entry)
        mSelectedFoods.remove(clickedFood);

        //add it to its original location if quantity is non-zero
        if (clickedFoodQuantity != 0) {
            mSelectedFoods.add(clickedFoodIndex, clickedFood);
        }

        //notify the adapter about the change
        foodItemAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                for (Food food : mSelectedFoods) {
                    Log.d(TAG, "Food: " + food.getName() + " : " + food.getQuantity());
                }
                break;

            default:
                break;
        }
        return true;
    }
}
