package com.cyx.main.data;

import com.cyx.common.data.source.TrieTree;
import org.springframework.stereotype.Component;

@Component
public class SensitiveData {
    private static TrieTree tree=new TrieTree();
    public static void addWord(String word){
        tree.addWord(word);
    }

    public boolean containsWord(String word){
        boolean res = tree.containsWord(word);
        return res;
    }

    public String filterWords(String word,char c){
        String res = tree.filterWords(word, c);
        return res;
    }

    public void removeWord(String word){
        tree.removeWord(word);
    }



}
