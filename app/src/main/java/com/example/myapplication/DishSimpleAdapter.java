package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DishSimpleAdapter extends ArrayAdapter<DishView> {

    public DishSimpleAdapter(@NonNull Context context, ArrayList<DishView> arrayList) {

        // pass the context and arrayList for the super
        // constructor of the ArrayAdapter class
        super(context, 0, arrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // convertView which is recyclable view
        View currentItemView = convertView;

        // of the recyclable view is null then inflate the custom layout for the same
        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.row_layout, parent, false);
        }

        // get the position of the view from the ArrayAdapter
        DishView currentNumberPosition = getItem(position);

        TextView timeDayText = currentItemView.findViewById(R.id.timeDay);

        ImageView dishImageView = currentItemView.findViewById(R.id.imageDish);
        TextView nameTextView = currentItemView.findViewById(R.id.name);

        assert currentNumberPosition != null;
        timeDayText.setText(currentNumberPosition.getName());

        nameTextView.setText(currentNumberPosition.getName());


        // then return the recyclable view
        return currentItemView;
    }
}
