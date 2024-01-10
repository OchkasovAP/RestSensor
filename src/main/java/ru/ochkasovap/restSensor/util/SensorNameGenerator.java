package ru.ochkasovap.restSensor.util;

public class SensorNameGenerator {
	private int count = 0;
	private final String defaultName = "Sensor";
	
	public String next() {
		return defaultName+count++;
	}
}
