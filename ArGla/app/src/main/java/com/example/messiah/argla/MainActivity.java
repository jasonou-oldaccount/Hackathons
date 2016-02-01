package com.example.messiah.argla;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.Firebase;

public class MainActivity extends AppCompatActivity {

    public static String userName;

    private Firebase ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Firebase.setAndroidContext(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        ref = new Firebase("https://jo-android-chat.firebaseio.com/users");

        TextView signIn = (TextView) findViewById(R.id.signButton);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText username = (EditText) findViewById(R.id.userName);
                EditText password = (EditText) findViewById(R.id.passWord);

                String user = username.getText().toString();
                String pass = password.getText().toString();

                Firebase newUser = ref.child(user);

                User newUserInfo = new User(user, pass, 0, 0, 0);

                userName = user;

                newUser.setValue(newUserInfo);

                startActivity(new Intent("com.example.messiah.argla.UserPage"));
            }
        });
    }
}
