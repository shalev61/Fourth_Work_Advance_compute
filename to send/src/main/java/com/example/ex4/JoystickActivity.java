package com.example.ex4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class JoystickActivity extends AppCompatActivity {
    MyClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joystick);

        Intent intent = getIntent();
        String ip = intent.getStringExtra("ip");
        String port = intent.getStringExtra("port");

        client = new MyClient();
        client.connect(ip, port);

        Joystick joystick = new Joystick(this.getApplicationContext());
        joystick.initJoystick(client, (float) getWindowManager().getDefaultDisplay().getWidth()
                , (float) getWindowManager().getDefaultDisplay().getHeight());
        setContentView(joystick);
    }
}