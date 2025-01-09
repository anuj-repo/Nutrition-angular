package com.fertilizer.enums.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.fertilizer.enums.ClientType;

@Converter(autoApply = true)
public class ClientTypeConverter implements AttributeConverter<ClientType, String> {

	@Override
	public String convertToDatabaseColumn(ClientType vehicle) {
		if (vehicle == null)
			return null;
		return vehicle.getName();
	}

	@Override
	public ClientType convertToEntityAttribute(String dbData) {
		if (dbData == null || dbData.isEmpty())
			return null;
		return ClientType.fromShortName(dbData);
	}
}
