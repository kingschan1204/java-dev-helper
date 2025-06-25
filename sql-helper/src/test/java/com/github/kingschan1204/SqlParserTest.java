package com.github.kingschan1204;

import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.drop.Drop;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.truncate.Truncate;
import net.sf.jsqlparser.statement.update.Update;
import org.junit.jupiter.api.Test;

public class SqlParserTest {
    String sql = """
            create database test;
            """;

    @Test
    public void sqlTypeTest() throws Exception {
        Statement statement = CCJSqlParserUtil.parse(sql);
        switch (statement) {
            case Select select -> System.out.println("select");
            case Insert insert -> System.out.println("insert");
            case Update update -> System.out.println("update");
            case Delete delete -> System.out.println("delete");
            case Drop drop -> System.out.println("drop");
            case Truncate truncate -> System.out.println("truncate");
            case CreateTable createTable -> System.out.println("createTable");
            default -> System.out.println(statement.getClass().getName());
        }
    }
}
