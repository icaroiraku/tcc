package com.example.gasolconomy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference   firebaseReference = FirebaseDatabase.getInstance().getReference();

    private EditText caixaTextoId;

    private  EditText caixaDeSenha;

    private Button Entrar;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        caixaTextoId = (EditText) findViewById(R.id.caixaTextoId);
        caixaDeSenha =  (EditText) findViewById(R.id.caixaDeSenha);
        Entrar = (Button) findViewById(R.id.Entrar);


    Entrar . setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            String usuario = caixaTextoId.getText().toString();

            String senha = caixaDeSenha.getText().toString();

            if(usuario.equals("") && !senha.equals("")) {
                if (usuario.equals("admin") && senha.equals("1234")) {
                    Toast.makeText(MainActivity.this, "Login Efetuado Com Sucesso!!", Toast.LENGTH_LONG).show();
                }

                 } else{
                    Toast.makeText(MainActivity.this,"Erro preencha todos os Campos!!!", Toast.LENGTH_LONG).show();

            }

        }
    });



    }
}
