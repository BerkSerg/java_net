package org.example.demo.bookingservice.model.responses;

import lombok.Getter;
import org.example.demo.bookingservice.model.enums.EResponse;

import java.util.HashMap;
import java.util.Map;

@Getter
public class ResponseResult {
    private EResponse result;
    private String resultMessage;
    private Map<String, String> body;

    public ResponseResult(EResponse result, String resultMessage) {
        this.result = result;
        this.resultMessage = resultMessage;
        body = new HashMap<>();
    }
}
