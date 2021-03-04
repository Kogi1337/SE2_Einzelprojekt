package com.kogler.se2einzelprojekt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView txtResponse;
    private Button btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //komponenten die benutzt werden initialisieren
        txtResponse = (TextView) findViewById(R.id.txtAnswer);
        btnSend = (Button) findViewById(R.id.btnSend);

        //Onclick action setzen
        btnSend.setOnClickListener(v -> {
            //Do something
        });
    }
}