package com.wyc.common.util;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;

import org.joda.time.DateTime;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class MyDateSerializer extends JsonSerializer<DateTime>{

	@Override
	public void serialize(DateTime value, JsonGenerator jgen, SerializerProvider provider)
			throws IOException, JsonProcessingException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		jgen.writeString(simpleDateFormat.format(value.toDate()));
		
	}
	

	
	
}
