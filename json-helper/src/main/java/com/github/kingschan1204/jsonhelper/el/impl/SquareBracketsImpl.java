package com.github.kingschan1204.jsonhelper.el.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.kingschan1204.jsonhelper.el.JsonExpression;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;

import static com.github.kingschan1204.jsonhelper.EasyJson.objectMapper;


/**
 * []方括号实现
 *
 * @author kingschan
 * @date 2025-07-3
 * <pre>
 * 数组：
 * [0] 第一个
 * [-1]最后一个
 * [3:]   从第4个开始到结束
 * [1:3] 从第2个到第3个
 * [0:-3] 从第1个到倒数第3个
 * 对象：
 * [key1] 获取对象key1属性值
 * [key1,key2 ...] 获取对象key1,key2属性值
 * [^key1] 获取对象除了key1属性值
 * </pre>
 */
public class SquareBracketsImpl implements JsonExpression {
    @Override
    public Object eval(JsonNode jsonNode, String expression) {
        if (jsonNode.isArray()) {
            return arrayEval(jsonNode, expression);
        } else if (jsonNode.isObject()) {
            return objectEval(jsonNode, expression);
        }
        return null;
    }

    Object arrayEval(JsonNode jsonNode, String expression) {
        ArrayNode arrayNode = (ArrayNode) jsonNode;
        String el = expression.replaceAll("\\[|\\]", "");
        if (el.matches("\\d+")) {
            return arrayNode.get(Integer.parseInt(el));
        } else if (el.matches("^-\\d+")) {
            return arrayNode.get(arrayNode.size() + Integer.parseInt(el));
        } else if (el.matches("\\d+:")) {
            int index = Integer.parseInt(el.substring(0, el.indexOf(":")));
            ArrayNode result = objectMapper.createArrayNode();
            for (int i = index; i < arrayNode.size(); i++) {
                result.add(arrayNode.get(i));
            }
            return result;
        } else if (el.matches("\\d+:(-)?\\d+")) {
            String[] indexArray = el.split(":");
            int start = Integer.parseInt(indexArray[0]);
            int end = Integer.parseInt(indexArray[1]);
            end = end < 0 ? arrayNode.size() + end : end;
            ArrayNode result = objectMapper.createArrayNode();
            for (int i = start; i < end; i++) {
                result.add(arrayNode.get(i));
            }
            return result;
        }

        return null;
    }

    Object objectEval(JsonNode jsonNode, String expression) {
        ObjectNode objectNode = (ObjectNode) jsonNode;
        String el = expression.replaceAll("\\[|\\]", "");
        Set<String> columns = Arrays.stream(el.split(",")).map(s -> s.replace("^", "").trim()).collect(Collectors.toSet());
        Iterator<String> fieldNames = jsonNode.fieldNames();
        if (el.startsWith("^")) {
            while (fieldNames.hasNext()) {
                String key = fieldNames.next();
                if (columns.contains(key)) {
                    objectNode.remove(key);
                }
            }
            return objectNode;
        } else if (el.matches("[a-zA-Z0-9_,]+")) {
            while (fieldNames.hasNext()) {
                String key = fieldNames.next();
                if (!columns.contains(key)) {
                    objectNode.remove(key);
                }
            }
            return objectNode;
        }
        return null;
    }


}
