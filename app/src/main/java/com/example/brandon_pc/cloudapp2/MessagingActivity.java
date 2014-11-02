package com.example.brandon_pc.cloudapp2;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import java.util.ArrayList;

public class MessagingActivity extends Activity {
    private Contact contact;
    private ArrayList<Message> messages; // message log
    private ActionBarController action;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging);

        // get contact list
        contact = getIntent().getParcelableExtra(getString(R.string.contact));

        // setup actionbar
        action = ActionBarController.init(getActionBar());
        action.setTabs(false);
        action.setBack(true);
        action.setTitle(true, contact.name);

        // edittext focus
        EditText editText = (EditText)findViewById(R.id.editTextMessage);
        editText.requestFocus();

        // show keyboard
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,0);

        // sample data
        messages = new ArrayList<Message>();
        for (int i = 1; i <= 6; ++i)
            messages.add(new Message("Message " + i, i % 2 == 0));

        // init messages listview
        ListView listView = (ListView)findViewById(R.id.listViewMessages);
        MessagingAdapter adapter = new MessagingAdapter(this, android.R.layout.simple_list_item_1, messages);
        listView.setAdapter(adapter);
    }

    // send message click
    public void btnSendClick(View view) {
        EditText editText = (EditText)findViewById(R.id.editTextMessage);
        String msg = editText.getText().toString().trim();
        if (!msg.isEmpty()) {
            // send message here...

            editText.setText(""); // clear edittext

            messages.add(new Message(msg, true)); // add to message log

            // update message list
            ListView listView = (ListView)findViewById(R.id.listViewMessages);
            ((ArrayAdapter)listView.getAdapter()).notifyDataSetChanged();
        }
    }

    public void actionBarBackClick(View view) {
        finish();
    }
}
