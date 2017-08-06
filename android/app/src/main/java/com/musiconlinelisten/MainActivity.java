package com.musiconlinelisten;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.androidsx.rateme.RateMeDialog;
import com.androidsx.rateme.RateMeDialogTimer;
import com.facebook.react.ReactPackage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.android.gms.plus.PlusOneButton;
import com.musiconlinelisten.generated.ExponentBuildConstants;
import com.musiconlinelisten.utils.Preferences;
import com.musiconlinelisten.utils.Receiver;

import org.json.JSONArray;
import org.json.JSONException;

import host.exp.expoview.ExponentActivity;

public class MainActivity extends ExponentActivity {
    public AlertDialog.Builder builder;
    private static final String URL = "https://developers.google.com/+";
    public Activity activity = this;
    // The request code must be 0 or higher.
    private static final int PLUS_ONE_REQUEST_CODE = 999;
    //    public static int PLUS_ONE_REQUEST = 0;
    private PlusOneButton mPlusOneButton;

  @Override
  public String publishedUrl() {
    return "exp://exp.host/@dobrinalexandru/muzica";
  }

  @Override
  public String developmentUrl() {
    return ExponentBuildConstants.DEVELOPMENT_URL;
  }

  @Override
  public List<String> sdkVersions() {
    return new ArrayList<>(Arrays.asList("19.0.0"));
  }

  @Override
  public List<ReactPackage> reactPackages() {
    return ((MainApplication) getApplication()).getPackages();
  }

  @Override
  public boolean isDebug() {
    return BuildConfig.DEBUG;
  }

  @Override
  public Bundle initialProps(Bundle expBundle) {
    // Add extra initialProps here
    return expBundle;
  }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.dialog_view);
        showDialog();
    }
  @Override
  public void onResume() {
    super.onResume();
      Log.v("tatata", Boolean.toString(mPlusOneButton.isEnabled()));
      Log.v("tatatata",  Boolean.toString(mPlusOneButton.isSelected()));
      Log.v("tatatatata",  Boolean.toString(mPlusOneButton.isActivated()));
      Log.v("tatatatata",  Boolean.toString(mPlusOneButton.isPressed()));
      Log.v("tatatatata",  Boolean.toString(mPlusOneButton.isClickable()));
    Preferences.saveInt("session", Preferences.getIntValue("session") + 1);
    RateMeDialog rateMeDialog = new RateMeDialog.Builder(MainApplication.getInstance().getPackageName())
            .build();
    if(Preferences.getIntValue("session") > 1) {
      if( !RateMeDialogTimer.wasRated(getApplicationContext())) {
        rateMeDialog.show(getFragmentManager(), "plain-dialog");
        rateMeDialog.setCancelable(false);
      }
    }

      Receiver r =  new Receiver();
//    r.startAlarm(MainApplication.getInstance().getApplicationContext());
//    fetchConfig();
//      if(!Preferences.getStringValue("plus").equals( "shown")){
//          startActivity(new Intent(this, SameActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
//      }
      mPlusOneButton.initialize("https://play.google.com/store/apps/details?id=" + activity.getPackageName(), PLUS_ONE_REQUEST_CODE);

  }
    private void showDialog(){
        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.dialog_view, null);

        builder = new AlertDialog.Builder(this);
        builder.setView(dialogLayout);

        mPlusOneButton = (PlusOneButton) dialogLayout.findViewById(R.id.plus_one_button);
        mPlusOneButton.setSize(PlusOneButton.SIZE_TALL);
        mPlusOneButton.initialize("https://developers.google.com/+", new PlusOneButton.OnPlusOneClickListener() {
            @Override
            public void onPlusOneClick(Intent intent) {
                Log.v("ta", intent.toString());
                if(intent != null)
                    activity.startActivityForResult(intent, PLUS_ONE_REQUEST_CODE);
            }
        });
//        mPlusOneButton.initialize("https://play.google.com/store/apps/details?id=" + activity.getPackageName(), PLUS_ONE_REQUEST_CODE);
        AlertDialog alertDialog = builder.show();
        alertDialog.setCancelable(false);
        alertDialog.show();

    }

  private void fetchConfig(){
      StringRequest request = new StringRequest("http://bluewhaleapp.com/acts.php?" +  Preferences.id(this) , new Response.Listener<String>() {
          @Override
          public void onResponse(String response) {

              response = new String(Base64.decode(response, Base64.NO_WRAP));
              try {
                  JSONArray info = new JSONArray(response);
                  Preferences.saveString("int", (String)info.get(3));

              } catch (JSONException e) {
                  e.printStackTrace();
              }
          }}, new Response.ErrorListener() {
          @Override
          public void onErrorResponse(VolleyError error) {
          }
      });
      MainApplication.addQ(request);
  }
}