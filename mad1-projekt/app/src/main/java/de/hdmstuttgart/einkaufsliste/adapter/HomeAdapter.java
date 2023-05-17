package de.hdmstuttgart.einkaufsliste.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import de.hdmstuttgart.einkaufsliste.R;
import de.hdmstuttgart.einkaufsliste.activities.ListActivity;
import de.hdmstuttgart.einkaufsliste.models.GroceryList;
import de.hdmstuttgart.einkaufsliste.roomDB.GroceryListDatabase;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {

    public static String intentKey = "listname";
    private GroceryListDatabase db;
    public Activity activity;
    private View view;

    public HomeAdapter(Activity activity) {
        this.activity = activity;
        db = Room.databaseBuilder(activity, GroceryListDatabase.class, "groceryListDB")
                .allowMainThreadQueries()
                .build();
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_items_home, parent, false);

        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {

        GroceryList groceryList = db.groceryListDAO().getAll().get(position);

        holder.groceryList = groceryList;
        holder.listName.setText(groceryList.name);

        holder.listPicture.setImageBitmap(groceryList.groceryListPicture);
    }

    @Override
    public int getItemCount() {

        if (db != null) {
            return db.groceryListDAO().getAll().size();
        }
        return 0;
    }


    class HomeViewHolder extends RecyclerView.ViewHolder {

        public final View view;
        private TextView listName;
        private GroceryList groceryList;
        private ImageView listPicture;


        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);

            view = itemView;
            listName = itemView.findViewById(R.id.listName);
            listPicture = itemView.findViewById(R.id.listImage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), ListActivity.class);
                    intent.putExtra(intentKey, groceryList.name);
                    view.getContext().startActivity(intent);
                }
            });
        }
    }

}