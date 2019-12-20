package com.alitersolutions.evolveyork.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alitersolutions.evolveyork.R;
import com.alitersolutions.evolveyork.model.SentModel;

import java.util.ArrayList;


public class CycleHistoryAdapter extends RecyclerView.Adapter<CycleHistoryAdapter.CHAViewHolder> {
    ArrayList<SentModel> sentModel;
    Context context;

    public CycleHistoryAdapter(Context context, ArrayList<SentModel> sentModel) {
        this.context = context;
        this.sentModel = sentModel;
    }

    @NonNull
    @Override
    public CycleHistoryAdapter.CHAViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.history_cycle_list_item,parent,false);
        return new CHAViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CycleHistoryAdapter.CHAViewHolder holder, int position) {
        holder.setData(sentModel.get(position));
    }

    @Override
    public int getItemCount() {
        return sentModel.size();
    }

    public class CHAViewHolder extends RecyclerView.ViewHolder {
        TextView added_date,location,qunatity,item_name;
//        LinearLayout parent;


        public CHAViewHolder(@NonNull View view) {
            super(view);
            added_date = view.findViewById(R.id.added_date);
            item_name = view.findViewById(R.id.item_name);
            location = view.findViewById(R.id.loaction);
            qunatity = view.findViewById(R.id.qunatity);
//            parent = view.findViewById(R.id.parent);
        }

        public void setData(final SentModel sentModel) {
           added_date.setText(sentModel.getUpdatedDate());
           item_name.setText(sentModel.getEvolveItemName());
           qunatity.setText(String.valueOf(sentModel.getEvolveItemQty()));
           location.setText(sentModel.getEvolveLocationName());

           /* parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ItemInfo.class);
                    //intent.putExtra(INDTENTDATA,bedInfoModel);
                    context.startActivity(intent);
                }
            });*/
        }
    }
}

