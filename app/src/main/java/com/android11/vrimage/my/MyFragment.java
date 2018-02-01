package com.android11.vrimage.my;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android11.vrimage.R;
import com.android11.vrimage.login.LoginActivity;
import com.android11.vrimage.login.event.LoginEvent;
import com.android11.vrimage.main.AboutActivity;
import com.android11.vrimage.main.BaseLazyFragment;
import com.android11.vrimage.utils.Tools;
import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;


public class MyFragment extends BaseLazyFragment {

    @BindView(R.id.ivhead)
    CircleImageView ivhead;
    @BindView(R.id.tv_name)
    TextView tvName;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(LoginEvent event) {
        initView();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.frag_my;
    }

    @Override
    protected void initView() {
        if (!TextUtils.isEmpty(spu.getName()))
            tvName.setText(spu.getName());
        if (!TextUtils.isEmpty(spu.getHeadurl()))
            Glide.with(this).load(spu.getHeadurl()).into(ivhead);


    }

    @Override
    protected boolean isImmersionBarEnabled() {
        return false;
    }

    @OnClick({R.id.ll_comment, R.id.ll_send, R.id.ll_about,R.id.ivhead,R.id.tv_name})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_comment:
                Tools.goMarket(getActivity());
                break;
            case R.id.ll_send:
                joinQQGroup("QsNrk2xeVNs5g1EnylBr_0g69RThSmPz");
                break;
            case R.id.ll_about:
                Intent about = new Intent(getActivity(), AboutActivity.class);
                startActivity(about);
                break;
            case R.id.ivhead:
            case R.id.tv_name:
                if (TextUtils.isEmpty(spu.getName())) {
                    Intent loginIntent = new Intent(getContext(), LoginActivity.class);
                    startActivity(loginIntent);
                }
                break;
        }
    }

    /****************
     *
     * 发起添加群流程。群号：VR全景照片(614619551) 的 key 为： QsNrk2xeVNs5g1EnylBr_0g69RThSmPz
     * 调用 joinQQGroup(QsNrk2xeVNs5g1EnylBr_0g69RThSmPz) 即可发起手Q客户端申请加群 VR全景照片(614619551)
     *
     * @param key 由官网生成的key
     * @return 返回true表示呼起手Q成功，返回fals表示呼起失败
     ******************/
    public boolean joinQQGroup(String key) {
        Intent intent = new Intent();
        intent.setData(Uri.parse("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26k%3D" + key));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            startActivity(intent);
            return true;
        } catch (Exception e) {
            // 未安装手Q或安装的版本不支持
            return false;
        }
    }

}
