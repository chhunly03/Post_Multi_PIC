package com.khrd.file_upload_btb.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ApiResponce<T>{
    private String message;
    private HttpStatus status;
    private T payload;
}
