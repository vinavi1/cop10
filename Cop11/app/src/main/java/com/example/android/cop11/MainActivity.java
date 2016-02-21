package com.example.android.cop11;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    Button button;
    TextView board;
    TextView board2;
    Button button2;
    EditText name;
    EditText pass;
    String url;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Name = "nameKey";
    public static final String Phone = "phoneKey";
    SharedPreferences sharedPreferences;
    MainActivity instance;

    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance=this;

        name= (EditText) findViewById(R.id.name);
        pass = (EditText) findViewById(R.id.pass);
        button = (Button) findViewById(R.id.button);
        button2 = (Button) findViewById(R.id.button2);
        board = (TextView) findViewById(R.id.board);
        board2 = (TextView) findViewById(R.id.board2);

        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        if(!sharedPreferences.getString(Name,null).isEmpty()){
            name.setText(sharedPreferences.getString(Name,null));
        }
        if(!sharedPreferences.getString(Phone,null).isEmpty()){
            pass.setText(sharedPreferences.getString(Phone,null));
        }
        if(sharedPreferences.getBoolean("logged",false)){
            Intent s= new Intent(this,Home_page.class);
            startActivity(s);
        }
        requestQueue = Volley.newRequestQueue(this);

        button.setOnClickListener(this);
        button2.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                String name1=name.getText().toString();
                String paswd= pass.getText().toString();

                final SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString(Name, name1);
                editor.putString(Phone, paswd);
                Toast.makeText(MainActivity.this, "asasa", Toast.LENGTH_LONG);

                url="http://192.168.23.1:8000/default/login.json?userid="+name1+"&password="+paswd;
                StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONObject jsonObject= new JSONObject(s);
                            if(jsonObject.getBoolean("success")){
                                editor.putBoolean("logged",true);
                                editor.commit();
                                Intent intent=new Intent(instance,Home_page.class);
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
                        board.setText("sasasasas");
                    }
                }, this);
                requestQueue.add(stringRequest);
                //Intent intent=new Intent(this,Home_page.class);
                //startActivity(intent);
                break;

            case R.id.button2:
                board2.setText("" + sharedPreferences.getString(Name, null));

                JsonObjectRequest JsonObjectRequest  = new JsonObjectRequest(Request.Method.GET,"http://192.168.24.1:8000/courses/list.json", null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    int y = response.getInt("current_sem");
                                    board2.setText(""+y);
                                }catch(JSONException e){e.printStackTrace();}
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("Volley","Error");

                            }
                        }
                );
                requestQueue.add(JsonObjectRequest);
                break;
        }
    }
}
