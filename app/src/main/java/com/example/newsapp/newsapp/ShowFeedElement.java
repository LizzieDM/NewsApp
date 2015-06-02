package com.example.newsapp.newsapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by ASUS on 27/05/2015.
 */
public class ShowFeedElement extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showfeedelement);
        /**
         * obtenemos el intent que levanto esta actividad
         */
        Intent it = getIntent();

        /**
         * ese intent deber�a traer la posisici�n del elemento a visualizar
         * si en caso fuera nulo (el intent que levanto esta aplicaci�n no manda nada)
         * entonces se le asigna -1
         */
        int position = it.getIntExtra(ElDiaActivity.POSITION_KEY, -1);
        if (position != -1) {

            /**
             * Obtenemos el listado de art�culos a partir de la clase de aplicaci�n
             */
            myAppData appState = ((myAppData) getApplication());
            FeedElement e = appState.getData().get(position);

            /**
             * Colocalos la data del art�culo que el usuario quiere visualizar
             */
            TextView txtTitle = (TextView) findViewById(R.id.txtTitle);
            txtTitle.setText(e.getTitle());

            TextView txtAuthor = (TextView) findViewById(R.id.txtAuthor);
            txtAuthor.setText("por " + e.getAuthor());

            TextView txtDesc = (TextView) findViewById(R.id.txtDesc);
            txtDesc.setText(e.getDescription());

        } else {
            /**
             * Si el identificador es inv�lido se regresa a la actividad principal
             * enviando un extra con el valor de -1 para indicarle que hubo error
             */
            Intent backToMainActivity = new Intent(this, ElDiaActivity.class);
            backToMainActivity.putExtra(ElDiaActivity.POSITION_KEY, -1);
            startActivity(backToMainActivity);
        }
    }
}
