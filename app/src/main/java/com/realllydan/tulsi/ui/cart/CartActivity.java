package com.realllydan.tulsi.ui.cart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.realllydan.tulsi.R;
import com.realllydan.tulsi.data.adapters.CartItemAdapter;
import com.realllydan.tulsi.data.adapters.FoodItemAdapter;
import com.realllydan.tulsi.data.models.Food;
import com.realllydan.tulsi.ui.main.MainActivity;
import com.realllydan.tulsi.ui.main.MainActivityPresenter;
import com.realllydan.tulsi.ui.receipt.ReceiptActivity;
import com.realllydan.tulsi.utils.ObservableArrayList;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity implements
        CartActivityView,
        View.OnClickListener,
        CartItemAdapter.OnCartItemClickListener {

    private static final String TAG = "CartActivity";

    //ui components
    private Toolbar mToolbar;
    private TextView mTotalPrice;
    private List<Food> mSelectedFoods;

    //vars
    private CartItemAdapter cartItemAdapter;
    private CartActivityPresenter cartActivityPresenter;
    private int totalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        mTotalPrice = findViewById(R.id.mTotalPrice);
        mTotalPrice.setOnClickListener(this);

        cartActivityPresenter = new CartActivityPresenter(this);
        cartActivityPresenter.initToolbar();
        cartActivityPresenter.loadData();
    }

    @Override
    public void displayData() {
        Log.d(TAG, "displayData: populating recyclerview");

        //populate the local list
        mSelectedFoods = new ArrayList<>();
        mSelectedFoods = getIntent().getParcelableArrayListExtra("mSelectedFoods");

        //init the total price textview
        cartActivityPresenter.updateTotalPrice();

        //init the recyclerview
        RecyclerView mRecyclerView = findViewById(R.id.mRecyclerView);
        cartItemAdapter = new CartItemAdapter(this, mSelectedFoods, this);
        mRecyclerView.setAdapter(cartItemAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
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

    @Override
    public void updateTotalPrice() {
        int tempTotalPrice = 0;

        for (Food foodItem : mSelectedFoods) {
            tempTotalPrice += foodItem.getPrice() * foodItem.getQuantity();
            totalPrice = tempTotalPrice;

        }
        mTotalPrice.setText("₹ " + totalPrice);
    }

    @Override
    public void onItemAdded(int position) {
        //get what was clicked
        Food clickedFood = mSelectedFoods.get(position);

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

        //update the price textview
        cartActivityPresenter.updateTotalPrice();

        //notify the adapter about the change
        cartItemAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemRemoved(int position) {
        //get what was clicked
        Food clickedFood = mSelectedFoods.get(position);

        //get its quantity
        int clickedFoodQuantity = clickedFood.getQuantity();

        //update its quantity (only if its non-zero)
        if (clickedFoodQuantity != 0) {
            clickedFoodQuantity -= 1;
            clickedFood.setQuantity(clickedFoodQuantity);

            //clicked food's index in selected foods
            int clickedFoodIndex = mSelectedFoods.indexOf(clickedFood);

            //remove any previous entry)
            mSelectedFoods.remove(clickedFood);

            //add it to its original location if quantity is non-zero
            if (clickedFoodQuantity != 0) {
                mSelectedFoods.add(clickedFoodIndex, clickedFood);
            }

            //update the price textview
            cartActivityPresenter.updateTotalPrice();

        } else if (clickedFoodQuantity == 1){
            mSelectedFoods.remove(clickedFood);
        }

        //notify the adapter about the change
        cartItemAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        Intent receiptIntent = new Intent(CartActivity.this, ReceiptActivity.class);
        startActivity(receiptIntent);
    }
}
