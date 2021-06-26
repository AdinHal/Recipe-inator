package com.example.recipeinator.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.recipeinator.AppDatabase;
import com.example.recipeinator.R;
import com.example.recipeinator.models.User;

public class LoginActivity extends AppCompatActivity {

    private EditText email, password;
    private static User loggedInUser;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        AppDatabase database = Room.databaseBuilder(
                getApplicationContext(),
                AppDatabase.class,
                "database"
        ).allowMainThreadQueries().build();

        AppDatabase.setInstance(database);

        email = findViewById(R.id.login_email);
        password = findViewById(R.id.login_password);
    }

    public void logIn(View view){
        // Created separate variables in order for code to look cleaner
        String mail = email.getText().toString();
        String pw = password.getText().toString();

        // Checking if not null
        if(!TextUtils.isEmpty(mail) || !TextUtils.isEmpty(pw)){
            loginUser(mail, pw);
        }
    }
    
    private void loginUser(String email, String password){
        User user = AppDatabase.getInstance().userDao().getUserByEmailAndPassword(email,password);

        if (user != null) {
            loggedInUser = user;
            Intent intent = new Intent(this, HomeActivity.class);
            // LoginActivity returns a result that indicates if the login was successful or not and calls finish()
            // Login Activity is no longer the task.
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            // OnboardActivity checks the result and if the login was successful,
            // launches HomeActivity (no flags needed) and calls finish() on itself.
            finish();
        } else {
            Toast.makeText(this, R.string.login_fail, Toast.LENGTH_SHORT).show();
        }
    }



    public void createAccount(View view){
        startActivity(new Intent(this, RegisterActivity.class));
    }

    public static User getLoggedInUser(){
        return loggedInUser;
    }

    public static void setLoggedInUser(User user) {
        loggedInUser = user;
    }
}
