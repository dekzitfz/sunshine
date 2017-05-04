package bali.iak.sunshine.model;

/**
 * Created by awidiyadew on 5/4/17.
 */

public class DummyForecast {

    private String date;
    private String weatherDescription;
    private String highTemperature;
    private String lowTemperature;
    private int weatherId;

    public DummyForecast(String date, String weatherDescription, String highTemperature, String lowTemperature, int weatherId) {
        this.date = date;
        this.weatherDescription = weatherDescription;
        this.highTemperature = highTemperature;
        this.lowTemperature = lowTemperature;
        this.weatherId = weatherId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWeatherDescription() {
        return weatherDescription;
    }

    public void setWeatherDescription(String weatherDescription) {
        this.weatherDescription = weatherDescription;
    }

    public String getHighTemperature() {
        return highTemperature;
    }

    public void setHighTemperature(String highTemperature) {
        this.highTemperature = highTemperature;
    }

    public String getLowTemperature() {
        return lowTemperature;
    }

    public void setLowTemperature(String lowTemperature) {
        this.lowTemperature = lowTemperature;
    }

    public int getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(int weatherId) {
        this.weatherId = weatherId;
    }
}
