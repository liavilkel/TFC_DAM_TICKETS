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
import com.example.tfc_dam_tickets.model.User;
import com.example.tfc_dam_tickets.persistence.UserPersistence;
import com.google.firebase.auth.FirebaseAuth;

public class RegistroFragment extends Fragment {

    static final String TIPO = "empleado";

    EditText etEmail, etNombre, etApellidos, etPass, etConfPass, etPhoneNum, etComId;
    Button btnRegistrar;
    float v = 0;

    FirebaseAuth mAuth;
    UserPersistence userPer;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        userPer = new UserPersistence(getContext());

        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.registro_tab_fragment,container,false);

        mAuth = FirebaseAuth.getInstance();

        etEmail = viewGroup.findViewById(R.id.etEmail);
        etNombre = viewGroup.findViewById(R.id.etNombre);
        etApellidos = viewGroup.findViewById(R.id.etApellidos);
        etPass = viewGroup.findViewById(R.id.etPassword);
        etConfPass = viewGroup.findViewById(R.id.etConfirmPassword);
        etPhoneNum = viewGroup.findViewById(R.id.etPhoneNum);
        etComId = viewGroup.findViewById(R.id.etComId);
        btnRegistrar = viewGroup.findViewById(R.id.btnRegistrarse);

        iniciarAnimacion();

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userPer.newUser(new User(etEmail.getText().toString(), etPass.getText().toString(),
                        etNombre.getText().toString(), etApellidos.getText().toString(),
                        etPhoneNum.getText().toString(), TIPO, null));

                //userPer.newUser(new User("test3@test.com", "password2",
                  //      "Liana2", "Guapa",
                    //    "112", TIPO, null));

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
        btnRegistrar.setTranslationX(800);
        etPhoneNum.setTranslationX(800);
        etComId.setTranslationX(800);

        etEmail.setAlpha(v);
        etNombre.setAlpha(v);
        etApellidos.setAlpha(v);
        etPass.setAlpha(v);
        etConfPass.setAlpha(v);
        btnRegistrar.setAlpha(v);
        etPhoneNum.setAlpha(v);
        etComId.setAlpha(v);

        etEmail.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        etNombre.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        etApellidos.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        etPass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        etConfPass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        btnRegistrar.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        etPhoneNum.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        etComId.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && getView() != null) {
            iniciarAnimacion();
        }
    }


}
