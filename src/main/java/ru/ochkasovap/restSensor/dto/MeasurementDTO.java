package ru.ochkasovap.restSensor.dto;

public class MeasurementDTO {
	private double temperature;

	private boolean raining;

	private SensorDTO sensor;
	
	
	public MeasurementDTO(double temperature, boolean raining, SensorDTO sensor) {
		this.temperature = temperature;
		this.raining = raining;
		this.sensor = sensor;
	}

	public MeasurementDTO() {
	}

	public double getTemperature() {
		return temperature;
	}

	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}

	public boolean isRaining() {
		return raining;
	}

	public void setRaining(boolean raining) {
		this.raining = raining;
	}

	public SensorDTO getSensor() {
		return sensor;
	}

	public void setSensor(SensorDTO sensor) {
		this.sensor = sensor;
	}
	
	

}
