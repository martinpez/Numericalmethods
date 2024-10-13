package methods.numericalmethods;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Gauss_seidel#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Gauss_seidel extends Fragment {

    private EditText a11EditText, a12EditText, a13EditText;
    private EditText a21EditText, a22EditText, a23EditText;
    private EditText a31EditText, a32EditText, a33EditText;
    private EditText b1EditText, b2EditText, b3EditText;
    private EditText raizXEditText, raizYEditText, raizZEditText;
    private Button btnSolve ;
    private ImageView back_incio ;
    private TextView resultadoTextView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Gauss_seidel() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Gauss_seidel.
     */
    // TODO: Rename and change types and number of parameters
    public static Gauss_seidel newInstance(String param1, String param2) {
        Gauss_seidel fragment = new Gauss_seidel();
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
        return inflater.inflate(R.layout.activity_met_gauss_seidel, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Inicializa los EditText de la matriz
        a11EditText = getActivity().findViewById(R.id.a11);
        a12EditText = getActivity().findViewById(R.id.a12);
        a13EditText = getActivity().findViewById(R.id.a13);
        a21EditText = getActivity().findViewById(R.id.a21);
        a22EditText = getActivity().findViewById(R.id.a22);
        a23EditText = getActivity().findViewById(R.id.a23);
        a31EditText = getActivity().findViewById(R.id.a31);
        a32EditText = getActivity().findViewById(R.id.a32);
        a33EditText = getActivity().findViewById(R.id.a33);
        b1EditText = getActivity().findViewById(R.id.b1);
        b2EditText = getActivity().findViewById(R.id.b2);
        b3EditText =getActivity(). findViewById(R.id.b3);

        // Inicializa los EditText de las raíces
        raizXEditText = getActivity().findViewById(R.id.raiz_x);
        raizYEditText = getActivity().findViewById(R.id.raiz_y);
        raizZEditText = getActivity().findViewById(R.id.raiz_z);

        // Inicializa el TextView de los resultados
        resultadoTextView = getActivity().findViewById(R.id.resultado);

        // Botón de resolver
        btnSolve = getActivity().findViewById(R.id.btn_solve);

        // imageswiew
        back_incio = getActivity().findViewById(R.id.back_incio);

        // Acción al hacer clic en el botón de resolver
        btnSolve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resolverGaussSeidel();
            }
        });



        back_incio.setOnClickListener(View -> {
             Navigation.findNavController(view).navigate(R.id.home_Framet);
            //Intent intent = new Intent(Met_Gauss_seidel.this, Home.class );
            //startActivity(intent);
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
            Toast.makeText(getActivity(), "Error: Ingresa valores numéricos válidos", Toast.LENGTH_SHORT).show();
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