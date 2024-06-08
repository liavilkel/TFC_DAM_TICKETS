package com.example.tfc_dam_tickets;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.tfc_dam_tickets.autenticacion.Login;
import com.example.tfc_dam_tickets.persistence.UserPersistence;
import com.example.tfc_dam_tickets.utils.EmailSender;
import com.example.tfc_dam_tickets.utils.PasswordValidator;
import com.example.tfc_dam_tickets.utils.RandomCodeGenerator;
import org.mindrot.jbcrypt.BCrypt;

public class SetNewPasswordActivity extends AppCompatActivity {

    UserPersistence userPersistence;
    EditText etNewPassword, etConfPassword;
    TextView tvPasswordLength, tvPasswordUppercase, tvPasswordLowercase, tvPasswordDigit, tvPasswordSpecialChar;
    Button btnSetPassword;
    String email;
    String outcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_new_password);

        userPersistence = new UserPersistence(this);

        Intent i = getIntent();
        email = i.getStringExtra("email");
        outcome = i.getStringExtra("outcome");

        etNewPassword = findViewById(R.id.etNewPassword);
        etConfPassword = findViewById(R.id.etNewPasswordConf);
        tvPasswordLength = findViewById(R.id.tvPasswordLength);
        tvPasswordUppercase = findViewById(R.id.tvPasswordUppercase);
        tvPasswordLowercase = findViewById(R.id.tvPasswordLowercase);
        tvPasswordDigit = findViewById(R.id.tvPasswordDigit);
        tvPasswordSpecialChar = findViewById(R.id.tvPasswordSpecialChar);
        btnSetPassword = findViewById(R.id.btnSetNewPassword);

        // Validación de la contraseña en tiempo real
        PasswordValidator.addPasswordValidation(this, etNewPassword, tvPasswordLength, tvPasswordUppercase,
                tvPasswordLowercase, tvPasswordDigit, tvPasswordSpecialChar);

        btnSetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PasswordValidator.isPasswordValid(etNewPassword.getText().toString())) {
                    if (!etConfPassword.getText().toString().isBlank()) {
                        if (etNewPassword.getText().toString().equals(etConfPassword.getText().toString())) {
                            setNewData();
                        } else {
                            Toast.makeText(SetNewPasswordActivity.this, R.string.passw_must_match, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(SetNewPasswordActivity.this, R.string.no_password_conf, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(SetNewPasswordActivity.this, R.string.invalid_password, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setNewData() {
        String hashedPassword = BCrypt.hashpw(etNewPassword.getText().toString(), BCrypt.gensalt());
        Boolean success = userPersistence.updatePasswordAndRecoveryCode(email, hashedPassword, RandomCodeGenerator.generateRandomCode());

        if (success) {
            EmailSender.sendEmail(this, email, getString(R.string.password_changed_email_subject), getString(R.string.password_changed_email_body));
            Toast.makeText(SetNewPasswordActivity.this, R.string.password_changed_success, Toast.LENGTH_SHORT).show();

            if (outcome.equals("login")) {
                Intent i = new Intent(SetNewPasswordActivity.this, Login.class);
                startActivity(i);
            } else if (outcome.equals("userInfo")){
                Intent i = new Intent(SetNewPasswordActivity.this, ActivityDetalleUsuario.class);
                startActivity(i);
            }
        } else {
            Toast.makeText(SetNewPasswordActivity.this, R.string.password_changed_fail, Toast.LENGTH_SHORT).show();
        }
    }
}
