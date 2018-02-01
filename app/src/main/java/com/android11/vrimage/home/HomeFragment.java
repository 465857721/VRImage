package com.android11.vrimage.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android11.vrimage.BuildConfig;
import com.android11.vrimage.R;
import com.android11.vrimage.find.bean.FindListBean;
import com.android11.vrimage.home.adapter.HomeListAdapter;
import com.android11.vrimage.main.BaseLazyFragment;
import com.android11.vrimage.main.VrImageDetailActivity;
import com.android11.vrimage.utils.Const;
import com.android11.vrimage.utils.GsonUtils;
import com.android11.vrimage.utils.Tools;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.qq.e.ads.banner.ADSize;
import com.qq.e.ads.banner.AbstractBannerADListener;
import com.qq.e.ads.banner.BannerView;
import com.qq.e.comm.util.AdError;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class HomeFragment extends BaseLazyFragment implements HomeListAdapter.OnItemClickLitener, SwipeRefreshLayout.OnRefreshListener, OnMoreListener {
    @BindView(R.id.listview)
    SuperRecyclerView listview;
    private List<FindListBean.PayloadBean.PostsBean> list = new ArrayList<>();
    private HomeListAdapter adapter;
    private int page = 0;
    private ViewGroup bannerContainer;
    private BannerView bv;

    @Override
    protected void initData() {
        adapter = new HomeListAdapter(list, getActivity());
        listview.setLayoutManager(new GridLayoutManager(getContext(), 1));
        listview.setAdapter(adapter);
        adapter.setOnItemClickLitener(this);
        listview.setRefreshListener(this);
        listview.setRefreshing(true);
        getData(page++);
        bannerContainer = mRootView.findViewById(R.id.bv);
        initAd();
    }

    @Override
    protected boolean isImmersionBarEnabled() {
        return false;
    }

    @Override
    protected int setLayoutId() {
        return R.layout.frag_home;
    }

    private void initAd() {


        initBanner();
        if (Tools.getAppMetaData(getContext(), "UMENG_CHANNEL").equals("vivo")
                || Tools.getAppMetaData(getContext(), "UMENG_CHANNEL").equals("oppo")
                || Tools.getAppMetaData(getContext(), "UMENG_CHANNEL").equals("ali")) {
            if (System.currentTimeMillis() - Long.valueOf(BuildConfig.releaseTime) < 2 * 24 * 60 * 60 * 1000) {
                bv.loadAD();
            }
        } else {
            bv.loadAD();
        }
    }

    private void initBanner() {
        //yyb
        // this.bv = new BannerView(this, ADSize.BANNER, "1101189414", "5040624571474334");
        //baidu
        this.bv = new BannerView(getActivity(), ADSize.BANNER, "1106343208", "4070833002664776");

        bv.setRefresh(30);
        bv.setADListener(new AbstractBannerADListener() {

//            @Override
//            public void onNoAD(int arg0) {
//                Log.d("zk","onNoAD");
//            }

            @Override
            public void onNoAD(AdError adError) {
                Log.e("AD_DEMO", "onNoAD");
            }

            @Override
            public void onADReceiv() {
                Log.e("AD_DEMO", "onADReceiv");
            }
        });
        bannerContainer.addView(bv);
    }

    private void getData(final int p) {
        OkGo.<String>get(Const.HOME)
                .params("offset", p)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e("zk ", response.body());
                        FindListBean bean = GsonUtils.getGsonInstance().fromJson(response.body(), FindListBean.class);
                        if (p == 0) {
                            list.clear();
                        }
                        list.addAll(bean.getPayload().getPosts());
                        adapter.notifyDataSetChanged();
                        if (listview == null)
                            return;
                        listview.setRefreshing(false);
                        if (bean.getPayload().getPosts().size() > 0) {
                            listview.setOnMoreListener(HomeFragment.this);
                        } else {
                            listview.removeMoreListener();
                        }
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent go = new Intent(getActivity(), VrImageDetailActivity.class);
        go.putExtra("url", String.format("https://files.kuula.io/%s/01-4096.jpg",
                list.get(position).getUuid()));
        go.putExtra("uuid", list.get(position).getUuid());
        startActivity(go);
    }

    @Override
    public void onRefresh() {
        page = 0;
        getData(page++);
    }

    @Override
    public void onMoreAsked(int i, int i1, int i2) {
        getData(page++);
    }
}
