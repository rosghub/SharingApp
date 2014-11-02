package com.example.brandon_pc.cloudapp2;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/* Contact list adapter
 * Todo:
 * get font on view inflate - remove from constructor
 * set textview font on view inflate
 */
public class ContactsAdapter extends ArrayAdapter<Contact> {
    private Context context;
    private int[] bgColors;
    private Typeface font;


    private static final int[] bgColorIds = {
            R.color.purple,
            R.color.blue,
            R.color.green,
            R.color.orange
    };

    public ContactsAdapter(Context c, int textViewResourceId, List<Contact> objects) {
        super(c, textViewResourceId, objects);

        context = c;
        font = MainActivity.getTypeFace(c);

        // init background colors
        bgColors = new int[bgColorIds.length];
        for (int i = 0; i < bgColors.length; ++i)
            bgColors[i] = context.getResources().getColor(bgColorIds[i]);
    }

    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            // inflate
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.contact_item, parent, false);
        }

        // set background
        view.setBackgroundColor(bgColors[position % bgColors.length]);

        Contact c = getItem(position);

        // contact name
        TextView tvContact = (TextView)view.findViewById(R.id.tvContact);
        tvContact.setText(c.name);
        tvContact.setTypeface(font);

        // set indicator color
        View vIndic = view.findViewById(R.id.indicator);
        GradientDrawable g = (GradientDrawable)vIndic.getBackground().mutate();
        Resources resources = context.getResources();

        if (c.days < resources.getInteger(R.integer.days_inactive))
            g.setColor(resources.getColor(R.color.g_indic));
        else if (c.days < resources.getInteger(R.integer.days_warning))
            g.setColor(resources.getColor(R.color.y_indic));
        else
            g.setColor(resources.getColor(R.color.r_indic));
        g.invalidateSelf();

        return view;
    }
}