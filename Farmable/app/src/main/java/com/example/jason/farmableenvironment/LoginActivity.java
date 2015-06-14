package com.example.jason.farmableenvironment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

/**
 * Created by Jason on 1/10/2015.
 */
public class LoginActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void onEnterUserClick(View view) {
        EditText usersNameET = (EditText) findViewById(R.id.users_name_edit_text);
        String usersName = usersNameET.getText().toString();

        Intent goToUserMain = new Intent(this, UsermainActivity.class);
        goToUserMain.putExtra("UsersName", usersName);
        startActivity(goToUserMain);
    }
}
