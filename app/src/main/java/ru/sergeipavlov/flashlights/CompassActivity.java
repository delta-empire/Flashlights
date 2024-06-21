package ru.sergeipavlov.flashlights;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class CompassActivity extends AppCompatActivity {
    TextView myLocationText;
    TextView myCityLocation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_compass);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.compass), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        String provider = LocationManager.GPS_PROVIDER;
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location l = locationManager.getLastKnownLocation(provider);
        updateWithNewLocation(l);
    }

    private void updateWithNewLocation (Location location) {
        myLocationText = findViewById(R.id.textView3);
        myCityLocation = findViewById(R.id.tvCity);
        String latLongString = "No location found";
        String addressString = "No address found";
        if (location != null) {
            double lat = location.getLatitude();
            double lng = location.getLongitude();

            double latitude = location.getLatitude();
            double longitude = location.getLongitude();

            Geocoder gc = new Geocoder(this, Locale.getDefault());

            try {
                List<Address> addresses = gc.getFromLocation(
                        latitude, longitude, 1
                );
                StringBuilder sb = new StringBuilder();
                if (addresses.size() > 0) {
                    Address address = addresses.get(0);

                    for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                        sb.append(address.getLocality()).append("/n");
//                        sb.append(address.getPostalCode()).append("\n");
//                        sb.append(address.getCountryName()).append("\n");
                    }
                    addressString = sb.toString();
                    //addressString = address.toString();
                    Toast.makeText(this, addressString, Toast.LENGTH_LONG).show();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            latLongString = "Lat " + lat + "\nLong " + lng;
        }
        myCityLocation.setText(addressString);
        myLocationText.setText(latLongString);
    }

}
