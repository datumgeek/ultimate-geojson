package com.erumi.ugeojson.parser;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.junit.Test;

import com.erumi.ugeojson.model.GeoJSONObjectDto;
import com.erumi.ugeojson.model.feature.FeatureCollectionDto;
import com.erumi.ugeojson.model.geometry.GeometryDto;
import com.erumi.ugeojson.model.geometry.MultiPolygonDto;
import com.erumi.ugeojson.model.geometry.PointDto;
import com.erumi.ugeojson.model.geometry.PolygonDto;

public class UltimateGeoJSONParserShould {

	@Test public void 
	parse_sample_geojson(){
		String sampleGeojson = getFile("sample.json");
		
		GeoJSONObjectDto dto = UltimateGeoJSONParser.getInstance().parse(sampleGeojson);
		assertThat(dto, instanceOf(FeatureCollectionDto.class));
		FeatureCollectionDto featureCollection = (FeatureCollectionDto) dto;
		
		assertThat(featureCollection.getFeatures().size(), equalTo(3));
		GeometryDto geometry = featureCollection.getFeatures().get(0).getGeometry();
		assertThat(geometry, instanceOf(PointDto.class));
		PointDto p = (PointDto) geometry;
		assertThat(p.getLongitude(), equalTo(102.0));
		assertThat(p.getLatitude(), equalTo(0.5));

		GeometryDto geometry2 = featureCollection.getFeatures().get(2).getGeometry();
		assertThat(geometry2, instanceOf(PolygonDto.class));
		PolygonDto polygon = (PolygonDto) geometry2;
		assertThat(polygon.getLinearRings().get(0).getPositions().size(), equalTo(5));
		assertThat(polygon.getLinearRings().get(0).getPositions().get(3).getLongitude(), equalTo(100.0));
		
	}
	
	@Test public void 
	parse_stations_geojson(){
		String stationsGeojson = getFile("stations.json");
		GeoJSONObjectDto dto = UltimateGeoJSONParser.getInstance().parse(stationsGeojson);
		assertThat(dto, instanceOf(FeatureCollectionDto.class));
		
		FeatureCollectionDto featureCollection = (FeatureCollectionDto) dto;
		assertThat(featureCollection.getFeatures().size(), equalTo(86));
	}
	
	@Test public void 
	parse_turkey_geojson(){
		String stationsGeojson = getFile("turkiye.json");
		GeoJSONObjectDto dto = UltimateGeoJSONParser.getInstance().parse(stationsGeojson);
		assertThat(dto, instanceOf(FeatureCollectionDto.class));
		
		FeatureCollectionDto featureCollection = (FeatureCollectionDto) dto;
		assertThat(featureCollection.getFeatures().size(), equalTo(1));
		MultiPolygonDto multiPolygon = (MultiPolygonDto) featureCollection.getFeatures().get(0).getGeometry();
		assertThat(multiPolygon.getPolygons().size(), equalTo(2));
	}
	

	  private String getFile(String fileName) {

			StringBuilder result = new StringBuilder("");

			//Get file from resources folder
			ClassLoader classLoader = getClass().getClassLoader();
			File file = new File(classLoader.getResource(fileName).getFile());

			try (Scanner scanner = new Scanner(file)) {

				while (scanner.hasNextLine()) {
					String line = scanner.nextLine();
					result.append(line).append("\n");
				}

				scanner.close();

			} catch (IOException e) {
				e.printStackTrace();
			}

			return result.toString();

		  }

}
