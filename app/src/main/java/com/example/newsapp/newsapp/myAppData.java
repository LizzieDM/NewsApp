package com.example.newsapp.newsapp;

import android.app.Application;

import java.util.LinkedList;

/**
 * Created by ASUS on 27/05/2015.
 */
public class myAppData extends Application {
        private LinkedList<FeedElement> data = null;
        private int selectedOption = ElDiaActivity.APP_VIEW;

        public LinkedList<FeedElement> getData(){
            return this.data;
        }
        public void setData(LinkedList<FeedElement> d){
            this.data = d;
        }

        public int getSelectedOption(){
            return this.selectedOption;
        }

        public void setSelectedOption(int selectedOption) {
            this.selectedOption = selectedOption;
        }
   }

