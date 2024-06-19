package ru.sergeipavlov.flashlights;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Switch;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MenuActivity extends AppCompatActivity {
    private SharedPreferences mSettings;
    private SharedPreferences.Editor mEditor;

    private boolean isEnebleAutoStart;
    private boolean isEnableAutoExit;
    private boolean isChooseLanguage;
    private boolean isShockFromStart;
    private boolean isChooseTheme;

    Switch swEnableAutoStart;
    Switch swEnableAutoExit;
    Switch swChooseLanguage;
    Switch swShockFromStart;
    Switch swChooseTheme;

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_settings);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.settings), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if(item.getItemId() == R.id.settings) {
                startActivity(new Intent(getApplicationContext(), MenuActivity.class));
                return true;
            } else if (item.getItemId() == R.id.navigation) {
                startActivity(new Intent(getApplicationContext(), CompassActivity.class));
                return true;
            } else if (item.getItemId() == R.id.flaslights) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                return true;
            }
            return false;
        });

        swEnableAutoStart = findViewById(R.id.swEnableAutoStart);
        swEnableAutoExit = findViewById(R.id.swEnableAutoExit);
        swChooseLanguage = findViewById(R.id.swChooseLanguage);
        swShockFromStart = findViewById(R.id.swShockFromStart);
        swChooseTheme = findViewById(R.id.swChooseTheme);

        mSettings = getSharedPreferences("FlashLights", MODE_PRIVATE);

        mEditor = mSettings.edit();

        isEnebleAutoStart = mSettings.getBoolean("EnableAutoStart", false);
        isEnableAutoExit = mSettings.getBoolean("EnableAutoExit", false);
        isChooseLanguage = mSettings.getBoolean("EnableChangelanguage", false);
        isShockFromStart = mSettings.getBoolean("EnableShockFromStart", false);
        isChooseTheme = mSettings.getBoolean("ChooseTheme", false);

        swEnableAutoStart.setChecked(isEnebleAutoStart);
        swEnableAutoExit.setChecked(isEnableAutoExit);
        swChooseLanguage.setChecked(isChooseLanguage);
        swShockFromStart.setChecked(isShockFromStart);
        swChooseTheme.setChecked(isChooseTheme);

        swEnableAutoStart.setOnCheckedChangeListener(
                (buttonView, isChecked) -> {
                    if (isChecked) {
                        mEditor.putBoolean("EnableAutoStart", true);
                        isEnebleAutoStart = true;
                    } else {
                        mEditor.putBoolean("EnableAutoStart", false);
                        isEnebleAutoStart = false;
                    }
                }
        );

        swEnableAutoExit.setOnCheckedChangeListener(
                (buttonView, isChecked) -> {
                    if (isChecked) {
                        mEditor.putBoolean("EnableAutoExit", true);
                        isEnableAutoExit = true;
                    } else {
                        mEditor.putBoolean("EnableAutoExit", false);
                        isEnableAutoExit = false;
                    }
                }
        );

        swChooseLanguage.setOnCheckedChangeListener(
                (buttonView, isChecked) -> {
                    if (isChecked) {
                        mEditor.putBoolean("EnableChangelanguage", true);
                        isChooseLanguage = true;
                    } else {
                        mEditor.putBoolean("EnableChangelanguage", false);
                        isChooseLanguage = false;
                    }
                }
        );

        swShockFromStart.setOnCheckedChangeListener(
                (buttonView, isChecked) -> {
                    if (isChecked) {
                        mEditor.putBoolean("EnableShockFromStart", true);
                        isShockFromStart = true;
                    } else {
                        mEditor.putBoolean("EnableShockFromStart", false);
                        isShockFromStart = false;
                    }
                }
        );

        swChooseTheme.setOnCheckedChangeListener(
                (buttonView, isChecked) -> {
                    if (isChecked) {
                        mEditor.putBoolean("ChooseTheme", true);
                        isChooseTheme = true;
                    } else {
                        mEditor.putBoolean("ChooseTheme", false);
                        isChooseTheme = false;
                    }
                }
        );
    }

    @Override
    protected void onPause() {
        super.onPause();
        mEditor.commit();
    }
}
