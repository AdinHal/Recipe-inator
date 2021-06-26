package com.example.recipeinator.Activities;


import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipeinator.Adapters.HomeRecipeAdapter;
import com.example.recipeinator.AppDatabase;
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

        TextView username = findViewById(R.id.home_usernameTxt);
        username.setText(LoginActivity.getLoggedInUser().nickname);

        BottomNavigationView bottomNavbar = findViewById(R.id.bottom_navbar);
        bottomNavbar.setSelectedItemId(R.id.page_home);
        bottomNavbar.setOnNavigationItemSelectedListener(new BottomNavbarListener(this));
        Toast.makeText(this,"Swipe left, top and bottom news",Toast.LENGTH_LONG).show();
        webView = findViewById(R.id.webview);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        article1 = findViewById(R.id.horizontalRectangle1);
        article2 = findViewById(R.id.horizontalRectangle2);
        article3 = findViewById(R.id.horizontalRectangle3);
        article4 = findViewById(R.id.horizontalRectangle4);
        article5 = findViewById(R.id.horizontalRectangle5);

        article1.setOnClickListener(v -> {
            webView.setVisibility(View.VISIBLE);
            webView.loadUrl("https://www.kitchenstories.com/en/stories/more-mayo-more-flavor-6-yum-mayo-upgrades");
        });

        article2.setOnClickListener(v -> {
            webView.setVisibility(View.VISIBLE);
            webView.loadUrl("https://www.kitchenstories.com/en/stories/11-hot-sauces-that-are-on-fire-with-flavor");
        });

        article3.setOnClickListener(v -> {
            webView.setVisibility(View.VISIBLE);
            webView.loadUrl("https://www.kitchenstories.com/en/stories/need-more-everyday-cooking-tips-here-are-23-of-our-favorites");
        });

        article4.setOnClickListener(v -> {
            webView.setVisibility(View.VISIBLE);
            webView.loadUrl("https://www.kitchenstories.com/en/stories/how-to-make-a-really-good-cup-of-coffee-at-home");
        });

        article5.setOnClickListener(v -> {
            webView.setVisibility(View.VISIBLE);
            webView.loadUrl("https://www.kitchenstories.com/en/stories/our-15-essential-spices-and-how-to-store-them");
        });

        RecyclerView recyclerView = findViewById(R.id.main_recipe_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));

        HomeRecipeAdapter adapter = new HomeRecipeAdapter(AppDatabase.getInstance().recipeDao().getAll(), i -> {
            Intent intent = new Intent(this, RecipeDetailActivity.class);
            intent.putExtra("RECIPE_ID", i);
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed(){
        if(webView.canGoBack()){
            webView.goBack();
        }else{
            super.onBackPressed();
        }
    }

}
