package com.example.clever;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.SwitchCompat;

import java.security.PrivilegedAction;

public class Settings extends MainActivity{

    private String MY_PREFS = "switch_prefs";
    private String SWITCH_STAT = "switch_ON";
    boolean switchStatus;
    SharedPreferences sharedPreferences;    // stores switch value
    SharedPreferences.Editor editor;
    private MediaPlayer buttonSound;

    /**
     * Activity's creation method
     * @param savedInstanceState
     */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        // Sound
        buttonSound = MediaPlayer.create(this, R.raw.any_button_sound);

        // Back button
        Button settingsBackBtn = findViewById(R.id.backSettings);
        settingsBackBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                buttonSound.start();
                finish();
            }
        });


        // Switch for night mode
        SwitchCompat nightSwitch = (SwitchCompat) findViewById(R.id.switchNight);
        nightSwitch.setChecked(true);
        sharedPreferences = getSharedPreferences(MY_PREFS, MODE_PRIVATE);
        editor = getSharedPreferences(MY_PREFS, MODE_PRIVATE).edit();
        switchStatus = sharedPreferences.getBoolean(SWITCH_STAT, false);
        nightSwitch.setChecked(switchStatus);

        // Setting the dark/light mode
        if (switchStatus) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        // When switch is checked and unchecked
        nightSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()) {
                    // Set night mode
                    getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    editor.putBoolean(SWITCH_STAT, true);
                    editor.apply();
                    nightSwitch.setChecked(true);
                }
                else {
                    // Set Light mode
                    getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    editor.putBoolean(SWITCH_STAT, false);
                    editor.apply();
                    nightSwitch.setChecked(false);
                }
            }
        });

        // Image View
        ImageView coffee = (ImageView)findViewById(R.id.coffee);
        coffee.setAlpha(190);
        coffee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonSound.start();
                Intent donationActivity = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.paypal.com/donate/?hosted_button_id=CKB3VPE6LGF6Q"));
                startActivity(donationActivity);
            }
        });
    }
}
