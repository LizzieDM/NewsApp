package com.example.newsapp.utils;

import android.os.AsyncTask;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by ASUS on 10/05/2015.
 */
public class EndScroll {

    private ListView listview = null;
    private ArrayAdapter adapter = null;
    private ArrayList<?> array = null;
    private int PAGESIZE = 0;
    private boolean loading = false;

    public EndScroll(ListView listview, ListAdapter adapter, ArrayList<?> array, int pagesize)
    {
        this.adapter = (ArrayAdapter)adapter;
        this.array = array;
        this.listview = listview;
        this.PAGESIZE = pagesize;
        this.listview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (checkLastItem(firstVisibleItem, visibleItemCount, totalItemCount))
                {
                    //System.out.println("LLEGA");
                    loading = true;
                    new nextPage().execute();
                }
            }
        });
    }

    protected boolean checkLastItem(int firstVisibleItem, int visibleItemCount, int totalItemCount)
    {
        boolean lastItem = firstVisibleItem + visibleItemCount == totalItemCount - 2;
        boolean moreRows = this.adapter.getCount() < this.array.size();

        return moreRows && lastItem && !this.loading;
    }

    protected class nextPage extends AsyncTask<Void, Void, Boolean>
    {
        private ArrayList<?> newData = null;

        @Override
        protected Boolean doInBackground(Void... voids) {
            newData = Tools.getData(adapter.getCount(), PAGESIZE, array);
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result){
            try{
                for(Object value : newData){
                    adapter.add(value);
                }
                loading = false;
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
