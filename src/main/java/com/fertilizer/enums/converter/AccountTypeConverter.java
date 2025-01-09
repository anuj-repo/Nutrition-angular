package com.fertilizer.enums.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.fertilizer.enums.AccountType;

@Converter(autoApply = true)
public class AccountTypeConverter  implements AttributeConverter<AccountType, String> {
	
	@Override
	public String convertToDatabaseColumn(AccountType couponType) {
		if (couponType == null)
			return null;
		return couponType.getName();
	}

	@Override
	public AccountType convertToEntityAttribute(String dbData) {
		if (dbData == null)
			return null;
		return AccountType.fromShortName(dbData);
	}
}
