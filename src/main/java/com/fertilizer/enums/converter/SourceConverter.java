package com.fertilizer.enums.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.fertilizer.enums.Source;

/**
 * @author Dhiraj
 *
 */
@Converter(autoApply = true)
public class SourceConverter implements AttributeConverter<Source, String> {

	@Override
	public String convertToDatabaseColumn(Source vehicle) {
		if (vehicle == null)
			return null;
		return vehicle.getName();
	}

	@Override
	public Source convertToEntityAttribute(String dbData) {
		if (dbData == null)
			return null;
		return Source.fromShortName(dbData);
	}
}