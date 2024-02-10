package com.google.fajarpro.adapter;
/*
Author  : Nurul Fajar
Email   : cirebonredhat@gmail.com
*/


import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.fajarpro.R;
import com.google.fajarpro.response.GetChannelResponse;

public class ChannelAdapter extends RecyclerView.Adapter<ChannelAdapter.ViewHolder> {
    public boolean disable;
    private View.OnFocusChangeListener focusChangeListener;
    private OnItemClickListener listener;
//    private final List<M3UItem> mChannels;
    private String primaryColor = "#000000";
    private GetChannelResponse getChannelResponse;

    public interface OnItemClickListener {
        void onItemClick(View view, int i);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setOnFocusChangeListener(View.OnFocusChangeListener listener) {
        this.focusChangeListener = listener;
    }

    public ChannelAdapter(GetChannelResponse getChannelResponse) {
        this.getChannelResponse = getChannelResponse;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View channelView = inflater.inflate(R.layout.channel_item, parent, false);
        return new ViewHolder(channelView);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        M3UItem channel = this.mChannels.get(position);
        Button button = holder.channel;
        button.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#000000")));
        button.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    String str = primaryColor;
                    if (str != "#000000" && str.length() == 7) {
                        v.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(primaryColor)));
                    } else {
                        v.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#9e8a4c")));
                    }
                } else {
                    v.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#000000")));
                }
                View.OnFocusChangeListener onFocusChangeListener = focusChangeListener;
                if (onFocusChangeListener != null) {
                    onFocusChangeListener.onFocusChange(v, hasFocus);
                }
            }
        });
        button.setText(getChannelResponse.getData().get(position).getName());

    }

    @Override
    public int getItemCount() {
        return getChannelResponse.getData().size();
    }

    public void setPrimaryColor(String color) {
        this.primaryColor = color;
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public Button channel;

        public ViewHolder(View itemView) {
            super(itemView);
            Button button = (Button) itemView.findViewById(R.id.channel_name);
            this.channel = button;
            button.setOnClickListener(this);


        }


        @Override
        public void onClick(View v) {
            int position;
            if (ChannelAdapter.this.listener != null && (position = getAbsoluteAdapterPosition()) != -1) {
                ChannelAdapter.this.listener.onItemClick(this.itemView, position);
            }

        }
    }

}
