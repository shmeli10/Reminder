package com.shmeli.reminder.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.shmeli.reminder.fragment.CurrentTaskFragment;
import com.shmeli.reminder.fragment.DoneTaskFragment;

/**
 * Created by Serghei Ostrovschi on 9/19/17.
 */

public class TabAdapter extends FragmentStatePagerAdapter {

    private int numberOfTabs;

    public TabAdapter(FragmentManager   fragmentManager,
                      int               numberOfTabs) {

        super(fragmentManager);

        this.numberOfTabs = numberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch(position) {

            case 0:
                return new CurrentTaskFragment();
            case 1:
                return new DoneTaskFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}
