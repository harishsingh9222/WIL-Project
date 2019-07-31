package com.lambton.lofterapp.activities;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.lambton.lofterapp.R;
import com.lambton.lofterapp.database.DatabaseHelper;
import com.lambton.lofterapp.models.agents.AgentInfo;
import com.lambton.lofterapp.models.user.LoginUserProfile;
import com.lambton.lofterapp.utils.CommonMethods;
import com.lambton.lofterapp.utils.Utility;

import java.util.ArrayList;
import java.util.List;

import static com.lambton.lofterapp.utils.Utility.REQUEST_MULTIPLE_PERMISSIONS;

public class SplashActivity extends AppCompatActivity {

    int SPLASH_TIME_OUT = 1500;
    SharedPreferences prefs = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);

        prefs = getSharedPreferences(getPackageName(), MODE_PRIVATE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean isAlreadyRun = prefs.getBoolean(Utility.KEY_FIRST_RUN, false);
                if (!isAlreadyRun) {
                    new DatabaseHelper(SplashActivity.this).addUserProfile(new
                            LoginUserProfile("Lofter", "Agent",
                            "https://pbs.twimg.com/profile_images/819018148817076224/MJ2VSw7s_400x400.jpg",
                            "Sanujot@gmail.com",
                            "+16476871771"));
                    prefs.edit().putBoolean(Utility.KEY_FIRST_RUN, true).apply();
                    storeInitLofters();
                }

                if (!checkAndRequestPermissions()) {
                    return;
                }
                proceedAfterPermissions();
            }
        }, SPLASH_TIME_OUT);
    }

    private void proceedAfterPermissions() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void storeInitLofters() {
        new DatabaseHelper(SplashActivity.this).addLofterAgent(new AgentInfo("Missao - 2 Bedroom Apartment", converDrawableToPath(R.drawable.ic_property1), "1800 4 Street SW", "+1(416) 398-7291", "SALE", 43.7003139, -79.6453664, "$17000.00", "1090 (Square Feet)"));
        new DatabaseHelper(SplashActivity.this).addLofterAgent(new AgentInfo("2100 Regent Street - 2 Bedroom Apartment", converDrawableToPath(R.drawable.ic_property2), "2100 Regent Street", "+1(437) 992-9831", "RENT", 43.7184038, -79.5181437, "$800.00", "850 (Square Feet)"));
        new DatabaseHelper(SplashActivity.this).addLofterAgent(new AgentInfo("Deveraux Heights - 1 Bedroom w/ Den Apartment", converDrawableToPath(R.drawable.ic_property3), "5601 Gordon Road", "+91 (708) 777-4484", "RENT", 42.9740891, -82.3485736, "$650.00", "550 (Square Feet)"));
        new DatabaseHelper(SplashActivity.this).addLofterAgent(new AgentInfo("Galaxy Apartments", converDrawableToPath(R.drawable.ic_property4), "14 Galaxy Ave", "+1-(647) 877-4652", "RENT", 42.9303659, -82.413741, "$1050.00", "1100 (Square Feet)"));
        new DatabaseHelper(SplashActivity.this).addLofterAgent(new AgentInfo("Deveraux Heights", converDrawableToPath(R.drawable.ic_property5), "5601 Gordon Road", "+1-(587) 834-0490", "SALE", 42.9740891, -82.3485736, "$9550.00", "600 (Square Feet)"));
        new DatabaseHelper(SplashActivity.this).addLofterAgent(new AgentInfo("Missao - 1 Bedroom Apartment", converDrawableToPath(R.drawable.ic_property6), "1800 4 Street SW", "+1-(416) 746-5627", "RENT", 42.9740891, -82.3485736, "$400.00", "350 (Square Feet)"));
        new DatabaseHelper(SplashActivity.this).addLofterAgent(new AgentInfo("Galaxy Apartments - 2 Bedroom Apartment", converDrawableToPath(R.drawable.ic_property7), "14 Galaxy Ave", "+1-(647) 551-9444", "SALE", 43.6605288, -79.3817561, "$8000.00", "850 (Square Feet)"));
        new DatabaseHelper(SplashActivity.this).addLofterAgent(new AgentInfo("Deveraux Heights - 1 Bedroom Barrier Free Apartment", converDrawableToPath(R.drawable.ic_property8), "5601 Gordon Road", "+1-(647) 544-1299", "RENT", 49.1966913, -123.183701, "$900.00", "850 (Square Feet)"));
        new DatabaseHelper(SplashActivity.this).addLofterAgent(new AgentInfo("4 bedroom Apartment", converDrawableToPath(R.drawable.ic_property9), "15 Reading Crescent", "+1-(905) 456-6363", "RENT", 43.7199966, -79.3637643, "$1050.00", "1250 (Square Feet)"));
    }

    private String converDrawableToPath(int drawable) {
        return Uri.parse("android.resource://com.lambton.lofterapp/" + drawable).toString();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private boolean checkAndRequestPermissions() {
        int storageCourseLocation = ContextCompat.checkSelfPermission(SplashActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION);

        int storageFineLocation = ContextCompat.checkSelfPermission(SplashActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION);

        List<String> listPermissionsNeeded = new ArrayList<>();
        if (storageCourseLocation != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if (storageFineLocation != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(SplashActivity.this,
                    listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()])
                    , REQUEST_MULTIPLE_PERMISSIONS);
            return false;
        }

        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_MULTIPLE_PERMISSIONS: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    proceedAfterPermissions();
                } else {
                    CommonMethods.alertMessageOkCallBack(SplashActivity.this,
                            getString(android.R.string.dialog_alert_title),
                            "Permissions required.");
                }
                return;
            }
        }
    }
}