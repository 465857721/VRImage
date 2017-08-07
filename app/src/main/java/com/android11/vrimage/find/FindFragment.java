package com.android11.vrimage.find;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android11.vrimage.R;
import com.android11.vrimage.find.adapter.FindListAdapter;
import com.android11.vrimage.find.bean.FindListBean;
import com.android11.vrimage.main.VrImageDetailActivity;
import com.android11.vrimage.utils.Const;
import com.android11.vrimage.utils.GsonUtils;
import com.jaeger.library.StatusBarUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class FindFragment extends Fragment implements FindListAdapter.OnItemClickLitener, SwipeRefreshLayout.OnRefreshListener,OnMoreListener {
    @Bind(R.id.listview)
    SuperRecyclerView listview;
    private List<FindListBean.PayloadBean.PostsBean> list = new ArrayList<>();
    private FindListAdapter adapter;
    private int page = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.frag_find, null);
//        StatusBarUtil.setColor(getActivity(), ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
        ButterKnife.bind(this, v);


        adapter = new FindListAdapter(list, getActivity());
        listview.setLayoutManager(new GridLayoutManager(getContext(), 2));
        listview.setAdapter(adapter);
        adapter.setOnItemClickLitener(this);
        listview.setRefreshListener(this);
        listview.setRefreshing(true);
        getData(page++);
        return v;
    }

    private void getData(final int p) {
        OkGo.<String>get(Const.FIND)
                .params("offset", p)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e("zk ", response.body());
                        FindListBean bean = GsonUtils.getGsonInstance().fromJson(response.body(), FindListBean.class);
                        if (p == 1) {
                            list.clear();
                        }
                        list.addAll(bean.getPayload().getPosts());
                        adapter.notifyDataSetChanged();
                        listview.setRefreshing(false);
                        if(bean.getPayload().getPosts().size()>0){
                            listview.setOnMoreListener(FindFragment.this);
                        }else {
                            listview.removeMoreListener();
                        }
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
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
