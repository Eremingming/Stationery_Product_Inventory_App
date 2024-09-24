package com.example.inventory_app;



import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends MainActivity {
    EditText username;
    EditText password;
    Button loginButton;

    private String Username = "admin";
    private String Password = "spims";

    boolean isValid = false;
    private int counter = 5;

    public void onBackPressed() {

    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String inputName = username.getText().toString();
                String inputPassword = password.getText().toString();

                if (inputName.isEmpty() || inputPassword.isEmpty())
                {
                    Toast.makeText(Login.this,"Please enter all the details properly", Toast.LENGTH_SHORT).show();
                } else {
                    isValid = validate(inputName,inputPassword);

                    if (!isValid){

                        counter--;

                        Toast.makeText(Login.this, "Incorrect credentials entered!!", Toast.LENGTH_SHORT).show();

                        if (counter==0){
                            loginButton.setEnabled(false);
                        }
                    } else{
                        Toast.makeText(Login.this, "Login Successful!!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Login.this, MainActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });
    }
    private boolean validate (String username, String password) {
        if (username.equals(Username) && password.equals(Password))
        {
            return true;
        }

        return false;
    }
}