package com.example.clever;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;


public class Settings extends MainActivity{

    // Properties instantiation
    private MediaPlayer buttonSound;
    private String MY_NIGHT_PREFS = "night_switch_prefs";
    private String NIGHT_SWITCH_STAT = "night_switch_ON";
    boolean nightSwitchStatus;
    SharedPreferences nightSwitchSharedPreferences;
    SharedPreferences.Editor nightSwitchEditor;
    private String MY_SOUND_PREFS = "sound_switch_prefs";
    private String SOUND_SWITCH_STAT = "sound_switch_ON";
    boolean soundSwitchStatus;
    SharedPreferences soundSwitchSharedPreferences;
    SharedPreferences.Editor soundSwitchEditor;

    /**
     * Activity's creation method
     * @param savedInstanceState
     */
    public void onCreate(Bundle savedInstanceState) {
        // Activity initialisation
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        // Sound
        buttonSound = MediaPlayer.create(this, R.raw.any_button_sound);

        // Back button
        Button settingsBackBtn = findViewById(R.id.backSettings);
        settingsBackBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (MainActivity.soundOn) { buttonSound.start(); }
                //finish();
                MainActivity.populateFreedom = false;
                Intent mainActivity = new Intent(Settings.this, MainActivity.class);
                startActivityForResult(mainActivity, 1);
            }
        });

        // Switch for night mode
        SwitchCompat nightSwitch = (SwitchCompat) findViewById(R.id.switchNight);
        nightSwitch.setChecked(true);
        nightSwitchSharedPreferences = getSharedPreferences(MY_NIGHT_PREFS, MODE_PRIVATE);
        nightSwitchEditor = getSharedPreferences(MY_NIGHT_PREFS, MODE_PRIVATE).edit();
        nightSwitchStatus = nightSwitchSharedPreferences.getBoolean(NIGHT_SWITCH_STAT, false);
        nightSwitch.setChecked(nightSwitchStatus);

        // Setting the dark/light mode
        if (nightSwitchStatus) {
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
                    MainActivity.populateFreedom = false;
                    Toast.makeText(getBaseContext(), "Dark mode on!", Toast.LENGTH_LONG).show();
                    getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    nightSwitchEditor.putBoolean(NIGHT_SWITCH_STAT, true);
                    nightSwitchEditor.apply();
                    nightSwitch.setChecked(true);
                }
                else {
                    // Set Light mode
                    MainActivity.populateFreedom = false;
                    Toast.makeText(getBaseContext(), "Dark mode off!", Toast.LENGTH_LONG).show();
                    getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    nightSwitchEditor.putBoolean(NIGHT_SWITCH_STAT, false);
                    nightSwitchEditor.apply();
                    nightSwitch.setChecked(false);
                }
            }
        });

        // Switch for sound
        SwitchCompat soundSwitch = (SwitchCompat) findViewById(R.id.switchSound);
        soundSwitch.setChecked(true);
        soundSwitchSharedPreferences = getSharedPreferences(MY_SOUND_PREFS, MODE_PRIVATE);
        soundSwitchEditor = getSharedPreferences(MY_SOUND_PREFS, MODE_PRIVATE).edit();
        soundSwitchStatus = soundSwitchSharedPreferences.getBoolean(SOUND_SWITCH_STAT, false);
        soundSwitch.setChecked(soundSwitchStatus);

        // Setting the sound on/off
        if (soundSwitchStatus) {
            MainActivity.soundOn = true;
        }
        else {
            MainActivity.soundOn = false;
        }

        // When switch is checked and unchecked
        soundSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()) {
                    // Set sound on
                    Toast.makeText(getBaseContext(), "Sound on!", Toast.LENGTH_LONG).show();
                    soundSwitchEditor.putBoolean(SOUND_SWITCH_STAT, true);
                    soundSwitchEditor.apply();
                    soundSwitch.setChecked(true);
                    MainActivity.soundOn = true;
                }
                else {
                    // Set sound off
                    Toast.makeText(getBaseContext(), "Sound off!", Toast.LENGTH_LONG).show();
                    soundSwitchEditor.putBoolean(SOUND_SWITCH_STAT, false);
                    soundSwitchEditor.apply();
                    soundSwitch.setChecked(false);
                    MainActivity.soundOn = false;
                }
            }
        });

        // Image View
        ImageView coffee = (ImageView)findViewById(R.id.coffee);
        coffee.setAlpha(190);
        coffee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MainActivity.soundOn) { buttonSound.start(); }
                Intent donationActivity = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.paypal.com/donate/?hosted_button_id=CKB3VPE6LGF6Q"));
                startActivity(donationActivity);
            }
        });
    }
}
