package com.example.tfc_dam_tickets.autenticacion;

import android.content.Intent;
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
import com.example.tfc_dam_tickets.ActivityCategorias;
import com.example.tfc_dam_tickets.persistence.UserPersistence;

public class LoginFragment extends Fragment {

    UserPersistence userPer;
    EditText email, pass;
    Button login;
    float v = 0;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        userPer = new UserPersistence(getContext());

        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.login_tab_fragment,container,false);

        email = view.findViewById(R.id.email);
        pass = view.findViewById(R.id.password);
        login = view.findViewById(R.id.btnLogin);

        iniciarAnimacion();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (userPer.verifyUser(email.getText().toString(), pass.getText().toString())){
                    Intent intent = new Intent(getActivity(), ActivityCategorias.class);
                    startActivity(intent);
                }

            }
        });

        return view;

    }

    private void iniciarAnimacion() {
        email.setTranslationX(800);
        pass.setTranslationX(800);
        login.setTranslationX(800);

        email.setAlpha(v);
        pass.setAlpha(v);
        login.setAlpha(v);

        email.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        pass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
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
