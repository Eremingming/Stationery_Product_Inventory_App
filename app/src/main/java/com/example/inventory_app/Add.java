package com.example.inventory_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class Add extends MainActivity {

    EditText ed1,ed2,ed3,ed4,ed5, ed6;
    Button b1,b2;
    Spinner Category;

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

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        ed1 = findViewById(R.id.prodname);
        ed2 = findViewById(R.id.purprice);
        ed3 = findViewById(R.id.retprice);
        ed4 = findViewById(R.id.idealstock);
        ed5 = findViewById(R.id.avstock);
        ed6 = findViewById(R.id.availstocksval);
        Category = findViewById(R.id.category);

        String[] cat={"Select Item","Ballpen","Bond Paper","Pencil","Notebook","Paper","Eraser","Folder","Envelope","Book","Tapes","Board","Coloring Materials"};
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,cat);
        Category.setAdapter(arrayAdapter);

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
                        startActivity(new Intent(Add.this, dashboard.class));
                        break;
                    case R.id.nav_inventory:
                        startActivity(new Intent(Add.this, view.class));
                        break;
                    case R.id.nav_addstock:
                        startActivity(new Intent(Add.this, Add.class));
                        break;
                    case R.id.nav_logout:
                        startActivity(new Intent(Add.this, Login.class));
                        break;
                }
                return true;
            }
        });

        b1 = findViewById(R.id.bt1);
        b2 = findViewById(R.id.bt2);

        b2.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(),view.class);
            startActivity(i);
        });
        b1.setOnClickListener(v -> insert());
    }

    public void insert()
    {
        try
        {
            String prodname = ed1.getText().toString();
            String purprice = ed2.getText().toString();
            String retprice = ed3.getText().toString();
            String idealstock = ed4.getText().toString();
            String avstock = ed5.getText().toString();
            String availstocksval = ed6.getText().toString();

            SQLiteDatabase db = openOrCreateDatabase("SliteDb", Context.MODE_PRIVATE,null);
            db.execSQL("CREATE TABLE IF NOT EXISTS records(prodid INTEGER PRIMARY KEY AUTOINCREMENT,prodname VARCHAR, purprice VARCHAR," +
                    "retprice VARCHAR,idealstock VARCHAR,avstock VARCHAR,availstocksval VARCHAR)");

            String sql = "insert into records(prodname,purprice,retprice,idealstock,avstock,availstocksval)values(?,?,?,?,?,?)";
            SQLiteStatement statement = db.compileStatement(sql);
            statement.bindString(1,prodname);
            statement.bindString(2,purprice);
            statement.bindString(3,retprice);
            statement.bindString(4,idealstock);
            statement.bindString(5,avstock);
            statement.bindString(6,availstocksval);
            statement.execute();
            Toast.makeText(this,"Record addded",Toast.LENGTH_LONG).show();

            ed1.setText("");
            ed2.setText("");
            ed3.setText("");
            ed4.setText("");
            ed5.setText("");
            ed6.setText("");
            ed1.requestFocus();

        }

        catch (Exception ex)
        {
            Toast.makeText(this,"Record Fail",Toast.LENGTH_LONG).show();
        }

    }
}