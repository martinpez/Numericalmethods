package methods.numericalmethods;

import android.annotation.SuppressLint;
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

import com.zanvent.mathview.MathView;

import net.objecthunter.exp4j.ExpressionBuilder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Biseccion_Framet#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Biseccion_Framet extends Fragment {

    private Button btn_solu;
    private TextView print_vra_a , print_vra_b ,print_vra_c,print_f_a,print_f_b,print_f_c,print_errorP, raiz;
    private EditText funcion ,intervals_a,intervals_b,tole;
    private ImageView back_incio;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Biseccion_Framet() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Biseccion_Framet.
     */
    // TODO: Rename and change types and number of parameters
    public static Biseccion_Framet newInstance(String param1, String param2) {
        Biseccion_Framet fragment = new Biseccion_Framet();
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
        return inflater.inflate(R.layout.activity_met_biseccion, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        // image
        back_incio = getActivity().findViewById(R.id.back_incio);

        back_incio.setOnClickListener(View -> {
             Navigation.findNavController(view).navigate(R.id.home_Framet);
            //Intent intent = new Intent(Met_Biseccion.this, Home.class);
            //startActivity(intent);
        });


        // txt
        print_vra_a = getActivity().findViewById(R.id.print_vra_a);
        print_vra_b = getActivity().findViewById(R.id.print_vra_b);
        print_vra_c = getActivity().findViewById(R.id.print_vra_c);
        print_f_a = getActivity().findViewById(R.id.print_f_a);
        print_f_b = getActivity().findViewById(R.id.print_f_b);
        print_f_c = getActivity().findViewById(R.id.print_f_c);
        print_errorP = getActivity().findViewById(R.id.print_errorP);
        raiz = getActivity().findViewById(R.id.raiz);

        // editext
        funcion = getActivity().findViewById(R.id.funcion);
        intervals_a = getActivity().findViewById(R.id.intervals_a);
        intervals_b = getActivity().findViewById(R.id.intervals_b);
        tole = getActivity().findViewById(R.id.tole);

        // Button
        btn_solu = getActivity().findViewById(R.id.btn_solu2);

        btn_solu.setOnClickListener(View -> {
            ValidadorEntradas();

        });


    }

    public void ValidadorEntradas(){
        MathView mathview = getActivity().findViewById(R.id.mathview);
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

            bisecion( getContext(), expression, a,b,tolerancia);

        }else {
            showAlertDialog(getContext(),"Error", "Los datos no se pueden estar vacios");
        }
    }



    public double evalu(String expression, double x) {
        try {
            // Crea una expresión que reemplaza "x" por el valor de la variable
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
    public void bisecion (Context context, String expression, double a, double b, double tolerancia){
        double c = a;
        double fa = evalu(expression,a);
        double fb = evalu(expression,b);
        double fc;
        double e ;

        // valida que a y b tenga signos opuestos
        if (fa * fb > 0){
            //showAlertDialog(context, "Error", "Los valores a y b deben tener signos opuestos.");
            // Toast.makeText(context, "La función debe tener signos opuestos en a y b." ,Toast.LENGTH_LONG).show();
        }
        // StringBuilder para cada TextView
        StringBuilder sb_a = new StringBuilder();
        StringBuilder sb_b = new StringBuilder();
        StringBuilder sb_c = new StringBuilder();
        StringBuilder sb_fa = new StringBuilder();
        StringBuilder sb_fb = new StringBuilder();
        StringBuilder sb_fc = new StringBuilder();
        StringBuilder sb_e = new StringBuilder();

        do {
            c = (a + b) / 2;
            fc = evalu(expression,c);
            e = Math.abs(b - a) / 2;

            sb_a.append(String.format("%.3f", a)).append("\n");
            sb_b.append(String.format("%.3f", b)).append("\n");
            sb_c.append(String.format("%.3f", c)).append("\n");
            sb_fa.append(String.format("%.3f", fa)).append("\n");
            sb_fb.append(String.format("%.3f", fb)).append("\n");
            sb_fc.append(String.format("%.3f", fc)).append("\n");
            sb_e.append(String.format("%.3f", e)).append("\n");


            if (fa * fc < 0) {
                b = c;
                fb = fc;
            } else {
                a = c;
                fa = fc;
            }

        } while (e > tolerancia);



        print_vra_a.setText(sb_a.toString());
        print_vra_b.setText(sb_b.toString());
        print_vra_c.setText(sb_c.toString());
        print_f_a.setText(sb_fa.toString());
        print_f_b.setText(sb_fb.toString());
        print_f_c.setText(sb_fc.toString());
        print_errorP.setText(sb_e.toString());

        String result = String.format("%.5f",c);
        raiz.setText("La raiz aproximadamete es : " + result);
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