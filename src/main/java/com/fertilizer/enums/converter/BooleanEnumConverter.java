package com.fertilizer.enums.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.fertilizer.enums.BooleanEnum;

/**
 * @author Dhiraj
 *
 */
@Converter(autoApply = true)
public class BooleanEnumConverter implements AttributeConverter<BooleanEnum, String> {

	@Override
	public String convertToDatabaseColumn(BooleanEnum vehicle) {
		if (vehicle == null)
			return null;
		return vehicle.getName();
	}

	@Override
	public BooleanEnum convertToEntityAttribute(String dbData) {
		if (dbData == null)
			return null;
		return BooleanEnum.fromShortName(dbData);
	}
}