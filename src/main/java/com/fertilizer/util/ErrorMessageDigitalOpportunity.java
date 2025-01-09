package com.fertilizer.util;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties
public class ErrorMessageDigitalOpportunity {
    private @JsonProperty("status_code") String statusCode;

    private @JsonProperty("message") String message;
}
