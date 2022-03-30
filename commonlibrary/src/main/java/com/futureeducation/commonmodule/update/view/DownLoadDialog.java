package com.futureeducation.commonmodule.update.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.futureeducation.commonmodule.R;
import com.futureeducation.commonmodule.update.UpdateFunGO;
import com.futureeducation.commonmodule.update.config.DownloadKey;
import com.futureeducation.commonmodule.update.module.Download;
import com.futureeducation.commonmodule.view.CommonShapeButton;


public class DownLoadDialog extends Activity {
    private CommonShapeButton close;
    public ProgressBar progressBar;
    public TextView textView;
    private boolean iscomple;
    public TextView speed;
    private Download download;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.download_dialog);
        speed = findViewById(R.id.speed);
        close = (CommonShapeButton) findViewById(R.id.downloaddialog_close);
        progressBar = (ProgressBar) findViewById(R.id.downloaddialog_progress);
        textView = (TextView) findViewById(R.id.downloaddialog_count);
        iscomple = DownloadKey.IsCompel;
        if (iscomple) {
            close.setVisibility(View.GONE);
        }
        if (DownloadKey.interceptFlag) {
            DownloadKey.interceptFlag = false;
        }
        download = new Download(this);
        download.start();
        close.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                DownloadKey.TOShowDownloadView = 1;
                DownloadKey.interceptFlag = true;
                if (DownloadKey.ISManual) {
                    DownloadKey.LoadManual = false;
                }
                download.Canceller();
                DownloadKey.IsNeedUpdate = false;
                UpdateFunGO.onStop(DownLoadDialog.this);
                finish();
            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            LogUtils.e("");
            if (iscomple) {
                return true;
            } else {
                DownloadKey.TOShowDownloadView = 1;
                DownloadKey.interceptFlag = true;
                if (DownloadKey.ISManual) {
                    DownloadKey.LoadManual = false;
                }
                finish();
                return super.onKeyDown(keyCode, event);
            }
        }
        return false;
    }

}
