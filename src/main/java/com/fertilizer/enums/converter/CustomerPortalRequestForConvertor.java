package com.fertilizer.enums.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.fertilizer.enums.CustomerPortalRequestFor;

@Converter(autoApply = true)
public class CustomerPortalRequestForConvertor implements AttributeConverter<CustomerPortalRequestFor, String>{
	@Override
	public String convertToDatabaseColumn(CustomerPortalRequestFor channel) {
		if (channel == null)
			return null;
		return channel.getName();
	}

	@Override
	public CustomerPortalRequestFor convertToEntityAttribute(String dbData) {
		if (dbData == null)
			return null;
		return CustomerPortalRequestFor.fromShortName(dbData);
	}
}
