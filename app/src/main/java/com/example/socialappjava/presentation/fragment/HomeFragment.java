package com.example.socialappjava.presentation.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.socialappjava.databinding.HomeBinding;
import com.example.socialappjava.presentation.adapter.HomeAdapter;
import com.google.android.material.tabs.TabLayout;

public class HomeFragment extends Fragment {
    HomeBinding binding;
    FragmentStateAdapter homeAdapter;
    FragmentManager fManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = HomeBinding.inflate(inflater, container, false);
        fManager = getChildFragmentManager();
        homeAdapter = new HomeAdapter(fManager, getLifecycle());
        binding.pager.setAdapter(homeAdapter);

        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Posts"));
      //  binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Users"));

        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binding.pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        binding.pager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                binding.tabLayout.selectTab(binding.tabLayout.getTabAt(position));
            }
        });

        return binding.getRoot();
    }
}
