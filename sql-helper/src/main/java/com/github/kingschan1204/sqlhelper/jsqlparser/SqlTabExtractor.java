package com.github.kingschan1204.sqlhelper.jsqlparser;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.FromItem;
import net.sf.jsqlparser.statement.select.Join;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author kingschan
 * @date 2025/6/24
 */
public class SqlTabExtractor {


    public static Map<String, String> getTablesAndAliases(String sql) throws JSQLParserException {
        Map<String, String> tableAliasMap = new HashMap<>();
        // 解析 SQL 语句
        Statement statement = CCJSqlParserUtil.parse(sql);
        // 判断是否为 Select 语句
        if (statement instanceof Select) {
            Select selectStatement = (Select) statement;
            PlainSelect plainSelect = (PlainSelect) selectStatement.getSelectBody();
            // 获取主表及其别名
            FromItem fromItem = plainSelect.getFromItem();
            if (fromItem instanceof Table) {
                Table table = (Table) fromItem;
                tableAliasMap.put(table.getName(), table.getAlias() != null ? table.getAlias().getName() : null);
            }
            // 获取 JOIN 表及其别名
            List<Join> joins = plainSelect.getJoins();
            if (joins != null) {
                for (Join join : joins) {
                    FromItem joinItem = join.getRightItem();
                    if (joinItem instanceof Table) {
                        Table table = (Table) joinItem;
                        tableAliasMap.put(table.getName(), table.getAlias() != null ? table.getAlias().getName() : null);
                    }
                }
            }
        }

        return tableAliasMap;
    }


}


