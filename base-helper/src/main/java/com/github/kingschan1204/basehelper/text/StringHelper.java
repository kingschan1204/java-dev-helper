package com.github.kingschan1204.basehelper.text;

/**
 * @author kingschan
 * @version 1.0
 * @description: 字符串工具类
 * @date 2025/6/24
 */

public class StringHelper {
    String text;

    public StringHelper(String text) {
        this.text = text;
    }

    public StringHelper of(String text) {
        return new StringHelper(text);
    }

    public boolean isNull() {
        return null == text;
    }

    public boolean isEmpty() {
        if (null == this.text) {
            return true;
        } else if (this.text.trim().length() == 0) {
            return true;
        } else {
            return false;
        }
    }
}
