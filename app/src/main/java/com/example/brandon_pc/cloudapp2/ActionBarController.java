package com.example.brandon_pc.cloudapp2;

import android.app.ActionBar;
import android.view.View;
import android.widget.TextView;

/* For controlling actionbar custom views buttons/tabs
 * Use: controller = ActionBarController.init(getActionBar());
 */
public class ActionBarController {
    private View view;

    private ActionBarController(View v) {
        view = v;
    }

    // init actionbar
    public static ActionBarController init(ActionBar actionBar) {
        // set custom view
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.actionbar_layout);

        // set textview font
        View v = actionBar.getCustomView();
        ((TextView)v.findViewById(R.id.tvActionBar)).setTypeface(MainActivity.getTypeFace(v.getContext()));

        return new ActionBarController(v);
    }

    // show/hide tab buttons
    public void setTabs(boolean show) {
        int visibility = (show ? View.VISIBLE : View.INVISIBLE);
        view.findViewById(R.id.actionContacts).setVisibility(visibility);
        view.findViewById(R.id.actionHome).setVisibility(visibility);
        view.findViewById(R.id.actionInbox).setVisibility(visibility);
    }

    // set current tab
    public void setTab(int i) {
        if (i == 0) {
            view.findViewById(R.id.actionContacts).setAlpha(1);
            view.findViewById(R.id.actionHome).setAlpha(.5f);
            view.findViewById(R.id.actionInbox).setAlpha(.5f);
            setNew(true);
        }
        else if (i == 1) {
            view.findViewById(R.id.actionContacts).setAlpha(.5f);
            view.findViewById(R.id.actionHome).setAlpha(1);
            view.findViewById(R.id.actionInbox).setAlpha(.5f);
            setNew(false);
        }
        else { // i == 2
            view.findViewById(R.id.actionContacts).setAlpha(.5f);
            view.findViewById(R.id.actionHome).setAlpha(.5f);
            view.findViewById(R.id.actionInbox).setAlpha(1);
            setNew(false);
        }
    }

    // show/hide title
    // use: setTitle(true, title) / setTitle(false, null)
    public void setTitle(boolean show, String title) {
        TextView tv = (TextView)view.findViewById(R.id.tvActionBar);
        if (show) {
            tv.setVisibility(View.VISIBLE);
            tv.setText(title);
        }
        else
            tv.setVisibility(View.INVISIBLE);
    }

    // show/hide new button
    public void setNew(boolean show) {
        view.findViewById(R.id.actionNew).setVisibility(show ? View.VISIBLE : View.GONE);
    }

    // show/hide back button
    public void setBack(boolean show) {
        view.findViewById(R.id.actionBack).setVisibility(show ? View.VISIBLE : View.GONE);
    }
}
