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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button button;
    TextView board;
    TextView board2;
    Button button2;

    RequestQueue requestQueue;
    RequestQueue requestQueue2;
    String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);
        button2 = (Button) findViewById(R.id.button2);
        board = (TextView) findViewById(R.id.board);
        board2 = (TextView) findViewById(R.id.board2);
        requestQueue = Volley.newRequestQueue(this);
        requestQueue2 = Volley.newRequestQueue(this);
        button.setOnClickListener(this);
        button2.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:

                JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET,"http://192.168.24.1:8000/default/login.json?userid=vinay&password=vinay", null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                try {

                                    boolean jo = response.getBoolean("success");

                                    board.setText(""+jo);
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
                requestQueue.add(jor);
                break;

            case R.id.button2:
                board2.setText(""+"yuuuuu");
                JsonObjectRequest JsonObjectRequest  = new JsonObjectRequest(Request.Method.GET,"http://192.168.24.1:8000/default/logout.json", null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                try {

                                    int y = response.getInt("noti_count");

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
