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

    public static final int CURRENT_TASK_FRAGMENT_POSITION  = 0;
    public static final int DONE_TASK_FRAGMENT_POSITION     = 1;

    private CurrentTaskFragment currentTaskFragment;
    private DoneTaskFragment    doneTaskFragment;

    private int numberOfTabs;

    public TabAdapter(FragmentManager   fragmentManager,
                      int               numberOfTabs) {

        super(fragmentManager);

        this.numberOfTabs = numberOfTabs;

        currentTaskFragment = new CurrentTaskFragment();
        doneTaskFragment    = new DoneTaskFragment();
    }

    @Override
    public Fragment getItem(int position) {

        switch(position) {

            case CURRENT_TASK_FRAGMENT_POSITION:
                return currentTaskFragment;
            case DONE_TASK_FRAGMENT_POSITION:
                return doneTaskFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}
