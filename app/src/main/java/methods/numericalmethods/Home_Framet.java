package methods.numericalmethods;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Home_Framet#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Home_Framet extends Fragment {

    private Button btn_bicce, btn_raphson, btn_falsa, btn_secante, btn_Jacobi, btn_gauss , btn_spline , btn_spline_cubica;
    private TextView link;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Home_Framet() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Home_Framet.
     */
    // TODO: Rename and change types and number of parameters
    public static Home_Framet newInstance(String param1, String param2) {
        Home_Framet fragment = new Home_Framet();
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
        return inflater.inflate(R.layout.activity_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


            //buton
            btn_bicce = getActivity().findViewById(R.id.btn_bicce);
            btn_raphson = getActivity().findViewById(R.id.btn_raphson);
            btn_falsa = getActivity().findViewById(R.id.btn_falsa);
            btn_secante = getActivity().findViewById(R.id.btn_secante);
            btn_Jacobi = getActivity().findViewById(R.id.btn_Jacobi);
            btn_gauss = getActivity().findViewById(R.id.btn_gauss);
            btn_spline = getActivity().findViewById(R.id.btn_spline);
            btn_spline_cubica = getActivity().findViewById(R.id.btn_spline_cubica);
            link = getActivity().findViewById(R.id.link);

            btn_gauss.setOnClickListener(View -> {
                Navigation.findNavController(view).navigate(R.id.gauss_seidel);

                // Intent intent = new Intent(Home.this, Met_Gauss_seidel.class);
                //startActivity(intent);
            });

            btn_bicce.setOnClickListener(View -> {
                  Navigation.findNavController(view).navigate(R.id.biseccion_Framet);
                //Intent intent = new Intent(Home.this, Met_Biseccion.class);
                //startActivity(intent);
            });

            btn_raphson.setOnClickListener(View -> {
                Navigation.findNavController(view).navigate(R.id.raphson);
                 //  Intent intent = new Intent(Home.this, Met_Raphson.class);
               // startActivity(intent);
            });

            btn_falsa.setOnClickListener(View -> {
                Navigation.findNavController(view).navigate(R.id.falsa_P_Frament);
               // Intent intent = new Intent(Home.this, Met_falsa_pos.class);
               // startActivity(intent);
            });

            btn_secante.setOnClickListener(View -> {
                Navigation.findNavController(view).navigate(R.id.secante);
               // Intent intent = new Intent(Home.this, Met_Secante.class);
               // startActivity(intent);
            });

            btn_Jacobi.setOnClickListener(View -> {
                Navigation.findNavController(view).navigate(R.id.jaccobi);
              //  Intent intent = new Intent(Home.this, Met_jacobi.class);
               // startActivity(intent);
            });

            btn_spline.setOnClickListener(View -> {
                Navigation.findNavController(view).navigate(R.id.spline_linear);
            });


        btn_spline_cubica.setOnClickListener(View ->{
            Navigation.findNavController(view).navigate(R.id.spline_cubica);
        } );


            link.setOnClickListener(View -> {

                Intent intent = new Intent(Intent.ACTION_VIEW);
               intent.setData(Uri.parse("https://github.com/martinpez/Numericalmethods"));
               startActivity(intent);
            });


        }
    }




