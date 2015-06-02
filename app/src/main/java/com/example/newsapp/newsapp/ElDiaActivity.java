package com.example.newsapp.newsapp;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.LinkedList;


/**
 * Created by ASUS on 22/04/2015.
 */
public class ElDiaActivity extends ListActivity {
    /**
     * constantes para identificar si el usuario quiere visualizar los art�tuclos
     * dentro de la aplicaci�n o en el navegador
     */
    final static int APP_VIEW = 1;
    final static int BROWSER_VIEW = 2;

    /**
     * constante para identificar la llave con la que env�o datos a trav�s de intents
     * para comunicar entre las dos actividades: Main y ShowElement
     */
    final static String POSITION_KEY = "P";

    /**
     * Guardamos la direcci�n del feed como otra variable de clase para poder modificarla sin
     * complicaciones m�s adelante.
     */
    static String feedUrl = "http://eldia.es/rss/portada.rss";

    /**
     * Utilizamos una variable de instancia para la clase de aplicaci�n, de esta forma se accesar�n,
     * para guardar y recuperar, la lista de art�culos y la preferncia del usuario
     */
    private myAppData appState;


    /**
     * para el di�logo de progreso es necesaria una variable global porque iniciamos el di�logo en
     * una funci�n y lo ocultamos en otra
     */
    private ProgressDialog progressDialog;
    /**
     * Android nos presenta la restricciones que no podemos alterar los elementos de interfaz
     * gr�fica en un hilo de ejecuci�n que no sea el principal por lo que es necesario utilizar
     * un manejador(Handler) para enviar un mensaje de un hilo a otro cuando la carga de datos
     * haya terminado.
     */

    private final Handler progressHandler = new Handler() {
        public void handleMessage(Message msg) {
            setData();
            progressDialog.dismiss();
        }
    };


    /** Este mŽtodo es llamado cuando la actividad es creada */

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_el_dia);
        /**

         * Le ponemos nombre a la ventana
         */
        setTitle("Lector de el Día ");


        /**
         * Inicializamos la variable para nuestra clase de aplicaci�n
         */
        appState = ((myAppData)getApplication());


        /**
         * Validamos si el intent lo levant� alguna otra actividad y si viene un -1
         * en el mensaje mostramos un error
         */
        Intent it = getIntent();
        int fromShowElement = it.getIntExtra(POSITION_KEY, 0);
        if (fromShowElement == -1) {
            Toast.makeText(this, "Error, imposible visualizar el elemento", Toast.LENGTH_LONG);
        }


        Button btn = (Button) findViewById(R.id.btnLoad);


        /**
         * personalizamos el evento del click del bot�n de carga
         */

        btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                LinkedList<FeedElement> data = appState.getData();
                /**

                 * Si ya hay datos que se fueron a traer y reconocer (parsear)
                 * mostramos un di�logo preguntando al usuario si est� seguro de
                 * realizar la carga de nuevo
                 */

                if (data != null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ElDiaActivity.this);
                    builder.setMessage("ya ha cargado datos, �Est� seguro de hacerlo de nuevo?")
                            .setCancelable(false)
                            .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    loadData();
                                }
                            })



                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            })
                            .create()
                            .show();
                    /**






                     * Si no hay datos a�n, cargamos con loadData()
                     */
                } else {


                    loadData();
                }
            }
        });




    }

    /**
     * Funci�n que inicializa el men� para mostrarlo
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.layout.menu, menu);
        return true;
    }


    /**
     * Funci�n que se dispara al elegir una funci�n del men� y guarda lo que eligi� el usuario
     * en la clase de aplicacion a trav�s de una llamada a setSelectedOption
     */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mmElementApp:
                appState.setSelectedOption(APP_VIEW);
                break;

            case R.id.mmElementBrw:
                appState.setSelectedOption(BROWSER_VIEW);
                break;

        }


        return true;
    }



    /**
     * Funci�n que se dispara cuando el usuario haga click en alg�n elemento de la lista,
     * dependiendo de la configuraci�n lo llevaremos al enlace del elemento a trav�s del navegador o
     * bien se le mostrar� una vista previa dentro de la aplicaci�n
     */
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent nextActivity = null;


        if (appState.getSelectedOption() == APP_VIEW) {
            nextActivity = new Intent(this, ShowFeedElement.class);
            nextActivity.putExtra(POSITION_KEY, position);
        } else {
            LinkedList<FeedElement> data = appState.getData();
            nextActivity = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(data.get(position).getLink()));

        }




        this.startActivity(nextActivity);
    }


    /**
     * Funci�n auxiliar que recibe una lista de mapas, y utilizando esta data crea un adaptador
     * para poblar al ListView del dise�o
     * */

    private void setData(){
        this.setListAdapter(new MyAdapter(this, R.layout.rss_row, 0, appState.getData()));
    }



    /**
     * Funci�n auxiliar que inicia la carga de datos, muestra al usuario un di�logo de que
     * se est�n cargando los datos y levanta un thread para lograr la carga.
     */

    private void loadData() {
        progressDialog = ProgressDialog.show(
                ElDiaActivity.this,
                "",


                "Por favor espere mientras se cargan los datos...",
                true);



        new Thread(new Runnable(){
            @Override
            public void run() {
                ElDiaXMLParser parser = new ElDiaXMLParser(feedUrl);

                appState.setData(parser.parse());
                progressHandler.sendEmptyMessage(0);
            }}).start();
    }

}
