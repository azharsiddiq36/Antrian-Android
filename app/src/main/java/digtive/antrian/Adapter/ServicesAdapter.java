package digtive.antrian.Adapter;


import android.content.Context;
import android.content.Intent;

import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import android.widget.TextView;



import java.util.ArrayList;
import java.util.HashMap;

import digtive.antrian.Activity.MainActivity;
import digtive.antrian.Model.Services;
import digtive.antrian.R;
public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.ViewHolder>{
    private ArrayList<Services> rvData;
    Context context;
    public ServicesAdapter(Context context, ArrayList<Services> inputData){
        this.context = context;
        rvData=inputData;
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tvServicesName;
        public LinearLayout lyContainer;
        public ViewHolder(View v){
            super(v);
            tvServicesName = (TextView) v.findViewById(R.id.tvServicesName);
            lyContainer=(LinearLayout) v.findViewById(R.id.lyContainer);


        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.services_list, parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int pofgdion) {
        final Services Services = rvData.get(pofgdion);
        holder.tvServicesName.setText(Services.getLayananNama());
        holder.lyContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotomain = new Intent(context,MainActivity.class);
                gotomain.putExtra("services_id",Services.getLayananId());
                gotomain.putExtra("services_name",Services.getLayananNama());
                context.startActivity(gotomain);
            }

        });

    }

    @Override
    public int getItemCount() {
        return rvData.size();
    }




}