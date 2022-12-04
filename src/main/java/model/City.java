package model;

public enum City {

    MOSCOW("55.755696", "37.617306", "Москва"),
    ST_PETERSBURG("59.939079", "30.315766", "Питер"),
    ULYANOVSK("54.314192", "48.403132", "Ульяновск");

    private final String lat;
    private final String lon;
    private final String name;


    City(String lat, String lon, String name) {
        this.lat = lat;
        this.lon = lon;
        this.name = name;
    }

    public String getLat() {
        return lat;
    }

    public String getLon() {
        return lon;
    }

    public String getName() {
        return name;
    }
}
