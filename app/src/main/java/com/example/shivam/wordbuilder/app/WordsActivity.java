package com.example.shivam.wordbuilder.app;

import android.content.Context;
import android.content.res.AssetManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.InputType;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class WordsActivity extends AppCompatActivity {

    public static final String START_MESSAGE = "%c";
    private CountriesDictionary dictionary;
    public Character currentLetter;
    public ArrayList<String> answer;
    public String word;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countries);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        AssetManager assetManager = getAssets();
        try {
            InputStream inputStream = assetManager.open("words.txt");
            dictionary = new CountriesDictionary(inputStream);
        }catch (IOException e){
            Toast.makeText(this, "Could not load dictionay", Toast.LENGTH_SHORT).show();
        }

        final EditText editText = (EditText) findViewById(R.id.editText);
        editText.setRawInputType(InputType.TYPE_CLASS_TEXT);
        editText.setImeOptions(EditorInfo.IME_ACTION_GO);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if(actionId == EditorInfo.IME_ACTION_GO){
                    processWord(editText);
                    handled = true;
                }
                return handled;
            }
        });
        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void processWord(EditText editText) {
        TextView resultView = (TextView)findViewById(R.id.resultView);
        TextView gameStatus = (TextView)findViewById(R.id.gameStatusView);
        word = editText.getText().toString().trim().toLowerCase();
        if(word.length() == 0)
            return;
        String color = "#cc0029";
        if (dictionary.isGoodWord(word,currentLetter) /*&& answer.contains(word)*/){
            // answer.remove(word);
            color = "#00aa29";
            char arr[] = word.toCharArray();
            int len = arr.length;
            currentLetter = arr[len-1];
            gameStatus.setText(Html.fromHtml(String.format("Words begining with %c", currentLetter-32)));

        }else{
            word = "X "+word;
        }

        resultView.append(Html.fromHtml(String.format("<font color=%s>%s</font><BR>", color, word)));
        editText.setText("");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.show();
    }

    public boolean defaultAction(View view){
        TextView gameStatus = (TextView)findViewById(R.id.gameStatusView);
        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
        EditText editText = (EditText) findViewById(R.id.editText);
        TextView resultView = (TextView) findViewById(R.id.resultView);
        TextView ansView = (TextView)findViewById(R.id.answer);

        if(currentLetter == null){
            currentLetter = dictionary.pickGoodLetter();
            gameStatus.setText(Html.fromHtml(String.format(START_MESSAGE, currentLetter, currentLetter)));
            fab.setImageResource(android.R.drawable.ic_menu_help);
            fab.hide();
            resultView.setText("");
            ansView.setText("");
            editText.setText("");
            editText.setEnabled(true);
            editText.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
        }else{
//            editText.setText(currentLetter);
            editText.setEnabled(false);
            fab.setImageResource(android.R.drawable.ic_media_play);
            gameStatus.setText(" Hit 'Play' to start again");
            currentLetter = null;
        }
        return true;
    }

}

