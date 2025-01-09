package com.fertilizer.enums.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.fertilizer.enums.GraphDataChoice;

@Converter(autoApply = true)
public class GraphDataChoiceConverter implements AttributeConverter<GraphDataChoice, String> {

	@Override
	public String convertToDatabaseColumn(GraphDataChoice attribute) {
		if (attribute == null)
			return null;

		return attribute.getName();
	}

	@Override
	public GraphDataChoice convertToEntityAttribute(String dbData) {
		if (dbData == null)
			return null;
		return GraphDataChoice.fromShortName(dbData);
	}
}
