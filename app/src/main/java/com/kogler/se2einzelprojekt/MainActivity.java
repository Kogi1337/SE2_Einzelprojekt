package com.kogler.se2einzelprojekt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView txtMatrikelNummber;
    private TextView txtResponse;
    private Button btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //komponenten die benutzt werden initialisieren
        txtMatrikelNummber = (TextView) findViewById(R.id.txtNumber);
        txtResponse = (TextView) findViewById(R.id.txtAnswer);
        btnSend = (Button) findViewById(R.id.btnSend);

        //Onclick action setzen
        btnSend.setOnClickListener(v -> {
            doTcpRequest();
        });
    }

    private void doTcpRequest () {
        //Checken ob überhaupt was eingegeben wurde
        if (txtMatrikelNummber != null && txtMatrikelNummber.getText().toString().length() != 0) {
            //Checken ob die Eingabe überhautp 8 Zeichen lang ist
            if (txtMatrikelNummber.getText().toString().length() == 8) {
                TcpClient tcpClient = new TcpClient(txtMatrikelNummber.getText().toString());

                Thread myThread = new Thread(tcpClient);
                myThread.start();

                try {
                    myThread.join();

                    //Wenn beim Absetzten des Request ein Fehler aufgetreten ist
                    if (tcpClient.getErrorMsg() != null)
                        Toast.makeText(getApplicationContext(), "Es ist ein Fehler aufgetreten: " + tcpClient.getErrorMsg(), Toast.LENGTH_LONG).show();
                    else
                        txtResponse.setText(tcpClient.getTcpResult());
                } catch (InterruptedException e) {
                    Toast.makeText(getApplicationContext(), "Es ist ein Fehler aufgetreten: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Ungültige Länge der Matrikelnummer", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Bitte geben Sie eine Matrikelnummer ein", Toast.LENGTH_LONG).show();
        }
    }
}