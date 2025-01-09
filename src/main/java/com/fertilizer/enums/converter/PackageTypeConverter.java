package com.fertilizer.enums.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.fertilizer.enums.PackageType;

@Converter(autoApply = true)
public class PackageTypeConverter implements AttributeConverter<PackageType, String> {

	@Override
	public String convertToDatabaseColumn(PackageType channel) {
		if (channel == null)
			return null;
		return channel.getName();
	}

	@Override
	public PackageType convertToEntityAttribute(String dbData) {
		if (dbData == null)
			return null;
		return PackageType.fromShortName(dbData);
	}

}
