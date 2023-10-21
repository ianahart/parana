package com.hart.backend.parana.geocode;

import com.opencagedata.jopencage.JOpenCageGeocoder;
import com.opencagedata.jopencage.model.JOpenCageForwardRequest;
import com.opencagedata.jopencage.model.JOpenCageResponse;
import java.util.Map;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GeocodeService {
    @Value("${opencageapikey}")
    private String API_KEY;

    public Map<String, Double> geocode(String city, String state) {
        JOpenCageGeocoder jOpenCageGeocoder = new JOpenCageGeocoder(API_KEY);
        JOpenCageForwardRequest request = new JOpenCageForwardRequest(String.format("%s,%s", city, state));

        request.setMinConfidence(1);
        request.setNoAnnotations(false);
        request.setNoDedupe(true);

        JOpenCageResponse response = jOpenCageGeocoder.forward(request);

        Map<String, Double> coords = new HashMap<>();
        coords.put("latitude", response.getFirstPosition().getLat());
        coords.put("longitude", response.getFirstPosition().getLng());

        return coords;
    }
}
