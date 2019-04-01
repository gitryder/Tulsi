package com.realllydan.tulsi.utils;

import android.util.Log;

import java.util.ArrayList;

public class ObservableArrayList<E> extends ArrayList<E> {

    private static final String TAG = "ObservableArrayList";

    //vars
    private OnChangeListener onChangeListener;

    @Override
    public boolean add(E element) {
        try {
            onChangeListener.onElementAdded(element);
            onChangeListener.onListSizeChanged(size() + 1);

        } catch (NullPointerException e) {
            Log.d(TAG, "add: " + e.getMessage());
        }
        return super.add(element);
    }

    @Override
    public void add(int index, E element) {
        try {
            onChangeListener.onElementAddedAtPos(index, element);
            onChangeListener.onListSizeChanged(size() + 1);

        } catch (NullPointerException e) {
            Log.d(TAG, "add: " + e.getMessage());

        }
        super.add(index, element);
    }

    @Override
    public boolean remove(Object o) {
        try {
            onChangeListener.onElementRemoved(o);
            onChangeListener.onListSizeChanged(size() - 1);

        } catch (NullPointerException e) {
            Log.d(TAG, "remove: " + e.getMessage());
        }
        return super.remove(o);
    }

    public void addOnChangeListener(OnChangeListener onChangeListener){
        this.onChangeListener = onChangeListener;
    }

    public interface OnChangeListener{
        /**
         * Called when an element is added to the ObservableArrayList
         *
         * @param o The object that was added to the ArrayList.
         */
        void onElementAdded(Object o);

        /**
         * Called when an element is added to the ObservableArrayList
         * at a specific position
         *
         * @param pos The position at which the object was added.
         * @param o The object that was added to the ArrayList.
         *
         */
        void onElementAddedAtPos(int pos, Object o);

        /**
         * Called when an element is removed from the ObservableArrayList
         *
         * @param o The object that was removed from the ArrayList.
         */
        void onElementRemoved(Object o);

        /**
         * Called when the size of the ObservableArrayList changes.
         *
         */
        void onListSizeChanged(int size);
    }
}
