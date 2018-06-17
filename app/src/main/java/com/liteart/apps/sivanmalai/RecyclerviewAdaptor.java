package com.liteart.apps.sivanmalai;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.liteart.apps.sivanmalai.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by Admin on 7/28/2017.
 */

public class RecyclerviewAdaptor extends RecyclerView.Adapter<RecyclerviewAdaptor.ViewHolder> {

    private List<CommonModel> list;
    Context context;


    public RecyclerviewAdaptor(ArrayList<CommonModel> list, Context context) {
        super();
        this.context=context;
        this.list=list;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.cards_layout, null);

        // create ViewHolder

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.long_text.setText(list.get(position).getLong_text());
        SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat targetFormat = new SimpleDateFormat("dd-MM-yyyy h:mm a");
        Date date = null;
        try {
            date = originalFormat.parse(list.get(position).getDate_updated());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formattedDate = targetFormat.format(date);
        holder.timestamp.setText(formattedDate);
      //  DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy h:mm a");

    }

    @Override
    public int getItemCount() {
        return (list == null) ? 0 : list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView long_text;
        public TextView timestamp;


        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.cards_layout, parent, false));


        }
        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            long_text = (TextView) itemView.findViewById(R.id.long_text);
            timestamp = (TextView) itemView.findViewById(R.id.card_date_updated);

            itemLayoutView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(v.getContext(), NewsDesActivity.class);
                    intent.putExtra("description",long_text.getText().toString());
                    intent.putExtra("date",timestamp.getText().toString());
                    v.getContext().startActivity(intent);
                   // Toast.makeText(v.getContext(), "os version is: "+long_text.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}

