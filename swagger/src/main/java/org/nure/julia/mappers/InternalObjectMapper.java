package org.nure.julia.mappers;

import kong.unirest.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class InternalObjectMapper implements ObjectMapper {

    @Override
    public <T> T readValue(String s, Class<T> aClass) {
        try {
            return new com.fasterxml.jackson.databind.ObjectMapper().readValue(s, aClass);
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public String writeValue(Object o) {
        try {
            return new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(o);
        } catch (IOException e) {
            return "";
        }
    }
}
