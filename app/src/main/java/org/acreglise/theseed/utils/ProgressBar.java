package org.acreglise.theseed.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import org.acreglise.theseed.R;

public class ProgressBar {

    public static AlertDialog create(Context context, LayoutInflater inflater, String message) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = inflater.inflate(R.layout.progress_layout, null);

        TextView textView = (TextView) view.findViewById(R.id.progress_title);
        textView.setText(message);

        builder.setView(view);

        AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(true);

        return alertDialog;
    }
}
