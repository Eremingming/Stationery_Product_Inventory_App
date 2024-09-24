package com.example.inventory_app;



import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;


public class dashboard extends MainActivity {

    TextView txt1, txt2, txt3, txt4, txt5;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navView);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.menu_Open, R.string.close_Menu);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_dashboard:
                        startActivity(new Intent(dashboard.this, dashboard.class));
                        break;
                    case R.id.nav_inventory:
                        startActivity(new Intent(dashboard.this, view.class));
                        break;
                    case R.id.nav_addstock:
                        startActivity(new Intent(dashboard.this, Add.class));
                        break;
                    case R.id.nav_logout:
                        startActivity(new Intent(dashboard.this, Login.class));
                        break;
                }
                return true;
            }
        });


        txt1 = findViewById(R.id.tp);
        txt2 = findViewById(R.id.lsp);
        txt3 = findViewById(R.id.osp);
        txt5 = findViewById(R.id.msp);



        SQLiteDatabase db = openOrCreateDatabase("SliteDb", Context.MODE_PRIVATE,null);
        db.execSQL("CREATE TABLE IF NOT EXISTS records(prodid INTEGER PRIMARY KEY AUTOINCREMENT,prodname VARCHAR, purprice VARCHAR," +
                "retprice VARCHAR,idealstock VARCHAR,avstock VARCHAR,availstocksval VARCHAR)");



        String countQuery = "SELECT COUNT(*) FROM records WHERE avstock = 0";
        Cursor cursor = db.rawQuery(countQuery, null);
        if (cursor.moveToFirst()) {
            int count = cursor.getInt(0);
            txt3.setText(String.valueOf(count));
        }
        cursor.close();


        String total = "SELECT COUNT(*) FROM records ";
        Cursor cursorTotal = db.rawQuery(total, null);
        if (cursorTotal.moveToFirst()) {
            int totals = cursorTotal.getInt(0);
            txt1.setText(String.valueOf(totals));
        }
        cursorTotal.close();

        String low = "SELECT * FROM records WHERE  avstock >= 5 AND avstock !=0";
        Cursor cursorlow = db.rawQuery(low, null);
        if (cursorlow.moveToFirst()) {
            int lowest = cursorlow.getInt(0);
            txt2.setText(String.valueOf(lowest));
        }
        cursorlow.close();

        String most = "SELECT prodname FROM records WHERE avstock = (SELECT MAX(avstock) FROM records) GROUP BY prodname;";
        Cursor cursormost = db.rawQuery(most, null);
        if (cursormost.moveToFirst()) {
            String moststock = cursormost.getString(0);
            txt5.setText(moststock);
        }
        cursormost.close();


    }
}
