package com.vegana.vegana.jdbc.query;

import java.util.List;

public interface QueryBuilder {
    QueryBuilder append(CharSequence csq);

    QueryBuilder append(CharSequence csq, int start, int end);

    QueryBuilder append(char c);

    QueryBuilder append(String format, Object... ars);

    QueryBuilder bind(char binder);

    QueryBuilder bind(int binder);

    QueryBuilder bind(long binder);

    QueryBuilder bind(float binder);

    QueryBuilder bind(double binder);

    QueryBuilder bind(String binder);

    QueryBuilder bind(List<Object> binder);

    QueryBuilder bind(Object[] binder);

    QueryBuilder ln();

    String build();

    Object[] toBind();
}
