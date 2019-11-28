package orn.nure.julia.notifications.dto;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public interface Mappable {
    default Map<String, String> toMap() {
        return new ObjectMapper().convertValue(this, Map.class);
    }
}
