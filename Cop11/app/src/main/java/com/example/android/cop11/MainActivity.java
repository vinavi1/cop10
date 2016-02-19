package com.example.android.cop11;

import android.app.DownloadManager;
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

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button button;
    TextView board;
    TextView userid;
    TextView pass;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);
        board = (TextView) findViewById(R.id.board);
        userid = (TextView) findViewById(R.id.login);
        pass = (TextView) findViewById(R.id.password);
        requestQueue = Volley.newRequestQueue(this);
        button.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                board.setText(""+"22current_sem");
                JsonObjectRequest jsonObjectRequest;
                jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "http://10.205.156.218:8000/courses/list.json",null ,
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject jsonObject) {
                                try {
                                    int current_sem = jsonObject.getInt("\"current_sem\"");
                                    board.setText(""+current_sem);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }

                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("volley", "error");
                            }
                        }
                );
                requestQueue.add(jsonObjectRequest);
                break;

        }
    }
}