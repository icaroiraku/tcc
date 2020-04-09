package com.example.gasolconomy;

import android.os.Bundle;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class PesquisaActivity extends AppCompatActivity implements OnMapReadyCallback {

    private EditText editDestino;
    private LinearLayout linearLayoutDestino;
    private Button buttonChamarUber;
    private FirebaseAuth autenticacao;
    private DatabaseReference firebaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesquisa);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        inicializarComponentes();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }

    private void inicializarComponentes(){

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Pesquisa de postos");
        setSupportActionBar(toolbar);

        //Inicializar componentes
        linearLayoutDestino = findViewById(R.id.linearLayoutDestino);
        buttonChamarUber = findViewById(R.id.buttonChamarPiratex);

        //Configurações iniciais
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        firebaseRef = ConfiguracaoFirebase.getFirebaseDatabase();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }
}
