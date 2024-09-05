package methods.numericalmethods;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Met_Gauss_seidel extends AppCompatActivity {
    private EditText a11EditText, a12EditText, a13EditText;
    private EditText a21EditText, a22EditText, a23EditText;
    private EditText a31EditText, a32EditText, a33EditText;
    private EditText b1EditText, b2EditText, b3EditText;
    private EditText raizXEditText, raizYEditText, raizZEditText;
    private Button btnSolve ;
    private ImageView back_incio ;
    private TextView resultadoTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_met_gauss_seidel);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Inicializa los EditText de la matriz
        a11EditText = findViewById(R.id.a11);
        a12EditText = findViewById(R.id.a12);
        a13EditText = findViewById(R.id.a13);
        a21EditText = findViewById(R.id.a21);
        a22EditText = findViewById(R.id.a22);
        a23EditText = findViewById(R.id.a23);
        a31EditText = findViewById(R.id.a31);
        a32EditText = findViewById(R.id.a32);
        a33EditText = findViewById(R.id.a33);
        b1EditText = findViewById(R.id.b1);
        b2EditText = findViewById(R.id.b2);
        b3EditText = findViewById(R.id.b3);

        // Inicializa los EditText de las raíces
        raizXEditText = findViewById(R.id.raiz_x);
        raizYEditText = findViewById(R.id.raiz_y);
        raizZEditText = findViewById(R.id.raiz_z);

        // Inicializa el TextView de los resultados
        resultadoTextView = findViewById(R.id.resultado);

        // Botón de resolver
        btnSolve = findViewById(R.id.btn_solve);

        // imageswiew
        back_incio = findViewById(R.id.back_incio);

        // Acción al hacer clic en el botón de resolver
        btnSolve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resolverGaussSeidel();
            }
        });



        back_incio.setOnClickListener(view -> {
            Intent intent = new Intent(Met_Gauss_seidel.this, Home.class );
            startActivity(intent);
        });

    }

    private void resolverGaussSeidel() {
        try {
            // Captura y convierte los valores de las raíces ingresadas por el usuario
            double x0 = parseDoubleSafely(raizXEditText.getText().toString());
            double y0 = parseDoubleSafely(raizYEditText.getText().toString());
            double z0 = parseDoubleSafely(raizZEditText.getText().toString());

            // Captura y convierte los valores de la matriz ingresados por el usuario
            double a11 = parseDoubleSafely(a11EditText.getText().toString());
            double a12 = parseDoubleSafely(a12EditText.getText().toString());
            double a13 = parseDoubleSafely(a13EditText.getText().toString());
            double a21 = parseDoubleSafely(a21EditText.getText().toString());
            double a22 = parseDoubleSafely(a22EditText.getText().toString());
            double a23 = parseDoubleSafely(a23EditText.getText().toString());
            double a31 = parseDoubleSafely(a31EditText.getText().toString());
            double a32 = parseDoubleSafely(a32EditText.getText().toString());
            double a33 = parseDoubleSafely(a33EditText.getText().toString());
            double b1 = parseDoubleSafely(b1EditText.getText().toString());
            double b2 = parseDoubleSafely(b2EditText.getText().toString());
            double b3 = parseDoubleSafely(b3EditText.getText().toString());

            // Llama al método de Gauss-Seidel con los valores capturados
            gaussSeidel(a11, a12, a13, b1, a21, a22, a23, b2, a31, a32, a33, b3, x0, y0, z0);
        } catch (NumberFormatException e) {
            // Muestra un mensaje de error si el usuario no ingresó números válidos
            Toast.makeText(this, "Error: Ingresa valores numéricos válidos", Toast.LENGTH_SHORT).show();
        }
    }

    private void gaussSeidel(double a11, double a12, double a13, double b1, double a21, double a22, double a23, double b2, double a31, double a32, double a33, double b3, double x0, double y0, double z0) {
        double x = x0;
        double y = y0;
        double z = z0;

        double epsilon = 0.00001; // Tolerancia para la convergencia
        int maxIteraciones = 100; // Máximo número de iteraciones
        int iteraciones = 0; // Contador de iteraciones

        for (int i = 0; i < maxIteraciones; i++) {
            iteraciones++;
            double xAnterior = x;
            double yAnterior = y;
            double zAnterior = z;

            x = (b1 - a12 * y - a13 * z) / a11;
            y = (b2 - a21 * x - a23 * z) / a22;
            z = (b3 - a31 * x - a32 * y) / a33;

            // Verifica la convergencia
            if (Math.abs(x - xAnterior) < epsilon && Math.abs(y - yAnterior) < epsilon && Math.abs(z - zAnterior) < epsilon) {
                break;
            }
        }

        // Muestra los resultados
        if (iteraciones >= maxIteraciones) {
            resultadoTextView.setText("No convergió después de " + maxIteraciones + " iteraciones.");
        } else {
            resultadoTextView.setText("Raíz X: " + String.format("%.4f", x) + "\n" +
                    "Raíz Y: " + String.format("%.4f", y) + "\n" +
                    "Raíz Z: " + String.format("%.4f", z) + "\n" +
                    "Iteraciones: " + iteraciones);
        }
    }

    // Método auxiliar para parsear números de forma segura
    private double parseDoubleSafely(String text) throws NumberFormatException {
        return Double.parseDouble(text.trim().replace(",", "."));
    }
}