package com.example.finalproject;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class adaptercontact extends ArrayAdapter<String> {

    private Activity context;
    private String[] tag;
    private long[] number;


    public adaptercontact(Activity context, String[] data, long[] number) {
        super(context, R.layout.contact_details, data);
        this.context = context;
        this.number = number;
        this.tag = data;
    }

    public View getView(int pos, View view, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View small = inflater.inflate(R.layout.contact_details, null, true);
        TextView contactd = small.findViewById(R.id.contact_id);
        TextView num = small.findViewById(R.id.number_id);
        contactd.setText(tag[pos]);
        num.setText((int) number[pos]);
        return small;
    }

    ;

}
