package com.example.brandon_pc.cloudapp2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MainFragment extends Fragment implements PieView.PieClickListener {
    private ActionListener listener;

    public interface ActionListener {
        public void onMessagingClick();
        public void onPhotosClick();
        public void onPokeClick();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment, container, false);

        PieView pieView = (PieView)view.findViewById(R.id.pieView);
        pieView.setOnPieClickListener(this);

        return view;
    }

    public void setOnActionListener(ActionListener l) { listener = l; }

    @Override
    public void onLeftClick() {
        listener.onMessagingClick();
    }

    @Override
    public void onBottomClick() {
        listener.onPokeClick();
    }

    @Override
    public void onRightClick() {
        listener.onPhotosClick();
    }
}
