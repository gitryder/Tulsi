package com.realllydan.tulsi.ui.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.realllydan.tulsi.R;
import com.realllydan.tulsi.data.adapters.FoodItemAdapter;
import com.realllydan.tulsi.data.models.Food;
import com.realllydan.tulsi.ui.cart.CartActivity;
import com.realllydan.tulsi.utils.ObservableArrayList;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        FoodItemAdapter.OnFoodItemClickListener,
        ObservableArrayList.OnChangeListener,
        View.OnClickListener,
        MainActivityView {

    private static final String TAG = "MainActivity";

    //ui components
    private Toolbar mToolbar;
    private ProgressBar mProgressBar;
    private ImageView mCartButton;

    //vars
    private List<Food> mFoodList;
    private ObservableArrayList<Food> mSelectedFoods = new ObservableArrayList<>();
    private FoodItemAdapter foodItemAdapter;
    private MainActivityPresenter mainActivityPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: started");

        mProgressBar = findViewById(R.id.mProgressBar);
        mCartButton = findViewById(R.id.mCartButton);

        mSelectedFoods.addOnChangeListener(this);

        mCartButton.setOnClickListener(this);

        mainActivityPresenter = new MainActivityPresenter(this);
        mainActivityPresenter.initToolbar();
        mainActivityPresenter.loadData();
    }

    /**
     * ObservableArrayList OnChangeListener Callback methods
     * */

    @Override
    public void onElementAdded(Object o) {

    }

    @Override
    public void onElementAddedAtPos(int pos, Object o) {

    }

    @Override
    public void onElementRemoved(Object o) {

    }

    @Override
    public void onListSizeChanged(int size) {
        if (size == 0) {
            /*
            * The mSelectedFoods list is empty
            * remove the cart button from sight
            * */
            mainActivityPresenter.hideCartButton();

        } else {
            /*
             * The mSelectedFoods list is not empty
             * show the cart button on screen
             * */
            mainActivityPresenter.showCartButton();
        }
    }

    /*
     * MainActivityView callback methods
     * */

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
    public void showCartButton() {
        mCartButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideCartButton() {
        mCartButton.setVisibility(View.GONE);
    }

    @Override
    public void showDatabaseError(String errorMessage) {
        Toast.makeText(this, "Error: " + errorMessage, Toast.LENGTH_LONG).show();
    }

    /*
    * RecyclerView OnItemAddedListener callback methods
    * */

    @Override
    public void onItemAdded(int position) {
        //get what was clicked
        Food clickedFood = mFoodList.get(position);

        //update its quantity
        clickedFood.setQuantity(clickedFood.getQuantity() + 1);

        //get the previous index if its already in the list
        int previousIndex = mSelectedFoods.indexOf(clickedFood);

        //add it to the previous position if present
        //else just add it to the selected foods
        if (previousIndex != -1){
            /*
            * The Object already exists in the list.
            * Remove the previous entry and add the new entry.
            * */
            mSelectedFoods.remove(previousIndex);
            mSelectedFoods.add(previousIndex, clickedFood);
        } else {
            /*
             * The Object doesn't exist in the list.
             * Add the new item to the list
             * */
            mSelectedFoods.add(clickedFood);
        }

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
                Log.d(TAG, "Food: mSelectedSize: " + mSelectedFoods.size());
                break;

            default:
                break;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        Intent cartIntent = new Intent(MainActivity.this, CartActivity.class);
        cartIntent.putParcelableArrayListExtra("mSelectedFoods", mSelectedFoods);
        startActivity(cartIntent);
    }
}
