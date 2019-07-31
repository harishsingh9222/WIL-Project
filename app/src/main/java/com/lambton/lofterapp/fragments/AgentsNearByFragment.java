package com.lambton.lofterapp.fragments;

import android.annotation.SuppressLint;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lambton.lofterapp.R;
import com.lambton.lofterapp.adapters.NearByAgentsRecyclerAdapter;
import com.lambton.lofterapp.database.DatabaseHelper;
import com.lambton.lofterapp.models.agents.AgentInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Create this class to display Contact Us
 * functionality of this Golf Club.
 */
public class AgentsNearByFragment extends Fragment {

    /*********************************
     * INSTANCES OF CLASSES
     *******************************/
    View viewRootFragment;
    RecyclerView rvAgentList;
    DatabaseHelper databaseHelper;
    List<AgentInfo> agentInfoList = new ArrayList<>();

    double myLattitude = 42.9740891;
    double myLongitude = -82.3485736;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewRootFragment = inflater.inflate(R.layout.fragment_agents_nearby, container, false);

        rvAgentList = viewRootFragment.findViewById(R.id.rv_agent_list);

        databaseHelper = new DatabaseHelper(getActivity());
        agentInfoList.addAll(databaseHelper.getAllLofterAgents());
        if (agentInfoList.size() > 0) {
            List<AgentInfo> filterNearByAgents = new ArrayList<>();
            for (int iCount = 0; iCount < agentInfoList.size(); iCount++) {
                AgentInfo agentInfo = agentInfoList.get(iCount);
                Location startPoint = new Location("locationA");
                startPoint.setLatitude(myLattitude);
                startPoint.setLongitude(myLongitude);

                Location endPoint = new Location("locationA");
                endPoint.setLatitude(agentInfo.getLlat());
                endPoint.setLongitude(agentInfo.getLlaong());

                double distance = (startPoint.distanceTo(endPoint) / 1000);//In Kms
                Log.e("Distance", "" + distance);
                if(distance <= 250) {
                    agentInfo.setDistance(distance);
                    filterNearByAgents.add(agentInfo);
                }
            }

            @SuppressLint("CutPasteId")
            RecyclerView rvAgentList = viewRootFragment.findViewById(R.id.rv_agent_list);
            NearByAgentsRecyclerAdapter adapter = new NearByAgentsRecyclerAdapter(getActivity(), filterNearByAgents, true);
            rvAgentList.setHasFixedSize(true);
            rvAgentList.setLayoutManager(new LinearLayoutManager(getActivity()));
            /*rvAgentList.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getActivity())
                    , LinearLayoutManager.VERTICAL));*/
            rvAgentList.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }

        return viewRootFragment;
    }
}