package com.example.bancoproyectos;

import androidx.annotation.NonNull;
import androidx.annotation.StyleableRes;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

import kotlinx.coroutines.scheduling.Task;

public class RecuperarActivity extends AppCompatActivity {

    private EditText gmail;
    private Button recuperar;
    private ProgressDialog progress;

    private String correo;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.activity_inicio);

        gmail = findViewById(R.id.edtusuario);
        recuperar = findViewById(R.id.buttonrecuperar);

        auth = FirebaseAuth.getInstance();

        progress = new ProgressDialog(this);
        
        getRecuperar();

        

    }

    private void getRecuperar() {
        recuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String correo = gmail.getText().toString().trim();
                if(!correo.isEmpty()){
                    progress.setMessage("");
                    progress.setCanceledOnTouchOutside(false);
                    progress.show();
                    getEnviarCorreo();

                }else
                {
                    StyleableToast.makeText(getApplicationContext(), "El correo no se pudo enviar",
                            Toast.LENGTH_LONG, R.style.Theme_BancoProyectos).show();
                }


            }
        });
    }

    private void getEnviarCorreo(){
        auth.setLanguageCode("es");

        auth.sendPasswordResetEmail(correo).addOnCompleteListener(new OnCompleteListener<Void>(){
            @Override
            public void onComplete(@NonNull Task<Void> task){
                if(task.isSuccessful()){
                    StyleableRes.makeText(getApplicationContext(), "Por favor revisar su correo para restaurar contraseña",
                            Toast.LENGTH_LONG, R.style.ColoredBackground).show();
                    Intent i = new Intent(RecuperarActivity.this, Inicio.class);
                    startActivity(i);
                    finish();
                }else
                {
                    StyleableRes(getApplicationContext(), "Por favor revisar su correo para restaurar contraseña",
                            Toast.LENGTH_LONG, R.style.ColoredBackground).show();
                }

            }
        });

    }
}