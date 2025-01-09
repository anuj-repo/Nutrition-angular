package com.fertilizer.enums.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.fertilizer.enums.CompanySize;

/**
 * @author Dhiraj
 *
 */
@Converter(autoApply = true)
public class CompanySizeConverter implements AttributeConverter<CompanySize, String> {

	@Override
	public String convertToDatabaseColumn(CompanySize vehicle) {
		if (vehicle == null)
			return null;
		return vehicle.getName();
	}

	@Override
	public CompanySize convertToEntityAttribute(String dbData) {
		if (dbData == null)
			return null;
		return CompanySize.fromShortName(dbData);
	}
}
