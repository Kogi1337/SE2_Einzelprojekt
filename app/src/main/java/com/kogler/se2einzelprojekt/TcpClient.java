package com.kogler.se2einzelprojekt;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class TcpClient implements Runnable {

    private String _mtkrNummer;
    private String errorMsg;     //Falls ein Error auftritt wird die Message hier gespeichert
    private String tcpResult;

    public TcpClient (String mtkrNummer) {
        _mtkrNummer = mtkrNummer;
    }

    @Override
    public void run() {
        try {
            int matrikelNummer = Integer.parseInt(_mtkrNummer);
            //matrikelNummer = matrikelNummer % 7;
            Socket clientSocket = new Socket("se2-isys.aau.at", 53212);

            DataOutputStream outStream = new DataOutputStream(clientSocket.getOutputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            outStream.writeBytes(Integer.toString(matrikelNummer) + "\n");
            String result = reader.readLine();
            clientSocket.close();
            tcpResult = result;
        } catch (IOException e) {
            errorMsg = e.getMessage();
        }
    }

    public String getTcpResult() {
        return tcpResult;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
