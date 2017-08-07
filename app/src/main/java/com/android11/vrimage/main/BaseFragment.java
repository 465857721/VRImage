package com.android11.vrimage.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.android11.vrimage.utils.Const;
import com.android11.vrimage.utils.SharePreferenceUtil;
import com.android11.vrimage.utils.Tools;


public class BaseFragment extends Fragment  {
    public SharePreferenceUtil spu;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        spu = Tools.getSpu(getActivity());
    }



}
