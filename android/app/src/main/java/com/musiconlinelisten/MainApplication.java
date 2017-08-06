package com.musiconlinelisten;

import android.support.multidex.MultiDexApplication;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.react.ReactPackage;

import java.util.Arrays;
import java.util.List;

// Needed for `react-native link`
// import com.facebook.react.ReactApplication;
import com.RNFetchBlob.RNFetchBlobPackage;
public class MainApplication extends MultiDexApplication {
  private static MainApplication inst;
  private static RequestQueue req;
  // Needed for `react-native link`
  public List<ReactPackage> getPackages() {
    return Arrays.<ReactPackage>asList(
        // Add your own packages here!
        // TODO: add cool native modules

        // Needed for `react-native link`
        // new MainReactPackage
            new RNFetchBlobPackage()
    );
  }

  @Override
  public void onCreate() {
    super.onCreate();
    inst = this;
//    FirebaseApp.initializeApp(this);
    req = Volley.newRequestQueue(this);
  }

  public MainApplication() {
    inst = this;
  }

  public static MainApplication getInstance() {
    return inst;
  }

  public static void addQ(StringRequest request){
    req.add(request);
  }
}
