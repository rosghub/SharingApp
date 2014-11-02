package com.example.brandon_pc.cloudapp2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity
        implements ViewPager.OnPageChangeListener, MainFragment.ActionListener, FragmentManager.OnBackStackChangedListener {

    private MainFragment mainFragment;
    private ContactsFragment contactsFragment, contactSelectionFragment;
    private InboxFragment inboxFragment;

    private ActionBarController action;
    private ArrayList<Contact> contacts;
    private int state;
    private String actionText;

    private static int STATE_HOME = 0x01, STATE_SELECTION = 0x02;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // init actionbar
        action = ActionBarController.init(getActionBar());

        // init viewpager fragments
        mainFragment = new MainFragment();
        mainFragment.setOnActionListener(this);

        contactsFragment = new ContactsFragment();
        Bundle args = new Bundle();
        args.putInt("bgColor", getResources().getColor(R.color.blue));
        contactsFragment.setArguments(args);

        inboxFragment = new InboxFragment();

        // init viewpager
        ArrayList<Fragment> fragmentList = new ArrayList<Fragment>();
        fragmentList.add(contactsFragment);
        fragmentList.add(mainFragment);
        fragmentList.add(inboxFragment);

        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1);
        viewPager.setOnPageChangeListener(this);

        contacts = new ArrayList<Contact>();
        // sample contacts
        for (int i = 1; i <= 6; ++i) {
            Contact c = new Contact("Contact " + i);
            c.days = i - 1;

            contacts.add(c);
        }

        getSupportFragmentManager().addOnBackStackChangedListener(this);

        state = STATE_HOME;
    }

    public static Typeface getTypeFace(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "fonts/montserrat.ttf");
    }

    private void startMessaging(Contact c) {
        Intent intent = new Intent(this, MessagingActivity.class);
        intent.putExtra(getResources().getString(R.string.contact_id), c);
        startActivity(intent);
    }

    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        }
        else
            super.onBackPressed();
    }

    public ArrayList<Contact> getContacts() {
        return contacts;
    }

    public void actionBarBackClick(View view) {
        onBackPressed();
    }

    public void actionBarContactsClick(View view) {
        ((ViewPager)findViewById(R.id.pager)).setCurrentItem(0);
    }

    public void actionBarHomeClick(View view) {
        ((ViewPager)findViewById(R.id.pager)).setCurrentItem(1);
    }

    public void actionBarInboxClick(View view) {
        ((ViewPager)findViewById(R.id.pager)).setCurrentItem(2);
    }

    public void updateActionView() {
        if (state == STATE_HOME) {
            action.setTitle(false, null);
            action.setBack(false);
            action.setTabs(true);
        }
        else {
            action.setTabs(false);
            action.setTitle(true, actionText);
            action.setBack(true);
        }
    }

    // PageChangedListener
    @Override
    public void onPageScrolled(int i, float v, int i2) {
    }

    @Override
    public void onPageSelected(int i) {
        action.setTab(i);
    }

    @Override
    public void onPageScrollStateChanged(int i) {  }

    // MainFragment.ActionListener
    @Override
    public void onMessagingClick() {
        state = STATE_SELECTION;
        actionText = "Message";
        updateActionView();

        startContactsPick(R.anim.msg_show, R.anim.msg_hide, getResources().getColor(R.color.blue),
                new ContactsFragment.ContactSelectedListener() {
                    public void onContactSelected(Contact c) {
                        startMessaging(c);
                    }
                });
    }

    @Override
    public void onPhotosClick() {
        state = STATE_SELECTION;
        actionText = "Photos";
        updateActionView();

        startContactsPick(R.anim.pics_show, R.anim.pics_hide, getResources().getColor(R.color.green), null);
    }

    @Override
    public void onPokeClick() {
        state = STATE_SELECTION;
        actionText = "Poke";
        updateActionView();

        startContactsPick(R.anim.poke_show, R.anim.poke_hide, getResources().getColor(R.color.purple), null);
    }

    private void startContactsPick(int animShow, int animExit, int bgColor,
                                  ContactsFragment.ContactSelectedListener listener) {
        Bundle args = new Bundle();
        args.putInt("bgColor", bgColor);

        contactSelectionFragment = new ContactsFragment();
        contactSelectionFragment.setArguments(args);
        contactSelectionFragment.setContactSelectedListener(listener);

        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(animShow, animExit, animShow, animExit)
                .replace(R.id.frameLayout, contactSelectionFragment)
                .addToBackStack(null)
                .commit();
    }

    // BackStackChangedListener
    @Override
    public void onBackStackChanged() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            state = STATE_HOME;
            updateActionView();
        }
    }
}
