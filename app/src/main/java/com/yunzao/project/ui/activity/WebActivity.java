package com.yunzao.project.ui.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.ike.l2_base.base.ToolBarActivity;
import com.yunzao.project.R;
import com.yunzao.project.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类描述：H5 页面
 */
public class WebActivity extends ToolBarActivity {
    @BindView(R.id.webview)
    WebView webview;
    @BindView(R.id.progressbar)
    ProgressBar progressBar;

    private String url;
    private String title;
    private String id;

    public ValueCallback<Uri> mUploadMessage;
    public ValueCallback<Uri[]> mUploadMessageForAndroid5;

    public final static int FILECHOOSER_RESULTCODE = 1;
    public final static int FILECHOOSER_RESULTCODE_FOR_ANDROID_5 = 2;

    private String mTitle, mContent, mPic, mUrl;//分享

    private static FragmentManager fragmentManager;
    private Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        ButterKnife.bind(this);
        fragmentManager = getSupportFragmentManager();

        url = getIntent().getStringExtra(Constants.URL_H5);
        title = getIntent().getStringExtra(Constants.TITLE_H5);

        initView();
    }

    @Override
    public String setupToolBarTitle() {
        return title;
    }

    @Override
    public boolean setupToolBarLeftButton(View leftButton) {
        ((ImageView) leftButton).setImageResource(R.drawable.base_ic_back_selector);
        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        return true;
    }

    public static FragmentManager getFm() {
        return fragmentManager;
    }

    private void initView() {
        context = this;

        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setDisplayZoomControls(false);
        webview.getSettings().setBuiltInZoomControls(true);
        webview.getSettings().setDomStorageEnabled(true);
        //页面展示适应屏幕
        webview.getSettings().setUseWideViewPort(true);
        webview.getSettings().setLoadWithOverviewMode(true);

        webview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webview.setBackgroundColor(0);//设置背景透明
        webview.getSettings().setAllowFileAccess(true);

        // 设置与Js交互的权限
        webview.getSettings().setJavaScriptEnabled(true);

        webview.loadUrl(url);

        webview.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {// 载入进度改变而触发
                super.onProgressChanged(view, progress);
                if (progress == 100) {
                    if (progressBar.getVisibility() == View.VISIBLE)
                        progressBar.setVisibility(View.INVISIBLE);
                } else {
                    if (progressBar.getVisibility() == View.INVISIBLE)
                        progressBar.setVisibility(View.VISIBLE);
                }
            }

            //扩展支持alert事件
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("提示").setMessage(message).setPositiveButton("确定", null);
                builder.setCancelable(false);
                builder.setIcon(R.mipmap.ic_launcher);
                AlertDialog dialog = builder.create();
                dialog.show();
                result.confirm();
                return true;
            }

            //扩展浏览器上传文件
            //3.0++版本
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
                openFileChooserImpl(uploadMsg);
            }

            //3.0--版本
            public void openFileChooser(ValueCallback<Uri> uploadMsg) {
                openFileChooserImpl(uploadMsg);
            }

            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                openFileChooserImpl(uploadMsg);
            }

            // For Android > 5.0
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> uploadMsg, FileChooserParams fileChooserParams) {
                openFileChooserImplForAndroid5(uploadMsg);
                return true;
            }
        });

        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }

            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                // TODO Auto-generated method stub
                super.onReceivedError(view, errorCode, description, failingUrl);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isFinishing()) {
            webview.pauseTimers();
            webview.loadUrl("about:blank");
        }
        webview.onPause();

    }

    @Override
    protected void onResume() {
        super.onResume();
        webview.resumeTimers();
        webview.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webview.destroy();
    }

    private void openFileChooserImpl(ValueCallback<Uri> uploadMsg) {
        mUploadMessage = uploadMsg;
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("image/*");
        startActivityForResult(Intent.createChooser(i, "File Chooser"), FILECHOOSER_RESULTCODE);
    }

    private void openFileChooserImplForAndroid5(ValueCallback<Uri[]> uploadMsg) {
        mUploadMessageForAndroid5 = uploadMsg;
        Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
        contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
        contentSelectionIntent.setType("image/*");
        Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
        chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
        chooserIntent.putExtra(Intent.EXTRA_TITLE, "Image Chooser");
        startActivityForResult(chooserIntent, FILECHOOSER_RESULTCODE_FOR_ANDROID_5);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (webview.canGoBack()) {
//            //获取历史列表
//            WebBackForwardList mWebBackForwardList = webview.copyBackForwardList();
//            //判断当前历史列表是否最顶端,其实canGoBack已经判断过
//            if (mWebBackForwardList.getCurrentIndex() > 0) {
                webview.goBack();
                return true;
//            }
            } else {
                WebActivity.this.finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == FILECHOOSER_RESULTCODE) {
            if (null == mUploadMessage)
                return;
            Uri result = intent == null || resultCode != RESULT_OK ? null : intent.getData();
            mUploadMessage.onReceiveValue(result);
            mUploadMessage = null;

        } else if (requestCode == FILECHOOSER_RESULTCODE_FOR_ANDROID_5) {
            if (null == mUploadMessageForAndroid5)
                return;
            Uri result = (intent == null || resultCode != RESULT_OK) ? null : intent.getData();
            if (result != null) {
                mUploadMessageForAndroid5.onReceiveValue(new Uri[]{result});
            } else {
                mUploadMessageForAndroid5.onReceiveValue(new Uri[]{});
            }
            mUploadMessageForAndroid5 = null;
        }
    }

    /**
     * 检测该包名所对应的应用是否存在
     *
     * @param packageName
     * @return
     */
    public boolean checkPackage(String packageName) {
        if (packageName == null || "".equals(packageName))
            return false;

        try {
            getPackageManager().getApplicationInfo(packageName, PackageManager
                    .GET_UNINSTALLED_PACKAGES);
            return true;

        } catch (PackageManager.NameNotFoundException e) {
            return false;

        }
    }


}

