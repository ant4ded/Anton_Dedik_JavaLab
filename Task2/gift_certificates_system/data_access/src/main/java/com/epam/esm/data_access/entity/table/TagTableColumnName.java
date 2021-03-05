package com.epam.esm.data_access.entity.table;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TagTableColumnName {
    public static final String TABLE_NAME = "tag";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String ALIAS_DELIMITER = "_";
}
