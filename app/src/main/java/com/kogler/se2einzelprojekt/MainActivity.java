package com.kogler.se2einzelprojekt;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView txtMatrikelNummber;
    private TextView txtResponse;
    private Button btnSend;
    private Button btnCalc;
    private TextView txtAnswerCalc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //komponenten die benutzt werden initialisieren
        txtMatrikelNummber = (TextView) findViewById(R.id.txtNumber);
        txtResponse = (TextView) findViewById(R.id.txtAnswer);
        btnSend = (Button) findViewById(R.id.btnSend);
        btnCalc = (Button) findViewById(R.id.btnCalc);
        txtAnswerCalc = (TextView) findViewById(R.id.txtAnswerCalc);

        //Onclick action setzen
        btnSend.setOnClickListener(v -> {
            //Checken ob überhaupt was eingegeben wurde
            if (txtMatrikelNummber != null && txtMatrikelNummber.getText().toString().length() != 0) {
                //Checken ob die Eingabe überhautp 8 Zeichen lang ist
                if (txtMatrikelNummber.getText().toString().length() == 8) {
                    doTcpRequest();
                } else {
                    Toast.makeText(getApplicationContext(), "Eine Matrikelnummer muss 8 Zeichen lang sein.", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Bitte geben Sie eine Matrikelnummer ein", Toast.LENGTH_LONG).show();
            }
        });

        //Onclick action setzen
        btnCalc.setOnClickListener(v -> {
            //Checken ob überhaupt was eingegeben wurde
            if (txtMatrikelNummber != null && txtMatrikelNummber.getText().toString().length() != 0) {
                //Checken ob die Eingabe überhaupt 8 Zeichen lang ist
                if (txtMatrikelNummber.getText().toString().length() == 8) {
                    sortMatrikelNumber();
                } else {
                    Toast.makeText(getApplicationContext(), "Eine Matrikelnummer muss 8 Zeichen lang sein.", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Bitte geben Sie eine Matrikelnummer ein.", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void doTcpRequest () {
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
    }

    /**
     * 11903829 mod 7 = 0.
     * Matrikelnummer wird sortiert, wobei zuerst die geraden dann erst die ungeraden Ziffern sortiert werden.
     */
    private void sortMatrikelNumber() {
        String matrikelnummer = txtMatrikelNummber.getText().toString();
        List<Integer> evenNumbers = new ArrayList<>();
        List<Integer> oddNumbers = new ArrayList<>();

        //Gerade und ungerade Zahlen abspeichern.
        for (int i=0; i < matrikelnummer.length(); i++) {
            int mNr = Character.getNumericValue(matrikelnummer.charAt(i));

            if (mNr % 2 == 0)
                evenNumbers.add(mNr);
            else
                oddNumbers.add(mNr);
        }

        Collections.sort(evenNumbers);
        Collections.sort(oddNumbers);

        //Die sortierten Listen zu einem String concatinieren
        String result = "";

        for (int nr : evenNumbers) {
            result += String.valueOf(nr);
        }

        for (int nr : oddNumbers) {
            result += String.valueOf(nr);
        }

        //Result auf dem component in der view setzen
        txtAnswerCalc.setText(result);
    }
}