package com.bear.house.date.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author WangTao
 *         Created at 18/6/7 下午5:40.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DateTimeDTO {

    /*private String startDate;
    private String endDate;*/
    private String startDateTime;
    private String endDateTime;

}
