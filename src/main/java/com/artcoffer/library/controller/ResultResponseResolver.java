package com.artcoffer.library.controller;

import com.artcoffer.library.model.Result;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ResultResponseResolver {

    public <T> ResponseEntity<T> resolve(Result<T> result) {
        if(result.hasErrors()) {
            if (result.getErrorType() == Result.ErrorType.NOT_FOUND) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.status(500).build();
        }else {
            if(result.getEntity() == null) {
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.ok(result.getEntity());
        }

    }

}
