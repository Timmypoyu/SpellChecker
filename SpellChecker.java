/*
Wu Po Yu 
Homework 4 
pw2440
SpellChecker.java
This program spell check texts.
*/

import java.util.*;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
 
public class SpellChecker{
    //Instance Valuable 
    private Map<String, Object> dict = new HashMap<String, Object>();
    //A char array that includes all alphabet and apostrophe that needs to be appended
    private char[] alphabet = "abcdefghijklmnopqrstuvwxyz'".toCharArray();

    //constructor 
    public SpellChecker(){
    }
    //return a arraylist of words with appended char
    private ArrayList<String> charAppend(String word){
        ArrayList<String> match = new ArrayList<String>();
        //char appended at back and front      
        for (char ch : alphabet){
            String front = ch + word; 
            String back = word + ch;   
            if (dict.containsKey(front)){
                match.add(front);
            }
            if (dict.containsKey(back)){
                match.add(back);
            }
        }
        //char appended in the middle     
        for ( int h = 1 ; h < word.length()  ; h++){
            for (char ch : alphabet){
                String mid = word.substring(0,h) + ch + word.substring(h);
                if (dict.containsKey(mid)){
                        match.add(mid);
                }
            }
        }
        
        return match; 
    } 

    //return a arraylist of words with char removed 
    private ArrayList<String> charRemove(String word){
        ArrayList<String> match = new ArrayList<String>();
        String rmFirst = word.substring(1);
        if(dict.containsKey(rmFirst)){
            match.add(rmFirst);
        }
    
        //Remove the middle and last characters 
        for(int i = 1; i < word.length()-1 ; i++){
            String rmMid = word.substring(0,i) 
                        + word.substring(i+1);
            if (dict.containsKey(rmMid)){
            match.add(rmMid);
            }
        }

        String rmLast = word.substring(0, word.length()-1);
        if (dict.containsKey(rmLast)){
            match.add(rmLast);
        }

        return match;
    }
    //return a arraylist of words with swapped chars
    private ArrayList<String> charSwap(String word){
        ArrayList<String> match = new ArrayList<String>();
        for(int i = 0; i< word.length() - 2; i++){
            String swap = word.substring(0, i)
                        + word.substring(i+1, i+2)
                        + word.substring(i,i+1)
                        + word.substring(i+2);
            if (dict.containsKey(swap)){
                match.add(swap);
            }
        }
        return match;
    }
    //makeSuggestion, add all arraylist of suggestions 
    private ArrayList<String> makeSuggestion(String word){
        ArrayList<String> suggest = new ArrayList<String>();
        suggest.addAll(charAppend(word));
        suggest.addAll(charRemove(word));
        suggest.addAll(charSwap(word));
        return suggest;
    }
    //printSuggestion
    private void printSuggestion(String word){
    if (makeSuggestion(word).isEmpty()){
        System.out.println("I dont have suggestion for you, sorry!");
    }else{
        System.out.println("These are my suggestions:" + makeSuggestion(word));
        }
    }

    //dictToHash
    private void dictToHash(String word){
	dict.put(word, null);
    }

    //create wordcell to house line and word
    private static class WordCell{
        //Instance Variable 
        String textWord;
        int textLine;
        //constructor 
        WordCell(String word, int line){
            textWord = word;
            textLine = line; 
        }
    }
    
    //main method 
    public static void main(String[] args) throws FileNotFoundException{
	SpellChecker t = new SpellChecker();
    //Arraylist of wordcell to read in words from textfile with lines
    ArrayList<WordCell> textList = new ArrayList<WordCell>();
    //Scanner for dictionary to hash 
    Scanner scanDict = new Scanner (new File(args[0]));
    while(scanDict.hasNext()){
        String word = ""; 
            word = scanDict.nextLine();
            word = word.toLowerCase();
            if(word.length()>0){
                t.dictToHash(word);
            }         
        }
    //Scanner for the spell checked text file
    Scanner scanTxt = new Scanner (new File(args[1]));  
    int i =1; 
    while(scanTxt.hasNext()){
        for (String word: scanTxt.nextLine().split(" ")){
            //strip text file of punctuations but not apostrophe, and to lower case
            word = word.toLowerCase().replaceAll("\\B'\\b|\\b'\\B|[^a-z0-9']","");
            if(word.length() > 0){
                WordCell temp = new WordCell(word, i);
                textList.add(temp);
            }
        }
        i++;
    }
    //print out result 
    for ( int j = 0 ; j < textList.size(); j++){
        String tempWord = textList.get(j).textWord;
        if(!(t.dict.containsKey(tempWord))){
            System.out.println("misspelled: " + tempWord);
            //t.spellCheck(tempWord);
            System.out.println("line: "+ textList.get(j).textLine);
            t.printSuggestion(tempWord);
            } 
        }
	}

}