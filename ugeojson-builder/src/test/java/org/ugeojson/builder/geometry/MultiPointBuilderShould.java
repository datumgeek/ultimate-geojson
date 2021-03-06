package org.ugeojson.builder.geometry;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.ugeojson.builder.geometry.MultiPointBuilder;
import org.ugeojson.model.PositionDto;
import org.ugeojson.model.geometry.MultiPointDto;

public class MultiPointBuilderShould {

	@Test
	public void build_multipoint() {
		MultiPointDto multiPoint = new MultiPointDto();
		List<PositionDto> positions = new ArrayList<>();
		positions.add(new PositionDto(32.123, 24.587));
		positions.add(new PositionDto(36.1478, 29.3645));
		multiPoint.setPositions(positions);

		String geometryGeoJSON = MultiPointBuilder.getInstance().toGeoJSON(multiPoint);
		System.out.println(geometryGeoJSON);
		assertThat(geometryGeoJSON, equalTo(
				"{\n\"type\": \"MultiPoint\",\n\"coordinates\": [\n[32.123, 24.587],\n[36.1478, 29.3645]\n]\n}"));
	}
}
