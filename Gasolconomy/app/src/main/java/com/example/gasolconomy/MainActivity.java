package com.example.gasolconomy;

import androidx.annotation.NonNull;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends Activity {

    private DatabaseReference   firebaseReference = FirebaseDatabase.getInstance().getReference();

    private EditText caixaTextoId;
    private  EditText caixaDeSenha;
    private Button Entrar;
    private Button botaoCadastrar;
    private FirebaseAuth autenticao;
    private String[] permissoes = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //validar Permissões
        Permissoes.validarPermissoes(permissoes, this, 1);

        caixaTextoId = (EditText) findViewById(R.id.caixaTextoId);
        caixaDeSenha =  (EditText) findViewById(R.id.caixaDeSenha);
        botaoCadastrar = findViewById(R.id.botaoCadastrarid);
        Entrar = (Button) findViewById(R.id.Entrar);

        botaoCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CadastroAcitivity.class));
            }
        });
    }



    public void validarLoginUsuario(View view){
        String textoEmail = caixaTextoId.getText().toString();
        String textoSenha = caixaDeSenha.getText().toString();

        if(!textoEmail.isEmpty()){
            if(!textoSenha.isEmpty()){
                Usuario usuario = new Usuario();
                usuario.setEmail(textoEmail);
                usuario.setSenha(textoSenha);

                logarUsuario(usuario);

            }else{
                Toast.makeText(MainActivity.this, "Preencha o senha!", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(MainActivity.this, "Preencha o e-mail!", Toast.LENGTH_SHORT).show();
        }
    }

    public void logarUsuario(Usuario usuario){
        autenticao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticao.signInWithEmailAndPassword(
                usuario.getEmail(), usuario.getSenha()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.v("Teste", "caiu aqui");
                    Intent i = new Intent(MainActivity.this, PesquisaActivity.class);
                    startActivity(i);

                }else {
                    String excecao = "";
                    try {
                        throw task.getException();
                    }catch ( FirebaseAuthInvalidUserException e ) {
                        excecao = "Usuário não está cadastrado.";
                    }catch ( FirebaseAuthInvalidCredentialsException e ){
                        excecao = "E-mail e senha não correspondem a um usuário cadastrado";
                    }catch (Exception e){
                        excecao = "Erro ao logar usuário: "  + e.getMessage();
                        e.printStackTrace();
                    }
                    Toast.makeText(MainActivity.this, excecao, Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for(int permissaoResultado : grantResults){
            if(permissaoResultado == PackageManager.PERMISSION_DENIED){
                alertaValidacaoPermissao();
            }
        }
    }

    private void alertaValidacaoPermissao() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permissões Negadas");
        builder.setMessage("Para utilizar o app é necessário aceitar as permissões");
        builder.setCancelable(false);
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}