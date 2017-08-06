package com.musiconlinelisten.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;

import com.google.android.gms.plus.PlusOneButton;
import com.musiconlinelisten.MainApplication;
import com.musiconlinelisten.R;

/**
 * Created by alexd on 05/08/2017.
 */

public class SameActivity extends Activity {
    public AlertDialog.Builder builder;
    private static final String URL = "https://developers.google.com/+";
    public Activity activity = this;
    // The request code must be 0 or higher.
    private static final int PLUS_ONE_REQUEST_CODE = 999;
//    public static int PLUS_ONE_REQUEST = 0;
    private PlusOneButton mPlusOneButton;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.dialog_view);

    }

    @Override
    public void onResume() {
        super.onResume();
//        showDialog();
//        .initialize(URL, PLUS_ONE_REQUEST_CODE);
//        mPlusOneButton.isActivated();

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLUS_ONE_REQUEST_CODE) {
            switch (resultCode) {
                case RESULT_OK:
//                    RateMeDialogTimer.setOptOut(App.getInstance().getApplicationContext(), true);
                    break;
                case RESULT_CANCELED:
//                    RateMeDialogTimer.setOptOut(App.getInstance().getApplicationContext(), false);
                    break;
            }
        }
    }

}
