package com.lambton.lofterapp.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lambton.lofterapp.R;
import com.lambton.lofterapp.adapters.AgentListRecyclerAdapter;
import com.lambton.lofterapp.database.DatabaseHelper;
import com.lambton.lofterapp.models.agents.AgentInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Create this class to display Contact Us
 * functionality of this Golf Club.
 */
public class LoftListingFragment extends Fragment {

    /*********************************
     * INSTANCES OF CLASSES
     *******************************/
    View viewRootFragment;
    RecyclerView rvAgentList;
    DatabaseHelper databaseHelper;
    List<AgentInfo> agentInfoList = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewRootFragment = inflater.inflate(R.layout.fragment_loft_listings,
                container,
                false);
        rvAgentList = viewRootFragment.findViewById(R.id.rv_agent_list);

        databaseHelper = new DatabaseHelper(getActivity());
        agentInfoList.addAll(databaseHelper.getAllLofterAgents());
        if(agentInfoList.size()>0){
            @SuppressLint("CutPasteId")
            RecyclerView rvAgentList = viewRootFragment.findViewById(R.id.rv_agent_list);
            AgentListRecyclerAdapter adapter = new AgentListRecyclerAdapter(getActivity(), agentInfoList, false);
            rvAgentList.setHasFixedSize(true);
            rvAgentList.setLayoutManager(new LinearLayoutManager(getActivity()));
            rvAgentList.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getActivity())
                    , LinearLayoutManager.VERTICAL));
            rvAgentList.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }

        return viewRootFragment;
    }
}