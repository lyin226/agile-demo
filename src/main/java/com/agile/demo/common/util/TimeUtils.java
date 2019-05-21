package com.agile.demo.common.util;

/**
 * @author liuyi
 * @date 2019/5/20
 *
 * 时间操作相关方法
 */
public class TimeUtils {

    private static final int SECOND = 1000;

    public static Long toSecond(final String value) {
        final long number = Long.parseLong(value.substring(0, value.length() - 1));
        final long bytes = Unit.valueOf(value.substring(value.length() - 1).toUpperCase()).toSecond(number);
        if (bytes < 0) {
            return Math.abs(bytes);
        }
        return bytes;
    }

    public static Long toMilliSecond(final String value) {
        return (long) toSecond(value) * SECOND;
    }


    enum Unit {
        D {
            private static final int ONE_DAY = 86400;

            @Override
            long toSecond(final long value) {
                return ONE_DAY * value;
            }
        },
        H {
            private static final int ONE_HOUR = 3600;

            @Override
            long toSecond(final long value) {
                return ONE_HOUR * value;
            }
        },
        M {
            private static final int ONE_MINUTE = 60;

            @Override
            long toSecond(final long value) {
                return ONE_MINUTE * value;
            }
        },
        S {
            private static final int ONE_SECOND = 1;

            @Override
            long toSecond(long value) {
                return ONE_SECOND * value;
            }
        };

        abstract long toSecond(long value);
    }

}
