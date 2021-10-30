package com.example.vijaya.myorder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class summary extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        String temp = MainActivity.orderSummaryMessage;//accessing the order summary from the MainActivity
        //Toast.makeText(this,temp, Toast.LENGTH_LONG).show(); TESTING
        TextView summary=(TextView)findViewById(R.id.list_order);
        summary.setText(temp);//setting textView to display summary
    }

    public void back(View view){//goes back to the order page
        Intent _return = new Intent(summary.this, MainActivity.class);
        _return.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);//prevents the app from being cleared when returning
        startActivity(_return);
    }
}