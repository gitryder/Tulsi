package com.realllydan.tulsi.ui.cart;

import com.realllydan.tulsi.data.models.Food;

import java.util.List;

public interface CartActivityView {
    void displayData();

    void initToolbar();

    void updateTotalPrice();

}
