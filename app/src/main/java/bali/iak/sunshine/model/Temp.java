package bali.iak.sunshine.model;

import com.google.gson.annotations.SerializedName;

public class Temp{

	@SerializedName("min")
	private double min;

	@SerializedName("max")
	private double max;

	@SerializedName("eve")
	private double eve;

	@SerializedName("night")
	private double night;

	@SerializedName("day")
	private double day;

	@SerializedName("morn")
	private double morn;

	public double getMin(){
		return min;
	}

	public void setMin(double min) {
		this.min = min;
	}

	public double getMax(){
		return max;
	}

	public void setMax(double max) {
		this.max = max;
	}

	public double getEve(){
		return eve;
	}

	public void setEve(int eve) {
		this.eve = eve;
	}

	public double getNight(){
		return night;
	}

	public void setNight(double night) {
		this.night = night;
	}

	public double getDay(){
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public double getMorn(){
		return morn;
	}

	public void setMorn(int morn) {
		this.morn = morn;
	}

	@Override
 	public String toString(){
		return 
			"Temp{" + 
			"min = '" + min + '\'' + 
			",max = '" + max + '\'' + 
			",eve = '" + eve + '\'' + 
			",night = '" + night + '\'' + 
			",day = '" + day + '\'' + 
			",morn = '" + morn + '\'' + 
			"}";
		}
}