package com.example.brandon_pc.cloudapp2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class InboxFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.inbox_fragment, container, false);

        TextView tvEmpty = (TextView)view.findViewById(R.id.tvInboxEmpty);
        tvEmpty.setTypeface(MainActivity.getTypeFace(getActivity()));

        return view;
    }
}
