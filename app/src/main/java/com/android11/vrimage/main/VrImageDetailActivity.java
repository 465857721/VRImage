package com.android11.vrimage.main;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.android11.vrimage.R;
import com.android11.vrimage.utils.Const;
import com.android11.vrimage.utils.Tools;
import com.daimajia.numberprogressbar.NumberProgressBar;
import com.google.vr.sdk.widgets.pano.VrPanoramaView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.android11.vrimage.R.id.vr_pano;


public class VrImageDetailActivity extends BaseActivity {

    @BindView(vr_pano)
    VrPanoramaView vrPano;
    @BindView(R.id.number_progress_bar)
    NumberProgressBar progressBar;

    private Bitmap imageBitmap;
    private String localUrl = "";

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_find, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_showurl:
                new MaterialDialog.Builder(this)
                        .content(Const.VRIMAGE_PATH)
                        .positiveText("确定")
                        .negativeText("复制路径")
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(MaterialDialog dialog, DialogAction which) {
                                // TODO
                                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                                //创建ClipData对象
                                ClipData clipData = ClipData.newPlainText("opy", Const.VRIMAGE_PATH);
                                //添加ClipData对象到剪切板中
                                clipboardManager.setPrimaryClip(clipData);
                                Tools.toastInBottom(VrImageDetailActivity.this, "复制成功");
                            }
                        })
                        .show();
                break;
            case android.R.id.home:
                finish();
                break;
            case R.id.action_share:
                Tools.shareMsg(this, getString(R.string.app_name),
                        "发现新世界", "下载地址：http://a.app.qq.com/o/simple.jsp?pkgname=com.android11.vriamge", localUrl);
                break;

        }
        return true;
    }

    private void initPanoView() {

        Intent intent = getIntent();
        if (!TextUtils.isEmpty(intent.getStringExtra("path"))) {
            File file = new File(intent.getStringExtra("path"));
            if (file.exists()) {
                showLocalImage(file);
            }
        } else {
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
                        Log.e("zkzk", "error");
                    }
                });
            }
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
        localUrl = Const.VRIMAGE_PATH + File.separator + getIntent().getStringExtra("uuid") + ".jpg";
    }

    private void showLocalImage(File file) {
        vrPano.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        VrPanoramaView.Options options = new VrPanoramaView.Options();
        options.inputType = VrPanoramaView.Options.TYPE_MONO;
        imageBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
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
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {

//        return super.onOptionsItemSelected(item);
//    }
}
