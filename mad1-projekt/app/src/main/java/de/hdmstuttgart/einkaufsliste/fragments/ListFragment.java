package de.hdmstuttgart.einkaufsliste.fragments;

import static de.hdmstuttgart.einkaufsliste.adapter.HomeAdapter.intentKey;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import de.hdmstuttgart.einkaufsliste.R;
import de.hdmstuttgart.einkaufsliste.adapter.ListAdapter;
import de.hdmstuttgart.einkaufsliste.roomDB.GroceryListDatabase;
import de.hdmstuttgart.einkaufsliste.roomDB.ListWithProducts;


public class ListFragment extends Fragment {

    private ListAdapter adapter = new ListAdapter();
    public static String clickedList;
    private GroceryListDatabase db;

    @Override
    public void onResume() {
        super.onResume();
    }

    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = GroceryListDatabase.getInstance(getContext());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list, container, false);

        Intent intent = getActivity().getIntent();
        clickedList = intent.getStringExtra(intentKey);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        int indexOfChosenList = db.groceryListDAO().getGroceryListID(clickedList);
        ListWithProducts chosenGroceryListWithProducts = db.groceryListDAO().getListWithProducts(indexOfChosenList);
        adapter.setList(chosenGroceryListWithProducts);

        //LISTNAME - SAVE CODE
        EditText listName = view.findViewById(R.id.listNameText);
        Button saveButton = view.findViewById(R.id.saveButton);

        if (clickedList != null) {
            listName.setText(clickedList.toString());
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.groceryListDAO().updateGroceryList(indexOfChosenList, listName.getText().toString());
            }
        });

        //BACK BUTTON CODE
        ImageButton backButton = view.findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

        //DELETE BUTTON CODE
        ImageButton deleteButton = view.findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.groceryListDAO().deleteGroceryList(db.groceryListDAO().getList(indexOfChosenList));
                db.groceryListDAO().deleteProducts(db.groceryListDAO().getProducts(indexOfChosenList));
                getActivity().finish();

            }
        });

        //ADD BUTTON CODE
        FloatingActionButton addButton = view.findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddProductFragment addProductFragment = new AddProductFragment();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.listFragmentContainer, addProductFragment);
                fragmentTransaction.commit();
            }
        });

        //RECYCLERVIEW CODE
        RecyclerView recyclerView = view.findViewById(R.id.listRecyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
}