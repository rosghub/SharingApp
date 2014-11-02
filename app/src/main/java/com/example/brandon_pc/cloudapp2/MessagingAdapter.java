package com.example.brandon_pc.cloudapp2;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class MessagingAdapter extends ArrayAdapter<Message> {
    private Context context;

    public MessagingAdapter(Context c, int textViewResourceId, List<Message> objects) {
        super(c, textViewResourceId, objects);
        context = c;
    }

    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.message_item, parent, false);
        }

        Message m = getItem(position);

        TextView tvMsg = (TextView)view.findViewById(R.id.tvMessage);
        tvMsg.setText(m.message);
        tvMsg.setGravity(Gravity.CENTER_VERTICAL | (m.sent ? Gravity.RIGHT : Gravity.LEFT));

        view.findViewById(R.id.arrow_left).setVisibility(m.sent ? View.INVISIBLE : View.VISIBLE);
        view.findViewById(R.id.arrow_right).setVisibility(m.sent ? View.VISIBLE : View.INVISIBLE);

        return view;
    }
}