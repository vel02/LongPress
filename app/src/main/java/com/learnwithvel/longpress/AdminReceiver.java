package com.learnwithvel.longpress;

import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class AdminReceiver extends DeviceAdminReceiver {
    //https://github.com/nurawat/LockScreen-Protector---Android-Security-APP

    private static final String TAG = "AdminReceiver";

    String lat, lon;

    @Override
    public void onEnabled(Context ctxt, Intent intent) {
        ComponentName cn = new ComponentName(ctxt, AdminReceiver.class);
        DevicePolicyManager mgr =
                (DevicePolicyManager) ctxt.getSystemService(Context.DEVICE_POLICY_SERVICE);

        mgr.setPasswordQuality(cn,
                DevicePolicyManager.PASSWORD_QUALITY_SOMETHING);

        onPasswordChanged(ctxt, intent);
    }



    @Override
    public void onPasswordChanged(Context ctxt, Intent intent) {
        DevicePolicyManager mgr =
                (DevicePolicyManager) ctxt.getSystemService(Context.DEVICE_POLICY_SERVICE);
        int msgId;

        if (mgr.isActivePasswordSufficient()) {
            msgId = R.string.compliant;
        } else {
            msgId = R.string.not_compliant;
        }

        Toast.makeText(ctxt, msgId, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPasswordFailed(Context ctxt, Intent intent) {

        Log.d(TAG, "onPasswordFailed: " + R.string.password_failed);
        Toast.makeText(ctxt, R.string.password_failed, Toast.LENGTH_LONG)
                .show();
    }

    @Override
    public void onPasswordSucceeded(Context ctxt, Intent intent) {
        Log.d(TAG, "Congratulation");

        Toast.makeText(ctxt, "Congratulation", Toast.LENGTH_LONG)
                .show();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);


    }
}
