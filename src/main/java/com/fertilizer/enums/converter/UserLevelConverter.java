
package com.fertilizer.enums.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.fertilizer.enums.UserLevel;

@Converter(autoApply = true)
public class UserLevelConverter implements AttributeConverter<UserLevel, String> {

	@Override
	public String convertToDatabaseColumn(UserLevel vehicle) {
		if (vehicle == null)
			return null;
		return vehicle.getName();
	}

	@Override
	public UserLevel convertToEntityAttribute(String dbData) {
		if (dbData == null)
			return null;
		return UserLevel.fromShortName(dbData);
	}
}
