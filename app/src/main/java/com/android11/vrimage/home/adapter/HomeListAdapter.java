package com.android11.vrimage.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android11.vrimage.R;
import com.android11.vrimage.find.bean.FindListBean;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by yuxun on 2017/4/15.
 */

public class HomeListAdapter extends RecyclerView.Adapter {
    private List<FindListBean.PayloadBean.PostsBean> list;
    private Context mActivity;

    public HomeListAdapter(List<FindListBean.PayloadBean.PostsBean> list, Context mActivity) {
        this.list = list;
        this.mActivity = mActivity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new OrderViewHolder(LayoutInflater.from(mActivity).inflate(R.layout.item_home, parent, false));

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
        String cover = String.format("https://storage.kuula.co/%s/01-tinyp-cover.jpg", bean.getUuid());

        Log.d("cover", cover);
        Glide.with(mActivity)
                .load(cover).apply(new RequestOptions().centerCrop()
                .placeholder(R.drawable.ic_default)
                .dontAnimate())
                .into(oholder.iv);
        if (mOnItemClickLitener != null) {
            oholder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickLitener.onItemClick(v, position);
                }
            });
        }

        oholder.tvname.setText(bean.getUser().getDisplayname());
        String head = String.format("https://files.kuula.io/users/%s-icon.jpg", bean.getUser().getName());
        Glide.with(mActivity)
                .load(head).apply(new RequestOptions().centerCrop()
                .placeholder(R.drawable.default_avatar_round)
                .dontAnimate())
                .into(oholder.ivhead);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class OrderViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv)
        ImageView iv;
        @BindView(R.id.tv_name)
        TextView tvname;
        @BindView(R.id.ivhead)
        CircleImageView ivhead;
        public OrderViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
