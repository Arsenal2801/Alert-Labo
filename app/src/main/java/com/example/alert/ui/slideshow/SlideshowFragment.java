package com.example.alert.ui.slideshow;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.alert.R;
import com.example.alert.databinding.FragmentSlideshowBinding;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.example.alert.ui.gallery.GalleryFragment;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;
    private FragmentSlideshowBinding binding;
    private Spinner spinner;
    private String nombre,apellido,tipoSangre;
    int idContact;
    private GalleryFragment num1;

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

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);
        Conexion();

        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        spinner = binding.spinner;
        String [] opciones = {"O+","O-","A+","A-","B+","B-","AB+","AB-"};

        ArrayAdapter<String> a = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, opciones);
        spinner.setAdapter(a);
        spinner.setEnabled(false);
        binding.btnGuardarU.setEnabled(false);
        binding.txtNom.setEnabled(false);
        binding.txtApellido.setEnabled(false);

        binding.btnEditarU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinner.setEnabled(true);
                binding.btnGuardarU.setEnabled(true);
                binding.txtNom.setEnabled(true);
                binding.txtApellido.setEnabled(true);
            }
        });
        binding.btnGuardarU.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View view) {
                if(nombre == null && apellido == null && tipoSangre == null){

                    nombre = binding.txtNom.toString();
                    apellido = binding.txtApellido.toString();
                    tipoSangre = binding.spinner.getSelectedItem().toString();

                    try {
                        st = con.createStatement();
                        rs = st.executeQuery("select id from NumerosTelefonicos where Telefono_1 = "+num1.num1);
                        idContact = rs.getInt(String.valueOf(st.executeQuery("select id from NumerosTelefonicos where Telefono_1 = "+num1.num1)));
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                    registrarEnBd(nombre,apellido,tipoSangre,idContact);
                    Toast.makeText(getActivity(),"Tus datos se han registrado correctamente en la base de datos", 10).show();
                }else{
                    nombre = binding.txtNom.toString();
                    apellido = binding.txtApellido.toString();
                    tipoSangre = binding.spinner.getSelectedItem().toString();

                    try {
                        st = con.createStatement();
                        idContact = rs.getInt(String.valueOf(st.executeQuery("select idNumeros Telefonicos from NumerosTelefonicos where Telefono_1 = "+num1.num1)));
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                    actualizarBD(nombre,apellido,tipoSangre,idContact);

                    Toast.makeText(getActivity(),"Tus datos se han actualizado con exito", 10).show();
                }
            }
        });

        final TextView textView = binding.textSlideshow;
        slideshowViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    private void registrarEnBd(String nombre, String apellido, String tipoSangre, int idContact) {
        try {
            st = con.createStatement();
            rs = st.executeQuery("insert into `Usuario`(nombre,apellido,tipo_sangre,idUbicacion) values('"+nombre+"','"+apellido+"','"+tipoSangre+"',"+idContact+")");
            rs.close();
            con.close();
            st.close();

        }catch (Exception e){

        }
    }
    private void actualizarBD(String nombre, String apellido, String tipoSangre, int idContact) {
        try {
            st = con.createStatement();
            rs = st.executeQuery("update into `Usuario`(nombre,apellido,tipo_sangre,idUbicacion) values('"+nombre+"','"+apellido+"','"+tipoSangre+"',"+idContact+")");
            rs.close();
            con.close();
            st.close();

        }catch (Exception e){

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}