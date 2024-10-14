package methods.numericalmethods;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zanvent.mathview.MathView;

import org.apache.commons.math3.analysis.interpolation.SplineInterpolator;
import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Spline_cubica#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Spline_cubica extends Fragment {

    private ImageView cochete , back_incio;
    private Button btn_resolv;
    private EditText x_1a , x_1b, x_2a ,x_2b , x_3a ,x_3b , x_4a , x_4b , x_5a ,x_5b , x_6a , x_6b , x_7a , x_7b;
    private TextView interpolacion;
    StringBuilder latexOutput = new StringBuilder();


    private double[] x, y; // Puntos x, y
    private double[] a, b, c, d; // Coeficientes de cada tramo del spline
    private int n; // Número de intervalos

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Spline_cubica() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Spline_cubica.
     */
    // TODO: Rename and change types and number of parameters
    public static Spline_cubica newInstance(String param1, String param2) {
        Spline_cubica fragment = new Spline_cubica();
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
        return inflater.inflate(R.layout.fragment_spline_cubica, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // TXT
        interpolacion = getActivity().findViewById(R.id.interpolacion);

        // Button
        btn_resolv = getActivity().findViewById(R.id.btn_resolv);

        //IMAGEN WIEW
        cochete = getActivity().findViewById(R.id.cochete);
        back_incio = getActivity().findViewById(R.id.back_incio);


        //EDITEXT
        x_1a = getActivity().findViewById(R.id.x_1a);
        x_1b = getActivity().findViewById(R.id.x_1b);
        x_2a = getActivity().findViewById(R.id.x_2a);
        x_2b = getActivity().findViewById(R.id.x_2b);
        x_3a = getActivity().findViewById(R.id.x_3a);
        x_3b = getActivity().findViewById(R.id.x_3b);
        x_4a = getActivity().findViewById(R.id.x_4a);
        x_4b = getActivity().findViewById(R.id.x_4b);
        x_5a = getActivity().findViewById(R.id.x_5a);
        x_5b = getActivity().findViewById(R.id.x_5b);
        x_6a = getActivity().findViewById(R.id.x_6a);
        x_6b = getActivity().findViewById(R.id.x_6b);
        x_7a = getActivity().findViewById(R.id.x_7a);
        x_7b = getActivity().findViewById(R.id.x_7b);

        // Darle la funcion al edit text de póner el teclado numerico directamente

        x_1a.setInputType(InputType.TYPE_CLASS_PHONE);
        x_1b.setInputType(InputType.TYPE_CLASS_PHONE);
        x_2a.setInputType(InputType.TYPE_CLASS_PHONE);
        x_2b.setInputType(InputType.TYPE_CLASS_PHONE);
        x_3a.setInputType(InputType.TYPE_CLASS_PHONE);
        x_3b.setInputType(InputType.TYPE_CLASS_PHONE);
        x_4a.setInputType(InputType.TYPE_CLASS_PHONE);
        x_4b.setInputType(InputType.TYPE_CLASS_PHONE);
        x_5a.setInputType(InputType.TYPE_CLASS_PHONE);
        x_5b.setInputType(InputType.TYPE_CLASS_PHONE);
        x_6a.setInputType(InputType.TYPE_CLASS_PHONE);
        x_6b.setInputType(InputType.TYPE_CLASS_PHONE);
        x_7a.setInputType(InputType.TYPE_CLASS_PHONE);
        x_7b.setInputType(InputType.TYPE_CLASS_PHONE);

        btn_resolv.setOnClickListener(View -> {

            validador();
        });

        back_incio.setOnClickListener(View -> {
            Navigation.findNavController(view).navigate(R.id.home_Framet);
        });


    }
    public void validador(){
        String input = x_1a.getText().toString().trim();
        String input1 = x_1b.getText().toString().trim();
        String input2 = x_2a.getText().toString().trim();
        String input3= x_2b.getText().toString().trim();
        String input4 = x_3a.getText().toString().trim();
        String input5 = x_3b.getText().toString().trim();
        String input6 = x_4a.getText().toString().trim();
        String input7 = x_4b.getText().toString().trim();
        String input8 = x_5a.getText().toString().trim();
        String input9 = x_5b.getText().toString().trim();
        String input10 = x_6a.getText().toString().trim();
        String input11 = x_6b.getText().toString().trim();
        String input12 = x_7a.getText().toString().trim();
        String input13 = x_7b.getText().toString().trim();


        if (!input.isEmpty() && !input1.isEmpty() && !input2.isEmpty() && !input3.isEmpty()
                && !input4.isEmpty() && !input5.isEmpty() && !input6.isEmpty() && !input7.isEmpty()
                && !input8.isEmpty() && !input9.isEmpty() && !input10.isEmpty() && !input11.isEmpty()
                && !input12.isEmpty() && !input13.isEmpty() ){

            double x1a = Double.parseDouble(x_1a.getText().toString());
            double x1b = Double.parseDouble(x_1b.getText().toString());
            double x2a = Double.parseDouble(x_2a.getText().toString());
            double x2b = Double.parseDouble(x_2b.getText().toString());
            double x3a = Double.parseDouble(x_3a.getText().toString());
            double x3b = Double.parseDouble(x_3b.getText().toString());
            double x4a = Double.parseDouble(x_4a.getText().toString());
            double x4b = Double.parseDouble(x_4b.getText().toString());
            double x5a = Double.parseDouble(x_5a.getText().toString());
            double x5b = Double.parseDouble(x_5b.getText().toString());
            double x6a = Double.parseDouble(x_6a.getText().toString());
            double x6b = Double.parseDouble(x_6b.getText().toString());
            double x7a = Double.parseDouble(x_7a.getText().toString());
            double x7b = Double.parseDouble(x_7b.getText().toString());



            splineCubico(x1a , x1b ,x2a , x2b, x3a, x3b, x4a, x4b, x5a, x5b, x6a, x6b, x7a, x7b);


        }else {
            showAlertDialog(getContext(),"Error", "Los datos no se pueden estar vacios");
        }


    }



    public  void splineCubico(double x1a, double x1b, double x2a, double x2b, double x3a, double x3b, double x4a, double x4b, double x5a, double x5b, double x6a, double x6b, double x7a, double x7b) {
        double[] x = {x1a, x2a, x3a, x4a, x5a, x6a, x7a}; // Coordenadas X
        double[] y = {x1b, x2b, x3b, x4b, x5b, x6b, x7b}; // Coordenadas Y



        // Crear listas para guardar los puntos no vacíos
        ArrayList<Double> xValid = new ArrayList<>();
        ArrayList<Double> yValid = new ArrayList<>();

        // Eliminar los pares (0, 0) que representan puntos vacíos
        for (int i = 0; i < x.length; i++) {
            if (!(x[i] == 0 && y[i] == 0)) {
                xValid.add(x[i]);
                yValid.add(y[i]);
            }
        }

        // Convertir las listas a arrays nuevamente
        double[] xClean = new double[xValid.size()];
        double[] yClean = new double[yValid.size()];

        for (int i = 0; i < xValid.size(); i++) {
            xClean[i] = xValid.get(i);
            yClean[i] = yValid.get(i);
        }

        // Verificar si hay al menos dos puntos para interpolar
        if (xClean.length < 2) {
            showAlertDialog(getContext(),"Error", "Se necesitan al menos 2 puntos válidos para la interpolación");
            return;
        }

        // Crear el interpolador cúbico
        SplineInterpolator splineInterpolator = new SplineInterpolator();
        PolynomialSplineFunction splineFunction = splineInterpolator.interpolate(xClean, yClean);

        // Iterar sobre los intervalos de interpolación
        for (int i = 0; i < splineFunction.getN(); i++) {
            double a = splineFunction.getPolynomials()[i].getCoefficients()[3]; // Coeficiente cúbico
            double b = splineFunction.getPolynomials()[i].getCoefficients()[2]; // Coeficiente cuadrático
            double c = splineFunction.getPolynomials()[i].getCoefficients()[1]; // Coeficiente lineal
            double d = splineFunction.getPolynomials()[i].getCoefficients()[0]; // Coeficiente constante

            cochete.setVisibility(getView().getVisibility());
            // Mostrar la ecuación del polinomio en el intervalo
            System.out.printf("P%d(x) = %.4f(x - %.1f)^3 + %.4f(x - %.1f)^2 + %.4f(x - %.1f) + %.4f, para %.1f <= x <= %.1f\n",
                    i, a, xClean[i], b, xClean[i], c, xClean[i], d, xClean[i], xClean[i + 1]);

            latexOutput.append(String.format("P%d(x) = %.4f(x - %.1f)^3 + %.4f(x - %.1f)^2 + %.4f(x - %.1f) + %.4f, \t %.1f <= x <= %.1f\n",
                    i, a, xClean[i], b, xClean[i], c, xClean[i], d, xClean[i], xClean[i + 1]));



        }

        interpolacion.setText(latexOutput.toString());

    }

    private void showAlertDialog(Context context, String title, String message) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
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