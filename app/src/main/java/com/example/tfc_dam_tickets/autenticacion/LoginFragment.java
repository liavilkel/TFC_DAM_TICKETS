package com.example.tfc_dam_tickets.autenticacion;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tfc_dam_tickets.R;
import com.example.tfc_dam_tickets.ActivityCategorias;
import com.example.tfc_dam_tickets.persistence.UserPersistence;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginFragment extends Fragment {

    private UserPersistence userPer;
    private TextInputEditText etEmail, etPass;
    private TextInputLayout layoutEmail, layoutPass;
    private Button login;
    private float v = 0;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        userPer = new UserPersistence(getContext());

        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.login_tab_fragment, container, false);

        layoutEmail = view.findViewById(R.id.textInputLayoutEmail);
        layoutPass = view.findViewById(R.id.textInputLayoutPassword);
        etEmail = view.findViewById(R.id.email);
        etPass = view.findViewById(R.id.password);
        login = view.findViewById(R.id.btnLogin);

        iniciarAnimacion();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString().trim();
                String password = etPass.getText().toString().trim();

                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    if (TextUtils.isEmpty(email)) {
                        etEmail.setError("Introduce el correo electrónico");
                        return;
                    }
                    if (TextUtils.isEmpty(password)) {
                        etPass.setError("Introduce la contraseña");
                        layoutPass.setEndIconVisible(false);
                        return;
                    }
                } else {
                    if (userPer.verifyUser(email, password)) {
                        Intent intent = new Intent(getActivity(), ActivityCategorias.class);
                        intent.putExtra("email", email);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getContext(), "Email o contraseña incorrectos", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });


        /*Este metodo sirve para manejar y responder a los cambios en el texto del etPass. Para actualizar
        * la interfaz de usuario en tiempo real cuando el usuario interactua con el campo etPass.*/
        etPass.addTextChangedListener(new TextWatcher() {
            @Override
            //Este método se llama antes de que el texto sea cambiado.
            /*
* CharSequence s: La secuencia de caracteres del texto antes de cualquier cambio.
int start: La posición en el texto donde comenzará el cambio.
int count: El número de caracteres que serán reemplazados.
int after: El número de caracteres nuevos que se reemplazarán en el texto existente.*/
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            /*
            * Este método se llama durante el proceso de cambio del texto (es decir, inmediatamente después
            * de que el texto cambia).*/
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s)) {
                    etPass.setError(null); //elimina error mostrado anteriormente en el campo de etPass.
                    layoutPass.setEndIconVisible(true);//activamos el "ojo" en etPass.
                }
            }

            @Override
            //Este método se llama después de que el texto ha sido cambiado.
            public void afterTextChanged(Editable s) {
            }
        });

        return view;
    }

    private void iniciarAnimacion() {
        layoutEmail.setTranslationX(800);
        layoutPass.setTranslationX(800);
        login.setTranslationX(800);

        layoutEmail.setAlpha(v);
        layoutPass.setAlpha(v);
        login.setAlpha(v);

        layoutEmail.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        layoutPass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        login.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && getView() != null) {
            iniciarAnimacion();
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }


}
