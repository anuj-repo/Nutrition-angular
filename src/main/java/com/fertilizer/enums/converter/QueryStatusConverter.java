package com.fertilizer.enums.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.fertilizer.enums.QueryStatus;

@Converter(autoApply = true)
public class QueryStatusConverter implements AttributeConverter<QueryStatus, String> {

	@Override
	public String convertToDatabaseColumn(QueryStatus vehicle) {
		if (vehicle == null)
			return null;
		return vehicle.getName();
	}

	@Override
	public QueryStatus convertToEntityAttribute(String dbData) {
		if (dbData == null)
			return null;
		return QueryStatus.fromShortName(dbData);
	}

}
