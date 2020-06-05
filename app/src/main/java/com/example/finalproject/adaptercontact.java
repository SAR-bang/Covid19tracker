package com.example.finalproject;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

public class adaptercontact extends ArrayAdapter<String> {

    private Activity context;
    private String[] tag;
    private int[] number;


    public adaptercontact(Activity context, String[] data, int[] number) {
        super(context, R.layout.contact_details, data);
        this.context = context;
        this.number = number;
        this.tag = data;
    }

    public View getView(int pos, View view, final ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View small = inflater.inflate(R.layout.contact_details, null, true);
        final TextView contactd = small.findViewById(R.id.details_id);
        TextView num = small.findViewById(R.id.number_id);
        ImageButton phone = small.findViewById(R.id.phone);

        contactd.setText(tag[pos]);
        num.setText(String.valueOf(number[pos]));
        final int position = pos;
        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // making a call by starting the intent

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse(String.valueOf(number[position])));

                // method to get the permission from the user
                if (ActivityCompat.checkSelfPermission(getContext(),
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                getContext().startActivity(callIntent);
            }
        });
//            }
        return small;
    }

    ;

}
