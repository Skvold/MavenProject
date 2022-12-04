package model;

public class Weather {

    private final String city;
    private final String date;
    private final int nightTemperature;
    private final int dayTemperature;
    private final String nightCondition;
    private final String dayCondition;

    public Weather(String city, String date, int nightTemperature, int dayTemperature, String nightCondition, String dayCondition) {
        this.city = city;
        this.date = date;
        this.nightTemperature = nightTemperature;
        this.dayTemperature = dayTemperature;
        this.nightCondition = nightCondition;
        this.dayCondition = dayCondition;
    }

    public String getCity() {
        return city;
    }

    public String getDate() {
        return date;
    }

    public int getNightTemperature() {
        return nightTemperature;
    }

    public int getDayTemperature() {
        return dayTemperature;
    }

    public String getNightCondition() {
        return nightCondition;
    }

    public String getDayCondition() {
        return dayCondition;
    }

    @Override
    public String toString() {
        return "В городе " + city + " на дату " + date + " ожидается: ночью - " + nightCondition + ", температура " + nightTemperature +
                " градусов; день - " + dayCondition + ", температура " + dayTemperature + " градусов.";
    }
}
