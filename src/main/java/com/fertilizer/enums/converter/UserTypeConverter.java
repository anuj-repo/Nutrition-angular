package com.fertilizer.enums.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.fertilizer.enums.UserType;

@Converter(autoApply = true)
public class UserTypeConverter implements AttributeConverter<UserType, String> {

	@Override
	public String convertToDatabaseColumn(UserType vehicle) {
		if (vehicle == null)
			return null;
		return vehicle.getName();
	}

	@Override
	public UserType convertToEntityAttribute(String dbData) {
		if (dbData == null)
			return null;
		return UserType.fromShortName(dbData);
	}
}