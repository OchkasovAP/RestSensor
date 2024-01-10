package ru.ochkasovap.restSensor.util;

import java.util.Random;

import ru.ochkasovap.restSensor.dto.MeasurementDTO;
import ru.ochkasovap.restSensor.dto.SensorDTO;

public class MeasurementGenerator {
	private Random random = new Random();
	private double temperature = (Math.random()*45)-15;
	private final SensorDTO sensor;
	
	public MeasurementGenerator(SensorDTO sensor) {
		super();
		this.sensor = sensor;
	}

	public MeasurementDTO next() {
		return new MeasurementDTO(nextTemperature(), randomRaining(), sensor);
	}
	
	private double nextTemperature() {
		temperature=temperature+(Math.random()*4)-2;
		if(temperature<(-15)) {
			temperature=-30-temperature;
		} else if(temperature>30) {
			temperature=60-temperature;
		}
		return temperature;
	}
	private boolean randomRaining() {
		if(temperature>0&&random.nextInt(10)==1) {
			return true;
		}
		return false;
	}
}
