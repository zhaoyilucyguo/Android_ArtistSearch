package com.example.ArtistSearch.adapters;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.ArtistSearch.R;

import com.example.ArtistSearch.model.Artworks;

import java.util.List;

public class ArtworksRecyclerViewAdapter extends RecyclerView.Adapter<ArtworksRecyclerViewAdapter.MyViewHolder> {
    private Context mContext ;
    private List<Artworks> mData;
    RequestOptions option;
    private OnNoteListener mOnListener;

    public ArtworksRecyclerViewAdapter(Context mContext, List<Artworks> mData, OnNoteListener onListener) {
        this.mContext = mContext;
        this.mData = mData;
        option = new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape_artworks).error(R.drawable.loading_shape_artworks);
        this.mOnListener = onListener;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view ;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.artworks_row_item, parent, false);

        return new MyViewHolder(view, mOnListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tv_title.setText(mData.get(position).getTitle());

        Glide.with(mContext).load(mData.get(position).getHref()).apply(option).into(holder.iv_href);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView tv_title ;
        ImageView iv_href ;
        OnNoteListener onListener;
        public MyViewHolder(View itemView, OnNoteListener onListener) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.artworks_title);
            iv_href = itemView.findViewById(R.id.artworks_href);
            this.onListener = onListener;
            itemView.setOnClickListener(this);
        }

        public void onClick(View view) {

            onListener.onNoteClick(getAdapterPosition());
        }
    }

    public interface OnNoteListener{
        void onNoteClick(int position);

    }
}
