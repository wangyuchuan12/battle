package com.wyc.common.extend;
import org.hibernate.dialect.MySQL5InnoDBDialect;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.type.StandardBasicTypes;

public class MySqlDialectExtend extends MySQL5InnoDBDialect{
	public MySqlDialectExtend() {
		super();
		
		registerFunction("date_add" ,  new  SQLFunctionTemplate(StandardBasicTypes.DATE,  "date_add(?1, INTERVAL ?2 ?3)" ));  
	}
}
