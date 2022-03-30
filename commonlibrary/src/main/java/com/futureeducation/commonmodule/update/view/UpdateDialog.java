package com.futureeducation.commonmodule.update.view;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.futureeducation.commonmodule.R;
import com.futureeducation.commonmodule.update.UpdateFunGO;
import com.futureeducation.commonmodule.update.config.DownloadKey;


/**
 * 版本升级提示对话框
 */
public class UpdateDialog extends Activity {

    private TextView yes, no;
    private TextView tv_version, tv_changelog;
    private boolean iscomple;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_dialog_layout);

        yes = (TextView) findViewById(R.id.updatedialog_yes);
        no = (TextView) findViewById(R.id.updatedialog_no);
        tv_version = (TextView) findViewById(R.id.title);
        tv_changelog = (TextView) findViewById(R.id.updatedialog_text_changelog);
        tv_version.setText("发现新版本: V" + DownloadKey.version);
//        tv_changelog.setText("更新日志：\n" + DownloadKey.changeLog);
        initContentStyle(DownloadKey.changeLog);
        iscomple = DownloadKey.IsCompel;
        if (iscomple) {
            no.setVisibility(View.GONE);
        }
        yes.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                DownloadKey.TOShowDownloadView = 2;
                UpdateFunGO.onResume(UpdateDialog.this);
                finish();
            }
        });

        no.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                DownloadKey.TOShowDownloadView = 1;
                if (DownloadKey.ISManual) {
                    DownloadKey.LoadManual = false;
                }
                DownloadKey.IsNeedUpdate = false;
                UpdateFunGO.onStop(UpdateDialog.this);
                finish();

            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (iscomple) {
                return true;
            } else {
                DownloadKey.IsNeedUpdate = false;
                return super.onKeyDown(keyCode, event);
            }
        }
        return false;
    }

    public void initContentStyle(String content) {
        String[] strings = content.split("\\d、");
        String label = "<font color='#333333'><b>更新内容</b></font><br/>";
        LogUtils.i("日志：" + content);

        for (int i = 0, index = 1; i < strings.length; i++) {
            LogUtils.i("日志：" + strings[i]);
            String text = strings[i];
            if (text.trim().length() > 0) {
                label += "<font color='#666666'>" + strings[i] + "</font><br/>";
                index++;
            }
        }
//       String result= content.replace("\\n", "\n");
        tv_changelog.setText(Html.fromHtml(label));
    }

}
