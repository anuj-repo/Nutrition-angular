package com.fertilizer.enums.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.fertilizer.enums.SfdcEntity;
@Converter(autoApply = true)
public class SfdcRequestConvertor implements AttributeConverter<SfdcEntity, String>{
	@Override
	public String convertToDatabaseColumn(SfdcEntity channel) {
		if (channel == null)
			return null;
		return channel.getName();
	}

	@Override
	public SfdcEntity convertToEntityAttribute(String dbData) {
		if (dbData == null)
			return null;
		return SfdcEntity.fromShortName(dbData);
	}
}
