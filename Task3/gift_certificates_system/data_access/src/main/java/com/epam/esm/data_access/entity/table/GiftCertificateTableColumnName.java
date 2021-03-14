package com.epam.esm.data_access.entity.table;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GiftCertificateTableColumnName {
    public static final String TABLE_NAME = "gift_certificate";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_DURATION = "duration";
    public static final String COLUMN_CREATE_DATE = "create_date";
    public static final String COLUMN_LAST_UPDATE_DATE = "last_update_date";
    public static final String ALIAS_DELIMITER = "_";
}
