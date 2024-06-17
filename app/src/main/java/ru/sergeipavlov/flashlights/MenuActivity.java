package ru.sergeipavlov.flashlights;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MenuActivity extends AppCompatActivity {
    private SharedPreferences mSettings;
    private SharedPreferences.Editor mEditor;
    private  boolean isAutoLampOn;
    Switch switchEnableAutoStart;

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

        mSettings = getSharedPreferences("FlashLights", MODE_PRIVATE);
        mEditor = mSettings.edit();

        isAutoLampOn = mSettings.getBoolean("AutoLampON", true);

        switchEnableAutoStart = findViewById(R.id.enableAutoStart);
        switchEnableAutoStart.setChecked(isAutoLampOn);

        switchEnableAutoStart.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            mEditor.putBoolean("AutoLampON", true);
                            isAutoLampOn = true;
                        } else {
                            mEditor.putBoolean("AutoLampON", false);
                            isAutoLampOn = false;
                        }
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
