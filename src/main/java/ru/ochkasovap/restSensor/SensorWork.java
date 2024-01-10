package ru.ochkasovap.restSensor;

import java.util.ArrayList;
import java.util.List;

import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import ru.ochkasovap.restSensor.dto.MeasurementDTO;
import ru.ochkasovap.restSensor.dto.SensorDTO;
import ru.ochkasovap.restSensor.util.MeasurementGenerator;
import ru.ochkasovap.restSensor.util.MeasurementsList;
import ru.ochkasovap.restSensor.util.SensorNameGenerator;

public class SensorWork {
	private static RestTemplate restTemplate = new RestTemplate();
	

	public static void main(String[] args) {
		SensorDTO sensor = createSensor();
		createMeasurements(sensor);
		drawTemperatureGraph(new ArrayList<>(findAllMesurements()),sensor);
	}

	public static SensorDTO createSensor() {
		String url = "http://localhost:8080/sensors/registration";
		SensorNameGenerator nameGenerator = new SensorNameGenerator();
		SensorDTO requestSensor = new SensorDTO();
		HttpEntity<SensorDTO> request = new HttpEntity<SensorDTO>(requestSensor);
		SensorDTO responseSensor = null;
		while (responseSensor == null) {
			try {
				requestSensor.setName(nameGenerator.next());
				ResponseEntity<SensorDTO> response = restTemplate.exchange(url, HttpMethod.POST, request,
						SensorDTO.class);
				responseSensor = response.getBody();
			} catch (HttpClientErrorException ex) {
				if (!ex.getStatusCode().equals(HttpStatus.BAD_REQUEST)) {
					throw ex;
				}
			}
		}
		return responseSensor;
	}
	public static void createMeasurements(SensorDTO sensor) {
		MeasurementGenerator generator = new MeasurementGenerator(sensor);
		String url = "http://localhost:8080/measurements/add";
		HttpEntity<MeasurementDTO> request;
		for(int i = 0; i<1000; i++) {
			request = new HttpEntity<>(generator.next());
			restTemplate.postForObject(url, request, MeasurementDTO.class);
		}
	}
	public static List<MeasurementDTO> findAllMesurements() {
		String url = "http://localhost:8080/measurements";
		return restTemplate.getForObject(url, MeasurementsList.class);
	}
	public static void drawTemperatureGraph(List<MeasurementDTO> measurements, SensorDTO sensor) {
		List<Integer> xData = new ArrayList<>();
		List<Double> yData = new ArrayList<>();
		measurements.stream()
				.filter(m -> m.getSensor().getName().equals(sensor.getName()))
				.map(m -> m.getTemperature())
				.forEach(m -> {
					yData.add(m);
					xData.add(yData.size()-1);
				});
		XYChart chart = QuickChart.getChart("Sensor's measurements","NumOfMeasurement","Temperature",sensor.getName(), xData, yData);
		new SwingWrapper<XYChart>(chart).displayChart();
	}
}
