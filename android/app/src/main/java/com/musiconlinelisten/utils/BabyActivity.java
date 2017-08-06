package com.musiconlinelisten.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by alexd on 05/08/2017.
 */

public class BabyActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(checkIfOldAPi()) {
            finish();
        } else {
            finishAndRemoveTask();
        }
    }

    private boolean checkIfOldAPi(){
        if(android.os.Build.VERSION.SDK_INT < 21){
            return true;
        }
        return false;
    }

    public static void show(Context context){
        Intent intent = new Intent(context, BabyActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(intent);
    }
}
