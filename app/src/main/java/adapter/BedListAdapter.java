package adapter;


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
import com.alitersolutions.evolveyork.activity.BedInfo;
import com.alitersolutions.evolveyork.model.BedInfoModel;

import java.util.List;

import static com.alitersolutions.evolveyork.utils.Constants.INDTENTDATA;

public class BedListAdapter extends RecyclerView.Adapter<BedListAdapter.BedListViewHolder>{
    List<BedInfoModel> bedInfos;
    Context context;

    public BedListAdapter(Context context, List<BedInfoModel> bedInfos) {
        this.context = context;
        this.bedInfos = bedInfos;
    }

    @NonNull
    @Override
    public BedListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.bed_item_list,parent,false);
        return new BedListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BedListViewHolder holder, int position) {
        holder.setData(bedInfos.get(position));
    }

    @Override
    public int getItemCount() {
        return bedInfos.size();
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

        public void setData(final BedInfoModel bedInfoModel) {
            key.setText(bedInfoModel.getEvolveBed_Code());
            String status = "OUT";
            if (bedInfoModel.getEvolveBed_Status()){
                status = "IN";
            }
            value.setText(status);
            parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, BedInfo.class);
                    intent.putExtra(INDTENTDATA,bedInfoModel);
                    context.startActivity(intent);
                }
            });
        }
    }
}
