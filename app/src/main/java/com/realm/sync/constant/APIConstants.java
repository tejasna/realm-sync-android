package com.realm.sync.constant;

import com.realm.sync.model.OpenWeather;

import java.util.HashMap;
import java.util.Map;

public class APIConstants {

    public static final String City = "Bangalore";

    public static final String BASE_DOMAIN = "http://api.openweathermap.org/data/2.5/weather?q=";

    public static final int SOCKET_TIMEOUT_IN_MS = 30000;

    public static String getOpenWeatherEndpoint(String query) {

        return BASE_DOMAIN + query + "&APPID=fa2467bba18295733b55b1186896fca7";
    }

    public static final Map<String, Class> realmMapper;

    static {
        realmMapper = new HashMap<>();
        realmMapper.put(APIConstants.getOpenWeatherEndpoint(City), OpenWeather.class);
    }

}
