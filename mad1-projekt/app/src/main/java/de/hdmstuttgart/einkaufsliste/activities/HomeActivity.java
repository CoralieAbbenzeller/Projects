package de.hdmstuttgart.einkaufsliste.activities;

import android.Manifest;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentTransaction;

import de.hdmstuttgart.einkaufsliste.R;
import de.hdmstuttgart.einkaufsliste.fragments.HomeFragment;

public class HomeActivity extends AppCompatActivity {

    public int MY_PERMISSIONS_REQUESTS;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HomeFragment homeFragment = new HomeFragment();

        ActivityCompat.requestPermissions(this,
                new String[]{
                        Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE},
                MY_PERMISSIONS_REQUESTS);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, homeFragment);
        fragmentTransaction.commit();


    }

}