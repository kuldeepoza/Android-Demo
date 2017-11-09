package com.wmt.android.demobaseproject.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wmt.android.demobaseproject.R;

import java.util.ArrayList;

/**
 * Created by KD on 08-Nov-17.
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    ArrayList list;
    Context c;
    public MainAdapter(Context c,ArrayList list)
    {
            this.c=c;
            this.list=list;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_list,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MainAdapter.ViewHolder holder, int position) {
                String str= (String) list.get(position);
                holder.t.setText(str);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView t;

        public ViewHolder(View v) {
            super(v);
            t=(TextView)v.findViewById(R.id.tvList);
        }
    }
}
