package com.example.tfc_dam_tickets.autenticacion;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tfc_dam_tickets.R;
import com.google.firebase.auth.FirebaseAuth;

public class RegistroFragment extends Fragment {

    EditText etEmail, etNombre, etApellidos, etPass, etConfPass;
    Button registrar;
    float v = 0;

    FirebaseAuth mAuth;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.registro_tab_fragment,container,false);

        mAuth = FirebaseAuth.getInstance();

        etEmail = viewGroup.findViewById(R.id.etEmail);
        etNombre = viewGroup.findViewById(R.id.etNombre);
        etApellidos = viewGroup.findViewById(R.id.etApellidos);
        etPass = viewGroup.findViewById(R.id.etPassword);
        etConfPass = viewGroup.findViewById(R.id.etConfirmPassword);
        registrar = viewGroup.findViewById(R.id.btnRegistrarse);

        iniciarAnimacion();


        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = String.valueOf(etEmail.getText());
            }
        });

        return viewGroup;
    }

    private void iniciarAnimacion() {
        etEmail.setTranslationX(800);
        etNombre.setTranslationX(800);
        etApellidos.setTranslationX(800);
        etPass.setTranslationX(800);
        etConfPass.setTranslationX(800);
        registrar.setTranslationX(800);

        etEmail.setAlpha(v);
        etNombre.setAlpha(v);
        etApellidos.setAlpha(v);
        etPass.setAlpha(v);
        etConfPass.setAlpha(v);
        registrar.setAlpha(v);

        etEmail.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        etNombre.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        etApellidos.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        etPass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        etConfPass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        registrar.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && getView() != null) {
            iniciarAnimacion();
        }
    }


}
