package com.highway.customer.customerActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.highway.R;
import com.highway.common.base.activity.DashBoardActivity;
import com.highway.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;


public class WebViewActivity extends AppCompatActivity {

    private static final String TAG = "WebViewActivity";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.dataWebView)
    WebView mWebView;
    @BindView(R.id.rlNetworkUI)
    RelativeLayout rlNetworkUI;
    @BindView(R.id.rlWebView)
    RelativeLayout rlWebView;

    String title;

  /*  private static final String TAG = "WebViewActivity";
    Toolbar toolbar;
    ProgressBar progressBar;
    WebView mWebView;
    RelativeLayout rlNetworkUI;
    RelativeLayout rlWebView;
    ImageView imgWebBackArrow;

    String title;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_layout);

      /*  mWebView = findViewById(R.id.dataWebView);
        progressBar = findViewById(R.id.progressBar);
        rlNetworkUI = findViewById(R.id.rlNetworkUI);
        rlWebView = findViewById(R.id.rlWebView);
        imgWebBackArrow = findViewById(R.id.WebBackArrow);*/

        ButterKnife.bind(this);
       setSupportActionBar(toolbar);


        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        title = getIntent().getStringExtra("title");
        getSupportActionBar().setTitle("Terms & Conditions");
        try {
            initComponent();
        } catch (Exception e) {
            e.printStackTrace();
            //   String additionalDetail = null;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    private void initComponent() throws Exception {

        mWebView.setWebViewClient(new myWebClient());
        mWebView.getSettings().setJavaScriptEnabled(true);
        getSupportActionBar().setTitle(title);
        if (!Utils.isInternetConnected(this)) {
            rlWebView.setVisibility(GONE);
            rlNetworkUI.setVisibility(View.VISIBLE);

//            rlNetworkUI.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    try {
//                        initComponent();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
            return;
        }

        rlWebView.setVisibility(View.VISIBLE);
        rlNetworkUI.setVisibility(View.GONE);

        progressBar.setVisibility(View.VISIBLE);
        mWebView.loadUrl("http://dev.thehighways.in/privacypolicy/terms");
        switch (title) {
            case "Terms & Conditions":
                progressBar.setVisibility(View.VISIBLE);
                mWebView.loadUrl("http://dev.thehighways.in/privacypolicy/terms");
                break;

            case "Privacy Policy":
                progressBar.setVisibility(View.VISIBLE);
                mWebView.loadUrl("http://dev.thehighways.in/privacypolicy");
                break;

            case "Read More":
                // progressBar.setVisibility(View.VISIBLE);
                // mWebView.loadUrl("http://www.readingrockets.org/article/reading-information");
                break;
            case "condition":
                // progressBar.setVisibility(View.VISIBLE);
                //mWebView.loadUrl("http://reddyenterprise.com/education/termsncondition.php");
                break;

            case "FAQ":
                //  progressBar.setVisibility(View.VISIBLE);
                // mWebView.loadUrl("http://13.234.161.7/dnafaq.php");
                break;
            case "Report":
                // progressBar.setVisibility(View.VISIBLE);
                //  mWebView.loadUrl("http://reddyenterprise.com/education/termsncondition.php");
                break;

            case "About Us":
                // progressBar.setVisibility(View.VISIBLE);
                // mWebView.loadUrl("http://reddyenterprise.com/education/termsncondition.php");
                break;


            case "Contact Us":
                //  progressBar.setVisibility(View.VISIBLE);
                // mWebView.loadUrl("http://reddyenterprise.com/education/termsncondition.php");
                break;

        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public class myWebClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            progressBar.setVisibility(View.VISIBLE);
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.setVisibility(GONE);
        }
    }


  /*  // onBacked pressed
    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;

        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

*/
}

