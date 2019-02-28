package com.example.leona.teste;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    Context context = this;

    private GoogleSignInClient googleSignInClient;
    private SignInButton googleSignInButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button enable = (Button) findViewById(R.id.enable);
        Button desenable = (Button) findViewById(R.id.desenable);
        Button settings = (Button) findViewById(R.id.settings);


        enable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setGPS(true);
            }
        });


        desenable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setGPS(false);
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        });
    }



    void setGPS(boolean enable){

        String command = "";

        if(enable){

            command = "settings put secure location_providers_allowed +gps";
        }else {
            command = "settings put secure location_providers_allowed -gps";
        }
        {
            try {
                Process p;
                p = Runtime.getRuntime().exec("su");
                DataOutputStream dos = new DataOutputStream(p.getOutputStream());
                dos.writeBytes(command+"\n");
                dos.writeBytes("exit\n");
                dos.flush();
                dos.close();
                p.waitFor();


            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Toast.makeText(context, "Comando executado", Toast.LENGTH_SHORT).show();


    }


}
