package de.hdmstuttgart.einkaufsliste.fragments;

import static de.hdmstuttgart.einkaufsliste.adapter.HomeAdapter.intentKey;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import de.hdmstuttgart.einkaufsliste.R;
import de.hdmstuttgart.einkaufsliste.models.Product;
import de.hdmstuttgart.einkaufsliste.roomDB.GroceryListDatabase;


public class AddProductFragment extends Fragment {

    private AddProductFragment addProductFragment = this;
    private static String clickedList;
    private GroceryListDatabase db;

    public AddProductFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Intent intent = getActivity().getIntent();
        clickedList = intent.getStringExtra(intentKey);

        return inflater.inflate(R.layout.fragment_add, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        db = GroceryListDatabase.getInstance(getContext());

        //BACK BUTTON - CODE
        ImageButton backButton = view.findViewById(R.id.backButtonAdd);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.remove(addProductFragment);
                fragmentTransaction.commit();
                hideKeyboardFrom(getContext(), view);
            }
        });

        //SUBMIT BUTTON - CODE
        final EditText productName = view.findViewById(R.id.product);

        Button submitButton = view.findViewById(R.id.submitButtonAdd);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.groceryListDAO().insertProduct(productName.getText().toString(), false, db.groceryListDAO().getGroceryListID(clickedList));

                ListFragment listFragment = new ListFragment();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.listFragmentContainer, listFragment);
                fragmentTransaction.commit();
                hideKeyboardFrom(getContext(), view);

            }
        });
    }

    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}