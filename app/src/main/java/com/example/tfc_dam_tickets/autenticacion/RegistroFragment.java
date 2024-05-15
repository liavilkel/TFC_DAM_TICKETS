package com.example.tfc_dam_tickets.autenticacion;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tfc_dam_tickets.ActivityCategorias;
import com.example.tfc_dam_tickets.R;
import com.example.tfc_dam_tickets.model.User;
import com.example.tfc_dam_tickets.persistence.ClientPersistence;
import com.example.tfc_dam_tickets.persistence.UserPersistence;
import com.google.firebase.auth.FirebaseAuth;

public class RegistroFragment extends Fragment {

    static final String TIPO = "empleado";

    EditText etEmail, etNombre, etApellidos, etPass, etConfPass, etPhoneNum, etComId;
    Button btnRegistrar;
    float v = 0;
    int res;

    FirebaseAuth mAuth;
    UserPersistence userPer;
    ClientPersistence clientPer;
    LoginFragment login;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        userPer = new UserPersistence(getContext());
        clientPer = new ClientPersistence();
        login = new LoginFragment();

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

                if (!etEmail.getText().toString().isBlank()) {
                    if(!userPer.isEmailInUse(etEmail.getText().toString())) {
                        if (!etNombre.getText().toString().isBlank()) {
                            if (!etApellidos.getText().toString().isBlank()) {
                                if (!etPass.getText().toString().isBlank()) {
                                    if (!etConfPass.getText().toString().isBlank()) {
                                        if (etPass.getText().toString().equals(etConfPass.getText().toString())) {
                                            if (!etPhoneNum.getText().toString().isBlank()) {
                                                if (!etComId.getText().toString().isBlank()) {
                                                    if(clientPer.clientExists(etComId.getText().toString())) {
                                                        res =userPer.newUser(new User(etEmail.getText().toString().trim(), etPass.getText().toString().trim(),
                                                                etNombre.getText().toString().trim(), etApellidos.getText().toString().trim(),
                                                                etPhoneNum.getText().toString().trim(), TIPO, Long.valueOf(etComId.getText().toString())));
                                                        if (res == 1) {
                                                            Toast.makeText(getContext(), R.string.user_registered, Toast.LENGTH_SHORT).show();
                                                            Intent intent = new Intent(getActivity(), ActivityCategorias.class);
                                                            intent.putExtra("email", etEmail.getText().toString().trim());
                                                            startActivity(intent);
                                                        } else {
                                                            Toast.makeText(getContext(), R.string.unable_to_resgister, Toast.LENGTH_SHORT).show();
                                                        }
                                                    } else {
                                                        Toast.makeText(getContext(), R.string.unvalid_com_id, Toast.LENGTH_SHORT).show();
                                                    }
                                                } else {
                                                    Toast.makeText(getContext(), R.string.no_com_id, Toast.LENGTH_SHORT).show();
                                                }
                                            } else {
                                                Toast.makeText(getContext(), R.string.no_phone_num, Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            Toast.makeText(getContext(), R.string.passw_must_match, Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(getContext(), R.string.no_pass_conf, Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(getContext(), R.string.no_password, Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getContext(), R.string.no_lastname, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getContext(), R.string.no_name, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getContext(), R.string.email_in_use, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), R.string.no_email, Toast.LENGTH_SHORT).show();
                }
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
