package com.example.shivam.wordbuilder.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by Shivam on 28-06-2016.
 */
public class WordsDictionary {

    ArrayList wordList = new ArrayList();
    ArrayList usedWords = new ArrayList();
    HashMap<Character, ArrayList<String>> letterToWord = new HashMap<Character, ArrayList<String>>();


    public WordsDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        String line;

        while ((line = in.readLine())!= null){
            String word = line.trim();
            word = word.toLowerCase();
            wordList.add(word);
            char at[] = word.toCharArray();
            char key = at[0];

            if(letterToWord.containsKey(key)){
                ArrayList arrayList = letterToWord.get(key);
                if(arrayList == null){
                    arrayList = new ArrayList();
                    arrayList.add(word);
                }else{
                    if(!arrayList.contains(word))
                        arrayList.add(word);
                }
            }else{
                ArrayList arrayList = new ArrayList();
                arrayList.add(word);
                letterToWord.put(key,arrayList);
            }
        }
    }

    public boolean isGoodWord(String word, Character base)
    {
        if(base >= 'A' && base<='Z')
            base = (char)(base+32);
        word = word.toLowerCase();
        char at[] = word.toCharArray();
        if(at[0]==base){
            if(usedWords.contains(word))
                return false;
            else{
                if(wordList.contains(word)){
                    usedWords.add(word);
                    return true;
                }
            }
        }
        return false;
    }

    public char pickGoodLetter(){
        Random random=new Random();
        char letter = (char) (random.nextInt(26)+65);
        //usedWords= null;
        return letter;
    }
}


