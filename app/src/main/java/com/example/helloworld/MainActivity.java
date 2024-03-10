package com.example.helloworld;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private WishlistAdapter adapter;
    private List<WishlistItem> wishlistItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        wishlistItems = generateSampleWishlistItems();
        adapter = new WishlistAdapter(wishlistItems);
        recyclerView.setAdapter(adapter);

        double totalSum = calculateTotalSum();
        TextView totalSumTextView = findViewById(R.id.totalSumTextView);
        totalSumTextView.setText("Total: $" + String.format("%.2f", totalSum));
    }

    private double calculateTotalSum() {
        double totalSum = 0.0;

        for (WishlistItem item : wishlistItems) {
            totalSum += item.getCost();
        }

        return totalSum;
    }

    private List<WishlistItem> generateSampleWishlistItems() {
        List<WishlistItem> items = new ArrayList<>();
        items.add(new WishlistItem("solar charger", 20, R.drawable.charger));
        items.add(new WishlistItem("levitating speaker", 15, R.drawable.speaker));
        items.add(new WishlistItem("bamboo towels", 8, R.drawable.towels));
        return items;
    }

    private static class WishlistViewHolder extends RecyclerView.ViewHolder {
        private ImageView itemImage;
        private TextView itemName;
        private TextView itemCost;
        private CheckBox itemCheckbox;

        public WishlistViewHolder(View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.itemImage);
            itemName = itemView.findViewById(R.id.itemName);
            itemCost = itemView.findViewById(R.id.itemCost);
            itemCheckbox = itemView.findViewById(R.id.itemCheckbox);
        }
    }

    private class WishlistAdapter extends RecyclerView.Adapter<WishlistViewHolder> {
        private List<WishlistItem> items;

        public WishlistAdapter(List<WishlistItem> items) {
            this.items = items;
        }

        @Override
        public WishlistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
            return new WishlistViewHolder(view);
        }

        @Override
        public void onBindViewHolder(WishlistViewHolder holder, int position) {
            WishlistItem currentItem = items.get(position);

            // Set item data to the ViewHolder
            holder.itemImage.setImageResource(currentItem.getImageResourceId());
            holder.itemName.setText(currentItem.getName());
            holder.itemCost.setText(String.valueOf(currentItem.getCost()));
            holder.itemCheckbox.setChecked(false); // Initialize checkbox state (modify as needed)
        }

        @Override
        public int getItemCount() {
            return items.size();
        }
    }
    public class WishlistItem {
        private String name;
        private double cost;
        private int imageResourceId;

        public WishlistItem(String name, double cost, int imageResourceId) {
            this.name = name;
            this.cost = cost;
            this.imageResourceId = imageResourceId;
        }

        public String getName() {
            return name;
        }

        public double getCost() {
            return cost;
        }

        public int getImageResourceId() {
            return imageResourceId;
        }
    }
}