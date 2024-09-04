package methods.numericalmethods;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.zanvent.mathview.MathView;

import net.objecthunter.exp4j.ExpressionBuilder;


public class Met_Secante extends AppCompatActivity {

    private Button btn_solu;
    private TextView print_vra_a, print_vra_b, print_vra_c, print_f_a, print_f_b, print_f_c, print_errorP, raiz;
    private EditText funcion, intervals_a, intervals_b, tole;
    private ImageView back_incio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_secante);

        // image
        back_incio = findViewById(R.id.back_incio);
        back_incio.setOnClickListener(view -> {
            Intent intent = new Intent(Met_Secante.this, Home.class);
            startActivity(intent);
        });

        // txt
        print_vra_a = findViewById(R.id.print_vra_a);
        print_vra_b = findViewById(R.id.print_vra_b);
        print_vra_c = findViewById(R.id.print_vra_c);
        print_f_a = findViewById(R.id.print_f_a);
        print_f_b = findViewById(R.id.print_f_b);
        print_f_c = findViewById(R.id.print_f_c);
        print_errorP = findViewById(R.id.print_errorP);
        raiz = findViewById(R.id.raiz);

        // editext
        funcion = findViewById(R.id.funcion);
        intervals_a = findViewById(R.id.intervals_a);
        intervals_b = findViewById(R.id.intervals_b);
        tole = findViewById(R.id.tole);

        // Button
        btn_solu = findViewById(R.id.btn_solu2);
        btn_solu.setOnClickListener(view -> {
            ValidadorEntradas();
        });
    }

    public void ValidadorEntradas() {
        MathView mathview = findViewById(R.id.mathview);
        String input = funcion.getText().toString().trim();
        String input2 = intervals_a.getText().toString().trim();
        String input3 = intervals_b.getText().toString().trim();
        String input4 = tole.getText().toString().trim();

        if (!input.isEmpty() && !input2.isEmpty() && !input3.isEmpty() && !input4.isEmpty()) {
            String expression = funcion.getText().toString();
            String latexExpression = "$" + input + "$";
            mathview.setText(latexExpression);
            mathview.setPixelScaleType(MathView.Scale.SCALE_DP);
            mathview.setTextSize(16);
            mathview.setTextColor("#111111");

            double a = Double.parseDouble(intervals_a.getText().toString());
            double b = Double.parseDouble(intervals_b.getText().toString());
            double tolerancia = Double.parseDouble(tole.getText().toString());

            secante(this, expression, a, b, tolerancia);
        } else {
            showAlertDialog(this, "Error", "Los datos no se pueden estar vacíos");
        }
    }

    public double evalu(String expression, double x) {
        try {
            return new ExpressionBuilder(expression)
                    .variable("x")
                    .build()
                    .setVariable("x", x)
                    .evaluate();
        } catch (Exception e) {
            Log.e("MainActivity", "Error evaluando la expresión", e);
            throw new IllegalArgumentException("Error al evaluar la expresión: " + e.getMessage());
        }
    }

    @SuppressLint("DefaultLocale")
    public void secante(Context context, String expression, double x0, double x1, double tolerancia) {
        double x2;
        double e;

        StringBuilder sb_a = new StringBuilder();
        StringBuilder sb_b = new StringBuilder();
        StringBuilder sb_c = new StringBuilder();
        StringBuilder sb_fa = new StringBuilder();
        StringBuilder sb_fb = new StringBuilder();
        StringBuilder sb_fc = new StringBuilder();
        StringBuilder sb_e = new StringBuilder();

        do {
            double f0 = evalu(expression, x0);
            double f1 = evalu(expression, x1);
            x2 = x1 - f1 * (x1 - x0) / (f1 - f0);
            double f2 = evalu(expression, x2);
            e = Math.abs(x2 - x1);

            sb_a.append(String.format("%.3f", x0)).append("\n");
            sb_b.append(String.format("%.3f", x1)).append("\n");
            sb_c.append(String.format("%.3f", x2)).append("\n");
            sb_fa.append(String.format("%.3f", f0)).append("\n");
            sb_fb.append(String.format("%.3f", f1)).append("\n");
            sb_fc.append(String.format("%.3f", f2)).append("\n");
            sb_e.append(String.format("%.3f", e)).append("\n");

            x0 = x1;
            x1 = x2;

        } while (e > tolerancia);

        print_vra_a.setText(sb_a.toString());
        print_vra_b.setText(sb_b.toString());
        print_vra_c.setText(sb_c.toString());
        print_f_a.setText(sb_fa.toString());
        print_f_b.setText(sb_fb.toString());
        print_f_c.setText(sb_fc.toString());
        print_errorP.setText(sb_e.toString());

        String result = String.format("%.4f", x2);
        raiz.setText("La raíz aproximada es: " + result);
    }

    private void showAlertDialog(Context context, String title, String message) {
        new AlertDialog.Builder(context)
                .setTitle("Ups!")
                .setMessage("Algo salió mal")
                .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                    // Acción a realizar cuando el usuario presiona "Aceptar"
                })
                .setIcon(android.R.drawable.ic_delete)
                .show();
    }
}