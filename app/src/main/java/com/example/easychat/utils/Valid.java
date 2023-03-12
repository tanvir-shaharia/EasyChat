package com.example.easychat.utils;

import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

public class Valid {
    public static void inputvaliedfield(Context context,String sms){
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setMessage(sms)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .create()
                .show();
    }
}
