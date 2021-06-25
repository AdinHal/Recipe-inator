package com.example.recipeinator.Activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.recipeinator.BottomNavbarListener;
import com.example.recipeinator.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    public ImageView article1, article2, article3, article4, article5;
    public WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState){


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView bottomNavbar = findViewById(R.id.bottom_navbar);
        bottomNavbar.setSelectedItemId(R.id.page_home);
        bottomNavbar.setOnNavigationItemSelectedListener(new BottomNavbarListener(this));

        Toast.makeText(this,"Swipe left, top and bottom news",Toast.LENGTH_LONG).show();

        webView = (WebView)findViewById(R.id.webview);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        article1 = findViewById(R.id.horizontalRectangle1);
        article2 = findViewById(R.id.horizontalRectangle2);
        article3 = findViewById(R.id.horizontalRectangle3);
        article4 = findViewById(R.id.horizontalRectangle4);
        article5 = findViewById(R.id.horizontalRectangle5);

        article1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.setVisibility(View.VISIBLE);
                webView.loadUrl("https://www.kitchenstories.com/en/stories/more-mayo-more-flavor-6-yum-mayo-upgrades");
            }
        });

        article2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.setVisibility(View.VISIBLE);
                webView.loadUrl("https://www.kitchenstories.com/en/stories/11-hot-sauces-that-are-on-fire-with-flavor");
            }
        });

        article3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.setVisibility(View.VISIBLE);
                webView.loadUrl("https://www.kitchenstories.com/en/stories/need-more-everyday-cooking-tips-here-are-23-of-our-favorites");
            }
        });

        article4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.setVisibility(View.VISIBLE);
                webView.loadUrl("https://www.kitchenstories.com/en/stories/how-to-make-a-really-good-cup-of-coffee-at-home");
            }
        });

        article5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.setVisibility(View.VISIBLE);
                webView.loadUrl("https://www.kitchenstories.com/en/stories/our-15-essential-spices-and-how-to-store-them");
            }
        });


    }
    @Override
    public boolean onKeyDown(int keyCode,KeyEvent event){
        if((keyCode == KeyEvent.KEYCODE_BACK)){
            Intent intent = new Intent(this,HomeActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onKeyDown(keyCode,event);
    }
}
