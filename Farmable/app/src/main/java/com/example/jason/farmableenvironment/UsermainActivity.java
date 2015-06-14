package com.example.jason.farmableenvironment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Jason on 1/10/2015.
 */
public class UsermainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usermain);

        TextView nameReceived = (TextView) findViewById(R.id.users_name_message);

        Intent data = getIntent();

        String nameSentBack = data.getStringExtra("UsersName");
        nameReceived.setText(nameSentBack);
    }

    public void onShowUserProfileClick(View view) {
        Intent goToUserProfile = new Intent(this, UserprofileActivity.class);
        startActivity(goToUserProfile);
    }

    public void onShowSearchPlants(View view) {
        Intent goToSearchPlant = new Intent(this, SearchplantActivity.class);
        startActivity(goToSearchPlant);
    }
}
