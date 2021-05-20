package com.learnwithvel.longpress;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    Button btnShowLocation;
    GPSTracker gps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.setContentView(R.layout.activity_main);
        this.btnShowLocation = (Button)this.findViewById(R.id.button);
        this.btnShowLocation.setOnClickListener(arg0 -> {
            MainActivity.this.gps = new GPSTracker(MainActivity.this);
            if(MainActivity.this.gps.canGetLocation())
            {
                double latitude = MainActivity.this.gps.getLatitude();
                double longitude = MainActivity.this.gps.getLongitude();
                Toast.makeText(MainActivity.this.getApplicationContext(),
                        "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();


                ComponentName cn=new ComponentName(MainActivity.this, AdminReceiver.class);
                DevicePolicyManager mgr=
                        (DevicePolicyManager)getSystemService(DEVICE_POLICY_SERVICE);

                if (mgr.isAdminActive(cn)) {
                    int msgId;

                    if (mgr.isActivePasswordSufficient()) {
                        msgId=R.string.compliant;
                    }
                    else {
                        msgId=R.string.not_compliant;
                    }
                    Toast.makeText(MainActivity.this, msgId, Toast.LENGTH_LONG).show();
                }
                else
                {
                    Intent intent=
                            new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
                    intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, cn);
                    intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                            getString(R.string.device_admin_explanation));
                    startActivity(intent);
                }

                finish();
            }
            else
            {
                MainActivity.this.gps.showSettingsAlert();
            }
        });

    }

}