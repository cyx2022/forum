package com.cyx.common.data.source;

import java.util.ArrayList;
import java.util.List;

public class TrieTree {
    private TrieNode root;

    public TrieTree() {
        this.root = new TrieNode('\0'); // 根节点不包含字符
    }

    // 添加敏感词
    public void addWord(String word) {
        if (word == null || word.isEmpty()) {
            return;
        }

        TrieNode cur = root;
        for (char c : word.toCharArray()) {
            if (!cur.getChildren().containsKey(c)) {
                cur.getChildren().put(c, new TrieNode(c));
            }
            cur = cur.getChildren().get(c);
        }
        cur.setEnd(true);
    }

    // 检查是否包含敏感词
    public boolean containsWord(String word) {
        if (word == null || word.isEmpty()) {
            return false;
        }

        TrieNode cur = root;
        for (char c : word.toCharArray()) {
            if (!cur.getChildren().containsKey(c)) {
                return false;
            }
            cur = cur.getChildren().get(c);
        }
        return cur.isEnd();
    }

    // 删除敏感词
    public void removeWord(String word) {
        if (word == null || word.isEmpty()) {
            return;
        }

        TrieNode cur = root;
        List<TrieNode> path = new ArrayList<>(); // 记录路径
        for (char c : word.toCharArray()) {
            if (!cur.getChildren().containsKey(c)) {
                return;
            }
            path.add(cur);
            cur = cur.getChildren().get(c);
        }

        if (!cur.isEnd()) {
            return;
        }

        cur.setEnd(false); // 标记为非敏感词

        // 如果当前节点没有子节点，则删除其父节点下的记录
        if (cur.getChildren().isEmpty()) {
            for (int i = path.size() - 1; i >= 0; i--) {
                TrieNode parent = path.get(i);
                parent.getChildren().remove(cur.getVal());
                if (!parent.isEnd() && parent.getChildren().isEmpty()) { // 如果父节点不是敏感词，且没有子节点，则继续往上删除
                    cur = parent;
                } else {
                    break;
                }
            }
        }
    }

    // 过滤敏感词
    public String filterWords(String text, char mask) {
        if (text == null || text.isEmpty()) {
            return text;
        }

        StringBuilder filtered = new StringBuilder(text.length());

        int i = 0;
        while (i < text.length()) {
            int j = i;
            TrieNode cur = root;

            while (j < text.length() && cur.getChildren().containsKey(text.charAt(j))) {
                cur = cur.getChildren().get(text.charAt(j));
                j++;
                if (cur.isEnd()) { // 如果找到敏感词
                    filtered.append(mask).append(mask).append(mask); // 添加替换字符
                    break;
                }
            }

            if (j == i) { // 如果当前位置没有匹配上，则直接添加字符
                filtered.append(text.charAt(i));
                i++;
            } else { // 如果匹配上了，则从下一个位置开始查找
                i = j;
            }
        }

        return filtered.toString();
    }
}
