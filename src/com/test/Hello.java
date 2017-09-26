package com.test;

import java.io.File;

import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.je.LockMode;

public class Hello {
    public static void main(String args[]) throws Exception {
        Environment env = null;
        Database db = null;
        EnvironmentConfig envConfig = new EnvironmentConfig();
        envConfig.setAllowCreate(true);
        envConfig.setCacheSize(100000000);
        File home = new File("/Users/yinguoliang/Desktop/bdb/");
        env = new Environment(home, envConfig);

        DatabaseConfig dbConfig = new DatabaseConfig();
        dbConfig.setAllowCreate(true);
        db = env.openDatabase(null, "my", dbConfig);
        for (int i = 0; i < 1000000; i++) {
            String key = "mykey" + i;
            String value = "VALUE_" + i;
            DatabaseEntry k = new DatabaseEntry(key.getBytes());
            DatabaseEntry v = new DatabaseEntry(value.getBytes());
            db.put(null, k, v);
        }
//        for (int i = 0; i < 100; i++) {
//            DatabaseEntry value = new DatabaseEntry();
//            String key = "mykey" + i;
//            db.get(null, new DatabaseEntry(key.getBytes()), value, LockMode.DEFAULT);
//            System.out.println(new String(value.getData()));
//        }
        DatabaseEntry value = new DatabaseEntry();
        DatabaseEntry searchKey = new DatabaseEntry(("mykey"+500000).getBytes());
        db.get(null, searchKey, value, LockMode.DEFAULT);
        db.get(null, searchKey, value, LockMode.DEFAULT);
        db.get(null, searchKey, value, LockMode.DEFAULT);
        db.get(null, searchKey, value, LockMode.DEFAULT);
        db.get(null, searchKey, value, LockMode.DEFAULT);
        db.close();
        env.close();
    }
}
