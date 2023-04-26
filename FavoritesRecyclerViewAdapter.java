package com.example.ArtistSearch.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ArtistSearch.model.Artist;
import com.example.ArtistSearch.R;

import java.util.List;

public class FavoritesRecyclerViewAdapter extends RecyclerView.Adapter<FavoritesRecyclerViewAdapter.MyViewHolder>{

    private Context mContext;
    private List<Artist> mData;
    private OnNoteListener mOnListener;

    public FavoritesRecyclerViewAdapter(Context mContext, List<Artist> mData, OnNoteListener onListener) {
        this.mContext = mContext;
        this.mData = mData;
        this.mOnListener = onListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.favorites_row_item, parent, false);


        return new MyViewHolder(view, mOnListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tv_name.setText(mData.get(position).getName());
        holder.tv_nationality.setText(mData.get(position).getNationality());
        holder.tv_birthday.setText(mData.get(position).getBirthday());
//        holder.onListener.onNoteClickFav(position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tv_name ;
        TextView tv_nationality;
        TextView tv_birthday ;
        OnNoteListener onListener;

        public MyViewHolder(View itemView, OnNoteListener onListener) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.favorite_name);
            tv_birthday = itemView.findViewById(R.id.favorite_birthday);
            tv_nationality = itemView.findViewById(R.id.favorite_nationality);
            this.onListener = onListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view)  {
            onListener.onNoteClickFav(getAdapterPosition());
        }

    }
    public interface OnNoteListener{
        void onNoteClickFav(int position);

    }
}
