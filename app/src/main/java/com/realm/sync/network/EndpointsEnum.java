package com.realm.sync.network;

import com.realm.sync.constant.APIConstants;

public class EndpointsEnum {

    public enum Endpoints {
        OPEN_WEATHER
    }

    Endpoints endpoint;

    public EndpointsEnum(Endpoints endpoint) {

        this.endpoint = endpoint;

    }

    public String resolveEndpoint(String query) {
        switch (endpoint) {
            case OPEN_WEATHER:
                return APIConstants.getOpenWeatherEndpoint(query);
            default:
                break;
        }
        return null;
    }
}
