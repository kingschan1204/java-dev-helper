package com.github.kingschan1204.sqlhelper.jsqlparser.dto;

import lombok.Builder;
import lombok.Data;
/**
 * @author kingschan
 */
@Builder
@Data
public class SqlFieldDto {
    private String field;
    private String alias;
}
