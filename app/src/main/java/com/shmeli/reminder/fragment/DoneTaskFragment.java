package com.shmeli.reminder.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shmeli.reminder.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class DoneTaskFragment extends Fragment {

    private RecyclerView                rvDoneTasks;

    private RecyclerView.LayoutManager  rvLayoutManager;

    public DoneTaskFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_done_task, container, false);

        rvLayoutManager = new LinearLayoutManager(getActivity());

        rvDoneTasks = (RecyclerView) rootView.findViewById(R.id.rvDoneTasks);
        rvDoneTasks.setLayoutManager(rvLayoutManager);

        return rootView;
    }

}
