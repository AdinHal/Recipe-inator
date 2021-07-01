package com.example.recipeinator.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.recipeinator.AppDatabase;
import com.example.recipeinator.BottomNavbarListener;
import com.example.recipeinator.R;
import com.example.recipeinator.models.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class EditProfileActivity extends AppCompatActivity {
    private User user;
    private AppDatabase database;
    private EditText nickname, name, email, password;
    private Uri pictureUri;
    private ImageView imageView;
    private EditText pictureName;
    private ImageView removePicture;
    private final ActivityResultLauncher<Intent> selectImageLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getData() != null) {
                    pictureUri = result.getData().getData();
                    getContentResolver().takePersistableUriPermission(pictureUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    pictureName.setText(pictureUri.getLastPathSegment());
                    runOnUiThread(() -> {
                        imageView.setImageURI(result.getData().getData());
                        removePicture.setVisibility(View.VISIBLE);
                        imageView.setVisibility(View.VISIBLE);
                    });
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

        database = AppDatabase.getInstance();
        user = LoginActivity.getLoggedInUser();

        imageView = findViewById(R.id.picture_preview);
        pictureName = findViewById(R.id.profile_picture_text);
        Button selectPicture = findViewById(R.id.select_profile_picture);
        selectPicture.setOnClickListener(v -> selectImage());
        if (user.pictureUri != null && !user.pictureUri.isEmpty()) {
            pictureUri = Uri.parse(user.pictureUri);
            imageView.setImageURI(pictureUri);
            pictureName.setText(pictureUri.getLastPathSegment());
        } else {
            removePicture.setVisibility(View.GONE);
            imageView.setVisibility(View.GONE);
        }

        nickname = findViewById(R.id.register_nickname);
        nickname.setText(user.nickname);
        name = findViewById(R.id.register_name);
        name.setText(user.name);
        email = findViewById(R.id.register_email);
        email.setText(user.email);
        password = findViewById(R.id.register_password);
        password.setText(user.password);

        Button edit = findViewById(R.id.edit_button);
        edit.setOnClickListener(v -> updateUser());

        removePicture = findViewById(R.id.remove_profile_picture);
        removePicture.setOnClickListener(v -> removePicture());
    }

    private void removePicture() {
        pictureUri = Uri.EMPTY;
        imageView.setImageURI(pictureUri);
        pictureName.setText("");
        removePicture.setVisibility(View.GONE);
        imageView.setVisibility(View.GONE);
    }

    private void updateUser() {
        user.nickname = nickname.getText().toString();
        user.name = name.getText().toString();
        user.email = email.getText().toString();
        user.password = password.getText().toString();
        user.pictureUri = pictureUri.toString();
        database.userDao().update(user);
        finish();
    }

    private void selectImage(){
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        selectImageLauncher.launch(intent);
    }
}
