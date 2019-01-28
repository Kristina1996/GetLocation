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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText edtLogin, edtPassword;
    Button btnLogin, btnRegister;
    private static String URL_LOGIN = "https://kristinabaskakova15.000webhostapp.com/loginjson.php";
    private StringRequest request;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtLogin = (EditText) findViewById(R.id.edtLogin);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        requestQueue = Volley.newRequestQueue(this);
    }

    public void OnClickbtnLogin(View view) {
        String login = edtLogin.getText().toString();
        String password = edtPassword.getText().toString();
        String type = "login";
        Toast.makeText(LoginActivity.this, "запуск функции логин", Toast.LENGTH_SHORT).show();
        login();
        //BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        //backgroundWorker.execute(type, login, password);
    }

    public void OnClickbtnRegister(View view) {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    private void login() {
        final String login = edtLogin.getText().toString();
        final String password = edtPassword.getText().toString();
        Toast.makeText(LoginActivity.this, "запуск запроса", Toast.LENGTH_SHORT).show();
        request = new StringRequest(Request.Method.POST, URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ProgressDialog loading;
                        //loading = ProgressDialog.show(LoginActivity.this, "Пожалуйста, подождите...", null, true, true);
                        try {
                            Toast.makeText(LoginActivity.this, "зашел в трай", Toast.LENGTH_SHORT).show();
                            JSONObject object = null;
                            object = new JSONObject(response);
                            if (!object.getBoolean("error")) {
                                SharedPrefManager.getInstance(getApplicationContext()).userLogin(
                                        object.getInt("id"),
                                        object.getString("login"),
                                        object.getString("name"),
                                        object.getString("surname"),
                                        object.getString("password"));
                                Intent intent = new Intent(LoginActivity.this, MapActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(LoginActivity.this, "от сервера пришел еррор", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        /*if (response.contains("success")) {
                            SharedPrefManager.getInstance(getApplicationContext().userLogin(login, password);
                            Intent intent = new Intent(LoginActivity.this, MapActivity.class);
                            startActivity(intent);
                        } else if (response.contains("no")){
                            Toast.makeText(LoginActivity.this, "пришел не саксес", Toast.LENGTH_SHORT).show();
                        }*/
                        /*try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.names().get(0).equals("1")) {
                                Intent intent = new Intent(LoginActivity.this, MapActivity.class);
                                startActivity(intent);
                            }
                        } catch(JSONException e) {
                            e.printStackTrace();
                        }*/

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, "еррор респонс", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //params.put("register", "true");
                params.put("login", edtLogin.getText().toString().trim());
                params.put("password", edtPassword.getText().toString().trim());
                return params;
            }
        };
        Volley.newRequestQueue(this).add(request);
        //requestQueue.add(request);
    }

}
