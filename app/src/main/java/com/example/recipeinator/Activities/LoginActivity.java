package com.example.recipeinator.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.recipeinator.Adapters.DatabaseAdapter;
import com.example.recipeinator.R;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout email,password;


    private DatabaseAdapter databaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //TODO: databaseAdapter = DatabaseAdapter.getInstance(this);

        email = findViewById(R.id.register_email);
        password = findViewById(R.id.login_password);
    }

    public void onLogInClick(View view){
        String mail = email.getEditText().getText().toString();
        String pw = password.getEditText().getText().toString();
        
        if(!TextUtils.isEmpty(mail)||!TextUtils.isEmpty(pw)){
            loginUser(mail,pw);
        }
    }
    
    private void loginUser(String email, String password){
        boolean state = databaseAdapter.signIn(email,password);
        
        
         if(state){
          Intent intent = new Intent(this,HomeActivity.class);
          intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
          startActivity(intent);
          finish();
         }else{
             Toast.makeText(this, "False input. Try again!", Toast.LENGTH_SHORT).show();
         }
    }

    public void onCreateAccountClicked(View view){
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }
}
