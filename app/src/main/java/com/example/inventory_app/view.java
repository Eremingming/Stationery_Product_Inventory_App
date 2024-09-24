package com.example.inventory_app;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class view extends MainActivity {


    ListView lst1;
    ArrayList<String> titles = new ArrayList<String>();
    ArrayAdapter arrayAdapter;

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
        setContentView(R.layout.activity_view);


        SQLiteDatabase db = openOrCreateDatabase("SliteDb", Context.MODE_PRIVATE, null);

        lst1 = findViewById(R.id.lst1);
        final Cursor c = db.rawQuery("select * from records", null);
        int prodid = c.getColumnIndex("prodid");
        int prodname = c.getColumnIndex("prodname");
        int purprice = c.getColumnIndex("purprice");
        int retprice = c.getColumnIndex("retprice");
        int idealstock = c.getColumnIndex("idealstock");
        int avstock = c.getColumnIndex("avstock");
        int availstocksval = c.getColumnIndex("availstocksval");


        titles.clear();

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
                        startActivity(new Intent(view.this, dashboard.class));
                        break;
                    case R.id.nav_inventory:
                        startActivity(new Intent(view.this, view.class));
                        break;
                    case R.id.nav_addstock:
                        startActivity(new Intent(view.this, Add.class));
                        break;
                    case R.id.nav_logout:
                        startActivity(new Intent(view.this, Login.class));
                        break;
                }
                return true;
            }
        });


        arrayAdapter = new ArrayAdapter(this, com.google.android.material.R.layout.support_simple_spinner_dropdown_item, titles);
        lst1.setAdapter(arrayAdapter);

        final ArrayList<product> prod = new ArrayList<product>();


        if (c.moveToFirst()) {
            do {
                product prd = new product();
                prd.prodid = c.getString(prodid);
                prd.prodname = c.getString(prodname);
                prd.purprice = c.getString(purprice);
                prd.retprice = c.getString(retprice);
                prd.idealstock = c.getString(idealstock);
                prd.avstock = c.getString(avstock);
                prd.availstocksval = c.getString(availstocksval);


                prod.add(prd);

                titles.add(c.getString(prodid) + " \t " + c.getString(prodname) + " \t " + c.getString(purprice) + "\t "
                        + c.getString(retprice) + "\t " + c.getString(idealstock) + " \t " + c.getString(avstock)
                        + " \t " + c.getString(availstocksval));

            } while (c.moveToNext());
            arrayAdapter.notifyDataSetChanged();
            lst1.invalidateViews();


        }

        lst1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String aa = titles.get(position).toString();

                product prd = prod.get(position);
                Intent i = new Intent(getApplicationContext(), edit.class);
                i.putExtra("prodid", prd.prodid);
                i.putExtra("prodname", prd.prodname);
                i.putExtra("purprice", prd.purprice);
                i.putExtra("retprice", prd.retprice);
                i.putExtra("idealstock", prd.idealstock);
                i.putExtra("avstock", prd.avstock);
                i.putExtra("availstocksval", prd.availstocksval);
                startActivity(i);

            }
        });

    }
}