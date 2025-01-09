package com.fertilizer.config;

import org.hibernate.dialect.MySQL57Dialect;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.type.StandardBasicTypes;

import com.fertilizer.enums.EntityConstant;


public class MyCustomMYSQLDialect extends MySQL57Dialect {
	
	public MyCustomMYSQLDialect() {
        super();
        registerFunction(
            EntityConstant.GROUP_CONCAT.getName(),
            new StandardSQLFunction(
            	EntityConstant.GROUP_CONCAT.getName(),
                StandardBasicTypes.STRING
            )
        );
    }
}
