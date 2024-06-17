package ru.sergeipavlov.flashlights;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.yandex.mobile.ads.banner.BannerAdEventListener;
import com.yandex.mobile.ads.banner.BannerAdSize;
import com.yandex.mobile.ads.banner.BannerAdView;
import com.yandex.mobile.ads.common.AdRequest;
import com.yandex.mobile.ads.common.AdRequestError;
import com.yandex.mobile.ads.common.ImpressionData;

public class MainActivity extends AppCompatActivity {

    FlashLights flashLights;

    ImageView iv_flashlight_off;
    ImageView iv_flashlight_on;

    BottomNavigationView bottomNavigationView;

    private boolean isAutoLampOn;
    private SharedPreferences mSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.settings) {
                    startActivity(new Intent(getApplicationContext(), MenuActivity.class));
                    return true;
                } else if (item.getItemId() == R.id.navigation) {
                    startActivity(new Intent(getApplicationContext(), CompassActivity.class));
                    return true;
                }
                return false;
            }
        });

        BannerAdView mBannerAdView = findViewById(R.id.banner_ad_view);
        mBannerAdView.setAdUnitId("demo-banner-yandex");
        mBannerAdView.setAdSize(BannerAdSize.fixedSize(this, 320, 100));

        final AdRequest adRequest = new AdRequest.Builder().build();

        mBannerAdView.setBannerAdEventListener(new BannerAdEventListener() {
            @Override
            public void onAdLoaded() {

            }

            @Override
            public void onAdFailedToLoad(@NonNull AdRequestError adRequestError) {

            }

            @Override
            public void onAdClicked() {

            }

            @Override
            public void onLeftApplication() {

            }

            @Override
            public void onReturnedToApplication() {

            }

            @Override
            public void onImpression(@Nullable ImpressionData impressionData) {

            }
        });

        mBannerAdView.loadAd(adRequest);

        iv_flashlight_off = findViewById(R.id.iv_flashlight_off);
        iv_flashlight_on = findViewById(R.id.iv_flashlight_on);
        flashLights = new FlashLights(this);
        iv_flashlight_on.setVisibility(View.INVISIBLE);
        iv_flashlight_on.setOnClickListener(v -> lightsOff());
        iv_flashlight_off.setOnClickListener(v -> lightsOn());

    }

    private void lightsOn() {
        flashLights.lampOn();
        iv_flashlight_on.setVisibility(View.VISIBLE);
        iv_flashlight_off.setVisibility(View.INVISIBLE);
    }
    private void lightsOff() {
        flashLights.lampOff();
        iv_flashlight_on.setVisibility(View.INVISIBLE);
        iv_flashlight_off.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onResume() {
        super.onResume();

        mSettings = getSharedPreferences("FlashLights", MODE_PRIVATE);
        isAutoLampOn = mSettings.getBoolean("AutoLampON", true);

        if(isAutoLampOn) {
            flashLights.lampOn();
        } else {
            flashLights.lampOff();
        }
    }
}