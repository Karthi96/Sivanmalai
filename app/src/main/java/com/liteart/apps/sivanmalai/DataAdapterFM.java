package com.liteart.apps.sivanmalai;

/**
 * Created by magudesh on 11/23/17.
 */
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.liteart.apps.sivanmalai.R;

import java.util.ArrayList;

public class DataAdapterFM extends RecyclerView.Adapter<DataAdapterFM.ViewHolder> {
    private ArrayList<String> dataString;

    public DataAdapterFM(ArrayList<String> dataString) {
        this.dataString = dataString;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_row_fm, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {

        viewHolder.tv_country.setText(dataString.get(i));

    }

    @Override
    public int getItemCount() {
        return dataString.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_country;

        public ViewHolder(View view) {
            super(view);

            tv_country = (TextView)view.findViewById(R.id.tv_dataKavithaigal);

        }
    }

}