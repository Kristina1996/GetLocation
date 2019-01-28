package com.example.krist.getlocation;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    TextView tvEnabledGPS;
    TextView tvEnabledNetwork;
    TextView tvStatusGPS;
    TextView tvStatusNetwork;
    TextView tvLocationGPS;
    TextView tvLocationNetwork;

    private LocationManager locationManager;
    StringBuilder sbGPS = new StringBuilder();
    StringBuilder sbNetwork = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvEnabledGPS = (TextView) findViewById(R.id.tvEnabledGPS);
        tvEnabledNetwork = (TextView) findViewById(R.id.tvEnabledNetwork);
        tvStatusGPS = (TextView) findViewById(R.id.tvStatusGPS);
        tvStatusNetwork = (TextView) findViewById(R.id.tvStatusNetwork);
        tvLocationGPS = (TextView) findViewById(R.id.tvLocationGPS);
        tvLocationNetwork = (TextView) findViewById(R.id.tvLocationNetwork);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        Button btnMap = (Button) findViewById(R.id.btnMap);
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MapActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        //Слушатель requestLocationUpdates
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000 * 10, 10, locationListener);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000 * 10, 10, locationListener);
        checkEnabled();
    }

    //Отключение слушателя
    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(locationListener);
    }

    private LocationListener locationListener = new LocationListener() {
        //Новые данные о местоположении
        //Вызов метода для отображения новых данных
        @Override
        public void onLocationChanged(Location location) {
            showLocation(location);
        }

        //Вывод нового статуса на экран пользователя
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            if (provider.equals(LocationManager.GPS_PROVIDER)) {
                tvStatusGPS.setText("Status: " + String.valueOf(status));
            } else if (provider.equals(LocationManager.NETWORK_PROVIDER)) {
                tvStatusNetwork.setText("Status: " + String.valueOf(status));
            }
        }

        //Провайдер включен пользователем
        @Override
        public void onProviderEnabled(String provider) {
            checkEnabled();
            //showLocation(locationManager.getLastKnownLocation(provider));
        }

        //Провайдер отключен пользователем
        @Override
        public void onProviderDisabled(String provider) {
            checkEnabled();
        }
    };

    //Отображение координат
    private void showLocation(Location location) {
        if (location == null)
            return;
        if (location.getProvider().equals(LocationManager.GPS_PROVIDER)) {
            tvLocationGPS.setText(formatLocation(location));
        } else if (location.getProvider().equals(LocationManager.NETWORK_PROVIDER)) {
            tvLocationNetwork.setText(formatLocation(location));
        }
    }

    //Форматирование из обьекта строки с данными о местоположении
    private String formatLocation(Location location) {
        if (location == null)
            return "";
        return String.format("Координаты: Ширина = %1$.4f, Долгота = %2$.4f, Время = %3$tF",
                location.getLatitude(), location.getLongitude(), new Date(location.getTime()));
    }

    //Определение включен или выключен провайдер
    private void checkEnabled() {
        tvEnabledGPS.setText("Enabled: " + locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER));
        tvEnabledNetwork.setText("Enabled: " + locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER));
    }

    public void onClickLocationSettings(View view) {
        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
    };

}
