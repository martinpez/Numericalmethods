package methods.numericalmethods;

import android.content.Context;
import android.graphics.Typeface;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import org.apache.commons.math3.analysis.interpolation.LinearInterpolator;
import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;

import java.text.DecimalFormat;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link spline_linear#newInstance} factory method to
 * create an instance of this fragment.
 */
public class spline_linear extends Fragment {
    private ImageView cochete , back_incio;
    private Button btn_resolv;
    private EditText x_1a , x_1b, x_2a ,x_2b , x_3a ,x_3b , x_4a , x_4b , x_5a ,x_5b , x_6a , x_6b , x_7a , x_7b;
    private TextView interpolacion;

    StringBuilder resultados = new StringBuilder();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public spline_linear() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment spline_linear.
     */
    // TODO: Rename and change types and number of parameters
    public static spline_linear newInstance(String param1, String param2) {
        spline_linear fragment = new spline_linear();
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
        return inflater.inflate(R.layout.fragment_spline_linear, container, false);
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



            spline(x1a , x1b ,x2a , x2b, x3a, x3b, x4a, x4b, x5a, x5b, x6a, x6b, x7a, x7b);


        }else {
            showAlertDialog(getContext(),"Error", "Los datos no se pueden estar vacios");
        }
    }


    public void spline(double x1a, double x1b, double x2a, double x2b, double x3a, double x3b, double x4a, double x4b, double x5a, double x5b, double x6a, double x6b, double x7a, double x7b) {
        double[] x = {x1a, x2a, x3a, x4a, x5a, x6a, x7a}; // Coordenadas X
        double[] y = {x1b, x2b, x3b, x4b, x5b, x6b, x7b}; // Coordenadas Y


        // Comprobar si las entradas restantes son ceros, e ignorarlas
        for (int i = 0; i < x.length; i++) {
            if (x[i] == 0 && y[i] == 0) {
                // Eliminar los pares (0, 0) que representan puntos vacíos
                continue;
            }
            if (i < x.length - 1 && x[i + 1] != 0) {
                double pendiente = (y[i + 1] - y[i]) / (x[i + 1] - x[i]);
                double interseccion = y[i] - pendiente * x[i];


                cochete.setVisibility(getView().getVisibility());

                System.out.printf("y_%d = %sx + %s\t [%d,%d]\n",
                        i + 1, pendiente, interseccion, (int) x[i], (int) x[i + 1]);


                String resultado = "";
                if (interseccion > 0) {
                    resultado = String.format("y_%d = %.4fx + %.4f\t [%d, %d]\n",
                            i + 1, pendiente, interseccion, (int) x[i], (int) x[i + 1]);
                } else if (interseccion < 0 ) {
                    resultado = String.format("y_%d = %.4fx - %.4f\t [%d, %d]\n",
                            i + 1, pendiente, Math.abs(interseccion), (int) x[i], (int) x[i + 1]);
                }else{
                    resultado = String.format("y_%d = %.4fx\t [%d, %d]\n",
                            i + 1, pendiente, (int) x[i], (int) x[i + 1]);

                }

                resultados.append(resultado);



            }

        }
        interpolacion.setText(resultados.toString());

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