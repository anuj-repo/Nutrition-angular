package com.fertilizer.enums.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.fertilizer.enums.CustomerPortalRequestBy;
@Converter(autoApply = true)
public class CustomerPortalRequestByConvertor implements AttributeConverter<CustomerPortalRequestBy, String>{
	@Override
	public String convertToDatabaseColumn(CustomerPortalRequestBy channel) {
		if (channel == null)
			return null;
		return channel.getName();
	}

	@Override
	public CustomerPortalRequestBy convertToEntityAttribute(String dbData) {
		if (dbData == null)
			return null;
		return CustomerPortalRequestBy.fromShortName(dbData);
	}
}
