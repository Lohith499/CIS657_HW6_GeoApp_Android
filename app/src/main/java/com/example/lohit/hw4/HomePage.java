/**
 * Created by Lohith and Brain CIS
 */

package com.example.lohit.hw4;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.location.Location;
import android.view.inputmethod.InputMethodManager;
import android.content.Context;

import static android.text.TextUtils.split;


public class HomePage extends AppCompatActivity  {
    EditText x1,y1,x2,y2;
    Button bcal,bclear;
    TextView dist,bear,error;
    String dmeasure, bmeasure;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Intent intentcheck = getIntent();

        x1 = (EditText) findViewById(R.id.lat1);
        y1 = (EditText) findViewById(R.id.long1);
        x2 = (EditText) findViewById(R.id.lat2);
        y2 = (EditText) findViewById(R.id.long2);

        bcal = (Button) findViewById(R.id.bCalculate);
        bclear = (Button) findViewById(R.id.bClear);
        dist = (TextView) findViewById(R.id.textViewdistance);
        bear = (TextView) findViewById(R.id.textViewbearinf);





        if (intentcheck.hasExtra("dselected")){
            dmeasure = getIntent().getStringExtra("dselected");
        }else
        {
            dmeasure= "Kilometers";
        }
        if (intentcheck.hasExtra("bselected")){
             bmeasure = getIntent().getStringExtra("bselected");
        }else
        {
            bmeasure= "Degrees";
        }

        if (intentcheck.hasExtra("coordindate")){

            String[] s = split(getIntent().getStringExtra("coordindate"),",");
            x1.setText(s[0]);
            y1.setText(s[1]);
            x2.setText(s[2]);
            y2.setText(s[3]);
            update();
        }else
        {

        }





        //error = (TextView) findViewById(R.id.editText);

        bcal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                keyboardhide();
                update();

            }
        });
        bclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyboardhide();
                x1.setText("");
                x2.setText("");
                y1.setText("");
                y2.setText("");
                dist.setText("");
                bear.setText("");
                //error.setText("");
            }
        });


    }


    public void update(){
        String sx1 = x1.getText().toString();
        String sy1 = y1.getText().toString();
        String sx2 = x2.getText().toString();
        String sy2 = y2.getText().toString();

        if (sx1.length()==0 || sx2.length()==0 || sy1.length()==0 || sy2.length()==0)
        {
            // error.setText("Please Enter all fields");
            return;
        }

        Location loc1 = new Location("");
        loc1.setLatitude(Double.parseDouble(x1.getText().toString()));
        loc1.setLongitude(Double.parseDouble(y1.getText().toString()));


        Location loc2 = new Location("");
        loc2.setLatitude(Double.parseDouble(x2.getText().toString()));
        loc2.setLongitude(Double.parseDouble(y2.getText().toString()));


        float distanceInMeters = (loc1.distanceTo(loc2) / 1000) ; //kms
        if (dmeasure.compareTo("Kilometers") != 0){

            distanceInMeters = distanceInMeters * Float.valueOf("1.6");
            dist.setText(Float.toString(distanceInMeters)+" Miles");

        } else {
            dist.setText(Float.toString(distanceInMeters)+" Kms");
        }


        float bearingbetween = (loc1.bearingTo(loc2)); //degress

        if (bmeasure.compareTo("Degrees") != 0){

            bearingbetween = bearingbetween * Float.valueOf("17.777");
            bear.setText(Float.toString(bearingbetween)+"Mils");
        } else {
            bear.setText(Float.toString(bearingbetween)+"Degrees");
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

            switch (item.getItemId()) {

                case R.id.settings:
                    Intent intent1 =new Intent(this,Settings.class);
                    String sx1 = x1.getText().toString();
                    String sy1 = y1.getText().toString();
                    String sx2 = x2.getText().toString();
                    String sy2= y2.getText().toString();
                    String s =  sx1+","+sy1+","+sx2+","+sy2;
                    intent1.putExtra("dselected", dmeasure);
                    intent1.putExtra("bselected", bmeasure);
                    intent1.putExtra("coordindate", s);
                    this.startActivity(intent1);

                    return true;

                default:
                    return super.onOptionsItemSelected(item);
            }

    }

    public void keyboardhide(){
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(x1.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(y1.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(x2.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(y2.getWindowToken(), 0);
    }

}
