package com.realllydan.tulsi.ui.main;

import com.realllydan.tulsi.data.models.Food;

import java.util.List;

public interface MainActivityView {
    void displayData(List<Food> foodList);
    void showDatabaseError(String errorMessage);

    void initToolbar();

    void showProgressBar();
    void hideProgressBar();

    void showCartButton();
    void hideCartButton();
}
