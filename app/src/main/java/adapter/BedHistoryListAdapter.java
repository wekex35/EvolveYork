package adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alitersolutions.evolveyork.R;
import com.alitersolutions.evolveyork.model.BedHistoryModel;

import java.util.List;

import static android.view.View.GONE;
import static com.alitersolutions.evolveyork.utils.AppUtils.getDurationFromSeconds;
import static com.alitersolutions.evolveyork.utils.AppUtils.getParsedDate;

public class BedHistoryListAdapter extends RecyclerView.Adapter<BedHistoryListAdapter.BedListViewHolder>{
    List<BedHistoryModel> bedInfos;
    Context context;

    public BedHistoryListAdapter(Context context, List<BedHistoryModel> bedInfos) {
        this.context = context;
        this.bedInfos = bedInfos;
    }

    @NonNull
    @Override
    public BedListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.history_bed_item_list,parent,false);
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
        TextView in_time,out_time,bed_code,duration;
//        LinearLayout parent;


        public BedListViewHolder(@NonNull View view) {
            super(view);
            bed_code = view.findViewById(R.id.bed_code);
            in_time = view.findViewById(R.id.in_time);
            out_time = view.findViewById(R.id.out_time);
            duration = view.findViewById(R.id.duration);
//            parent = view.findViewById(R.id.parent);
        }

        public void setData(final BedHistoryModel bedInfoModel) {
            bed_code.setText(String.valueOf(bedInfoModel.getEvolveBed_ID()));
            bed_code.setVisibility(GONE);

            if (bedInfoModel.getEvolveBedHistory_InTime() != null) {
                String[] indTime = bedInfoModel.getEvolveBedHistory_InTime().split("T");
                in_time.setText(getParsedDate(indTime[0]) + "/" + indTime[1].replace(".000Z", ""));
            }

            if (bedInfoModel.getEvolveBedHistory_OutTime() != null) {

            String[] outTime = bedInfoModel.getEvolveBedHistory_OutTime().split("T");
            out_time.setText(getParsedDate(outTime[0]) + "/" + outTime[1].replace(".000Z", ""));
            }

            if (bedInfoModel.getEvolveBedHistory_Duration() != null) {

            int durationTime = Integer.parseInt(bedInfoModel.getEvolveBedHistory_Duration());
            duration.setText(getDurationFromSeconds(durationTime));

            }

           /* parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, BedInfo.class);
                    //intent.putExtra(INDTENTDATA,bedInfoModel);
                    context.startActivity(intent);
                }
            });*/
        }
    }
}
