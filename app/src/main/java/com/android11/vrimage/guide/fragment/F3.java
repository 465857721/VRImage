package com.android11.vrimage.guide.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android11.vrimage.R;
import com.android11.vrimage.main.BaseFragment;
import com.android11.vrimage.main.MainActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class F3 extends BaseFragment {

    @Bind(R.id.btn_go)
    Button btnGo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.frag3, null);

        ButterKnife.bind(this, v);
        return v;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.btn_go)
    public void onViewClicked() {
        Intent i = new Intent(getActivity(), MainActivity.class);
        spu.setGoGlide(false);
        startActivity(i);
        getActivity().finish();
    }
}
