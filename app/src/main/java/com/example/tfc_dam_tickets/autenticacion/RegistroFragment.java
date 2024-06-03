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
import com.example.tfc_dam_tickets.utils.RandomCodeGenerator;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class RegistroFragment extends Fragment {

    static final String TIPO = "empleado";

    TextInputEditText etEmail, etNombre, etApellidos, etPass, etConfPass, etPhoneNum, etComId;
    TextInputLayout lEmail, lNombre, lApellidos, lPassword, lConfirmPass, lTelefono, lEmpresaId;
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
        clientPer = new ClientPersistence(getContext());
        login = new LoginFragment();

        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.registro_tab_fragment,container,false);

        mAuth = FirebaseAuth.getInstance();


        //Layout
        //lEmail, lNombre, lApellidos, lPassword, lConfirmPass, lTelefono, lEmpresaId;
        lEmail = viewGroup.findViewById(R.id.textInputLayoutEmail);
        lNombre = viewGroup.findViewById(R.id.textInputLayoutNombre);
        lApellidos = viewGroup.findViewById(R.id.textInputLayoutApellidos);
        lPassword = viewGroup.findViewById(R.id.textInputLayoutPassword);
        lConfirmPass = viewGroup.findViewById(R.id.textInputLayoutConfPassword);
        lTelefono = viewGroup.findViewById(R.id.textInputLayoutTelefono);
        lEmpresaId = viewGroup.findViewById(R.id.textInputLayoutComId);

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
                String email = etEmail.getText().toString().trim();
                String nombre = etNombre.getText().toString().trim();
                String apellidos = etApellidos.getText().toString().trim();
                String password = etPass.getText().toString();
                String confirmPassword = etConfPass.getText().toString();
                String phoneNumber = etPhoneNum.getText().toString().trim();
                String companyId = etComId.getText().toString().trim();

                if (!email.isEmpty()) {
                    if (isValidEmail(email)) {
                        if (!userPer.isEmailInUse(email)) {
                            if (!nombre.isEmpty()) {
                                if (!apellidos.isEmpty()) {
                                    if (!password.isEmpty()) {
                                        if (!confirmPassword.isEmpty()) {
                                            if (password.equals(confirmPassword)) {
                                                if (!phoneNumber.isEmpty()) {
                                                    if (!companyId.isEmpty()) {
                                                        if (clientPer.clientExists(companyId)) {
                                                            User newUser = new User(email, password, nombre, apellidos, phoneNumber, TIPO,
                                                                    Long.parseLong(companyId), RandomCodeGenerator.generateRandomCode(6));
                                                            res = userPer.newUser(newUser);
                                                            if (res == 1) {
                                                                Toast.makeText(getContext(), R.string.user_registered, Toast.LENGTH_SHORT).show();
                                                                Intent intent = new Intent(getActivity(), ActivityCategorias.class);
                                                                intent.putExtra("email", email);
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
                        etEmail.setError(getString(R.string.invalid_email));
                        //Toast.makeText(getContext(), R.string.invalid_email, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), R.string.no_email, Toast.LENGTH_SHORT).show();
                }
            }
        });


        return viewGroup;
    }

    private void iniciarAnimacion() {


        //lEmail, lNombre, lApellidos, lPassword, lConfirmPass, lTelefono, lEmpresaId;
        lEmail.setTranslationX(800);
        lNombre.setTranslationX(800);
        lApellidos.setTranslationX(800);
        lPassword.setTranslationX(800);
        lConfirmPass.setTranslationX(800);
        lTelefono.setTranslationX(800);
        lEmpresaId.setTranslationX(800);

        lEmail.setAlpha(v);
        lNombre.setAlpha(v);
        lApellidos.setAlpha(v);
        lPassword.setAlpha(v);
        lConfirmPass.setAlpha(v);
        lTelefono.setAlpha(v);
        lEmpresaId.setAlpha(v);

        lEmail.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        lNombre.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        lApellidos.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        lPassword.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        lConfirmPass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        lTelefono.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        lEmpresaId.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Aquí limpias los campos cuando la vista del Fragment se destruye
        limpiarCampos();
    }

    void limpiarCampos() {
        if (etEmail != null) etEmail.setText("");
        if (etNombre != null) etNombre.setText("");
        if (etApellidos != null) etApellidos.setText("");
        if (etPass != null) etPass.setText("");
        if (etConfPass != null) etConfPass.setText("");
        if (etPhoneNum != null) etPhoneNum.setText("");
        if (etComId != null) etComId.setText("");
    }



    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && getView() != null) {
            iniciarAnimacion();
        }
    }
    public boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        if (email == null) {
            return false;
        }
        return pattern.matcher(email).matches();
    }



}
