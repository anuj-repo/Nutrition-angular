package com.fertilizer.enums.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.fertilizer.enums.Status;

/**
 * @author Dhiraj
 *
 */
@Converter(autoApply = true)
public class StatusConverter implements AttributeConverter<Status, String> {

	@Override
	public String convertToDatabaseColumn(Status vehicle) {
		if (vehicle == null)
			return null;

		return vehicle.getName();
	}

	@Override
	public Status convertToEntityAttribute(String dbData) {
		if (dbData == null)
			return null;
		return Status.fromShortName(dbData);
	}

}
