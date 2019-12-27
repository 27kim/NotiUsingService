package com.d27.adjoe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {
    public Context mContext;
    private List<AlbumsData> mData;
    public RecyclerViewAdapter(Context context, List<AlbumsData> data){
        mContext = context;
        mData = data;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.recyclerview_item,parent,false);
        return new RecyclerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        AlbumsData data = mData.get(position);
        holder.text.setText(data.title + " "+data.id);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView text;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            text = (TextView)itemView.findViewById(R.id.title);
        }
    }
}
