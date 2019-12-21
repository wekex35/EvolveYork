package com.alitersolutions.evolveyork.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.alitersolutions.evolveyork.R;
import com.alitersolutions.evolveyork.Retrofit.RetrofitUtil;
import com.alitersolutions.evolveyork.activity.CycleCountActivity;
import com.alitersolutions.evolveyork.activity.CycleHistory;
import com.alitersolutions.evolveyork.model.ResponseModel;
import com.alitersolutions.evolveyork.model.SentModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CycleHistoryAdapter extends RecyclerView.Adapter<CycleHistoryAdapter.CHAViewHolder> {
    ArrayList<SentModel> sentModel;
    Context context;
    String TAG  = "CycleHistoryAdapter";

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
        ImageView menu_setting;
//        LinearLayout parent;


        public CHAViewHolder(@NonNull View view) {
            super(view);
            added_date = view.findViewById(R.id.added_date);
            item_name = view.findViewById(R.id.item_name);
            location = view.findViewById(R.id.loaction);
            qunatity = view.findViewById(R.id.qunatity);
            menu_setting = view.findViewById(R.id.menu_setting);
//          parent = view.findViewById(R.id.parent);
        }

        public void setData(final SentModel sentModel) {
           added_date.setText(sentModel.getUpdatedDate());
           item_name.setText(sentModel.getEvolveItemName());
           qunatity.setText(String.valueOf(sentModel.getEvolveItemQty()));
           location.setText(sentModel.getEvolveLocationName());
           menu_setting.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   device_menu_list(v,sentModel);
               }
           });

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


    public void device_menu_list(View view, final SentModel sentModel) {
        //String ir_index = view.getTag().toString();
        PopupMenu popup = new PopupMenu(context, view);
        //Inflating the Popup using xml file
        popup.getMenuInflater()
                .inflate(R.menu.cycle_history_menu, popup.getMenu());

        //registering popup with OnMenuItemClickListener

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.reprint :
                        Log.d(TAG, "Print ");
                        break;
                    case R.id.upload :
                        Log.d(TAG, "Upload");
                        RetrofitUtil.createProviderAPI().sentToServer(sentModel).enqueue(uploadToserver());

                        break;
                }

                return true;
            }
        });
        popup.show();

    }

    private Callback<ResponseModel> uploadToserver() {
        ((CycleHistory)context).showProgressDialog();

        return new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                if(response.body().getMessage().toLowerCase().contains("success")){
                    Toast.makeText(context, "Uploading Success", Toast.LENGTH_SHORT).show();
                }
                ((CycleHistory)context).hideProgressDialog();
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                ((CycleHistory)context).logError("bedAdded",t.getMessage());
                ((CycleHistory)context).hideProgressDialog();
            }
        };
    }

}

