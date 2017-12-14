package com.android11.vrimage.guide.fragment;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android11.vrimage.R;
import com.google.vr.sdk.widgets.pano.VrPanoramaView;

import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;


public class F2 extends Fragment {

    @BindView(R.id.vr_pano)
    VrPanoramaView vrPano;
    @BindView(R.id.tv_tips)
    TextView tvTips;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.frag2, null);

        ButterKnife.bind(this, v);
        VrPanoramaView.Options options = new VrPanoramaView.Options();
        vrPano.setFullscreenButtonEnabled(false); //隐藏全屏模式按钮
//        vrPano.setVrModeButtonEnabled(false); //隐藏VR模式按钮
        options.inputType = VrPanoramaView.Options.TYPE_MONO;
        Bitmap imageBitmap = getImageFromAssetsFile("p1.jpg");
        vrPano.loadImageFromBitmap(
                imageBitmap,
                options);
        tvTips.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        return v;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private Bitmap getImageFromAssetsFile(String fileName) {
        Bitmap image = null;
        AssetManager am = getResources().getAssets();
        try {
            InputStream is = am.open(fileName);
            image = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;

    }
}
