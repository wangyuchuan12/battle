package com.wyc.common.util;

import java.lang.reflect.Field;
import org.jdom.Document;
import org.jdom.Element;

import com.wyc.annotation.NameAnnotation;
import com.wyc.annotation.ParamAnnotation;

public class XmlUtil {
	public static <T> T xmlToObject(Document document,Class<T> type)throws Exception{
        Object target = type.newInstance();
        Element rootElement = document.getRootElement();
        for(Field field:type.getDeclaredFields()){
            ParamAnnotation paramAnnotation = field.getAnnotation(ParamAnnotation.class);
            if(paramAnnotation!=null){
                String name = field.getName();
                NameAnnotation nameAnnotation = field.getAnnotation(NameAnnotation.class);
                if(nameAnnotation!=null){
                	name = nameAnnotation.name();
                }
                String value = rootElement.getChildText(name);
                field.setAccessible(true);
                field.set(target, value);
            }
         }
		return (T)target;
	}
}
