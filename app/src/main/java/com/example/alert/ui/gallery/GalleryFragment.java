package com.example.alert.ui.gallery;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.alert.R;
import com.example.alert.databinding.FragmentGalleryBinding;

import org.jetbrains.annotations.TestOnly;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    private FragmentGalleryBinding binding;
    private Editable tel1, tel2, tel3, tel4;
    public double num1,num2,num3,num4;

    Connection con;

    private String url = "jdbc:mysql://bbe85adf0f49b6:f2bb6668@us-cdbr-east-05.cleardb.net/heroku_b041bd3a0e04f36";
    private String usuario = "bbe85adf0f49b6";
    private String pass = "f2bb6668 ";

    private ResultSet rs;
    private Statement st;

    @SuppressLint("WrongConstant")
    public void Conexion(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url, usuario, pass);
            Toast.makeText(getActivity(),"Conexion exitosa",10 );
        }catch (Exception e){
            Toast.makeText(getActivity(),"Ocurrio un error" + e,10 );
        }
    }

    @SuppressLint("WrongConstant")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.btnGuardar.setEnabled(false);
        binding.NumPhoneC1.setEnabled(false);
        binding.NumPhoneC3.setEnabled(false);
        binding.NumPhoneC2.setEnabled(false);
        binding.NumPhoneC4.setEnabled(false);

        binding.NumPhoneC1.setText(tel1);
        binding.NumPhoneC2.setText(tel2);
        binding.NumPhoneC3.setText(tel3);
        binding.NumPhoneC4.setText(tel4);


        binding.btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.btnGuardar.setEnabled(true);
                binding.NumPhoneC1.setEnabled(true);
                binding.NumPhoneC3.setEnabled(true);
                binding.NumPhoneC2.setEnabled(true);
                binding.NumPhoneC4.setEnabled(true);
                Conexion();
            }
        });

        binding.btnGuardar.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View view) {
                if(tel1 == null && tel2 == null && tel3 == null && tel4 == null){
                    tel1 = binding.NumPhoneC1.getText();
                    tel2 = binding.NumPhoneC2.getText();
                    tel3 = binding.NumPhoneC3.getText();
                    tel4 = binding.NumPhoneC4.getText();
                    binding.btnGuardar.setEnabled(false);
                    binding.NumPhoneC1.setEnabled(false);
                    binding.NumPhoneC3.setEnabled(false);
                    binding.NumPhoneC2.setEnabled(false);
                    binding.NumPhoneC4.setEnabled(false);


                    num1 = Double.parseDouble(tel1.toString());
                    num2 = Double.parseDouble(tel2.toString());
                    num3 = Double.parseDouble(tel3.toString());
                    num4 = Double.parseDouble(tel4.toString());

                    registrarEnBd(num1,num2,num3,num4);


                    Toast.makeText(getActivity(),"Tus contactos se han registrado correctamente en la base de datos", 10).show();
                }else{
                    tel1 = binding.NumPhoneC1.getText();
                    tel2 = binding.NumPhoneC2.getText();
                    tel3 = binding.NumPhoneC3.getText();
                    tel4 = binding.NumPhoneC4.getText();

                    num1 = Double.parseDouble(tel1.toString());
                    num2 = Double.parseDouble(tel2.toString());
                    num3 = Double.parseDouble(tel3.toString());
                    num4 = Double.parseDouble(tel4.toString());

                    actualizarBD(num1,num2,num3,num4);

                    Toast.makeText(getActivity(),"Tus contactos se han actualizado con exito", 10).show();
                    binding.btnGuardar.setEnabled(false);
                    binding.NumPhoneC1.setEnabled(false);
                    binding.NumPhoneC3.setEnabled(false);
                    binding.NumPhoneC2.setEnabled(false);
                    binding.NumPhoneC4.setEnabled(false);
                }
            }
        });


        final TextView textView = binding.textGallery;
        galleryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void registrarEnBd(double num1, double num2, double num3, double num4){
        try {
            st = con.createStatement();
            rs = st.executeQuery("insert into `Numeros Telefonicos`(Telefono_1,Telefono_2,Telefono_3,Telefono_4) values("+num1+","+num2+","+num3+","+num4+")");
            rs.close();
            con.close();
            st.close();

        }catch (Exception e){

        }
    }
    public void actualizarBD(double num1, double num2, double num3, double num4){
        try {
            st = con.createStatement();
            rs = st.executeQuery("UPDATE into `Numeros Telefonicos`(Telefono_1,Telefono_2,Telefono_3,Telefono_4) values("+num1+","+num2+","+num3+","+num4+")");
            rs.close();
            con.close();
            st.close();
        }catch (Exception e){

        }
    }

}