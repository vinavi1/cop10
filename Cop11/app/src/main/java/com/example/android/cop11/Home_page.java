package com.example.android.cop11;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.service.voice.VoiceInteractionSession;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Vector;

public class Home_page extends AppCompatActivity implements View.OnClickListener {

    Button courses;
    Button Notifications;
    Button Logout;
    Button Grades;
    Home_page instance1;

    TextView cou;
    String url;
    Vector<String> courseslist;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Name = "nameKey";
    public static final String Phone = "phoneKey";
    SharedPreferences sharedPreferences;

    RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        instance1=this;

        courses= (Button) findViewById(R.id.courses);
        Logout= (Button) findViewById(R.id.Logout);
        Notifications= (Button) findViewById(R.id.notifi);
        Grades= (Button) findViewById(R.id.grades);

        cou = (TextView) findViewById(R.id.cou);

        courses.setOnClickListener(this);
        Notifications.setOnClickListener(this);
        Logout.setOnClickListener(this);
        Grades.setOnClickListener(this);

        queue = Volley.newRequestQueue(this);


        sharedPreferences = getSharedPreferences(MyPREFERENCES,Context.MODE_PRIVATE);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.courses:
                url = "http://192.168.23.1:8000/courses/list.json";
                StringRequest j = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        if(s==null){
                            cou.setText("NULL");
                        }
                        else{
                            cou.setText(s.toString());
                        }
                    }
                },new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        cou.setText("asasasasa");
                    }
                },this);
                queue.add(j);
                break;
            case R.id.notifi:
                url = "http://192.168.23.1:8000/default/notifications.json";
                StringRequest ja = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        if(s==null){
                            cou.setText("NULL");
                        }
                        else{
                            cou.setText(s.toString());
                        }
                    }
                },new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        cou.setText("asasasasa");
                    }
                },this);
                queue.add(ja);
                break;
            case R.id.Logout:
                url = "http://192.168.23.1:8000/default/logout.json";
                StringRequest jas = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONObject noticount= new JSONObject(s);
                            if(noticount.getInt("noti_count")==4){
                                SharedPreferences.Editor editor= sharedPreferences.edit();
                                editor.putBoolean("logged",false);
                                editor.commit();
                                Intent intent= new Intent(instance1,MainActivity.class);
                                startActivity(intent);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        cou.setText("asasasasa");
                    }
                },this);
                queue.add(jas);
                break;
            case R.id.grades:
                url = "http://192.168.23.1:8000/default/grades.json";
                StringRequest gradesR = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        if(s==null){
                            cou.setText("NULL");
                        }
                        else{
                            cou.setText(s.toString());
                        }
                    }
                },new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        cou.setText("asasasasa");
                    }
                },this);
                queue.add(gradesR);
                break;
        }
    }
}
