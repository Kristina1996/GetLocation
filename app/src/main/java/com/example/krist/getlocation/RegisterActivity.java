package com.example.krist.getlocation;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    EditText edtLogin, edtName, edtSurname, edtEmail, edtPassword, edtConfirmPassword;
    Button btnRegister;

    private static String URL_REGISTER = "https://kristinabaskakova15.000webhostapp.com/register.php";
    private StringRequest request;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtLogin = (EditText) findViewById(R.id.edtLogin);
        edtName = (EditText) findViewById(R.id.edtName);
        edtSurname = (EditText) findViewById(R.id.edtSurname);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        edtConfirmPassword = (EditText) findViewById(R.id.edtConfirmPassword);
        btnRegister = (Button) findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtPassword.getText().toString().equals(edtConfirmPassword.getText().toString())) {
                    registerNewUser();
                } else {
                    Toast.makeText(RegisterActivity.this, "Пароли не совпадают", Toast.LENGTH_SHORT).show();
                }
                //registerNewUser();
            }
        });
    }

    private void registerNewUser() {
        request = new StringRequest(Request.Method.POST, URL_REGISTER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ProgressDialog loading;
                if (response.contains("success")) {
                    loading = ProgressDialog.show(RegisterActivity.this, "Пожалуйста, подождите...", null, true, true);
                    Toast.makeText(RegisterActivity.this, "регистрация выполнена успешно", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, MapActivity.class);
                    startActivity(intent);
                } else if (response.contains("no")) {
                    Toast.makeText(RegisterActivity.this, "от сервера пришел не саксесс", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //params.put("register", "true");
                params.put("login", edtLogin.getText().toString().trim());
                params.put("username", edtName.getText().toString().trim());
                params.put("surname", edtSurname.getText().toString().trim());
                params.put("useremail", edtEmail.getText().toString().trim());
                params.put("password", edtPassword.getText().toString().trim());
                return params;
            }
        };
        Volley.newRequestQueue(this).add(request);
    }
}
