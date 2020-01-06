package com.alitersolutions.evolveyork.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alitersolutions.evolveyork.R;
import com.alitersolutions.evolveyork.activity.ItemInfo;
import com.alitersolutions.evolveyork.model.BedInfoModel;
import com.alitersolutions.evolveyork.model.MasterItems;

import java.util.List;

import static com.alitersolutions.evolveyork.utils.Constants.INDTENTDATA;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.BedListViewHolder> {
    List<MasterItems> masterItems;
    Context context;
    private final Object mLock = new Object();

    public ItemListAdapter(Context context, List<MasterItems> masterItems) {
        this.context = context;
        this.masterItems = masterItems;
    }

    @NonNull
    @Override
    public BedListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.two_item_list,parent,false);
        return new BedListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BedListViewHolder holder, int position) {
        holder.setData(masterItems.get(position));
    }

    @Override
    public int getItemCount() {
        return masterItems.size();
    }


    public class BedListViewHolder extends RecyclerView.ViewHolder {
        TextView key;
        TextView value;
        LinearLayout parent;


        public BedListViewHolder(@NonNull View view) {
            super(view);
            key = view.findViewById(R.id.key);
            value = view.findViewById(R.id.value);
            parent = view.findViewById(R.id.parent);
        }

        public void setData(final MasterItems masterItems) {
            key.setText(masterItems.getEvolveItemPart());
           /* String status = "OUT";
            if (bedInfoModel.getEvolveBed_Status()){
                status = "IN";
            }*/
            value.setText(masterItems.getEvolveItemDescription());
            parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ItemInfo.class);
                    intent.putExtra(INDTENTDATA,masterItems);
                    context.startActivity(intent);
                }
            });
        }
    }


}
