package com.lambton.lofterapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lambton.lofterapp.R;
import com.lambton.lofterapp.activities.PropertyDetailActivity;
import com.lambton.lofterapp.models.agents.AgentInfo;
import com.lambton.lofterapp.utils.CircleTransform;
import com.lambton.lofterapp.utils.CommonMethods;

import java.util.List;

public class AgentListRecyclerAdapter extends RecyclerView.Adapter<AgentListRecyclerAdapter.ViewHolder> {
    private List<AgentInfo> agentInfosList;
    private Context context;
    private boolean hasDistance;

    public AgentListRecyclerAdapter(Context context, List<AgentInfo> agentInfosList, boolean hasDistance) {
        this.context = context;
        this.agentInfosList = agentInfosList;
        this.hasDistance = hasDistance;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.list_item_agent_row, parent, false);
        return new ViewHolder(listItem);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final AgentInfo agentInfo = agentInfosList.get(position);
        holder.tvPropName.setText(agentInfo.getPropName());
        holder.tvPropPrice.setText(agentInfo.getPropPrice());
        holder.tvPropSize.setText(agentInfo.getPropSize());
        holder.tvPropAddress.setText(agentInfo.getPropAddress());
        holder.ivPropImg.setImageURI(Uri.parse(agentInfo.getPropImgPath()));

        String propType = agentInfo.getPropType();
        holder.tvPropType.setText(propType);
        if(propType.equals("RENT")){
            holder.tvPropType.setTextColor(Color.RED);
        }
    }

    @Override
    public int getItemCount() {
        return agentInfosList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView ivPropImg;
        private TextView tvPropName;
        private TextView tvPropPrice;
        private TextView tvPropAddress;
        private TextView tvPropType, tvPropSize;

        ViewHolder(View itemView) {
            super(itemView);
            this.ivPropImg = itemView.findViewById(R.id.iv_prop_img);
            this.tvPropName = itemView.findViewById(R.id.tv_prop_name);
            this.tvPropSize = itemView.findViewById(R.id.tv_prop_size);
            this.tvPropType = itemView.findViewById(R.id.tv_prop_type);
            this.tvPropPrice = itemView.findViewById(R.id.tv_price);
            this.tvPropAddress = itemView.findViewById(R.id.tv_prop_address);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            AgentInfo agentInfo = agentInfosList.get(getAdapterPosition());
            Intent intent = new Intent(context, PropertyDetailActivity.class);
            intent.putExtra("extra_property", agentInfo);
            context.startActivity(intent);
        }
    }
}