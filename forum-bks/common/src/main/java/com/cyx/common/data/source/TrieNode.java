package com.cyx.common.data.source;

import java.util.HashMap;
import java.util.Map;

public class TrieNode {
    private char val;
    private Map<Character, TrieNode> children;
    private boolean isEnd;

    public TrieNode(char val) {
        this.val = val;
        this.children = new HashMap<>();
        this.isEnd = false;
    }

    public char getVal() {
        return val;
    }

    public void setVal(char val) {
        this.val = val;
    }

    public Map<Character, TrieNode> getChildren() {
        return children;
    }

    public void setChildren(Map<Character, TrieNode> children) {
        this.children = children;
    }

    public boolean isEnd() {
        return isEnd;
    }

    public void setEnd(boolean end) {
        isEnd = end;
    }
}
