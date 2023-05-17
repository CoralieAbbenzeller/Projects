package de.hdmstuttgart.einkaufsliste.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import de.hdmstuttgart.einkaufsliste.R;
import de.hdmstuttgart.einkaufsliste.fragments.CreateListFragment;
import de.hdmstuttgart.einkaufsliste.fragments.ListFragment;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);


        Intent intent = getIntent();

        //opens CreateListFragment or ListFragment
        if (intent.getStringExtra("info") != null) {
            CreateListFragment createListFragment = new CreateListFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.listFragmentContainer, createListFragment);
            fragmentTransaction.commit();
        } else {
            ListFragment listFragment = new ListFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.listFragmentContainer, listFragment);
            fragmentTransaction.commit();
        }
    }
}
