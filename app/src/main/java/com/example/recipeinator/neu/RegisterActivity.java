package com.example.recipeinator.neu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.recipeinator.Activities.HomeActivity;
import com.example.recipeinator.R;
import com.example.recipeinator.models.User;

public class RegisterActivity extends AppCompatActivity {
    private DatabaseAdapter databaseAdapter;
    private EditText nickname, name, email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Assigning values
        databaseAdapter = DatabaseAdapter.getInstance(this);
        nickname = findViewById(R.id.register_nickname);
        name = findViewById(R.id.register_name);
        email = findViewById(R.id.register_email);
        password = findViewById(R.id.register_password);
    }
    public void createAccount(View view){
        // Creating separate strings for cleaner code
        String nick = nickname.getText().toString();
        String fullname = name.getText().toString();
        String mail = email.getText().toString();
        String pw = password.getText().toString();

        // Calling registerUser and creating a new User with the strings above
        registerUser(new User(nick,fullname,mail,pw));
    }
    public void registerUser(User user){
        // Adding the new user to the database
        databaseAdapter.addNewUser(user);
        startActivity(new Intent(this, HomeActivity.class));
    }

    // Method on TextView in activity_register.xml ("Already a member? Log in")
    public void alreadyMember(View view){
        startActivity(new Intent(this, LoginActivity.class));
    }
}
