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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
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
    private Context context;

    public interface OnItemClickListener {
        void onItemClick(View view, int i);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setOnFocusChangeListener(View.OnFocusChangeListener listener) {
        this.focusChangeListener = listener;
    }

    public ChannelAdapter(Context context,GetChannelResponse getChannelResponse) {
        this.getChannelResponse = getChannelResponse;
        this.context =context;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View channelView = inflater.inflate(R.layout.channel_adapter, parent, false);
        return new ViewHolder(channelView);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        M3UItem channel = this.mChannels.get(position);
        holder.cardChannel.setCardBackgroundColor(ContextCompat.getColor(context, R.color.transparent));
        holder.cardChannel.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    holder.cardChannel.setCardBackgroundColor(ContextCompat.getColor(context, R.color.card_focused));
                    holder.txtChannel.setTranslationX(40);
                } else {
                    holder.cardChannel.setCardBackgroundColor(ContextCompat.getColor(context, R.color.transparent));
                    holder.txtChannel.setTranslationX(0);
                }
                View.OnFocusChangeListener onFocusChangeListener = focusChangeListener;
                if (onFocusChangeListener != null) {
                    onFocusChangeListener.onFocusChange(v, hasFocus);
                }
            }
        });
        holder.txtChannel.setText(getChannelResponse.getData().get(position).getName());

    }

    @Override
    public int getItemCount() {
        return getChannelResponse.getData().size();
    }

    public void setPrimaryColor(String color) {
        this.primaryColor = color;
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cardChannel;
        TextView txtChannel;

        public ViewHolder(View itemView) {
            super(itemView);
            Button button = (Button) itemView.findViewById(R.id.channel_name);
            cardChannel = itemView.findViewById(R.id.card_channel);
            txtChannel = itemView.findViewById(R.id.txt_channel);
            cardChannel.setOnClickListener(this);


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
