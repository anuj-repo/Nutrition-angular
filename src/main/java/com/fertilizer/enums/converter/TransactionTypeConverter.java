package com.fertilizer.enums.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.fertilizer.enums.TransactionType;

/**
 * @author kabir
 *
 */
@Converter(autoApply = true)
public class TransactionTypeConverter implements AttributeConverter<TransactionType, String> {
	@Override
	public String convertToDatabaseColumn(TransactionType vehicle) {
		if (vehicle == null)
			return null;

		return vehicle.getName();
	}

	@Override
	public TransactionType convertToEntityAttribute(String dbData) {
		if (dbData == null)
			return null;
		return TransactionType.fromShortName(dbData);
	}

}
