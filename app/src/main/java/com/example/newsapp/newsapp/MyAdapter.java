package com.example.newsapp.newsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.newsapp.newsapp.FeedElement;
import com.example.newsapp.newsapp.R;

import java.util.LinkedList;

/**
 * Created by ASUS on 27/05/2015.
 */
public class MyAdapter extends ArrayAdapter<FeedElement> {
    LayoutInflater inf;
    LinkedList<FeedElement> objects;
    public MyAdapter(Context context, int resource, int textViewResourceId,
                     LinkedList<FeedElement> objects) {
        super(context, resource, textViewResourceId, objects);
        this.inf = LayoutInflater.from(context);
        this.objects = objects;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        View row = convertView;
        /**
         * obtenemos el elemento actual a renderizar
         */
        FeedElement currentElement = (FeedElement)objects.get(position);

        /**
         * Si la vista (convertView) recibida es nula, debemos instanciar desde el XML
         */
        if (row == null) {
            row = inf.inflate(R.layout.rss_row, null);
        }

        /**
         * Obtenemos la imagen del art�culo y la asignamos al ImageView correspondiente
         * dentro de su layout
         */
        ImageView iv = (ImageView) row.findViewById(R.id.imgElement);
        iv.setImageBitmap(currentElement.getImage());
        iv.setScaleType(ImageView.ScaleType.FIT_XY);

        /**
         * Obtenemos el t�tulo del art�culo y la asignamos al TextView correspondiente
         * dentro de su layout
         */
        TextView tv = (TextView) row.findViewById(R.id.txtElement);
        tv.setText(currentElement.getTitle());

        return row;
    }

}
