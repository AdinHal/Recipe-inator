package com.example.recipeinator.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.recipeinator.Adapters.DatabaseAdapter;
import com.example.recipeinator.R;
import com.example.recipeinator.models.User;

public class RegisterActivity extends AppCompatActivity {
    private DatabaseAdapter databaseAdapter;

    private EditText nickname, name, email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //TODO:databaseAdapter = DataBaseAdapter.getInstance(this);

        nickname = findViewById(R.id.register_nickname);
        name = findViewById(R.id.register_name);
        email = findViewById(R.id.register_email);
        password = findViewById(R.id.register_password);
    }
    public void onCreateAccountPressed(View view){
        String nick = nickname.getText().toString();
        String fullname = name.getText().toString();
        String mail = email.getText().toString();
        String pw = password.getText().toString();

        registerUser(new User(nick,fullname,mail,pw));
    }
    public void registerUser(User user){
       //databaseAdapter.addNewUser(user);
        startActivity(new Intent(this,HomeActivity.class));
    }
    public void onAlreadyAMemberClicked(View view){
        startActivity(new Intent(this,LoginActivity.class));
    }
}
