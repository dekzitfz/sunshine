package bali.iak.sunshine.model;

import com.google.gson.annotations.SerializedName;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class ListItem{

	@SerializedName("dt")
	private long dt;

	@SerializedName("rain")
	private double rain;

	@SerializedName("temp")
	private Temp temp;

	@SerializedName("deg")
	private int deg;

	@SerializedName("weather")
	private List<WeatherItem> weather;

	@SerializedName("humidity")
	private int humidity;

	@SerializedName("pressure")
	private double pressure;

	@SerializedName("clouds")
	private int clouds;

	@SerializedName("speed")
	private double speed;

	public long getDt(){
		return dt;
	}

	public void setDt(int dt) {
		this.dt = dt;
	}

	public double getRain(){
		return rain;
	}

	public void setRain(double rain) {
		this.rain = rain;
	}

	public Temp getTemp(){
		return temp;
	}

	public void setTemp(Temp temp) {
		this.temp = temp;
	}

	public int getDeg(){
		return deg;
	}

	public void setDeg(int deg) {
		this.deg = deg;
	}

	public List<WeatherItem> getWeather(){
		return weather;
	}

	public void setWeather(List<WeatherItem> weather) {
		this.weather = weather;
	}

	public int getHumidity(){
		return humidity;
	}

	public void setHumidity(int humidity) {
		this.humidity = humidity;
	}

	public double getPressure(){
		return pressure;
	}

	public void setPressure(double pressure) {
		this.pressure = pressure;
	}

	public int getClouds(){
		return clouds;
	}

	public void setClouds(int clouds) {
		this.clouds = clouds;
	}

	public double getSpeed(){
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	@Override
 	public String toString(){
		return 
			"ListItem{" + 
			"dt = '" + dt + '\'' + 
			",rain = '" + rain + '\'' + 
			",temp = '" + temp + '\'' + 
			",deg = '" + deg + '\'' + 
			",weather = '" + weather + '\'' + 
			",humidity = '" + humidity + '\'' + 
			",pressure = '" + pressure + '\'' + 
			",clouds = '" + clouds + '\'' + 
			",speed = '" + speed + '\'' + 
			"}";
	}

	public String getReadableTime(int pos){
		if(pos == 1){
			return "Tomorrow";
		}else{
			Date date = new Date(dt * 1000L);
			DateFormat format = new SimpleDateFormat("EEEE");
			format.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
			return format.format(date);
		}
	}

	public String getTodayReadableTime(){
		Date date = new Date(dt * 1000L);
		DateFormat format = new SimpleDateFormat("MMM dd");
		return "Today, "+format.format(date);
	}

	public String getReadableHumidity() {
		return humidity + " %";
	}

	public String getReadablePressure() {
		return Math.round(pressure) + " hPa";
	}

	public String getReadableWindSpeed() {
		return Math.round(speed) + " m/sec";
	}
}