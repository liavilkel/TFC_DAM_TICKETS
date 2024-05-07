package com.example.tfc_dam_tickets.persistence;

import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBContection {

    Connection con;
    String username, password, ip, port, database;

   public Connection connectionClass(){
       ip = "82.223.204.116";
       database = "resolver_rocket";
       username = "tfg_user";
       password = "javi javi";
       port = "3306";

       StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
       StrictMode.setThreadPolicy(policy);
       Connection connection = null;
       String connectionURL = null;

       try {
           Class.forName("net.sourcefotge.jtds.jdbc.Driver");
           connectionURL= "jdbc:jtds:sqlserver://"+ ip + ":"+ port+";"+ "databasename="
                   + database+";user="+username+";password="+password+";";
           connection = DriverManager.getConnection(connectionURL);
       } catch (Exception e) {
           Log.e("Error ", e.getMessage());
       }

       return connection;

   }

}
