package com.example.newsapp.newsapp;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import static com.example.newsapp.newsapp.R.layout.activity_el_dia;

public class NewsMainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_main);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_news_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /** Called when the user clicks the Locals button */
    public void gotoLocals(View view) {
        // Do something in response to button @Override
        setContentView(R.layout.activity_news_locals);
    }


    /** Called when the user clicks the Nationals button */
    public void gotoNationals(View view) {
        // Do something in response to button
        setContentView(R.layout.activity_news_main);
    }

    /** Called when the user clicks the ElDia  button */
    public void gotoElDia(View view) {
       setContentView(activity_el_dia);
        try {
            Intent startIntent = new Intent(this, ElDiaActivity.class);
            startIntent.putExtra("layout", R.layout.activity_el_dia);
            //Intent startIntent = new Intent(NewsMainActivity.this, ElDiaActivity.class);
            /*Intent startIntent;
            startIntent = new Intent(ElDiaActivity.class);*/
            startActivity(startIntent);
        } catch ( Exception e) {
            e.printStackTrace();
        }

    }



}
