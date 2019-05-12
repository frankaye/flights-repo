package com.fye.fly.app.enpoint.airports.domain;

import java.io.Serializable;

public class Airport implements Serializable{

	private static final long serialVersionUID = 1L;
	private String fs;
	private String iata;
	private String icao;
	private String name;
	private String city;
	private String cityCode;
	private String stateCode;
	private String countryCode;
	private String countryName;
	private String regionName;
	private String timeZoneRegionName;
	private String localTime;
	private String utcOffsetHours;
	private String latitude;
	private String longitude;
	private String elevationFeet;
	private String classification;
	private String active;
	private String delayIndexUrl;
	private String weatherUrl;
	public String getFs() {
		return fs;
	}
	public void setFs(String fs) {
		this.fs = fs;
	}
	public String getIata() {
		return iata;
	}
	public void setIata(String iata) {
		this.iata = iata;
	}
	public String getIcao() {
		return icao;
	}
	public void setIcao(String icao) {
		this.icao = icao;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public String getStateCode() {
		return stateCode;
	}
	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	public String getTimeZoneRegionName() {
		return timeZoneRegionName;
	}
	public void setTimeZoneRegionName(String timeZoneRegionName) {
		this.timeZoneRegionName = timeZoneRegionName;
	}
	public String getLocalTime() {
		return localTime;
	}
	public void setLocalTime(String localTime) {
		this.localTime = localTime;
	}
	public String getUtcOffsetHours() {
		return utcOffsetHours;
	}
	public void setUtcOffsetHours(String utcOffsetHours) {
		this.utcOffsetHours = utcOffsetHours;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getElevationFeet() {
		return elevationFeet;
	}
	public void setElevationFeet(String elevationFeet) {
		this.elevationFeet = elevationFeet;
	}
	public String getClassification() {
		return classification;
	}
	public void setClassification(String classification) {
		this.classification = classification;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public String getDelayIndexUrl() {
		return delayIndexUrl;
	}
	public void setDelayIndexUrl(String delayIndexUrl) {
		this.delayIndexUrl = delayIndexUrl;
	}
	public String getWeatherUrl() {
		return weatherUrl;
	}
	public void setWeatherUrl(String weatherUrl) {
		this.weatherUrl = weatherUrl;
	}

	
}
