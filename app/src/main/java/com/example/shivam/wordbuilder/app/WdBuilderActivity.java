package com.example.shivam.wordbuilder.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by Shivam on 28-06-2016.
 */
public class WdBuilderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wd_builder);

    }

    public void openAnimal(View view){
        startActivity(new Intent(this,AnimalActivity.class));
    }

    public void openCountries(View view){
        startActivity(new Intent(this,CountriesActivity.class));
    }

    public void openWords(View view){
        startActivity(new Intent(this, WordsActivity.class));
    }
}


