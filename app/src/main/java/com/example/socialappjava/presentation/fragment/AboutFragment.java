package com.example.socialappjava.presentation.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.socialappjava.databinding.AboutBinding;

public class AboutFragment extends Fragment {
AboutBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
binding=AboutBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }
}
