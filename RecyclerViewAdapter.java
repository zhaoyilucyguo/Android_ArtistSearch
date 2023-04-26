package com.example.ArtistSearch.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.ArtistSearch.model.Search;
import com.example.ArtistSearch.R;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>{
    @NonNull
    private Context mContext;
    private List<Search> mData;
    private OnNoteListener mOnListener;

    RequestOptions option;

    public RecyclerViewAdapter(@NonNull Context mContext, List<Search> mData, OnNoteListener onListener) {
        this.mContext = mContext;
        this.mData = mData;
        this.mOnListener = onListener;

        // request option for Glide
        option = new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.search_row_item, parent, false);
        return new MyViewHolder(view, mOnListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tv_title.setText(mData.get(position).getTitle());
        Log.d("hello", "onBindViewHolder: " + mData.get(position).getImg_href());
        if (mData.get(position).getImg_href() != null) {
        // Glide
            Glide.with(mContext).load(mData.get(position).getImg_href()).apply(option).into(holder.tv_img_href);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_title;
        ImageView tv_img_href;
        OnNoteListener onListener;

        public MyViewHolder(@NonNull View itemView, OnNoteListener onListener) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.search_title);
            tv_img_href = itemView.findViewById(R.id.search_image);
            this.onListener = onListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onListener.onNoteClick(getAdapterPosition());
        }
    }

    public interface OnNoteListener{
        void onNoteClick(int position);

    }
}
