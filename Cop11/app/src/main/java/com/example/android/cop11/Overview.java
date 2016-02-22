package com.example.android.cop11;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Overview extends AppCompatActivity implements View.OnClickListener {

    TextView welcome;
    TextView entry_no;
    TextView email;
    Button conti;
    Overview instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);
        instance = this ;

        welcome = (TextView) findViewById(R.id.welcome);
        entry_no = (TextView) findViewById(R.id.entry_no);
        email = (TextView) findViewById(R.id.email);
        conti = (Button) findViewById(R.id.conti);
        conti.setOnClickListener(this);

        Intent intent = getIntent();

        if(null != intent) {
            welcome.setText("Welcome ," + intent.getStringExtra("first"));
            entry_no.setText(intent.getStringExtra("entry_no"));
            email.setText(intent.getStringExtra("email"));


        }




    }

    @Override
    public void onClick(View v) {
        Intent intent2=new Intent(instance,Home_page.class);
        startActivity(intent2);
    }
}
