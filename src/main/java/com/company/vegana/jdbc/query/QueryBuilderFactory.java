package com.company.vegana.jdbc.query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Query를 쉽게 만들기 위한 유틸리티 클래스
 */
public final class QueryBuilderFactory {

    protected QueryBuilderFactory() {
    }

    public static QueryBuilder builder() {
        return new QueryBuilderImpl(new StringBuilder(), new ArrayList<>());
    }

    private static class QueryBuilderImpl implements QueryBuilder {
        private final StringBuilder query;
        private final List<Object> bindList;

        public QueryBuilderImpl(final StringBuilder query, final List<Object> bindList) {
            this.query = query;
            this.bindList = bindList;
        }

        @Override
        public QueryBuilder append(final CharSequence csq) {
            try {
                query.append(csq);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return this;
        }

        @Override
        public QueryBuilder append(final CharSequence csq, final int start, final int end) {
            try {
                query.append(csq, start, end);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return this;
        }

        @Override
        public QueryBuilder append(final char c) {
            try {
                query.append(c);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return this;
        }

        @Override
        public QueryBuilder append(final String format, final Object... ars) {
            query.append(String.format(format, ars));
            return this;
        }

        @Override
        public QueryBuilder bind(final char binder) {
            bindList.add(binder);
            return this;
        }

        @Override
        public QueryBuilder bind(final int binder) {
            bindList.add(binder);
            return this;
        }

        @Override
        public QueryBuilder bind(final long binder) {
            bindList.add(binder);
            return this;
        }

        @Override
        public QueryBuilder bind(final float binder) {
            bindList.add(binder);
            return this;
        }

        @Override
        public QueryBuilder bind(final double binder) {
            bindList.add(binder);
            return this;
        }

        @Override
        public QueryBuilder bind(final String binder) {
            bindList.add(binder);
            return this;
        }

        @Override
        public QueryBuilder bind(final List<Object> binder) {
            bindList.addAll(binder);
            return this;
        }

        @Override
        public QueryBuilder bind(final Object[] binder) {
            bindList.addAll(Arrays.asList(binder));
            return this;
        }

        @Override
        public QueryBuilder ln() {
            query.append("\n");
            return this;
        }

        @Override
        public String build() {
            return this.query.toString();
        }

        @Override
        public Object[] toBind() {
            return this.bindList.toArray();
        }

        @Override
        public String toString() {
            return this.build();
        }
    }
}
