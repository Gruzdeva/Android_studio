package com.example.lab2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class FragmentPager extends Fragment {

    private int position;
    private TextView textView;
    private Button button;
    public View view;

    public FragmentPager(int position) {
        this.position = position;
    }

    public static FragmentPager newInstance(int position) {
        FragmentPager fragmentPager = new FragmentPager(position);
        return fragmentPager;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_pager, parent, false);
        textView = view.findViewById(R.id.textView);
        button = view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DownloadImage((ImageView) view.findViewById(R.id.imageView)).execute("https://a.wattpad.com/cover/84608722-352-k886345.jpg");
            }
        });
        textView.setText(String.valueOf(position  + 1));
        return view;
    }
}
