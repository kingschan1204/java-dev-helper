package com.github.kingschan1204.sqlhelper.jsqlparser;

import com.github.kingschan1204.sqlhelper.jsqlparser.dto.SqlFieldDto;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectItem;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kingschan
 */
public class SqlFieldAliasExtractor {

    public static List<SqlFieldDto> getSelectFieldsWithAliases(String sql) throws JSQLParserException {
        List<SqlFieldDto> columns = new ArrayList<>();
        // 解析 SQL 语句
        Statement statement = CCJSqlParserUtil.parse(sql);
        // 判断是否为 Select 语句
        if (statement instanceof Select) {
            Select selectStatement = (Select) statement;
            PlainSelect plainSelect = (PlainSelect) selectStatement.getSelectBody();
            // 遍历 SELECT 字段
            for (SelectItem item : plainSelect.getSelectItems()) {
                String columnName = item.toString(); // 获取字段名
                String aliasName = null;
                // 检查别名
                if (item instanceof SelectItem) {
                    Alias alias = item.getAlias(); // 获取别名
                    aliasName = (alias != null) ? alias.getName() : null;
                }
                // 保存字段名和别名到 Map
                columns.add(SqlFieldDto.builder().field(columnName).alias(aliasName).build());
            }
        }
        return columns;
    }


}


