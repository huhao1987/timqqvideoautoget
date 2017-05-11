package com.example.hao.timspeakerlisten;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Service check";
    private String action="";
    private RadioButton accept,deject,usemessagesend,videotoaudio;
    private RadioGroup videochose;
    private Button cancelauto;
    private SwitchCompat setauto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setauto=(SwitchCompat) findViewById(R.id.setauto);
        setauto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                    mySharedPreferences.setOpenAuto(b);
            }
        });
        videochose=(RadioGroup)findViewById(R.id.videochose);
        videochose.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch(i)
                {
                    case R.id.accept:
                        mySharedPreferences.setAction(0);
                        break;
                    case R.id.deject:
                        mySharedPreferences.setAction(1);

                        break;
                    case R.id.usemessagesend:
                        mySharedPreferences.setAction(2);

                        break;
                    case R.id.videotoaudio:
                        mySharedPreferences.setAction(3);

                        break;

                }
            }
        });
        accept=(RadioButton)findViewById(R.id.accept);
        deject=(RadioButton)findViewById(R.id.deject);
        usemessagesend=(RadioButton)findViewById(R.id.usemessagesend);
        videotoaudio=(RadioButton)findViewById(R.id.videotoaudio);
        if(mySharedPreferences.getOpenAuto())
        {
            setauto.setChecked(true);
        }
        else setauto.setChecked(false);
        switch(mySharedPreferences.getAction())
        {
            case Global.ACCPET: {
                accept.setChecked(true);
                mySharedPreferences.setAction(0);
            }

                break;
            case Global.DEJECT: {
                deject.setChecked(true);
                mySharedPreferences.setAction(1);

            }
                break;
            case Global.MESSAGE: {
                usemessagesend.setChecked(true);
                mySharedPreferences.setAction(2);

            }
                break;
            case Global.CHANGETOAU: {
                videotoaudio.setChecked(true);
                mySharedPreferences.setAction(3);

            }
                break;
        }

        if (!isAccessibilitySettingsOn(this)) {
            Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
            startActivity(intent);
        }
    }

    private boolean isAccessibilitySettingsOn(Context mContext) {
        int accessibilityEnabled = 0;
        final String service = getPackageName() + "/" + Myaccessibiligtyservice.class.getCanonicalName();
        try {
            accessibilityEnabled = Settings.Secure.getInt(mContext.getApplicationContext().getContentResolver(),
                    android.provider.Settings.Secure.ACCESSIBILITY_ENABLED);
            Log.v(TAG, "accessibilityEnabled = " + accessibilityEnabled);
        } catch (Settings.SettingNotFoundException e) {
            Log.e(TAG, "Error finding setting, default accessibility to not found: " + e.getMessage());
        }
        TextUtils.SimpleStringSplitter mStringColonSplitter = new TextUtils.SimpleStringSplitter(':');

        if (accessibilityEnabled == 1) {
            Log.v(TAG, "***ACCESSIBILITY IS ENABLED*** -----------------");
            String settingValue = Settings.Secure.getString(mContext.getApplicationContext().getContentResolver(),
                    Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (settingValue != null) {
                mStringColonSplitter.setString(settingValue);
                while (mStringColonSplitter.hasNext()) {
                    String accessibilityService = mStringColonSplitter.next();

                    Log.v(TAG, "-------------- > accessibilityService :: " + accessibilityService + " " + service);
                    if (accessibilityService.equalsIgnoreCase(service)) {
                        Log.v(TAG, "We've found the correct setting - accessibility is switched on!");
                        return true;
                    }
                }
            }
        } else {
            Log.v(TAG, "***ACCESSIBILITY IS DISABLED***");
        }
        return false;
    }

}
