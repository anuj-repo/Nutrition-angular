package com.fertilizer.enums.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.fertilizer.enums.Channel;

@Converter(autoApply = true)
public class ChannelConverter implements AttributeConverter<Channel, String> {

	@Override
	public String convertToDatabaseColumn(Channel channel) {
		if (channel == null)
			return null;
		return channel.getName();
	}

	@Override
	public Channel convertToEntityAttribute(String dbData) {
		if (dbData == null)
			return null;
		return Channel.fromShortName(dbData);
	}
}
