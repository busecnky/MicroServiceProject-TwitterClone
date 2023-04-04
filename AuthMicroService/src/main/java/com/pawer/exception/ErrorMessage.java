package com.pawer.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Component
public class ErrorMessage {
    private int code;
    private String message;
    /**
     * String username -> @Valid => min=3, max=16, notNull, Regex(aZ,0-9,'*-/?')
     */
    private List<String> fields;
}
