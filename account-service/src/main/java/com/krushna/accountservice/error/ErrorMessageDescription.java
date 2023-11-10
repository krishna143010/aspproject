package com.krushna.accountservice.error;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ErrorMessageDescription {
    private int statusCode;
    private Date timestamp;
    private String message;
    private String description;
}
