package alisha.com.news360_demo.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import alisha.com.news360_demo.R;

public class EpisodeWebActivity extends AppCompatActivity {
    WebView webview;
    String url, name;
    ProgressBar mprogressBar;
    FloatingActionButton rightFab, leftFab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.episode_web);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        name = intent.getStringExtra("name");
        actionBar.setTitle(name);

        webview = (WebView) findViewById(R.id.webview);
        mprogressBar = (ProgressBar) findViewById(R.id.circular_progress_bar);

        loadUrlInWebview();
        //Fab to go forward
        rightFab = (FloatingActionButton) findViewById(R.id.fab);
        rightFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (webview.canGoForward()) {
                    webview.goForward();
                } else {
                    Toast.makeText(EpisodeWebActivity.this, "Can't go forward!!", Toast.LENGTH_LONG).show();
                }
            }
        });
        //Fab to go backward
        leftFab = (FloatingActionButton) findViewById(R.id.fab1);
        leftFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (webview.canGoBack()) {
                    webview.goBack();

                } else {
                    Toast.makeText(EpisodeWebActivity.this, "You're on home page!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void loadUrlInWebview() {
        webview.getSettings().setJavaScriptEnabled(true);
        //Handling Page Navigation
        webview.setWebViewClient(new MyWebViewClient());
        //Load a URL on WebView
        webview.loadUrl(url);
    }

    // Subclass WebViewClient() para Handling Page Navigation
    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            if (Uri.parse(url).getHost().equals(url)) { //Force to open the url in WEBVIEW
                return false;
            }
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
            return super.shouldOverrideUrlLoading(view, request);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            mprogressBar.setVisibility(view.GONE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
