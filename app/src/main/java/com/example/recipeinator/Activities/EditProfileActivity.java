package com.example.recipeinator.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.recipeinator.AppDatabase;
import com.example.recipeinator.BottomNavbarListener;
import com.example.recipeinator.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class EditProfileActivity extends AppCompatActivity {
    private Uri pictureUri;
    private ImageView imageView;
    private EditText pictureName;
    private final ActivityResultLauncher<Intent> selectImageLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getData() != null) {
                    pictureUri = result.getData().getData();
                    getContentResolver().takePersistableUriPermission(pictureUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    pictureName.setText(pictureUri.getLastPathSegment());
                    runOnUiThread(() -> imageView.setImageURI(result.getData().getData()));
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        BottomNavigationView bottomNavbar = findViewById(R.id.bottom_navbar);
        bottomNavbar.setSelectedItemId(R.id.page_list);
        bottomNavbar.setOnNavigationItemSelectedListener(new BottomNavbarListener(this));

        imageView = findViewById(R.id.picture_preview);
        pictureName = findViewById(R.id.recipe_picture);
        Button selectPicture = findViewById(R.id.select_recipe_picture);
        selectPicture.setOnClickListener(v -> selectImage());
    }

    private void selectImage(){
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        selectImageLauncher.launch(intent);
    }
}
