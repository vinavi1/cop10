package com.example.android.cop11;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.xml.transform.ErrorListener;
import javax.xml.transform.TransformerException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button login;
    TextView board;
    EditText nameid;
    EditText passid;
    String url;
    String u_entered;
    String p_entered;
    public static final String MyPREFERENCES = "MyPrefs" ;

    SharedPreferences sharedPreferences;
    MainActivity instance;

    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance = this;

        nameid = (EditText) findViewById(R.id.name);
        passid = (EditText) findViewById(R.id.pass);
        login = (Button) findViewById(R.id.login);
        board = (TextView) findViewById(R.id.board);

        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        if (sharedPreferences.getString("namekey", null) != null) {
            nameid.setText(sharedPreferences.getString("namekey", null));
        }
        if (sharedPreferences.getString("passkey", null) != null) {
            passid.setText(sharedPreferences.getString("passkey", null));
        }
        if (sharedPreferences.getBoolean("logged", false)) {
            Intent s = new Intent(this, Home_page.class);
            startActivity(s);
        }
        requestQueue = Volley.newRequestQueue(this);

        login.setOnClickListener(this);
        nameid.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                u_entered = nameid.getText().toString();
                p_entered = passid.getText().toString();
                final SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("namekey", u_entered);
                editor.putString("passkey", p_entered);
                editor.commit();
            }
        });
        passid.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                u_entered = nameid.getText().toString();
                p_entered = passid.getText().toString();
                final SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("namekey", u_entered);
                editor.putString("passkey", p_entered);
                editor.commit();
            }
        });

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:

                final SharedPreferences.Editor editor=sharedPreferences.edit();
                url="http://192.168.23.1:8000/default/login.json?userid="+u_entered+"&password="+p_entered;
                StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONObject jsonObject= new JSONObject(s);
                            JSONObject info = jsonObject.getJSONObject("user");
                            String first  = info.getString("first_name");
                            String last = info.getString("last_name");
                            String entry_no = info.getString("entry_no");
                            String email = info.getString("email");

                            if(jsonObject.getBoolean("success")){
                                editor.putBoolean("logged", true);
                                editor.commit();
                                Intent intent=new Intent(instance,Overview.class);
                                intent.putExtra("first",first);
                                intent.putExtra("username", first+" "+last);
                                intent.putExtra("entry_no", entry_no);
                                intent.putExtra("email", email);
                                startActivity(intent);
                            }
                            editor.commit();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        board.setText("Check-URL");
                    }
                }, this);
                requestQueue.add(stringRequest);
                //Intent intent=new Intent(this,Home_page.class);
                //startActivity(intent);
                break;
        }
    }
}
