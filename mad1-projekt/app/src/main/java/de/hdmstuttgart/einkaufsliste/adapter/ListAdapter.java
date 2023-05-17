package de.hdmstuttgart.einkaufsliste.adapter;

import static de.hdmstuttgart.einkaufsliste.fragments.ListFragment.clickedList;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import de.hdmstuttgart.einkaufsliste.R;
import de.hdmstuttgart.einkaufsliste.models.Product;
import de.hdmstuttgart.einkaufsliste.roomDB.GroceryListDatabase;
import de.hdmstuttgart.einkaufsliste.roomDB.ListWithProducts;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {

    private ListWithProducts chosenGroceryList;
    private GroceryListDatabase db;
    private View view;


    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_items_list, parent, false);

        db = GroceryListDatabase.getInstance(view.getContext());

        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {

        if (chosenGroceryList.products.size() != 0) {
            holder.product = chosenGroceryList.products.get(position);
            holder.productName.setText(chosenGroceryList.products.get(position).name);

            if (holder.product.getIsChecked() == true) {
                holder.checkBox.setChecked(true);
                holder.productName.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            }
        }

    }

    @Override
    public int getItemCount() {
        return chosenGroceryList.products.size();
    }

    class ListViewHolder extends RecyclerView.ViewHolder {

        CheckBox checkBox;
        TextView productName;
        Product product;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkbox);
            productName = itemView.findViewById(R.id.productName);

            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int zutatID = db.groceryListDAO().getProductID(product.name, db.groceryListDAO().getGroceryListID(clickedList));
                    boolean isChecked = db.groceryListDAO().getIsChecked(db.groceryListDAO().getProductName(zutatID), db.groceryListDAO().getGroceryListID(clickedList));

                    if (isChecked == true) {
                        checkBox.setChecked(false);
                        productName.setPaintFlags(0);
                        db.groceryListDAO().updateProductIsChecked(false, zutatID);
                    }

                    if (isChecked == false) {
                        checkBox.setChecked(true);
                        productName.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                        db.groceryListDAO().updateProductIsChecked(true, zutatID);
                    }
                }
            });
        }
    }

    public void setList(ListWithProducts chosenGroceryList) {
        this.chosenGroceryList = chosenGroceryList;
        notifyDataSetChanged();
    }

}

