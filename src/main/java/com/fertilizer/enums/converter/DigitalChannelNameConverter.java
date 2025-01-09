package com.fertilizer.enums.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.fertilizer.enums.DigitalChannelName;

@Converter(autoApply = true)
public class DigitalChannelNameConverter  implements AttributeConverter<DigitalChannelName, String> {
	
	@Override
	public String convertToDatabaseColumn(DigitalChannelName couponType) {
		if (couponType == null)
			return null;
		return couponType.getName();
	}

	@Override
	public DigitalChannelName convertToEntityAttribute(String dbData) {
		if (dbData == null)
			return null;
		return DigitalChannelName.fromShortName(dbData);
	}
}