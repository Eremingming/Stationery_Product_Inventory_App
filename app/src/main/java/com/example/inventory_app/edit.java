package com.example.inventory_app;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class edit extends MainActivity {

    EditText ed1,ed2,ed3,ed4,ed5,ed6,ed7;
    Button b1,b2,b3, b4;

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
        setContentView(R.layout.activity_edit);


        ed1 = findViewById(R.id.prodid);
        ed2 = findViewById(R.id.prodname);
        ed3 = findViewById(R.id.purprice);
        ed4 = findViewById(R.id.retprice);
        ed5 = findViewById(R.id.idealstock);
        ed6 = findViewById(R.id.avstock);
        ed7 = findViewById(R.id.availstocksval);

        b1 = findViewById(R.id.bt1);
        b2 = findViewById(R.id.bt2);
        b3 = findViewById(R.id.bt3);
        b4 = findViewById(R.id.bt4);

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
                        startActivity(new Intent(edit.this, dashboard.class));
                        break;
                    case R.id.nav_inventory:
                        startActivity(new Intent(edit.this, view.class));
                        break;
                    case R.id.nav_addstock:
                        startActivity(new Intent(edit.this, Add.class));
                        break;
                    case R.id.nav_logout:
                        startActivity(new Intent(edit.this, Login.class));
                        break;
                }
                return true;
            }
        });



        Intent i = getIntent();

        String t1 = i.getStringExtra("prodid");
        String t2 = i.getStringExtra("prodname");
        String t3 = i.getStringExtra("purprice");
        String t4 = i.getStringExtra("retprice");
        String t5 = i.getStringExtra("idealstock");
        String t6 = i.getStringExtra("avstock");
        String t7 = i.getStringExtra("availstocksval");

        ed1.setText(t1);
        ed2.setText(t2);
        ed3.setText(t3);
        ed4.setText(t4);
        ed5.setText(t5);
        ed6.setText(t6);
        ed7.setText(t7);


        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Delete();
            }
        });


        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(),Add.class);
                startActivity(i);

            }
        });


        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(),view.class);
                startActivity(i);

            }
        });


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Edit ();
            }
        });

    }

    public void Delete()
    {
        try
        {
            String prodid = ed1.getText().toString();

            SQLiteDatabase db = openOrCreateDatabase("SliteDb", Context.MODE_PRIVATE,null);

            String sql = "delete from records where prodid = ?";
            SQLiteStatement statement = db.compileStatement(sql);
            statement.bindString(1,prodid);
            statement.execute();
            Toast.makeText(this,"Record Deleted",Toast.LENGTH_LONG).show();

            ed1.setText("");
            ed2.setText("");
            ed3.setText("");
            ed4.setText("");
            ed5.setText("");
            ed6.setText("");
            ed7.setText("");


        }
        catch (Exception ex)
        {
            Toast.makeText(this,"Record Fail",Toast.LENGTH_LONG).show();
        }
    }
    public void Edit()
    {
        try
        {
            String prodid = ed1.getText().toString();
            String prodname = ed2.getText().toString();
            String purprice = ed3.getText().toString();
            String retprice = ed4.getText().toString();
            String idealstock = ed5.getText().toString();
            String avstock = ed6.getText().toString();
            String availstocksval = ed7.getText().toString();

            SQLiteDatabase db = openOrCreateDatabase("SliteDb",Context.MODE_PRIVATE,null);


            String sql = "UPDATE records SET prodid = ?, prodname = ?, purprice = ?, retprice = ?, idealstock = ?, avstock = ?, availstocksval = ? WHERE prodid = ?";
            SQLiteStatement statement = db.compileStatement(sql);
            statement.bindString(1, prodid);
            statement.bindString(2, prodname);
            statement.bindString(3, purprice);
            statement.bindString(4, retprice);
            statement.bindString(5, idealstock);
            statement.bindString(6, avstock);
            statement.bindString(7, availstocksval);
            statement.bindString(8, prodid);
            statement.execute();
            Toast.makeText(this,"Record Updated",Toast.LENGTH_LONG).show();


            ed1.setText("");
            ed2.setText("");
            ed3.setText("");
            ed4.setText("");
            ed5.setText("");
            ed6.setText("");
            ed7.setText("");
            ed1.requestFocus();


        }
        catch (Exception exception)
        {
            Toast.makeText(this,"Record Fail",Toast.LENGTH_LONG).show();
        }

    }

}