package methods.numericalmethods;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zanvent.mathview.MathView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Jaccobi#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Jaccobi extends Fragment {

    private EditText funcion1, funcion2, funcion3;
    private TextView print_vra_c, print_f_a, print_f_b, print_errorP, raiz;
    private Button btn_solu2;
    private ImageView back_inicio;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Jaccobi() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Jaccobi.
     */
    // TODO: Rename and change types and number of parameters
    public static Jaccobi newInstance(String param1, String param2) {
        Jaccobi fragment = new Jaccobi();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_met_jacobi , container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);




        // Initialize UI elements
        back_inicio = getActivity().findViewById(R.id.back_incio);
        back_inicio.setOnClickListener(View -> {
              Navigation.findNavController(view).navigate(R.id.home_Framet);
            // Intent intent = new Intent(Met_jacobi.this, Home.class);
            //startActivity(intent);
        });

        funcion1 = getActivity().findViewById(R.id.Funcion1);
        funcion2 = getActivity().findViewById(R.id.Funcion2);
        funcion3 = getActivity().findViewById(R.id.Funcion3);
        print_vra_c = getActivity().findViewById(R.id.print_vra_c);
        print_f_a = getActivity().findViewById(R.id.print_f_a);
        print_f_b = getActivity().findViewById(R.id.print_f_b);
        print_errorP = getActivity().findViewById(R.id.print_errorP);
        raiz = getActivity().findViewById(R.id.raiz);
        btn_solu2 = getActivity().findViewById(R.id.btn_solu2);

        btn_solu2.setOnClickListener(View -> {
            ValidadorEntradas();
        });
    }

    public void ValidadorEntradas() {
        MathView mathview = getActivity().findViewById(R.id.mathview);

        String input1 = funcion1.getText().toString().trim();
        String input2 = funcion2.getText().toString().trim();
        String input3 = funcion3.getText().toString().trim();

        if (!input1.isEmpty() && !input2.isEmpty() && !input3.isEmpty()) {
            // Mostrar función en MathView
            String latexExpression = "$" + input1 + "$\n$" + input2 + "$\n$" + input3 + "$";
            mathview.setText(latexExpression);
            mathview.setPixelScaleType(MathView.Scale.SCALE_DP);
            mathview.setTextSize(16);
            mathview.setTextColor("#111111");

            double[] resultado = metodoJacobi(input1, input2, input3);
            if (resultado != null) {
                String solucion = String.format("x = %.3f, y = %.3f, z = %.3f", resultado[0], resultado[1], resultado[2]);
                raiz.setText(solucion);
            }
        } else {
            showAlertDialog(getContext(), "Error", "Los datos no se pueden estar vacíos.");
        }
    }

    private double[] metodoJacobi(String ecuacion1, String ecuacion2, String ecuacion3) {
        double[][] coeficientes = new double[3][4];
        coeficientes[0] = extraerCoeficientes(ecuacion1);
        coeficientes[1] = extraerCoeficientes(ecuacion2);
        coeficientes[2] = extraerCoeficientes(ecuacion3);

        double[] x = {0, 0, 0}; // Valor inicial
        double[] prevX = new double[3];
        double error = 1e-6;
        int iteraciones = 1000;
        double errorCalculado = 0.0;

        for (int iter = 0; iter < iteraciones; iter++) {
            System.arraycopy(x, 0, prevX, 0, 3);

            x[0] = (coeficientes[0][3] - (coeficientes[0][1] * prevX[1]) - (coeficientes[0][2] * prevX[2])) / coeficientes[0][0];
            x[1] = (coeficientes[1][3] - (coeficientes[1][0] * prevX[0]) - (coeficientes[1][2] * prevX[2])) / coeficientes[1][1];
            x[2] = (coeficientes[2][3] - (coeficientes[2][0] * prevX[0]) - (coeficientes[2][1] * prevX[1])) / coeficientes[2][2];

            errorCalculado = Math.max(Math.abs(x[0] - prevX[0]), Math.max(Math.abs(x[1] - prevX[1]), Math.abs(x[2] - prevX[2])));

            // Mostrar resultados parciales en la UI
            print_vra_c.setText(String.format("%.3f", x[0]));
            print_f_a.setText(String.format("%.3f", x[1]));
            print_f_b.setText(String.format("%.3f", x[2]));
            print_errorP.setText(String.format("%.6f", errorCalculado));

            if (errorCalculado < error) {
                break;
            }
        }
        return x;
    }

    private double[] extraerCoeficientes(String ecuacion) {
        double a = 0, b = 0, c = 0, d = 0;

        String[] partes = ecuacion.split("=");
        String lhs = partes[0].trim();
        d = Double.parseDouble(partes[1].trim());

        if (lhs.charAt(0) != '-') {
            lhs = "+" + lhs;
        }

        String[] terminos = lhs.split("(?=[+-])");
        for (String termino : terminos) {
            termino = termino.trim();
            if (termino.contains("x")) {
                a = parseCoeficiente(termino, "x");
            } else if (termino.contains("y")) {
                b = parseCoeficiente(termino, "y");
            } else if (termino.contains("z")) {
                c = parseCoeficiente(termino, "z");
            }
        }

        return new double[]{a, b, c, d};
    }

    private double parseCoeficiente(String termino, String variable) {
        termino = termino.replace(variable, "").trim();

        if (termino.equals("+") || termino.equals("")) {
            return 1.0;
        } else if (termino.equals("-")) {
            return -1.0;
        }

        return Double.parseDouble(termino);
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
