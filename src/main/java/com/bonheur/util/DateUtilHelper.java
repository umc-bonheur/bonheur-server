package com.bonheur.util;

import com.bonheur.domain.common.exception.InvalidException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Calendar;

import static com.bonheur.domain.common.exception.dto.ErrorCode.E400_INVALID_FORMAT_DATE;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateUtilHelper {
    public static LocalDate isValidDateFormat(String localDate) { // 가능한 포맷 : yyyy-MM-dd
        // 1. - 체크(포맷)
        if (localDate.charAt(4) != '-' || localDate.charAt(7) != '-' || localDate.length() != 10)
            throw new InvalidException("잘못된 날짜 형식이 입력되었습니다", E400_INVALID_FORMAT_DATE);
        int idx = 0;
        while (idx < 10) {
            if (idx == 4 || idx == 7) {
                idx++; continue;
            }
            if (localDate.charAt(idx) < '0' || localDate.charAt(idx) > '9') throw new InvalidException("잘못된 날짜 형식이 입력되었습니다", E400_INVALID_FORMAT_DATE);
            System.out.println("idx : "+idx+"\n");
            idx++;
        }

        // 2. 날짜 범위 체크
        int year = Integer.parseInt(localDate.substring(0, 4));
        int month = Integer.parseInt(localDate.substring(5, 7));
        int day = Integer.parseInt(localDate.substring(8, 10));

        int monthLastDay = getLastDay(year, month);
        if (day < 1 || day > monthLastDay)
            throw new InvalidException("잘못된 날짜 형식이 입력되었습니다", E400_INVALID_FORMAT_DATE);

        // 3. 반환
        return LocalDate.of(year, month, day);
    }

    public static int getLastDay(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month-1, 1);
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        if (year < 2022 || year > 2100 || month < 1 || month > 12)
            throw new InvalidException("잘못된 날짜 형식이 입력되었습니다", E400_INVALID_FORMAT_DATE);

        return lastDay;
    }
}
