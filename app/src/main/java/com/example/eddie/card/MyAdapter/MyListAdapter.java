package com.example.eddie.card.MyAdapter;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.eddie.card.PoJo.card;
import com.example.eddie.card.R;

import java.util.List;

public class MyListAdapter extends ArrayAdapter {

    private Context context;
    private int resource;
    private List<card> list;

    public MyListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List objects) {
        super(context, resource, objects);

        this.context = context;
        this.resource = resource;
        this.list = objects;

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {

        view = LayoutInflater.from(context).inflate(resource,parent,false);

        TextView name = view.findViewById(R.id.name_item);
        TextView array = view.findViewById(R.id.array_item);

        name.setText(list.get(position).getName());
        array.setText(list.get(position).getArray());

        return view;
    }
}
