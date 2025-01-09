package com.fertilizer.enums.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.fertilizer.enums.OpportunityRequestStatus;

@Converter(autoApply = true)
public class SfdcRequestStatusConverter implements AttributeConverter<OpportunityRequestStatus, String> {

    @Override
    public String convertToDatabaseColumn(OpportunityRequestStatus vehicle) {
        if (vehicle == null)
            return null;
        return vehicle.getName();
    }

    @Override
    public OpportunityRequestStatus convertToEntityAttribute(String dbData) {
        if (dbData == null)
            return null;
        return OpportunityRequestStatus.fromShortName(dbData);
    }
    }

