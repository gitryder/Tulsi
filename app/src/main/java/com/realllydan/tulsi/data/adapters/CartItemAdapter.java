package com.realllydan.tulsi.data.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.realllydan.tulsi.R;
import com.realllydan.tulsi.data.models.Food;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.ViewHolder> {

    private static final String TAG = "CartItemAdapter";

    private Context context;
    private List<Food> mFoodList = new ArrayList<>();
    private OnCartItemClickListener onCartItemClickListener;

    public CartItemAdapter(Context context, List<Food> mFoodList, OnCartItemClickListener onCartItemClickListener) {
        this.context = context;
        this.mFoodList = mFoodList;
        this.onCartItemClickListener = onCartItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.single_food_item, viewGroup, false);
        return new ViewHolder(view, onCartItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        Log.d(TAG, "onBindViewHolder: " + i);

        if(mFoodList.get(i).getType().equals(Food.TYPE_NON_VEG)){
            viewHolder.mFoodTypeImage.setImageDrawable(context.getDrawable(R.drawable.ic_non_veg));
        } else {
            viewHolder.mFoodTypeImage.setImageDrawable(context.getDrawable(R.drawable.ic_veg));
        }

        viewHolder.mFoodName.setText(mFoodList.get(i).getName());
        viewHolder.mFoodPrice.setText("₹ " + mFoodList.get(i).getPrice());
        viewHolder.mFoodQuantity.setText("" + mFoodList.get(i).getQuantity());
    }

    @Override
    public int getItemCount() {
        return mFoodList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView mFoodTypeImage, mAddButton, mRemoveButton;
        TextView mFoodName, mFoodPrice, mFoodQuantity, mDefaultText;
        OnCartItemClickListener onCartItemClickListener;

        public ViewHolder(@NonNull View itemView, OnCartItemClickListener onCartItemClickListener) {
            super(itemView);
            mFoodTypeImage = itemView.findViewById(R.id.mFoodTypeImage);
            mFoodName = itemView.findViewById(R.id.mFoodName);
            mFoodPrice = itemView.findViewById(R.id.mFoodPrice);
            mFoodQuantity = itemView.findViewById(R.id.mFoodQuantity);
            mAddButton = itemView.findViewById(R.id.mAddButton);
            mRemoveButton = itemView.findViewById(R.id.mRemoveButton);
            mDefaultText = itemView.findViewById(R.id.mDefaultText);
            this.onCartItemClickListener = onCartItemClickListener;

            mAddButton.setOnClickListener(this);
            mRemoveButton.setOnClickListener(this);
            mDefaultText.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.mAddButton:
                    onCartItemClickListener.onItemAdded(getAdapterPosition());
                    break;

                case R.id.mRemoveButton:
                    onCartItemClickListener.onItemRemoved(getAdapterPosition());
                    break;

                case R.id.mDefaultText:
                    hideDefaultText();
                    break;

                default:
                    break;
            }
        }

        private void hideDefaultText(){
            mAddButton.setVisibility(View.VISIBLE);
            mRemoveButton.setVisibility(View.VISIBLE);
            mFoodQuantity.setVisibility(View.VISIBLE);
            mDefaultText.setVisibility(GONE);
            notifyDataSetChanged();
        }
    }

    public interface OnCartItemClickListener{
        /**
         * Called when a food item is added to the RecyclerView
         * by clicking the plus button on the view
         *
         * @param position The position of the item that was added.
         */
        void onItemAdded(int position);
        /**
         * Called when a food item is removed from the RecyclerView
         * by clicking the minus button on the view
         *
         * @param position The position of the item that was removed.
         */
        void onItemRemoved(int position);
    }
}
