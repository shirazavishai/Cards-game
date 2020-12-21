package com.example.hw_02.utils;

import android.content.Context;
import android.content.SharedPreferences;

/*
* Used the third version we studied at class with guy
* Singleton class - no public constructor
*/
public class MySP {
        private static MySP instance;
        private final SharedPreferences prefs;

        private MySP(Context context) {
                prefs = context.getSharedPreferences("MY_SP", Context.MODE_PRIVATE);
        }

        /*
        Init only one time
         */
        public static void init(Context context) {
                if (instance == null) {
                        instance = new MySP(context.getApplicationContext());
                }
        }

        public static MySP getInstance() {
                return instance;
        }

        /*
        Put new item.
        */
        public void putString(String key, String value) {
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString(key, value);
                editor.apply();
        }

        /*
        Take specified key's value
         */
        public String getString(String key, String def) {
                return prefs.getString(key, def);
        }

        /*
        Remove item with key specified
         */
        public void removeKey(String key) {
                SharedPreferences.Editor editor = prefs.edit();
                editor.remove(key);
                editor.apply();
        }
}
