package com.example.krist.getlocation;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ListUsersActivity extends AppCompatActivity {

    ListView listView;
    ArrayAdapter <String> adapter;
    String address = "https://kristinabaskakova15.000webhostapp.com/testscript.php";

    InputStream is = null;
    String line = null;
    String result = null;
    String[] name;
    String[] surname;
    String[] login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_users);

        listView = (ListView) findViewById(R.id.listView);

        StrictMode.setThreadPolicy((new StrictMode.ThreadPolicy.Builder().permitNetwork().build()));
        getData();
        //adapter = new ArrayAdapter<String>(this, R.layout.activity_item, data);
        //listView.setAdapter(adapter);

        UserListViewActivity listViewactivity = new UserListViewActivity(this, name, surname, login);
        listView.setAdapter(listViewactivity);
    }

    private void getData() {
        try {
            URL url = new URL(address);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            is = new BufferedInputStream(con.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();

            while ((line  = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            result = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            JSONArray ja = new JSONArray(result);
            JSONObject jo = null;
            name = new String[ja.length()];
            surname = new String[ja.length()];
            login = new String[ja.length()];

            for (int i = 0; i < ja.length(); i++) {
                jo = ja.getJSONObject(i);
                name[i] = jo.getString("name");
                surname[i] = jo.getString("surname");
                login[i] = jo.getString("login");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
