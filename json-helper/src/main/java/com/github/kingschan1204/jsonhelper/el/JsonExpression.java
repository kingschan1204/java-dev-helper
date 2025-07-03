package com.github.kingschan1204.jsonhelper.el;

import com.fasterxml.jackson.databind.JsonNode;

public interface JsonExpression {
    Object eval(JsonNode jsonNode,String expression);
}
