package com.example.tfc_dam_tickets.autenticacion;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
                    // Muestra un error si alguno de los campos está vacío
                    if (TextUtils.isEmpty(email)) {
                        etEmail.setError("Introduce el correo electrónico");
                        return;
                    }
                    if (TextUtils.isEmpty(password)) {
                        etPass.setError("Introduce la contraseña");
                        return;
                    }
                } else {
                    // Verifica el usuario con la base de datos si ambos campos están llenos
                    if (userPer.verifyUser(email, password)) {
                        Intent intent = new Intent(getActivity(), ActivityCategorias.class);
                        intent.putExtra("email", email);
                        startActivity(intent);
                    } else {
                        // Opcional: mostrar error si las credenciales no son correctas
                        Toast.makeText(getContext(), "Email o contraseña incorrectos", Toast.LENGTH_LONG).show();
                    }
                }
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
}
