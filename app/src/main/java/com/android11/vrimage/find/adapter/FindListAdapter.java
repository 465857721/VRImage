package com.android11.vrimage.find.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android11.vrimage.R;
import com.android11.vrimage.find.bean.FindListBean;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by yuxun on 2017/4/15.
 */

public class FindListAdapter extends RecyclerView.Adapter {
    private List<FindListBean.PayloadBean.PostsBean> list;
    private Context mActivity;

    public FindListAdapter(List<FindListBean.PayloadBean.PostsBean> list, Context mActivity) {
        this.list = list;
        this.mActivity = mActivity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new OrderViewHolder(LayoutInflater.from(mActivity).inflate(R.layout.item_find, parent, false));

    }

    public interface OnItemClickLitener {
        void onItemClick(View view, int position);
    }


    private OnItemClickLitener mOnItemClickLitener;


    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final OrderViewHolder oholder = (OrderViewHolder) holder;
        FindListBean.PayloadBean.PostsBean bean = list.get(position);
        String cover = String.format("https://storage.kuula.co/%s/01-cover.jpg", bean.getUuid());
        Log.d("cover", cover);
        Glide.with(mActivity)
                .load(cover)
                .centerCrop()
                .placeholder(R.drawable.ic_default)
                .dontAnimate()
//                .transition(new DrawableTransitionOptions().dontTransition())
                .into(oholder.iv);
        if (mOnItemClickLitener != null) {
            oholder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickLitener.onItemClick(v, position);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class OrderViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv)
        ImageView iv;

        public OrderViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
