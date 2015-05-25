package com.example.newsapp.utils;

import java.util.ArrayList;

/**
 * Created by ASUS on 10/05/2015.
 */
public class Tools {
    public static ArrayList<?> getData(int offset, int limit, ArrayList<?> array)
    {
        ArrayList newFeeds = new ArrayList(limit);
        int end = offset + limit;
        if (end > array.size())
            end = array.size();

        newFeeds.addAll(array.subList(offset, end));
        return newFeeds;
    }
}
