package com.example.ex4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void connect(View view) {
        EditText ip = (EditText) findViewById(R.id.ipText);
        EditText port = (EditText) findViewById(R.id.portText);

        Intent intent = new Intent(this ,JoystickActivity.class);

        String ipStr = ip.getText().toString();
        String portStr = port.getText().toString();

        intent.putExtra("ip", ipStr);
        intent.putExtra("port", portStr);

        startActivity(intent);
    }
}
