package com.example.ArtistSearch.adapters;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.ArtistSearch.ArtistFragment;
import com.example.ArtistSearch.ArtworksFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {
    private String id;
    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, String id) {
        super(fragmentActivity);
        this.id=id;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch(position) {
            case 0:
                return new ArtistFragment(id);
            default:
                return new ArtworksFragment(id);
        }
    }
    @Override
    public int getItemCount() {
        return 2;
    }
}
