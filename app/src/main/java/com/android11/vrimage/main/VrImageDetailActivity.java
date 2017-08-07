package com.android11.vrimage.main;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.android11.vrimage.R;
import com.android11.vrimage.utils.Const;
import com.daimajia.numberprogressbar.NumberProgressBar;
import com.google.vr.sdk.widgets.pano.VrPanoramaView;
import com.liulishuo.filedownloader.DownloadTask;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.android11.vrimage.R.id.vr_pano;


public class VrImageDetailActivity extends BaseActivity {

    @Bind(vr_pano)
    VrPanoramaView vrPano;
    @Bind(R.id.number_progress_bar)
    NumberProgressBar progressBar;

    private Bitmap imageBitmap;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        initActionBar();
        initPanoView();
    }

    private void initPanoView() {

        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        final String uuid = intent.getStringExtra("uuid");
//		String url = "https://files.kuula.io/584e-c2dd-be0c-f209/01-4096.jpg";
        File imageFile = new File(Const.VRIMAGE_PATH + File.separator + uuid + ".jpg");
        if (imageFile.exists()) {
            showImage();
        } else {
            OkGo.<File>get(url).execute(new FileCallback(Const.VRIMAGE_PATH, uuid + ".jpg") {
                @Override
                public void onSuccess(Response<File> response) {
                    showImage();
                }

                @Override
                public void downloadProgress(Progress progress) {
                    super.downloadProgress(progress);
                    progressBar.setProgress((int) (progress.currentSize * 100 / progress.totalSize));
                }

                @Override
                public void onError(Response<File> response) {
                    super.onError(response);
                    Log.e("zkzk","error");
                }
            });
        }


    }

    private void showImage() {
        vrPano.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        VrPanoramaView.Options options = new VrPanoramaView.Options();
        options.inputType = VrPanoramaView.Options.TYPE_MONO;
        imageBitmap = BitmapFactory.decodeFile(
                Const.VRIMAGE_PATH + File.separator + getIntent().getStringExtra("uuid") + ".jpg");
        vrPano.loadImageFromBitmap(
                imageBitmap,
                options);

    }

    @Override
    protected void onResume() {
        super.onResume();
        vrPano.resumeRendering();

    }

    @Override
    protected void onPause() {
        super.onPause();
        vrPano.pauseRendering();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        vrPano.shutdown();
        if (imageBitmap != null)
            imageBitmap.recycle();

    }

    private void initActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
