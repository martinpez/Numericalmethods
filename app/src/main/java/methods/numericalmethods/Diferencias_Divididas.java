package methods.numericalmethods;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zanvent.mathview.MathView;

import org.apache.commons.math3.analysis.interpolation.DividedDifferenceInterpolator;
import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;
import org.apache.commons.math3.analysis.polynomials.PolynomialFunctionNewtonForm;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Diferencias_Divididas#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Diferencias_Divididas extends Fragment {

    private ImageView cochete , back_incio;
    private Button btn_res;
    private EditText x_1a , x_1b, x_2a ,x_2b , x_3a ,x_3b , x_4a , x_4b , x_5a ,x_5b , x_6a , x_6b ;
    private TextView interpolacion ,textView4;
    StringBuilder latexOutput = new StringBuilder();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Diferencias_Divididas() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Diferencias_Divididas.
     */
    // TODO: Rename and change types and number of parameters
    public static Diferencias_Divididas newInstance(String param1, String param2) {
        Diferencias_Divididas fragment = new Diferencias_Divididas();
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
        return inflater.inflate(R.layout.fragment_diferencias__divididas, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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


        // Darle la funcion al edit text de póner el teclado numerico directamente

        btn_res = getActivity().findViewById(R.id.btn_res);

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

        textView4 =getView().findViewById(R.id.textView4);
        btn_res.setOnClickListener(View -> {

            validador();
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


        if (!input.isEmpty() && !input1.isEmpty() && !input2.isEmpty() && !input3.isEmpty()
                && !input4.isEmpty() && !input5.isEmpty() && !input6.isEmpty() && !input7.isEmpty()
                && !input8.isEmpty() && !input9.isEmpty() && !input10.isEmpty() && !input11.isEmpty()
        ){

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



            diferencias(x1a , x1b ,x2a , x2b, x3a, x3b, x4a, x4b, x5a, x5b, x6a, x6b);


        }else {
            showAlertDialog(getContext(),"Error", "Los datos no se pueden estar vacios");
        }


    }

    public  void diferencias(double x1a, double x1b, double x2a, double x2b, double x3a, double x3b, double x4a, double x4b, double x5a, double x5b, double x6a, double x6b) {

        double[] x = {x1a, x2a, x3a, x4a, x5a, x6a}; // Coordenadas X
        double[] y = {x1b, x2b, x3b, x4b, x5b, x6b}; // Coordenadas Y

        // Listas para los puntos válidos
        List<Double> xValid = new ArrayList<>();
        List<Double> yValid = new ArrayList<>();

        // Eliminar los pares (0, 0) que representan puntos vacíos
        for (int i = 0; i < x.length; i++) {
            if (!(x[i] == 0 && y[i] == 0)) {
                xValid.add(x[i]);
                yValid.add(y[i]);
            }
        }

        // Convertir las listas a arrays
        double[] xValidArr = xValid.stream().mapToDouble(Double::doubleValue).toArray();
        double[] yValidArr = yValid.stream().mapToDouble(Double::doubleValue).toArray();

        // Verificar si hay suficientes puntos para la interpolación
        if (xValidArr.length < 2) {
            System.out.println("Se necesitan al menos dos puntos válidos para la interpolación.");
            return;
        }

        // Crea un interpolador de diferencias divididas
        DividedDifferenceInterpolator interpolator = new DividedDifferenceInterpolator();

        // Genera el polinomio de Newton utilizando diferencias divididas
        PolynomialFunctionNewtonForm newtonPolinomio = interpolator.interpolate(xValidArr, yValidArr);

        // Imprime el polinomio de Newton en forma simbólica
        String polinomio = getNewtonPolynomialString(newtonPolinomio, xValidArr);
        System.out.println("Polinomio de Newton: " + polinomio);
        String polinomioSimplificado = getSimplifiedNewtonPolynomialString(newtonPolinomio, x);
        System.out.println("Polinomio de Newton simplificado: " + polinomioSimplificado);

        MathView mathview = getActivity().findViewById(R.id.mathview);
        MathView mathview2 = getActivity().findViewById(R.id.mathview2);
        String formi = ("F(x) = " + polinomio );
        String latex2 = ("$" + formi + "$");
        mathview2.setText(latex2);
        mathview2.setPixelScaleType(MathView.Scale.SCALE_DP);
        mathview2.setTextSize(20);
        mathview2.setTextColor("#111111");

        textView4.setVisibility(getView().getVisibility());
        String formi1 = ("F(x) = " + polinomioSimplificado );
        String latex1 = ("$" + formi1 + "$");


        mathview.setText(latex1);
        mathview.setPixelScaleType(MathView.Scale.SCALE_DP);
        mathview.setTextSize(20);
        mathview.setTextColor("#111111");

    }

    public static String getNewtonPolynomialString(PolynomialFunctionNewtonForm newtonPolinomio, double[] x) {
        double[] coeficientes = newtonPolinomio.getCoefficients();
        StringBuilder polinomio = new StringBuilder();

        // Construir el polinomio de Newton como una cadena
        for (int i = 0; i < coeficientes.length; i++) {
            if (i > 0 && coeficientes[i] >= 0) {
                polinomio.append(" + ");
            } else if (i > 0) {
                polinomio.append(" ");
            }
            polinomio.append(coeficientes[i]);

            for (int j = 0; j < i; j++) {
                polinomio.append("(x - ").append(x[j]).append(")");
            }
        }

        return polinomio.toString();
    }

    public static String getSimplifiedNewtonPolynomialString(PolynomialFunctionNewtonForm newtonPolinomio, double[] x) {
        double[] coeficientes = newtonPolinomio.getCoefficients();
        StringBuilder polinomio = new StringBuilder();

        // Formato para limitar los decimales
        DecimalFormat decimalFormat = new DecimalFormat("#.####");

        double termConstant = 0.0;
        double termLinear = 0.0;
        double termQuadratic = 0.0;

        for (int i = 0; i < coeficientes.length; i++) {
            if (coeficientes[i] == 0) {
                continue; // Omitir términos con coeficientes 0
            }

            switch (i) {
                case 0:
                    termConstant = coeficientes[i];
                    break;
                case 1:
                    termLinear = coeficientes[i];
                    break;
                case 2:
                    termQuadratic = coeficientes[i];
                    break;
                default:
                    break;
            }
        }

        // Construir el polinomio simplificado
        if (termQuadratic != 0) {
            polinomio.append(decimalFormat.format(termQuadratic)).append("x^2");
        }
        if (termLinear != 0) {
            if (polinomio.length() > 0 && termLinear > 0) {
                polinomio.append(" + ");
            }
            polinomio.append(decimalFormat.format(termLinear)).append("x");
        }
        if (termConstant != 0) {
            if (polinomio.length() > 0 && termConstant > 0) {
                polinomio.append(" + ");
            }
            polinomio.append(decimalFormat.format(termConstant));
        }

        return polinomio.toString();
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