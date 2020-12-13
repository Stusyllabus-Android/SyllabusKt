package com.stu.syllabuskt.oa;

import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.stu.syllabuskt.R;
import com.stu.syllabuskt.base.BaseActivity;
import com.stu.syllabuskt.utils.ToastUtil;
import com.stu.syllabuskt.widget.LoadingDialog;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Create by yxbao on 2020/12/13
 */
public class OADetailActivity extends BaseActivity implements OADetailContract.view{

    private WebView webView;
    private LoadingDialog loadingDialog;

    @Override
    public int getContentView() {
            return R.layout.activity_oa_detail;
    }

    @Override
    public void init() {
        TextView title=findViewById(R.id.titleBarTV);
        title.setText("详情");
        ImageView imageView=findViewById(R.id.backIcon);
        imageView.setVisibility(View.VISIBLE);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        webView=findViewById(R.id.oAWebView);
        int id=getIntent().getIntExtra("id",0);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);//允许使用js
        //不支持屏幕缩放
        webSettings.setSupportZoom(false);
        webSettings.setBuiltInZoomControls(false);
        //不显示webview缩放按钮
        webSettings.setDisplayZoomControls(false);
        new OADetailPresenter(this,this).loadOADetail(id);
    }

    @Override
    public void showLoading() {
        loadingDialog=new LoadingDialog(this,"加载中");
        loadingDialog.show();
    }

    @Override
    public void showErrMsg(@NotNull String msg) {
        ToastUtil.showShort(this,msg);
    }

    @Override
    public void setWebView(@Nullable String content) {
        loadingDialog.realDismiss();
        content=getHtmlData(content);
        webView.loadDataWithBaseURL(null,content, "text/html" , "utf-8", null);//加载html数据
    }

    private String getHtmlData(String bodyHTML) {
        String head = "<head>"
                + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> "
                + "<style>img{max-width: 100%; width:100%; height:auto;}*{margin:0px;}</style>"
                + "</head>";
        return "<html>" + head + "<body>" + bodyHTML + "</body></html>";
    }


}
