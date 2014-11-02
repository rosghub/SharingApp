package com.example.brandon_pc.cloudapp2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class ContactsFragment extends Fragment implements Animation.AnimationListener {
    private ContactSelectedListener mListener;

    public interface ContactSelectedListener {

        // contact selected event
        public void onContactSelected(Contact c);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contacts_fragment, container, false);

        Bundle args = getArguments();

        // background color
        int bgColor = args.getInt(getString(R.string.bgcolor));
        view.setBackgroundColor(bgColor);

        // contacts adapter
        ArrayList<Contact> contacts = args.getParcelableArrayList(getString(R.string.contact_list));
        ContactsAdapter adapter = new ContactsAdapter(getActivity(), android.R.layout.simple_list_item_1, contacts);

        // init listview
        ListView listView = (ListView)view.findViewById(R.id.listViewContacts);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mListener != null)
                    mListener.onContactSelected((Contact)parent.getItemAtPosition(position));
            }
        });

        return view;
    }

    public void setContactSelectedListener(ContactSelectedListener l) {
        mListener = l;
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        Animation anim = super.onCreateAnimation(transit, enter, nextAnim);

        if (anim == null && nextAnim != 0) {
            anim = AnimationUtils.loadAnimation(getActivity(), nextAnim);

            /* listener for show animation
             * (to hide list during animation)
             */
            if (enter)
                anim.setAnimationListener(this);

            else // exit animation starting
                getView().findViewById(R.id.listViewContacts).setVisibility(View.INVISIBLE);
        }
        return anim;
    }

    // AnimationListener
    @Override
    public void onAnimationStart(Animation animation) {
        getView().findViewById(R.id.listViewContacts).setVisibility(View.INVISIBLE);
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        getView().findViewById(R.id.listViewContacts).setVisibility(View.VISIBLE);
    }

    @Override
    public void onAnimationRepeat(Animation animation) { }
}
