package co.edu.unipiloto.ubicacion90;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.os.Bundle;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private Button bt_location;
    private TextView textView1,textView2,textView3,textView4,textView5;
    private FusedLocationProviderClient fusedLocationProviderClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //asignar valores
        bt_location= findViewById(R.id.bt_location);
        textView1= findViewById(R.id.text_view1);
        textView2= findViewById(R.id.text_view2);
        textView3= findViewById(R.id.text_view3);
        textView4= findViewById(R.id.text_view4);
        textView5= findViewById(R.id.text_view5);

        //Iniciar el fuseLocation

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        bt_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //verificar permisos
                if (ActivityCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
                    //se garantizan los permisos

                    getLocation();
                }else{
                    //Se negaron permisos
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
                }
            }
        });
    }
    public void getLocation(){
        if (ActivityCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>(){

                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    Location location=task.getResult();
                    if (location!=null){
                        Geocoder geocoder=new Geocoder(MainActivity.this, Locale.getDefault());

                        try {
                            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),
                                    location.getLongitude(),1);

                            textView1.setText("Latitud "+ addresses.get(0).getLatitude());
                            textView2.setText("Longitud "+ addresses.get(0).getLongitude());
                            textView3.setText("Pais "+ addresses.get(0).getCountryName());
                            textView4.setText("Localidad "+ addresses.get(0).getLocality());
                            textView5.setText("Direccion "+ addresses.get(0).getAddressLine(0));

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }
            });
        }else{
            //Se negaron permisos
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
        }


    }
}