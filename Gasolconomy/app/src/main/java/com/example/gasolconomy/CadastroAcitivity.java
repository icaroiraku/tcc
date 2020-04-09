package com.example.gasolconomy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class CadastroAcitivity extends Activity {

    private EditText cadastroEmail;
    private EditText cadastroSenha;
    private Button botaoCadastrar;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_acitivity);

        cadastroEmail = findViewById(R.id.cadastroEmailid);
        cadastroSenha = findViewById(R.id.cadastroSenhaId);
        botaoCadastrar = findViewById(R.id.botaoCadastrarinserirId);

        botaoCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String emailString = cadastroEmail.getText().toString();
                String senhaString = cadastroSenha.getText().toString();

                if(emailString != " " && senhaString != " ") {
                    autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
                    autenticacao.createUserWithEmailAndPassword(
                            emailString,
                            senhaString
                    ).addOnCompleteListener(CadastroAcitivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                try {
                                    startActivity(new Intent(CadastroAcitivity.this, MainActivity.class));
                                    Toast.makeText(CadastroAcitivity.this, "Sucesso ao cadastrar! Por favor realizar login", Toast.LENGTH_SHORT).show();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                String excecao = "";
                                try {
                                    throw task.getException();
                                } catch (FirebaseAuthWeakPasswordException e) {
                                    excecao = "Digite uma senha mais forte !";
                                } catch (FirebaseAuthInvalidCredentialsException e) {
                                    excecao = "Por favor, digite um e-mail válido";
                                } catch (FirebaseAuthUserCollisionException e) {
                                    excecao = "Esta conta já foi cadastrada;";
                                } catch (Exception e) {
                                    excecao = "Erro ao cadastrar usuário: " + e.getMessage();
                                    e.printStackTrace();
                                }
                                Toast.makeText(CadastroAcitivity.this, excecao
                                        , Toast.LENGTH_SHORT).show();
                            }
                        }
                        });
                    }
                }
        });
    }
}
