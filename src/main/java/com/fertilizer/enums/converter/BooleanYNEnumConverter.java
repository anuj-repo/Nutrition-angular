package com.fertilizer.enums.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.fertilizer.enums.BooleanYNEnum;

@Converter(autoApply = true)
public class BooleanYNEnumConverter implements AttributeConverter<BooleanYNEnum, String> {

		@Override
		public String convertToDatabaseColumn(BooleanYNEnum vehicle) {
			if (vehicle == null)
				return null;
			return vehicle.getName();
		}

		@Override
		public BooleanYNEnum convertToEntityAttribute(String dbData) {
			if (dbData == null)
				return null;
			return BooleanYNEnum.fromShortName(dbData);
		}
	}

