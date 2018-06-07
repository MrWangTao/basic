package com.bear.house.date.util;

import com.bear.house.date.constant.DateTimeFormatConstants;
import com.bear.house.date.dto.DateTimeDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * 这里使用的jdk1.8
 * @author WangTao
 *         Created at 18/6/7 下午5:26.
 */
public class DateUtils {

    /**
     * 当天的起始时间，格式 yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static DateTimeDTO dayDateTime() {
        return getDateTimeDTO(LocalDateTime.now(), false, "day");
    }

    /**
     * 今天开始时间到现在截止
     * @param dateTime
     * @return
     */
    public static DateTimeDTO dayDateTime(LocalDateTime dateTime) {
        if (Objects.isNull(dateTime)) {
            return null;
        }
        return getDateTimeDTO(dateTime, true, "day");
    }

    /**
     * 获得相隔days的天<br>
     *     正数 为今天以前， 负数 为今天以后
     * @param days
     * @return
     */
    public static DateTimeDTO dayDateTime(long days) {
        return getDateTimeDTO(LocalDateTime.now().minusDays(days), false, "day");
    }

    /**
     * 本周的起始时间， 格式yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static DateTimeDTO weekDateTime() {
        return getDateTimeDTO(LocalDateTime.now(), false, "week");
    }

    /**
     * 获得相隔今天weeks星期的星期<br>
     *     正数 为这周以前， 负数 为这周以后
     * @param weeks
     * @return
     */
    public static DateTimeDTO weekDateTime(long weeks) {
        return getDateTimeDTO(LocalDateTime.now().minusWeeks(weeks), false, "week");
    }

    /**
     * 从本周的开始那天，截止到当前
     * @param dateTime
     * @return
     */
    public static DateTimeDTO weekDateTime(LocalDateTime dateTime) {
        if (Objects.isNull(dateTime)) {
            return null;
        }
        return getDateTimeDTO(dateTime, true, "week");
    }

    /**
     * 本月的起始时间， 格式yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static DateTimeDTO monthDateTime() {
        return getDateTimeDTO(LocalDateTime.now(), false, "month");
    }

    /**
     * 获取和当前月相隔months的月<br>
     *     正数 为这月以前， 负数 为这月以后
     * @param months
     * @return
     */
    public static DateTimeDTO monthDateTime(long months) {
        return getDateTimeDTO(LocalDateTime.now().minusMonths(months), false, "month");
    }

    /**
     * 从本月的第一天，截止到当前
     * @param dateTime
     * @return
     */
    public static DateTimeDTO monthDateTime(LocalDateTime dateTime) {
        if (Objects.isNull(dateTime)) {
            return null;
        }
        return getDateTimeDTO(dateTime, true, "month");
    }

    /**
     * 将相似的方法抽离出来
     * @param dateTime
     * @param hasParam
     * @param type
     * @return
     */
    private static DateTimeDTO getDateTimeDTO (LocalDateTime dateTime, boolean hasParam, String type) {
        String startDateTime = "";
        String endDateTime = "";
        // 转换成LocalDate
        LocalDate localDate = dateTime.toLocalDate();
        switch (type.toLowerCase()) {
            case "day":
                startDateTime = localDate.atStartOfDay().format(DateTimeFormatter.ofPattern(DateTimeFormatConstants.BASIC_DATE_TIME_FORMAT));
                if (hasParam) {
                    endDateTime = dateTime.format(DateTimeFormatter.ofPattern(DateTimeFormatConstants.BASIC_DATE_TIME_FORMAT));
                } else {
                    endDateTime = dateTime.format(DateTimeFormatter.ofPattern(DateTimeFormatConstants.END_DATE_TIME_FORMAT));
                }
                break;
            case "week":
                int minusDays = dateTime.getDayOfWeek().getValue();
                startDateTime = localDate.minusDays(minusDays - 1).atStartOfDay().format(DateTimeFormatter.ofPattern(DateTimeFormatConstants.BASIC_DATE_TIME_FORMAT));
                if (hasParam) {
                    endDateTime = dateTime.format(DateTimeFormatter.ofPattern(DateTimeFormatConstants.BASIC_DATE_TIME_FORMAT));
                } else {
                    endDateTime = dateTime.plusDays(7 - minusDays).format(DateTimeFormatter.ofPattern(DateTimeFormatConstants.END_DATE_TIME_FORMAT));
                }
                break;
            case "month":
                int dayOfMonth = dateTime.getDayOfMonth();
                int lengthOfMonth = localDate.lengthOfMonth();
                startDateTime = localDate.minusDays(dayOfMonth - 1).atStartOfDay().format(DateTimeFormatter.ofPattern(DateTimeFormatConstants.BASIC_DATE_TIME_FORMAT));
                if (hasParam) {
                    endDateTime = dateTime.format(DateTimeFormatter.ofPattern(DateTimeFormatConstants.BASIC_DATE_TIME_FORMAT));
                } else {
                    endDateTime = dateTime.plusDays(lengthOfMonth - dayOfMonth).format(DateTimeFormatter.ofPattern(DateTimeFormatConstants.END_DATE_TIME_FORMAT));
                }
                break;
        }
        return DateTimeDTO.builder()
                .startDateTime(startDateTime)
                .endDateTime(endDateTime)
                .build();
    }


    public static void main(String[] args) {
        DateTimeDTO day = dayDateTime();
        System.out.println("day ==> " + day);
        DateTimeDTO week = weekDateTime();
        System.out.println("week ==> " + week);
        DateTimeDTO month = monthDateTime();
        System.out.println("month ==> " + month);

        DateTimeDTO dayByNum = dayDateTime(2);
        System.out.println("dayByNum ==> " + dayByNum);
        DateTimeDTO weekByNum = weekDateTime(2);
        System.out.println("weekByNum ==> " + weekByNum);
        DateTimeDTO monthByNum = monthDateTime(2);
        System.out.println("monthByNum ==> " + monthByNum);

        LocalDateTime now = LocalDateTime.now();
        DateTimeDTO dayByParam = dayDateTime(now);
        System.out.println("dayByParam ==> " + dayByParam);
        DateTimeDTO weekByParam = weekDateTime(now);
        System.out.println("weekByParam ==> " + weekByParam);
        DateTimeDTO monthByParam = monthDateTime(now);
        System.out.println("monthByParam ==> " + monthByParam);
    }

}
