package com.giridharan.json1;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity2Activity extends ActionBarActivity {
    TextView t1,t2,t3,t4,t5,t6,t7,t8,t9,t10;
    String lat,lon,link;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity2);
        t1 = (TextView) findViewById(R.id.textView);
        t2=(TextView) findViewById(R.id.textView2);
        t3=(TextView) findViewById(R.id.textView3);
        t4=(TextView) findViewById(R.id.textView4);
        t5=(TextView) findViewById(R.id.textView5);
        t6=(TextView) findViewById(R.id.textView6);
        t7=(TextView) findViewById(R.id.textView7);
        t8=(TextView) findViewById(R.id.textView8);
        t9=(TextView) findViewById(R.id.textView9);
        t10=(TextView) findViewById(R.id.textView10);



        Bundle bundle = getIntent().getExtras();
        String name= bundle.getString("name");
        String image= bundle.getString("name");
        String date= bundle.getString("date");
        String type= bundle.getString("type");
        String weight= bundle.getString("weight");
        String phone= bundle.getString("phone");
        String price= bundle.getString("price");
        String Qty= bundle.getString("quantity");
        String color= bundle.getString("color");
        link= bundle.getString("link");
         lat= bundle.getString("latitude");
        lon= bundle.getString("longitude");
        t1.setText("Name :"+name);
        t2.setText("Date :"+date);
        t3.setText("Type :"+type);
        t4.setText("Weight :"+weight);
        t5.setText("Phno :"+phone);
        t6.setText("Price :"+price);
        t7.setText("Qty :"+Qty);
        t8.setBackgroundColor(Color.parseColor(color));
        t9.setText("Link :" + link);
        t10.setText("Location"+ lat+" , "+lon);
        t10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent io = new Intent(MainActivity2Activity.this, MapsActivity.class);
                Bundle b=new Bundle();
                b.putString("Lat",lat);
                b.putString("Lon",lon);
                io.putExtras(b);
                startActivity(io);


            }
        });
       t9.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent sendIntent = new Intent();
               sendIntent.setAction(Intent.ACTION_SEND);
               sendIntent.putExtra(Intent.EXTRA_TEXT, link);
               sendIntent.setType("text/plain");

               // Do not forget to add this to open whatsApp App specifically
               sendIntent.setPackage("com.whatsapp");
               startActivity(sendIntent);
           }
       });

        Toast.makeText(getApplicationContext(),"NAME :"+ name+"\n"+image+"\n "+date+"\n "+type, Toast.LENGTH_SHORT).show();



    }

}
