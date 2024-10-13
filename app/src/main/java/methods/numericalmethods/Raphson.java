package methods.numericalmethods;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.zanvent.mathview.MathView;

import net.objecthunter.exp4j.ExpressionBuilder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Raphson#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Raphson extends Fragment {

    private EditText funcion, intervals_a, tole;
    private TextView print_vra_x1, print_vra_fx1, raiz, print_vra_fdx, print_f_x2, print_e;
    private Button btn_solu2 ;
    private ImageView back_incio;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Raphson() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Raphson.
     */
    // TODO: Rename and change types and number of parameters
    public static Raphson newInstance(String param1, String param2) {
        Raphson fragment = new Raphson();
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
        return inflater.inflate(R.layout.activity_met_raphson, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        // chaquo libreria
        if (!Python.isStarted()) {
            Python.start(new AndroidPlatform(getContext()));
        }

        // imagesView

        back_incio = getActivity().findViewById(R.id.back_incio);

        back_incio.setOnClickListener(View -> {
            Navigation.findNavController(view).navigate(R.id.home_Framet);
            //  Intent intent = new Intent(Met_Raphson.this, Home.class);
            // startActivity(intent);
        });


        // Edit text
        funcion = getActivity().findViewById(R.id.funcion);
        intervals_a = getActivity().findViewById(R.id.intervals_a);
        tole = getActivity().findViewById(R.id.tole);


        // txt
        print_vra_x1 = getActivity().findViewById(R.id.print_vra_x1);
        print_vra_fx1 = getActivity().findViewById(R.id.print_vra_fx1);
        print_vra_fdx = getActivity().findViewById(R.id.print_vra_fdx);
        print_f_x2 = getActivity().findViewById(R.id.print_f_x2);
        print_e = getActivity().findViewById(R.id.print_e);
        raiz = getActivity().findViewById(R.id.raiz);

        // Button
        btn_solu2 = getActivity().findViewById(R.id.btn_solu2);

        btn_solu2.setOnClickListener(View -> {
            ValidadorEntradas();
        });
    }

    private String derivarFuncionExacta(String expresion) {
        Python py = Python.getInstance();
        PyObject pyObj  = py.getModule("derivative");

        String expre = expresion; // Ejemplo de función
        //String variable = "x";


        PyObject pyresult = pyObj.callAttr("derive_function", expre);
        String derivative = pyresult.toString();
        System.out.println("Funcion Derivada " + derivative);
        // String  fundx= "x*(3+x+6)+12 ";
        return derivative.toString();
    }



    public void ValidadorEntradas(){
        MathView mathview = getActivity().findViewById(R.id.mathview);

        String input = funcion.getText().toString().trim();
        String input2 = intervals_a.getText().toString().trim();
        String input4 = tole.getText().toString().trim();

        if (!input.isEmpty() && !input2.isEmpty() && !input4.isEmpty()) {
            String expression = funcion.getText().toString();
            String dxLtex = "f(x) = ";
            String dx = "$" + dxLtex + "$";
            String latexExpression = "$" + expression + "$";

            mathview.setText(dx + latexExpression);
            mathview.setPixelScaleType(MathView.Scale.SCALE_DP);
            mathview.setTextSize(16);
            mathview.setTextColor("#111111");

            double a = Double.parseDouble(intervals_a.getText().toString());
            double tolerancia = Double.parseDouble(tole.getText().toString());

            System.out.println(expression + "expres");
            //
            metodoNewtonRaphson( expression, a, tolerancia);
        } else {
            showAlertDialog(getContext(), "Error", "Los datos no se pueden estar vacíos");
        }
    }




    private void metodoNewtonRaphson(String expresion, double xInicial, double tolerancia) {
        double x1 = xInicial;
        double x2;
        double error;

        StringBuilder sb_x1 = new StringBuilder();
        StringBuilder sb_fx1 = new StringBuilder();
        StringBuilder sb_dfx1 = new StringBuilder();
        StringBuilder sb_x2 = new StringBuilder();
        StringBuilder sb_error = new StringBuilder();

        // Obtiene la funcion y la deriva


        String derivadaExpresion = derivarFuncionExacta(expresion);

        do {
            // Evalúa la función y su derivada en x1
            double fx1 = evaluarFuncion(expresion, x1);
            double fdx1 = evaluarFuncionderivada(derivadaExpresion, x1);

            if (fdx1 == 0) {
                showAlertDialog(getContext(), "Error", "La derivada se ha vuelto cero.");
                return;
            }

            // Calcula la nueva aproximación x2
            x2 = x1 - (fx1 / fdx1);
            error = Math.abs(x2 - x1);

            // Almacena los valores de esta iteración
            sb_x1.append(String.format("%.3f", x1)).append("\n");
            sb_fx1.append(String.format("%.3f", fx1)).append("\n");
            sb_dfx1.append(String.format("%.3f", fdx1)).append("\n");
            sb_x2.append(String.format("%.3f", x2)).append("\n");
            sb_error.append(String.format("%.3f", error)).append("\n");

            // Actualiza los TextView en cada iteración
            print_vra_x1.setText(sb_x1.toString());
            print_vra_fx1.setText(sb_fx1.toString());
            print_vra_fdx.setText(sb_dfx1.toString());
            print_f_x2.setText(sb_x2.toString());
            print_e.setText(sb_error.toString());

            // Actualiza x1 para la siguiente iteración
            x1 = x2;

        } while (error > tolerancia);

        String result = String.format("%.4f",x1);

        raiz.setText("La raiz aproximadamete es : " + result);
    }


    private double evaluarFuncion(String expresion, double valorX){
        try {
            // Crea una expresión con exp4j
            return new ExpressionBuilder(expresion)
                    .variable("x")
                    .build()
                    .setVariable("x", valorX)
                    .evaluate();
        } catch (Exception e) {
            Log.e("MainActivity", "Error evaluando la expresión", e);
            throw new IllegalArgumentException("Error al evaluar la expresión: " + e.getMessage());
        }
    }
    private double evaluarFuncionderivada(String expresion, double valorX){
        try {
            // Reemplaza ^ con ** en la expresión
            String expresionConvertida = expresion.replace("**", "^");

            MathView mathview2 = getActivity().findViewById(R.id.mathview2);
            String latexDerivada = "$" + expresionConvertida + "$";
            String dxLtex = "f'(x) = ";
            String dx = "$" + dxLtex + "$";

            mathview2.setText(dx + latexDerivada);
            mathview2.setPixelScaleType(MathView.Scale.SCALE_DP);
            mathview2.setTextSize(16);
            mathview2.setTextColor("#111111");


            // Crea una expresión con exp4j
            return new ExpressionBuilder(expresionConvertida)
                    .variable("x")
                    .build()
                    .setVariable("x", valorX)
                    .evaluate();
        } catch (Exception e) {
            Log.e("MainActivity", "Error evaluando la expresión", e);
            throw new IllegalArgumentException("Error al evaluar la expresión: " + e.getMessage());
        }
    }

    private void showAlertDialog(Context context, String title, String message) {
        new AlertDialog.Builder(context)
                .setTitle("Ups!")
                .setMessage("Algo salio mal")
                .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                    // Acción a realizar cuando el usuario presiona "Aceptar"
                })
                /*
                .setNegativeButton(android.R.string.cancel, (dialog, which) -> {
                    // Acción a realizar cuando el usuario presiona "Cancelar"
                })
                */

                .setIcon(android.R.drawable.ic_delete)
                .show();
    }


}
