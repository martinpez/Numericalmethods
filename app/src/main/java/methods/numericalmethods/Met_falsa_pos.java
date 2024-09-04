package methods.numericalmethods;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.util.Log;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.zanvent.mathview.MathView;

import net.objecthunter.exp4j.ExpressionBuilder;


public class Met_falsa_pos extends AppCompatActivity {

    private EditText funcion, intervals_a, intervals_b, tole;
    private TextView print_vra_a, print_vra_b, print_vra_c, print_f_a, print_f_b, print_f_c, print_errorP, raiz1 ;
    private Button btn_solu2;
    private ImageView back_inicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_met_falsa_pos);



        back_inicio = findViewById(R.id.back_incio);

        back_inicio.setOnClickListener(view -> {
            Intent intent = new Intent(Met_falsa_pos.this, Home.class);
            startActivity(intent);
        });

        // Edit text
        funcion = findViewById(R.id.Funcion1);
        intervals_a = findViewById(R.id.Funcion2);
        intervals_b = findViewById(R.id.Funcion3);
        tole = findViewById(R.id.tole);

        // TextViews
        print_vra_a = findViewById(R.id.print_vra_a);
        print_vra_b = findViewById(R.id.print_vra_b);
        print_vra_c = findViewById(R.id.print_vra_c);
        print_f_a = findViewById(R.id.print_f_a);
        print_f_b = findViewById(R.id.print_f_b);
        print_f_c = findViewById(R.id.print_f_c);
        print_errorP = findViewById(R.id.print_errorP);
        raiz1 = findViewById(R.id.raiz1);


        // Button
        btn_solu2 = findViewById(R.id.btn_solu2);

        btn_solu2.setOnClickListener(view -> {
            ValidadorEntradas();
        });
    }

    public void ValidadorEntradas() {
        MathView mathview = findViewById(R.id.mathview);
        String input = funcion.getText().toString().trim();
        String inputA = intervals_a.getText().toString().trim();
        String inputB = intervals_b.getText().toString().trim();
        String inputTolerance = tole.getText().toString().trim();
        if (!input.isEmpty() && !inputA.isEmpty() && !inputB.isEmpty() && !inputTolerance.isEmpty()) {
            // Mostrar función y derivada
            String latexExpression = "$" + input + "$";
            mathview.setText(latexExpression);
            mathview.setPixelScaleType(MathView.Scale.SCALE_DP);
            mathview.setTextSize(16);
            mathview.setTextColor("#111111");

            double a = Double.parseDouble(intervals_a.getText().toString());
            double b = Double.parseDouble(intervals_b.getText().toString());
            double tolerancia = Double.parseDouble(tole.getText().toString());

            metodoFalsaPosicion(input, a, b, tolerancia);
        } else {
            showAlertDialog(this, "Error", "Los datos no se pueden estar vacíos.");
        }
    }

    private void metodoFalsaPosicion(String expresion, double a, double b, double tolerancia) {
        double c;
        double fa, fb, fc;
        double error;

        StringBuilder sb_vra_a = new StringBuilder();
        StringBuilder sb_vra_b = new StringBuilder();
        StringBuilder sb_vra_c = new StringBuilder();
        StringBuilder sb_f_a = new StringBuilder();
        StringBuilder sb_f_b = new StringBuilder();
        StringBuilder sb_f_c = new StringBuilder();
        StringBuilder sb_errorP = new StringBuilder();

        int iter = 0;

        do {
            fa = evaluarFuncion(expresion, a);
            fb = evaluarFuncion(expresion, b);
            c = b - (fb * (b - a)) / (fb - fa);
            fc = evaluarFuncion(expresion, c);

            if (fa * fc < 0) {
                b = c;
            } else {
                a = c;
            }

            error = Math.abs(fc);

            // Almacena los valores de esta iteración
            sb_vra_a.append(String.format("%.3f", a)).append("\n");
            sb_vra_b.append(String.format("%.3f", b)).append("\n");
            sb_vra_c.append(String.format("%.3f", c)).append("\n");
            sb_f_a.append(String.format("%.3f", fa)).append("\n");
            sb_f_b.append(String.format("%.3f", fb)).append("\n");
            sb_f_c.append(String.format("%.3f", fc)).append("\n");
            sb_errorP.append(String.format("%.3f", error)).append("\n");

            // Actualiza los TextView en cada iteración
            print_vra_a.setText(sb_vra_a.toString());
            print_vra_b.setText(sb_vra_b.toString());
            print_vra_c.setText(sb_vra_c.toString());
            print_f_a.setText(sb_f_a.toString());
            print_f_b.setText(sb_f_b.toString());
            print_f_c.setText(sb_f_c.toString());
            print_errorP.setText(sb_errorP.toString());

            iter++;





        } while (error > tolerancia && iter < 1000); // Limita las iteraciones para evitar ciclos infinitos

        String result = String.format("%.5f",c);
        raiz1.setText("La raiz aproximadamete es : " + result);
    }

    private double evaluarFuncion(String expresion, double valorX) {
        try {
            // Crea una expresión que reemplaza "x" por el valor de la variable
            return new ExpressionBuilder(expresion)
                    .variable("x")
                    .build()
                    .setVariable("x", valorX)
                    .evaluate();
        } catch (Exception e) {
            Log.e("MetodoFalsaPosicion", "Error evaluando la expresión", e);
            throw new IllegalArgumentException("Error al evaluar la expresión: " + e.getMessage());


        }


    }


    private void showAlertDialog(Context context, String title, String message) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                    // Acción a realizar cuando el usuario presiona "Aceptar"
                })
                .setIcon(android.R.drawable.ic_delete)
                .show();
    }

}