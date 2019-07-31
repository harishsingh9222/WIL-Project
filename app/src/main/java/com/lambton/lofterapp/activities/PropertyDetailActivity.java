package com.lambton.lofterapp.activities;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lambton.lofterapp.R;
import com.lambton.lofterapp.models.agents.AgentInfo;
import com.lambton.lofterapp.utils.CircleTransform;

public class PropertyDetailActivity extends AppCompatActivity {

    Toolbar tbPropDetail;
    CollapsingToolbarLayout ctlPropDetail;
    AppBarLayout appBarPropDetail;

    TextView tvPropName, tvpropAddress, tvPropSize, tvPropType, tvPropPrice;
    ImageView ivCollapseImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_detail);
        if (getIntent() == null || getIntent().getExtras() == null) {
            return;
        }
        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

    private void init() {
        tbPropDetail = findViewById(R.id.tb_prop_detail);
        ctlPropDetail = findViewById(R.id.ctl_prop_detail);
        appBarPropDetail = findViewById(R.id.apl_prop_detail);

        tvPropName = findViewById(R.id.tv_prop_name);
        tvpropAddress = findViewById(R.id.tv_prop_address);
        tvPropSize = findViewById(R.id.tv_prop_size);
        tvPropType = findViewById(R.id.tv_prop_type);
        tvPropPrice = findViewById(R.id.tv_prop_price);

        ivCollapseImage = findViewById(R.id.iv_collapse_image);

        setSupportActionBar(tbPropDetail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        AgentInfo agentInfo = (AgentInfo) getIntent().getSerializableExtra("extra_property");
        if(agentInfo != null){
            showTitleOnCollapse(agentInfo.getPropName());
            tvPropName.setText(agentInfo.getPropName());
            tvpropAddress.setText(agentInfo.getPropAddress());
            tvPropSize.setText(agentInfo.getPropSize());
            tvPropType.setText(agentInfo.getPropType());
            tvPropPrice.setText(agentInfo.getPropPrice());

            Glide.with(PropertyDetailActivity.this).load(agentInfo.getPropImgPath())
                    .crossFade()
                    .thumbnail(0.5f)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(ivCollapseImage);
        }
    }

    private void showTitleOnCollapse(final String strPropertyName) {
        appBarPropDetail.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    ctlPropDetail.setTitle(strPropertyName);
                    isShow = true;
                } else if (isShow) {
                    ctlPropDetail.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }
}
