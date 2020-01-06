package com.alitersolutions.evolveyork.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alitersolutions.evolveyork.R;
import com.alitersolutions.evolveyork.activity.LocationLists;
import com.alitersolutions.evolveyork.model.MasterItems;
import com.alitersolutions.evolveyork.model.MasterLocation;

import java.util.List;

import static com.alitersolutions.evolveyork.utils.Constants.INDTENTDATA;

public class LocationListsAdapter extends RecyclerView.Adapter<LocationListsAdapter.LocationListsViewHolder> {
    List<MasterLocation> masterLocations;
    Context context;
    private final Object mLock = new Object();

    public LocationListsAdapter(Context context, List<MasterLocation> filteredItem) {
        this.context = context;
        this.masterLocations = filteredItem;
    }



    @NonNull
    @Override
    public LocationListsAdapter.LocationListsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.two_item_list,parent,false);
        return new LocationListsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationListsAdapter.LocationListsViewHolder holder, int position) {
        holder.setData(masterLocations.get(position));

    }

    @Override
    public int getItemCount() {
        return masterLocations.size();
    }

    public class LocationListsViewHolder extends RecyclerView.ViewHolder {

        TextView key;
        TextView value;
        LinearLayout parent;


        public LocationListsViewHolder(@NonNull View view) {
            super(view);
            key = view.findViewById(R.id.key);
            value = view.findViewById(R.id.value);
            parent = view.findViewById(R.id.parent);
        }

        public void setData(final MasterLocation masterItems) {
            key.setText(String.valueOf(masterItems.getEvolveItemLocationID()));
           /* String status = "OUT";
            if (bedInfoModel.getEvolveBed_Status()){
                status = "IN";
            }*/
            value.setText(masterItems.getEvolveItemLocation());
//            parent.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(context, ItemInfo.class);
//                    intent.putExtra(INDTENTDATA,masterItems);
//                    context.startActivity(intent);
//                }
//            });
        }

    }
}
