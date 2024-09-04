package methods.numericalmethods;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class Home extends AppCompatActivity {
    private Button btn_bicce,btn_raphson , btn_falsa , btn_secante , btn_Jacobi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        //buton
        btn_bicce = findViewById(R.id.btn_bicce);
        btn_raphson = findViewById(R.id.btn_raphson);
        btn_falsa = findViewById(R.id.btn_falsa);
        btn_secante = findViewById(R.id.btn_secante);
        btn_Jacobi = findViewById(R.id.btn_Jacobi);

        btn_bicce.setOnClickListener(view -> {
           Intent intent = new Intent(Home.this, Met_Biseccion.class);
           startActivity(intent);
        });

        btn_raphson.setOnClickListener(view -> {
            Intent intent = new Intent(Home.this, Met_Raphson.class);
            startActivity(intent);
        });

        btn_falsa.setOnClickListener(view -> {
            Intent intent = new Intent(Home.this, Met_falsa_pos.class);
            startActivity(intent);
        });

        btn_secante.setOnClickListener(view -> {
            Intent intent = new Intent(Home.this, Met_Secante.class);
            startActivity(intent);
        });

        btn_Jacobi.setOnClickListener(view -> {
            Intent intent = new Intent(Home.this , Met_jacobi.class);
            startActivity(intent);
        });

    }
    }
