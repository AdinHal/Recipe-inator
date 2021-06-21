package com.example.recipeinator.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class User {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id") public int id;
    @ColumnInfo(name = "nickname") public String nickname;
    @ColumnInfo(name = "name") public String name;
    @ColumnInfo(name = "email") public String email;
    @ColumnInfo(name = "password") public String password;

    public User(){
    }

    @Ignore
    public User(String nickname, String name, String email, String password){
        this.nickname=nickname;
        this.name=name;
        this.email=email;
        this.password=password;
    }

    @Ignore
    public User(int id, String nickname, String name, String email, String password){
        this(nickname,name,email,password);
        this.id=id;
    }
}
