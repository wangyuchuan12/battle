package com.wyc.common.util;


import java.io.File;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.Set;
@SuppressWarnings({ "unchecked", "rawtypes" })
public abstract class TypeUtil {
	public static boolean isBasicLowerType(Class clazz){
		if(clazz.equals(int.class)){
			return true;
		}

		if(clazz.equals(short.class)){
			return true;
		}

		if(clazz.equals(byte.class)){
			return true;
		}

		if(clazz.equals(long.class)){
			return true;
		}

		if(clazz.equals(float.class)){
			return true;
		}
		if(clazz.equals(double.class)){
			return true;
		}
		if(clazz.equals(char.class)){
			return true;
		}
		if(clazz.equals(boolean.class)){
			return true;
		}
		return false;
	}
	public static boolean isBasicType(Class clazz){
		if(clazz.equals(int.class)||clazz.equals(Integer.class)){
			return true;
		}

		if(clazz.equals(short.class)||clazz.equals(Short.class)){
			return true;
		}

		if(clazz.equals(byte.class)||clazz.equals(Byte.class)){
			return true;
		}

		if(clazz.equals(long.class)||clazz.equals(Long.class)){
			return true;
		}

		if(clazz.equals(float.class)||clazz.equals(Float.class)){
			return true;
		}
		if(clazz.equals(double.class)||clazz.equals(Double.class)){
			return true;
		}
		if(clazz.equals(char.class)||clazz.equals(Character.class)){
			return true;
		}
		if(clazz.equals(boolean.class)||clazz.equals(Boolean.class)){
			return true;
		}
		return false;
	}
	public static boolean isNumberType(Class clazz){
		if(clazz.equals(int.class)){
			return true;
		}
		if(clazz.equals(Integer.class)){
			return true;
		}
		if(clazz.equals(short.class)){
			return true;
		}
		if(clazz.equals(Short.class)){
			return true;
		}
		if(clazz.equals(long.class)){
			return true;
		}
		if(clazz.equals(Long.class)){
			return true;
		}
		if(clazz.equals(float.class)){
			return true;
		}
		if(clazz.equals(Float.class)){
			return true;
		}
		if(clazz.equals(double.class)){
			return true;
		}
		if(clazz.equals(Double.class)){
			return true;
		}
		return false;
	}
	public static boolean isInt(Class clazz){
		if(clazz.equals(int.class)){
			return true;
		}
		if(clazz.equals(Integer.class)){
			return true;
		}
		return false;
	}
	public static boolean isShort(Class clazz){
		if(clazz.equals(short.class)){
			return true;
		}
		if(clazz.equals(Short.class)){
			return true;
		}
		return false;
	}
	public static boolean isLong(Class clazz){
		if(clazz.equals(long.class)){
			return true;
		}
		if(clazz.equals(Long.class)){
			return true;
		}
		return false;
	}
	public static boolean isFloat(Class clazz){
		if(clazz.equals(float.class)){
			return true;
		}
		if(clazz.equals(Float.class)){
			return true;
		}
		return false;
	}
	public static boolean isDouble(Class clazz){
		if(clazz.equals(double.class)){
			return true;
		}
		if(clazz.equals(Double.class)){
			return true;
		}
		return false;
	}
	public static boolean isByte(Class clazz){
		if(clazz.equals(byte.class)){
			return true;
		}
		if(clazz.equals(Byte.class)){
			return true;
		}
		return false;
	}
	public static boolean isBoolean(Class clazz){
		if(clazz.equals(boolean.class)){
			return true;
		}
		if(clazz.equals(Boolean.class)){
			return true;
		}
		return false;
	}
	public static boolean isCharacter(Class clazz){
		if(clazz.equals(char.class)){
			return true;
		}
		if(clazz.equals(Character.class)){
			return true;
		}
		return false;
	}
	public static boolean isString(Class clazz){
		if(clazz.equals(String.class)){
			return true;
		}
		return false;
	}
	public static boolean isArray(Class clazz){
		if(clazz.isArray()){
			return true;
		}
		if(Collection.class.isAssignableFrom(clazz)){
			return true;
		}
		return false;
	}
	public static boolean isArrayOfBasic(Class clazz){
		if(clazz.isArray()){
			return true;
		}
		return false;
	}
	public static boolean isArrayOfList(Class clazz){
		if(Collection.class.isAssignableFrom(clazz)){
			return true;
		}
		return false;
	}
	public static boolean isMap(Class clazz){
		if(Map.class.isAssignableFrom(clazz)){
			return true;
		}else{
			return false;
		}
	}
	public static boolean isDate(Class clazz){
		if(clazz.equals(Date.class)){
			return true;
		}else{
			return false;
		}
	}
	public static boolean isFile(Class clazz){
		if(File.class.isAssignableFrom(clazz)){
			return true;
		}else{
			return false;
		}
	}
	public static String getString(Object value){
		if(value==null){
			return null;
		}
		return value.toString();
	}
	public static int getInt(Object value){
		if(isDate(value.getClass())){
			return (int)((Date)value).getTime();
		}
		return Integer.parseInt(value.toString());
	}

	public static boolean getBoolean(Object value){
		if(value==null||value.equals("")){
			return false;
		}
		if(isNumberType(value.getClass())){
			float f = Float.parseFloat(value.toString());
			if(f==0){
				return false;
			}else{
				return true;
			}
		}
		String strValue = value.toString();
		if(strValue.toLowerCase().equals(false+"")){
			return false;
		}else{
			return true;
		}
	}
	public static Short getShort(Object value){
		return Short.parseShort(value.toString());
	}
	public static Byte getByte(Object value){
		return Byte.parseByte(value.toString());
	}
	public static Long getLong(Object value){
		return Long.parseLong(value.toString());
	}
	public static Float getFloat(Object value){
		return Float.parseFloat(value.toString());
	}
	public static Double getDouble(Object value){
		return Double.parseDouble(value.toString());
	}
	public static Character getCharacter(Object value){
		return value.toString().charAt(0);
	}
	@SuppressWarnings("deprecation")
	public static Date getDate(Object value){
		if(value==null){
			return null;
		}
		if(isNumberType(value.getClass())){
			return new Date(Long.parseLong(value.toString()));
		}else if (isDate(value.getClass())) {
			return (Date)value;
		}else{
			return new Date(value.toString());
		}
	}
	public static Object getValue(Object value , Class<?> type)throws Exception{
		if(value==null){
			return null;
		}
		if(isInt(type)){
			return getInt(value);
		}
		if(isShort(type)){
			return getShort(value);
		}
		if(isLong(type)){
			return getLong(value);
		}
		if(isFloat(type)){
			return getFloat(value);
		}
		if(isDouble(type)){
			return getDouble(value);
		}
		if(isByte(type)){
			return getByte(value);
		}
		if(isBoolean(type)){
			return getBoolean(value);
		}
		if(isCharacter(type)){
			return getCharacter(value);
		}
		if(isString(type)){
			return getString(value);
		}else if (isDate(type)) {
			return getDate(value);
		}
		if((isArray(value.getClass())&&!isArray(type))||(!isArray(value.getClass())&&isArray(type))){
			throw new RuntimeException("");
		}else{
			if(isArrayOfBasic(type)){
				Class elementType = getArrayOfBasicElementType(type);
				if(isString(elementType)){
					return getStringArrayOfBasic(value);
				}else if (isInt(elementType)) {
					return getIntArrayOfBasic(value);
				}else if (isShort(elementType)) {
					return getShortArrayOfBasic(value);
				}else if (isLong(elementType)) {
					return getLongArrayOfBasic(value);
				}else if (isBoolean(elementType)) {
					return getBooleanArrayOfBasic(value);
				}else if (isByte(elementType)) {
					return getByteArrayOfBasic(value);
				}else if (isCharacter(elementType)) {
					return getCharArrayOfBasic(value);
				}else if (isDouble(elementType)) {
					return getDoubleArrayOfBasic(value);
				}else if (isFloat(elementType)) {
					return getFloatArrayOfBasic(value);
				}
			}else{
				
			}
		}
		return value;
	}
	//�õ����鵥����Ԫ��ֵ
	public static Class getArrayOfBasicElementType(Class clazz)throws Exception{
		if(!isArrayOfBasic(clazz)){
			return null;
		}
		if (clazz.equals(int[].class)||clazz.equals(Integer[].class)) {
			return int.class;
		}
		if(clazz.equals(short[].class)||clazz.equals(Short[].class)){
			return short.class;
		}
		if(clazz.equals(long[].class)||clazz.equals(Long[].class)){
			return long.class;
		}
		if(clazz.equals(float[].class)||clazz.equals(Float[].class)){
			return float.class;
		}
		if(clazz.equals(double[].class)||clazz.equals(Double[].class)){
			return double.class;
		}
		if(clazz.equals(byte[].class)||clazz.equals(Byte[].class)){
			return byte.class;
		}
		if(clazz.equals(boolean[].class)||clazz.equals(Boolean[].class)){
			return boolean.class;
		}
		if(clazz.equals(char[].class)||clazz.equals(Character[].class)){
			return char.class;
		}
		if(clazz.getName().startsWith("[L")){
			String classPath = clazz.getName().substring(clazz.getName().indexOf("[L")+2,clazz.getName().length()-1);
			Class clazz2 = Class.forName(classPath);
			return clazz2;
		}
		return null;
	}
	public static Map getMap(Map value , Class keyType , Class valueType)throws Exception{
		Map map = value.getClass().newInstance();
		Set<Entry> entries = value.entrySet();
		for(Entry entry : entries){
			map.put(getValue(entry.getKey(), keyType), getValue(entry.getValue(), valueType));
		}
		return map;
	}
	public static Object[] getObjectArrayOfBasic(Collection value){
		if(value==null){
			return null;
		}
		Object[] objects = new Object[value.size()];
		Iterator iterator = value.iterator();
		for(int i = 0 ;i<value.size();i++){
			objects[i] = getString(iterator.next());
		}
		return objects;
	}
	public static String[] getStringArrayOfBasic(Collection value){
		if(value==null){
			return null;
		}
		String[] strings = new String[value.size()];
		Iterator iterator = value.iterator();
		for(int i = 0 ;i<value.size();i++){
			strings[i] = getString(iterator.next());
		}
		return strings;
	}
	public static String[] getStringArrayOfBasic(Object value)throws Exception{
		if(value==null){
			return null;
		}
		if(isArrayOfList(value.getClass())){
			return getStringArrayOfBasic((Collection)value);
		}else{
			Class elementType = getArrayOfBasicElementType(value.getClass());
			if(isInt(elementType)){
				return getStringArrayOfBasic((int[])value);
			}
			if(isShort(elementType)){
				return getStringArrayOfBasic((short[])value);
			}
			if(isFloat(elementType)){
				return getStringArrayOfBasic((float[])value);
			}
			if(isDouble(elementType)){
				return getStringArrayOfBasic((double[])value);
			}
			if(isByte(elementType)){
				return getStringArrayOfBasic((byte[])value);
			}
			if(isBoolean(elementType)){
				return getStringArrayOfBasic((boolean[])value);
			}
			if(isLong(elementType)){
				return getStringArrayOfBasic((long[])value);
			}
			if(isCharacter(elementType)){
				return getStringArrayOfBasic((char[])value);
			}
			if(isString(elementType)){
				return getStringArrayOfBasic((String[])value);
			}
			return getStringArrayOfBasic((Object[]) value);
		}
	}
	public static String[] getStringArrayOfBasic(int[] value){
		if(value==null){
			return null;
		}
		String[] strings = new String[value.length];
		for(int i = 0 ;i<value.length;i++){
			strings[i] = getString(value[i]);
		}
		return strings;
	}
	
	public static String[] getStringArrayOfBasic(Object[] value){
		if(value==null){
			return null;
		}
		String[] strings = new String[value.length];
		for(int i = 0 ;i<value.length;i++){
			strings[i] = getString(value[i]);
		}
		return strings;
	}
	public static String[] getStringArrayOfBasic(long[] value){
		if(value==null){
			return null;
		}
		String[] strings = new String[value.length];
		for(int i = 0 ;i<value.length;i++){
			strings[i] = getString(value[i]);
		}
		return strings;
	}
	public static String[] getStringArrayOfBasic(float[] value){
		if(value==null){
			return null;
		}
		String[] strings = new String[value.length];
		for(int i = 0 ;i<value.length;i++){
			strings[i] = getString(value[i]);
		}
		return strings;
	}
	public static String[] getStringArrayOfBasic(double[] value){
		if(value==null){
			return null;
		}
		String[] strings = new String[value.length];
		for(int i = 0 ;i<value.length;i++){
			strings[i] = getString(value[i]);
		}
		return strings;
	}
	public static String[] getStringArrayOfBasic(byte[] value){
		if(value==null){
			return null;
		}
		String[] strings = new String[value.length];
		for(int i = 0 ;i<value.length;i++){
			strings[i] = getString(value[i]);
		}
		return strings;
	}
	public static String[] getStringArrayOfBasic(boolean[] value){
		if(value==null){
			return null;
		}
		String[] strings = new String[value.length];
		for(int i = 0 ;i<value.length;i++){
			strings[i] = getString(value[i]);
		}
		return strings;
	}
	public static String[] getStringArrayOfBasic(char[] value){
		if(value==null){
			return null;
		}
		String[] strings = new String[value.length];
		for(int i = 0 ;i<value.length;i++){
			strings[i] = getString(value[i]);
		}
		return strings;
	}
	public static String[] getStringArrayOfBasic(short[] value){
		if(value==null){
			return null;
		}
		String[] strings = new String[value.length];
		for(int i = 0 ;i<value.length;i++){
			strings[i] = getString(value[i]);
		}
		return strings;
	}
	public static String[] getStringArrayOfBasic(String[] value){
		return value;
	}
	public static int[] getIntArrayOfBasic(Object[] value){
		if(value==null){
			return null;
		}
		int[] is = new int[value.length];
		for(int i = 0 ;i<value.length;i++){
			is[i] = getInt(value[i]);
		}
		return is;
	}
	public static int[] getIntArrayOfBasic(int[] value){
		
		return value;
	}
	public static int[] getIntArrayOfBasic(Collection value){
		if(value==null){
			return null;
		}
		int[] values = new int[value.size()];
		Iterator iterator = value.iterator();
		for(int i = 0 ;i<value.size();i++){
			values[i] = getInt(iterator.next());
		}
		return values;
	}
	public static int[] getIntArrayOfBasic(Object value)throws Exception{
		if(value==null){
			return null;
		}
		if(isArrayOfList(value.getClass())){
			return getIntArrayOfBasic((Collection)value);
		}else{
			Class elementType = getArrayOfBasicElementType(value.getClass());
			if(isInt(elementType)){
				return getIntArrayOfBasic((int[])value);
			}
			if(isShort(elementType)){
				return getIntArrayOfBasic((short[])value);
			}
			if(isFloat(elementType)){
				return getIntArrayOfBasic((float[])value);
			}
			if(isDouble(elementType)){
				return getIntArrayOfBasic((double[])value);
			}
			if(isByte(elementType)){
				return getIntArrayOfBasic((byte[])value);
			}
			if(isBoolean(elementType)){
				return getIntArrayOfBasic((boolean[])value);
			}
			if(isLong(elementType)){
				return getIntArrayOfBasic((long[])value);
			}
			if(isCharacter(elementType)){
				return getIntArrayOfBasic((char[])value);
			}
			if(isString(elementType)){
				return getIntArrayOfBasic((String[])value);
			}
			return getIntArrayOfBasic((Object[]) value);
		}
	}
	public static int[] getIntArrayOfBasic(short[] value){
		if(value==null){
			return null;
		}
		int[] is = new int[value.length];
		for(int i = 0 ;i<value.length;i++){
			is[i] = getInt(value[i]);
		}
		return is;
	}
	public static int[] getIntArrayOfBasic(long[] value){
		if(value==null){
			return null;
		}
		int[] is = new int[value.length];
		for(int i = 0 ;i<value.length;i++){
			is[i] = getInt(value[i]);
		}
		return is;
	}
	public static int[] getIntArrayOfBasic(float[] value){
		if(value==null){
			return null;
		}
		int[] is = new int[value.length];
		for(int i = 0 ;i<value.length;i++){
			is[i] = getInt(value[i]);
		}
		return is;
	}
	public static int[] getIntArrayOfBasic(double[] value){
		if(value==null){
			return null;
		}
		int[] is = new int[value.length];
		for(int i = 0 ;i<value.length;i++){
			is[i] = getInt(value[i]);
		}
		return is;
	}
	public static int[] getIntArrayOfBasic(byte[] value){
		if(value==null){
			return null;
		}
		int[] is = new int[value.length];
		for(int i = 0 ;i<value.length;i++){
			is[i] = getInt(value[i]);
		}
		return is;
	}
	public static int[] getIntArrayOfBasic(boolean[] value){
		if(value==null){
			return null;
		}
		int[] is = new int[value.length];
		for(int i = 0 ;i<value.length;i++){
			is[i] = getInt(value[i]);
		}
		return is;
	}
	public static int[] getIntArrayOfBasic(char[] value){
		if(value==null){
			return null;
		}
		int[] is = new int[value.length];
		for(int i = 0 ;i<value.length;i++){
			is[i] = getInt(value[i]);
		}
		return is;
	}
	public static int[] getIntArrayOfBasic(String[] value){
		if(value==null){
			return null;
		}
		int[] is = new int[value.length];
		for(int i = 0 ;i<value.length;i++){
			is[i] = getInt(value[i]);
		}
		return is;
	}
	
	public static short[] getShortArrayOfBasic(Collection value){
		if(value==null){
			return null;
		}
		short[] shorts = new short[value.size()];
		Iterator iterator = value.iterator();
		for(int i = 0 ;i<value.size();i++){
			shorts[i] = getShort(iterator.next());
		}
		return shorts;
	}
	public static short[] getShortArrayOfBasic(Object value)throws Exception{
		if(value==null){
			return null;
		}
		if(isArrayOfList(value.getClass())){
			return getShortArrayOfBasic((Collection)value);
		}else{
			Class elementType = getArrayOfBasicElementType(value.getClass());
			if(isInt(elementType)){
				return getShortArrayOfBasic((int[])value);
			}
			if(isShort(elementType)){
				return getShortArrayOfBasic((short[])value);
			}
			if(isFloat(elementType)){
				return getShortArrayOfBasic((float[])value);
			}
			if(isDouble(elementType)){
				return getShortArrayOfBasic((double[])value);
			}
			if(isByte(elementType)){
				return getShortArrayOfBasic((byte[])value);
			}
			if(isBoolean(elementType)){
				return getShortArrayOfBasic((boolean[])value);
			}
			if(isLong(elementType)){
				return getShortArrayOfBasic((long[])value);
			}
			if(isCharacter(elementType)){
				return getShortArrayOfBasic((char[])value);
			}
			if(isString(elementType)){
				return getShortArrayOfBasic((String[])value);
			}
			return getShortArrayOfBasic((Object[]) value);
		}
	}
	public static short[] getShortArrayOfBasic(short[] value){
		return value;
	}
	public static short[] getShortArrayOfBasic(long[] value){
		if(value==null){
			return null;
		}
		short[] is = new short[value.length];
		for(int i = 0 ;i<value.length;i++){
			is[i] = getShort(value[i]);
		}
		return is;
	}
	public static short[] getShortArrayOfBasic(int[] value){
		if(value==null){
			return null;
		}
		short[] is = new short[value.length];
		for(int i = 0 ;i<value.length;i++){
			is[i] = getShort(value[i]);
		}
		return is;
	}
	public static short[] getShortArrayOfBasic(Object[] value){
		if(value==null){
			return null;
		}
		short[] is = new short[value.length];
		for(int i = 0 ;i<value.length;i++){
			is[i] = getShort(value[i]);
		}
		return is;
	}
	public static short[] getShortArrayOfBasic(float[] value){
		if(value==null){
			return null;
		}
		short[] is = new short[value.length];
		for(int i = 0 ;i<value.length;i++){
			is[i] = getShort(value[i]);
		}
		return is;
	}
	public static short[] getShortArrayOfBasic(double[] value){
		if(value==null){
			return null;
		}
		short[] is = new short[value.length];
		for(int i = 0 ;i<value.length;i++){
			is[i] = getShort(value[i]);
		}
		return is;
	}
	public static short[] getShortArrayOfBasic(byte[] value){
		if(value==null){
			return null;
		}
		short[] is = new short[value.length];
		for(int i = 0 ;i<value.length;i++){
			is[i] = getShort(value[i]);
		}
		return is;
	}
	public static short[] getShortArrayOfBasic(boolean[] value){
		if(value==null){
			return null;
		}
		short[] is = new short[value.length];
		for(int i = 0 ;i<value.length;i++){
			is[i] = getShort(value[i]);
		}
		return is;
	}
	public static short[] getShortArrayOfBasic(char[] value){
		if(value==null){
			return null;
		}
		short[] is = new short[value.length];
		for(int i = 0 ;i<value.length;i++){
			is[i] = getShort(value[i]);
		}
		return is;
	}
	public static short[] getShortArrayOfBasic(String[] value){
		if(value==null){
			return null;
		}
		short[] is = new short[value.length];
		for(int i = 0 ;i<value.length;i++){
			is[i] = getShort(value[i]);
		}
		return is;
	}
	
	public static long[] getLongArrayOfBasic(Collection value){
		if(value==null){
			return null;
		}
		long[] values = new long[value.size()];
		Iterator iterator = value.iterator();
		for(int i = 0 ;i<value.size();i++){
			values[i] = getLong(iterator.next());
		}
		return values;
	}
	public static long[] getLongArrayOfBasic(Object value)throws Exception{
		if(value==null){
			return null;
		}
		if(isArrayOfList(value.getClass())){
			return getLongArrayOfBasic((Collection)value);
		}else{
			Class elementType = getArrayOfBasicElementType(value.getClass());
			if(isInt(elementType)){
				return getLongArrayOfBasic((int[])value);
			}
			if(isShort(elementType)){
				return getLongArrayOfBasic((short[])value);
			}
			if(isFloat(elementType)){
				return getLongArrayOfBasic((float[])value);
			}
			if(isDouble(elementType)){
				return getLongArrayOfBasic((double[])value);
			}
			if(isByte(elementType)){
				return getLongArrayOfBasic((byte[])value);
			}
			if(isBoolean(elementType)){
				return getLongArrayOfBasic((boolean[])value);
			}
			if(isLong(elementType)){
				return getLongArrayOfBasic((long[])value);
			}
			if(isCharacter(elementType)){
				return getLongArrayOfBasic((char[])value);
			}
			if(isString(elementType)){
				return getLongArrayOfBasic((String[])value);
			}
			return getLongArrayOfBasic((Object[]) value);
		}
	}
	public static long[] getLongArrayOfBasic(int[] value){
		if(value==null){
			return null;
		}
		long[] is = new long[value.length];
		for(int i = 0 ;i<value.length;i++){
			is[i] = getLong(value[i]);
		}
		return is;
	}
	public static long[] getLongArrayOfBasic(Object[] value){
		if(value==null){
			return null;
		}
		long[] is = new long[value.length];
		for(int i = 0 ;i<value.length;i++){
			is[i] = getLong(value[i]);
		}
		return is;
	}
	public static long[] getLongArrayOfBasic(short[] value){
		if(value==null){
			return null;
		}
		long[] is = new long[value.length];
		for(int i = 0 ;i<value.length;i++){
			is[i] = getLong(value[i]);
		}
		return is;
	}
	public static long[] getLongArrayOfBasic(long[] value){
		if(value==null){
			return null;
		}
		long[] is = new long[value.length];
		for(int i = 0 ;i<value.length;i++){
			is[i] = getLong(value[i]);
		}
		return is;
	}
	public static long[] getLongArrayOfBasic(float[] value){
		if(value==null){
			return null;
		}
		long[] is = new long[value.length];
		for(int i = 0 ;i<value.length;i++){
			is[i] = getLong(value[i]);
		}
		return is;
	}
	public static long[] getLongArrayOfBasic(double[] value){
		if(value==null){
			return null;
		}
		long[] is = new long[value.length];
		for(int i = 0 ;i<value.length;i++){
			is[i] = getLong(value[i]);
		}
		return is;
	}
	public static long[] getLongArrayOfBasic(byte[] value){
		if(value==null){
			return null;
		}
		long[] is = new long[value.length];
		for(int i = 0 ;i<value.length;i++){
			is[i] = getLong(value[i]);
		}
		return is;
	}
	public static long[] getLongArrayOfBasic(boolean[] value){
		if(value==null){
			return null;
		}
		long[] is = new long[value.length];
		for(int i = 0 ;i<value.length;i++){
			is[i] = getLong(value[i]);
		}
		return is;
	}
	public static long[] getLongArrayOfBasic(char[] value){
		if(value==null){
			return null;
		}
		long[] is = new long[value.length];
		for(int i = 0 ;i<value.length;i++){
			is[i] = getLong(value[i]);
		}
		return is;
	}
	public static long[] getLongArrayOfBasic(String[] value){
		if(value==null){
			return null;
		}
		long[] is = new long[value.length];
		for(int i = 0 ;i<value.length;i++){
			is[i] = getLong(value[i]);
		}
		return is;
	}
	public static float[] getFloatArrayOfBasic(Collection value){
		if(value==null){
			return null;
		}
		float[] values = new float[value.size()];
		Iterator iterator = value.iterator();
		for(int i = 0 ;i<value.size();i++){
			values[i] = getFloat(iterator.next());
		}
		return values;
	}
	public static float[] getFloatArrayOfBasic(Object value)throws Exception{
		if(value==null){
			return null;
		}
		if(isArrayOfList(value.getClass())){
			return getFloatArrayOfBasic((Collection)value);
		}else{
			Class elementType = getArrayOfBasicElementType(value.getClass());
			if(isInt(elementType)){
				return getFloatArrayOfBasic((int[])value);
			}
			if(isShort(elementType)){
				return getFloatArrayOfBasic((short[])value);
			}
			if(isFloat(elementType)){
				return getFloatArrayOfBasic((float[])value);
			}
			if(isDouble(elementType)){
				return getFloatArrayOfBasic((double[])value);
			}
			if(isByte(elementType)){
				return getFloatArrayOfBasic((byte[])value);
			}
			if(isBoolean(elementType)){
				return getFloatArrayOfBasic((boolean[])value);
			}
			if(isLong(elementType)){
				return getFloatArrayOfBasic((long[])value);
			}
			if(isCharacter(elementType)){
				return getFloatArrayOfBasic((char[])value);
			}
			if(isString(elementType)){
				return getFloatArrayOfBasic((String[])value);
			}
			return getFloatArrayOfBasic((Object[]) value);
		}
	}
	public static float[] getFloatArrayOfBasic(int[] value){
		if(value==null){
			return null;
		}
		float[] is = new float[value.length];
		for(int i = 0 ;i<value.length;i++){
			is[i] = getFloat(value[i]);
		}
		return is;
	}
	public static float[] getFloatArrayOfBasic(Object[] value){
		if(value==null){
			return null;
		}
		float[] is = new float[value.length];
		for(int i = 0 ;i<value.length;i++){
			is[i] = getFloat(value[i]);
		}
		return is;
	}
	public static float[] getFloatArrayOfBasic(short[] value){
		if(value==null){
			return null;
		}
		float[] is = new float[value.length];
		for(int i = 0 ;i<value.length;i++){
			is[i] = getFloat(value[i]);
		}
		return is;
	}
	public static float[] getFloatArrayOfBasic(long[] value){
		if(value==null){
			return null;
		}
		float[] is = new float[value.length];
		for(int i = 0 ;i<value.length;i++){
			is[i] = getFloat(value[i]);
		}
		return is;
	}
	public static float[] getFloatArrayOfBasic(float[] value){
		if(value==null){
			return null;
		}
		float[] is = new float[value.length];
		for(int i = 0 ;i<value.length;i++){
			is[i] = getFloat(value[i]);
		}
		return is;
	}
	public static float[] getFloatArrayOfBasic(double[] value){
		if(value==null){
			return null;
		}
		float[] is = new float[value.length];
		for(int i = 0 ;i<value.length;i++){
			is[i] = getFloat(value[i]);
		}
		return is;
	}
	public static float[] getFloatArrayOfBasic(byte[] value){
		if(value==null){
			return null;
		}
		float[] is = new float[value.length];
		for(int i = 0 ;i<value.length;i++){
			is[i] = getFloat(value[i]);
		}
		return is;
	}
	public static float[] getFloatArrayOfBasic(boolean[] value){
		if(value==null){
			return null;
		}
		float[] is = new float[value.length];
		for(int i = 0 ;i<value.length;i++){
			is[i] = getFloat(value[i]);
		}
		return is;
	}
	public static float[] getFloatArrayOfBasic(char[] value){
		if(value==null){
			return null;
		}
		float[] is = new float[value.length];
		for(int i = 0 ;i<value.length;i++){
			is[i] = getFloat(value[i]);
		}
		return is;
	}
	public static float[] getFloatArrayOfBasic(String[] value){
		if(value==null){
			return null;
		}
		float[] is = new float[value.length];
		for(int i = 0 ;i<value.length;i++){
			is[i] = getFloat(value[i]);
		}
		return is;
	}
	public static double[] getDoubleArrayOfBasic(Collection value){
		if(value==null){
			return null;
		}
		double[] values = new double[value.size()];
		Iterator iterator = value.iterator();
		for(int i = 0 ;i<value.size();i++){
			values[i] = getDouble(iterator.next());
		}
		return values;
	}
	public static double[] getDoubleArrayOfBasic(Object value)throws Exception{
		if(value==null){
			return null;
		}
		if(isArrayOfList(value.getClass())){
			return getDoubleArrayOfBasic((Collection)value);
		}else{
			Class elementType = getArrayOfBasicElementType(value.getClass());
			if(isInt(elementType)){
				return getDoubleArrayOfBasic((int[])value);
			}
			if(isShort(elementType)){
				return getDoubleArrayOfBasic((short[])value);
			}
			if(isFloat(elementType)){
				return getDoubleArrayOfBasic((float[])value);
			}
			if(isDouble(elementType)){
				return getDoubleArrayOfBasic((double[])value);
			}
			if(isByte(elementType)){
				return getDoubleArrayOfBasic((byte[])value);
			}
			if(isBoolean(elementType)){
				return getDoubleArrayOfBasic((boolean[])value);
			}
			if(isLong(elementType)){
				return getDoubleArrayOfBasic((long[])value);
			}
			if(isCharacter(elementType)){
				return getDoubleArrayOfBasic((char[])value);
			}
			if(isString(elementType)){
				return getDoubleArrayOfBasic((String[])value);
			}
			return getDoubleArrayOfBasic((Object[]) value);
		}
	}
	public static double[] getDoubleArrayOfBasic(int[] value){
		if(value==null){
			return null;
		}
		double[] is = new double[value.length];
		for(int i = 0 ;i<value.length;i++){
			is[i] = getDouble(value[i]);
		}
		return is;
	}
	public static double[] getDoubleArrayOfBasic(Object[] value){
		if(value==null){
			return null;
		}
		double[] is = new double[value.length];
		for(int i = 0 ;i<value.length;i++){
			is[i] = getDouble(value[i]);
		}
		return is;
	}
	public static double[] getDoubleArrayOfBasic(short[] value){
		if(value==null){
			return null;
		}
		double[] is = new double[value.length];
		for(int i = 0 ;i<value.length;i++){
			is[i] = getDouble(value[i]);
		}
		return is;
	}
	public static double[] getDoubleArrayOfBasic(long[] value){
		if(value==null){
			return null;
		}
		double[] is = new double[value.length];
		for(int i = 0 ;i<value.length;i++){
			is[i] = getDouble(value[i]);
		}
		return is;
	}
	public static double[] getDoubleArrayOfBasic(float[] value){
		if(value==null){
			return null;
		}
		double[] is = new double[value.length];
		for(int i = 0 ;i<value.length;i++){
			is[i] = getDouble(value[i]);
		}
		return is;
	}
	public static double[] getDoubleArrayOfBasic(double[] value){
		if(value==null){
			return null;
		}
		double[] is = new double[value.length];
		for(int i = 0 ;i<value.length;i++){
			is[i] = getDouble(value[i]);
		}
		return is;
	}
	public static double[] getDoubleArrayOfBasic(byte[] value){
		if(value==null){
			return null;
		}
		double[] is = new double[value.length];
		for(int i = 0 ;i<value.length;i++){
			is[i] = getDouble(value[i]);
		}
		return is;
	}
	public static double[] getDoubleArrayOfBasic(boolean[] value){
		if(value==null){
			return null;
		}
		double[] is = new double[value.length];
		for(int i = 0 ;i<value.length;i++){
			is[i] = getDouble(value[i]);
		}
		return is;
	}
	public static double[] getDoubleArrayOfBasic(char[] value){
		if(value==null){
			return null;
		}
		double[] is = new double[value.length];
		for(int i = 0 ;i<value.length;i++){
			is[i] = getDouble(value[i]);
		}
		return is;
	}
	public static double[] getDoubleArrayOfBasic(String[] value){
		if(value==null){
			return null;
		}
		double[] is = new double[value.length];
		for(int i = 0 ;i<value.length;i++){
			is[i] = getDouble(value[i]);
		}
		return is;
	}
	public static byte[] getByteArrayOfBasic(Collection value){
		if(value==null){
			return null;
		}
		byte[] values = new byte[value.size()];
		Iterator iterator = value.iterator();
		for(int i = 0 ;i<value.size();i++){
			values[i] = getByte(iterator.next());
		}
		return values;
	}
	public static byte[] getByteArrayOfBasic(Object value)throws Exception{
		if(value==null){
			return null;
		}
		if(isArrayOfList(value.getClass())){
			return getByteArrayOfBasic((Collection)value);
		}else{
			Class elementType = getArrayOfBasicElementType(value.getClass());
			if(isInt(elementType)){
				return getByteArrayOfBasic((int[])value);
			}
			if(isShort(elementType)){
				return getByteArrayOfBasic((short[])value);
			}
			if(isFloat(elementType)){
				return getByteArrayOfBasic((float[])value);
			}
			if(isDouble(elementType)){
				return getByteArrayOfBasic((double[])value);
			}
			if(isByte(elementType)){
				return getByteArrayOfBasic((byte[])value);
			}
			if(isBoolean(elementType)){
				return getByteArrayOfBasic((boolean[])value);
			}
			if(isLong(elementType)){
				return getByteArrayOfBasic((long[])value);
			}
			if(isCharacter(elementType)){
				return getByteArrayOfBasic((char[])value);
			}
			if(isString(elementType)){
				return getByteArrayOfBasic((String[])value);
			}
			return getByteArrayOfBasic((Object[]) value);
		}
	}
	public static byte[] getByteArrayOfBasic(int[] value){
		if(value==null){
			return null;
		}
		byte[] is = new byte[value.length];
		for(int i = 0 ;i<value.length;i++){
			is[i] = getByte(value[i]);
		}
		return is;
	}
	public static byte[] getByteArrayOfBasic(Object[] value){
		if(value==null){
			return null;
		}
		byte[] is = new byte[value.length];
		for(int i = 0 ;i<value.length;i++){
			is[i] = getByte(value[i]);
		}
		return is;
	}
	public static byte[] getByteArrayOfBasic(short[] value){
		byte[] is = new byte[value.length];
		for(int i = 0 ;i<value.length;i++){
			is[i] = getByte(value[i]);
		}
		return is;
	}
	public static byte[] getByteArrayOfBasic(long[] value){
		byte[] is = new byte[value.length];
		for(int i = 0 ;i<value.length;i++){
			is[i] = getByte(value[i]);
		}
		return is;
	}
	public static byte[] getByteArrayOfBasic(float[] value){
		byte[] is = new byte[value.length];
		for(int i = 0 ;i<value.length;i++){
			is[i] = getByte(value[i]);
		}
		return is;
	}
	public static byte[] getByteArrayOfBasic(double[] value){
		if(value==null){
			return null;
		}
		byte[] is = new byte[value.length];
		for(int i = 0 ;i<value.length;i++){
			is[i] = getByte(value[i]);
		}
		return is;
	}
	public static byte[] getByteArrayOfBasic(byte[] value){
		if(value==null){
			return null;
		}
		byte[] is = new byte[value.length];
		for(int i = 0 ;i<value.length;i++){
			is[i] = getByte(value[i]);
		}
		return is;
	}
	public static byte[] getByteArrayOfBasic(boolean[] value){
		if(value==null){
			return null;
		}
		byte[] is = new byte[value.length];
		for(int i = 0 ;i<value.length;i++){
			is[i] = getByte(value[i]);
		}
		return is;
	}
	public static byte[] getByteArrayOfBasic(char[] value){
		if(value==null){
			return null;
		}
		byte[] is = new byte[value.length];
		for(int i = 0 ;i<value.length;i++){
			is[i] = getByte(value[i]);
		}
		return is;
	}
	public static byte[] getByteArrayOfBasic(String[] value){
		if(value==null){
			return null;
		}
		byte[] is = new byte[value.length];
		for(int i = 0 ;i<value.length;i++){
			is[i] = getByte(value[i]);
		}
		return is;
	}

	public static char[] getCharArrayOfBasic(Collection value){
		if(value==null){
			return null;
		}
		char[] values = new char[value.size()];
		Iterator iterator = value.iterator();
		for(int i = 0 ;i<value.size();i++){
			values[i] = getCharacter(iterator.next());
		}
		return values;
	}
	public static char[] getCharArrayOfBasic(Object value)throws Exception{
		if(value==null){
			return null;
		}
		if(isArrayOfList(value.getClass())){
			return getCharArrayOfBasic((Collection)value);
		}else{
			Class elementType = getArrayOfBasicElementType(value.getClass());
			if(isInt(elementType)){
				return getCharArrayOfBasic((int[])value);
			}
			if(isShort(elementType)){
				return getCharArrayOfBasic((short[])value);
			}
			if(isFloat(elementType)){
				return getCharArrayOfBasic((float[])value);
			}
			if(isDouble(elementType)){
				return getCharArrayOfBasic((double[])value);
			}
			if(isByte(elementType)){
				return getCharArrayOfBasic((byte[])value);
			}
			if(isBoolean(elementType)){
				return getCharArrayOfBasic((boolean[])value);
			}
			if(isLong(elementType)){
				return getCharArrayOfBasic((long[])value);
			}
			if(isCharacter(elementType)){
				return getCharArrayOfBasic((char[])value);
			}
			if(isString(elementType)){
				return getCharArrayOfBasic((String[])value);
			}
			return getCharArrayOfBasic((Object[]) value);
		}
	}
	public static char[] getCharArrayOfBasic(short[] value){
		if(value==null){
			return null;
		}
		char[] is = new char[value.length];
		for(int i = 0 ;i<value.length;i++){
			is[i] = getCharacter(value[i]);
		}
		return is;
	}
	public static char[] getCharArrayOfBasic(Object[] value){
		if(value==null){
			return null;
		}
		char[] is = new char[value.length];
		for(int i = 0 ;i<value.length;i++){
			is[i] = getCharacter(value[i]);
		}
		return is;
	}
	public static char[] getCharArrayOfBasic(long[] value){
		if(value==null){
			return null;
		}
		char[] is = new char[value.length];
		for(int i = 0 ;i<value.length;i++){
			is[i] = getCharacter(value[i]);
		}
		return is;
	}
	public static char[] getCharArrayOfBasic(float[] value){
		if(value==null){
			return null;
		}
		char[] is = new char[value.length];
		for(int i = 0 ;i<value.length;i++){
			is[i] = getCharacter(value[i]);
		}
		return is;
	}
	public static char[] getCharArrayOfBasic(double[] value){
		if(value==null){
			return null;
		}
		char[] is = new char[value.length];
		for(int i = 0 ;i<value.length;i++){
			is[i] = getCharacter(value[i]);
		}
		return is;
	}
	public static char[] getCharArrayOfBasic(byte[] value){
		if(value==null){
			return null;
		}
		char[] is = new char[value.length];
		for(int i = 0 ;i<value.length;i++){
			is[i] = getCharacter(value[i]);
		}
		return is;
	}
	public static char[] getCharArrayOfBasic(boolean[] value){
		if(value==null){
			return null;
		}
		char[] is = new char[value.length];
		for(int i = 0 ;i<value.length;i++){
			is[i] = getCharacter(value[i]);
		}
		return is;
	}
	public static char[] getCharArrayOfBasic(char[] value){
		if(value==null){
			return null;
		}
		char[] is = new char[value.length];
		for(int i = 0 ;i<value.length;i++){
			is[i] = getCharacter(value[i]);
		}
		return is;
	}
	public static char[] getCharArrayOfBasic(String[] value){
		if(value==null){
			return null;
		}
		char[] is = new char[value.length];
		for(int i = 0 ;i<value.length;i++){
			is[i] = getCharacter(value[i]);
		}
		return is;
	}
	public static char[] getCharArrayOfBasic(int[] value){
		if(value==null){
			return null;
		}
		char[] is = new char[value.length];
		for(int i = 0 ;i<value.length;i++){
			is[i] = getCharacter(value[i]);
		}
		return is;
	}
	public static boolean[] getBooleanArrayOfBasic(Collection value){
		if(value==null){
			return null;
		}
		boolean[] values = new boolean[value.size()];
		Iterator iterator = value.iterator();
		for(int i = 0 ;i<value.size();i++){
			values[i] = getBoolean(iterator.next());
		}
		return values;
	}
	public static boolean[] getBooleanArrayOfBasic(Object value)throws Exception{
		if(value==null){
			return null;
		}
		if(isArrayOfList(value.getClass())){
			return getBooleanArrayOfBasic((Collection)value);
		}else{
			Class elementType = getArrayOfBasicElementType(value.getClass());
			if(isInt(elementType)){
				return getBooleanArrayOfBasic((int[])value);
			}
			if(isShort(elementType)){
				return getBooleanArrayOfBasic((short[])value);
			}
			if(isFloat(elementType)){
				return getBooleanArrayOfBasic((float[])value);
			}
			if(isDouble(elementType)){
				return getBooleanArrayOfBasic((double[])value);
			}
			if(isByte(elementType)){
				return getBooleanArrayOfBasic((byte[])value);
			}
			if(isBoolean(elementType)){
				return getBooleanArrayOfBasic((boolean[])value);
			}
			if(isLong(elementType)){
				return getBooleanArrayOfBasic((long[])value);
			}
			if(isCharacter(elementType)){
				return getBooleanArrayOfBasic((char[])value);
			}
			if(isString(elementType)){
				return getBooleanArrayOfBasic((String[])value);
			}
			return getBooleanArrayOfBasic((Object[]) value);
		}
	}
	public static boolean[] getBooleanArrayOfBasic(short[] value){
		if(value==null){
			return null;
		}
		boolean[] is = new boolean[value.length];
		for(int i = 0 ;i<value.length;i++){
			is[i] = getBoolean(value[i]);
		}
		return is;
	}
	public static boolean[] getBooleanArrayOfBasic(Object[] value){
		if(value==null){
			return null;
		}
		boolean[] is = new boolean[value.length];
		for(int i = 0 ;i<value.length;i++){
			is[i] = getBoolean(value[i]);
		}
		return is;
	}
	public static boolean[] getBooleanArrayOfBasic(long[] value){
		if(value==null){
			return null;
		}
		boolean[] is = new boolean[value.length];
		for(int i = 0 ;i<value.length;i++){
			is[i] = getBoolean(value[i]);
		}
		return is;
	}
	public static boolean[] getBooleanArrayOfBasic(float[] value){
		if(value==null){
			return null;
		}
		boolean[] is = new boolean[value.length];
		for(int i = 0 ;i<value.length;i++){
			is[i] = getBoolean(value[i]);
		}
		return is;
	}
	public static boolean[] getBooleanArrayOfBasic(double[] value){
		if(value==null){
			return null;
		}
		boolean[] is = new boolean[value.length];
		for(int i = 0 ;i<value.length;i++){
			is[i] = getBoolean(value[i]);
		}
		return is;
	}
	public static boolean[] getBooleanArrayOfBasic(byte[] value){
		if(value==null){
			return null;
		}
		boolean[] is = new boolean[value.length];
		for(int i = 0 ;i<value.length;i++){
			is[i] = getBoolean(value[i]);
		}
		return is;
	}
	public static boolean[] getBooleanArrayOfBasic(boolean[] value){
		if(value==null){
			return null;
		}
		boolean[] is = new boolean[value.length];
		for(int i = 0 ;i<value.length;i++){
			is[i] = getBoolean(value[i]);
		}
		return is;
	}
	public static boolean[] getBooleanArrayOfBasic(char[] value){
		if(value==null){
			return null;
		}
		boolean[] is = new boolean[value.length];
		for(int i = 0 ;i<value.length;i++){
			is[i] = getBoolean(value[i]);
		}
		return is;
	}
	public static boolean[] getBooleanArrayOfBasic(String[] value){
		if(value==null){
			return null;
		}
		boolean[] is = new boolean[value.length];
		for(int i = 0 ;i<value.length;i++){
			is[i] = getBoolean(value[i]);
		}
		return is;
	}
	public static boolean[] getBooleanArrayOfBasic(int[] value){
		if(value==null){
			return null;
		}
		boolean[] is = new boolean[value.length];
		for(int i = 0 ;i<value.length;i++){
			is[i] = getBoolean(value[i]);
		}
		return is;
	}
	public static <T>Collection<T> getArrayOfList(int[] value , Class<T> entryType , Class listType)throws Exception{
		if(value==null){
			return null;
		}
		Collection collection = null;
		if(listType.getModifiers()==1){
			collection = (Collection)listType.newInstance();
		}else{
			if(Set.class.isAssignableFrom(listType)){
				collection = new HashSet();
			}else{
				collection = new ArrayList();
			}
		}
		
		for(int i = 0 ;i<value.length;i++){
			collection.add(getValue(value[i], entryType));
		}
		return collection;
	}
	public static <T>Collection<T> getArrayOfListByArrayOfBasicType(Object value , Class<T> entryType , Class listType)throws Exception{
		if(value==null){
			return null;
		}
		if(!isArrayOfBasic(value.getClass())){
			return null;
		}else{
			Class elementType = getArrayOfBasicElementType(value.getClass());
			if(isBoolean(elementType)){
				return getArrayOfList((boolean[])value, entryType, listType);
			}else if (isByte(elementType)) {
				return getArrayOfList((byte[])value, entryType, listType);
			}else if (isCharacter(elementType)) {
				return getArrayOfList((char[])value, entryType, listType);
			}else if (isDouble(elementType)) {
				return getArrayOfList((double[])value , elementType , listType);
			}else if (isFloat(elementType)) {
				return getArrayOfList((float[])value , elementType , listType);
			}else if (isInt(elementType)) {
				return getArrayOfList((int[])value , elementType , listType);
			}else if (isLong(elementType)) {
				return getArrayOfList((long[])value , elementType , listType);
			}else if (isShort(elementType)) {
				return getArrayOfList((short[])value , elementType , listType);
			}else if (isString(elementType)) {
				return getArrayOfList((String[])value , elementType , listType);
			}else{
				return getArrayOfList((Object[])value , elementType , listType);
			}
		}
	}
	public static <T>Collection<T> getArrayOfList(short[] value , Class<T> entryType , Class listType)throws Exception{
		if(value==null){
			return null;
		}
		Collection collection = null;
		if(listType.getModifiers()==1){
			collection = (Collection)listType.newInstance();
		}else{
			if(Set.class.isAssignableFrom(listType)){
				collection = new HashSet();
			}else{
				collection = new ArrayList();
			}
		}
		for(int i = 0 ;i<value.length;i++){
			collection.add(getValue(value[i], entryType));
		}
		return collection;
	}
	public static <T>Collection<T> getArrayOfList(long[] value , Class<T> entryType , Class listType)throws Exception{
		if(value==null){
			return null;
		}
		Collection collection = null;
		if(listType.getModifiers()==1){
			collection = (Collection)listType.newInstance();
		}else{
			if(Set.class.isAssignableFrom(listType)){
				collection = new HashSet();
			}else{
				collection = new ArrayList();
			}
		}
		for(int i = 0 ;i<value.length;i++){
			collection.add(getValue(value[i], entryType));
		}
		return collection;
	}
	public static <T>Collection<T> getArrayOfList(float[] value , Class<T> entryType , Class listType)throws Exception{
		if(value==null){
			return null;
		}
		Collection collection = null;
		if(listType.getModifiers()==1){
			collection = (Collection)listType.newInstance();
		}else{
			if(Set.class.isAssignableFrom(listType)){
				collection = new HashSet();
			}else{
				collection = new ArrayList();
			}
		}
		for(int i = 0 ;i<value.length;i++){
			collection.add(getValue(value[i], entryType));
		}
		return collection;
	}
	public static <T>Collection<T> getArrayOfList(double[] value , Class<T> entryType , Class listType)throws Exception{
		if(value==null){
			return null;
		}
		Collection collection = null;
		if(listType.getModifiers()==1){
			collection = (Collection)listType.newInstance();
		}else{
			if(Set.class.isAssignableFrom(listType)){
				collection = new HashSet();
			}else{
				collection = new ArrayList();
			}
		}
		for(int i = 0 ;i<value.length;i++){
			collection.add(getValue(value[i], entryType));
		}
		return collection;
	}
	public static <T>Collection<T> getArrayOfList(char[] value , Class<T> entryType ,Class listType)throws Exception{
		if(value==null){
			return null;
		}
		Collection collection = null;
		if(listType.getModifiers()==1){
			collection = (Collection)listType.newInstance();
		}else{
			if(Set.class.isAssignableFrom(listType)){
				collection = new HashSet();
			}else{
				collection = new ArrayList();
			}
		}
		for(int i = 0 ;i<value.length;i++){
			collection.add(getValue(value[i], entryType));
		}
		return collection;
	}
	public static <T>Collection<T> getArrayOfList(byte[] value , Class<T> entryType , Class listType)throws Exception{
		if(value==null){
			return null;
		}
		Collection collection = null;
		if(listType.getModifiers()==1){
			collection = (Collection)listType.newInstance();
		}else{
			if(Set.class.isAssignableFrom(listType)){
				collection = new HashSet();
			}else{
				collection = new ArrayList();
			}
		}
		for(int i = 0 ;i<value.length;i++){
			collection.add(getValue(value[i], entryType));
		}
		return collection;
	}
	public static <T>Collection<T> getArrayOfList(boolean[] value , Class<T> entryType , Class listType)throws Exception{
		if(value==null){
			return null;
		}
		Collection collection = null;
		if(listType.getModifiers()==1){
			collection = (Collection)listType.newInstance();
		}else{
			if(Set.class.isAssignableFrom(listType)){
				collection = new HashSet();
			}else{
				collection = new ArrayList();
			}
		}
		for(int i = 0 ;i<value.length;i++){
			collection.add(getValue(value[i], entryType));
		}
		return collection;
	}
	public static <T>Collection<T> getArrayOfList(Object[] value , Class<T> entryType , Class listType)throws Exception{
		if(value==null){
			return null;
		}
		Collection collection = null;
		if(listType.getModifiers()==1){
			collection = (Collection)listType.newInstance();
		}else{
			if(Set.class.isAssignableFrom(listType)){
				collection = new HashSet();
			}else{
				collection = new ArrayList();
			}
		}
		for(int i = 0 ;i<value.length;i++){
			collection.add(getValue(value[i], entryType));
		}
		return collection;
	}
	public static Map newMapObject(Class clazz)throws Exception{
		if(clazz.getModifiers()==1){
			return (Map)clazz.newInstance();
		}else{
			if(AbstractMap.class.isAssignableFrom(clazz)){
				return new HashMap();
			}else{
				return new TreeMap();
			}
		}
	}
	public static Collection newListObject(Class<? extends Collection> clazz)throws Exception{
		Collection collection = null;
		if(clazz.getModifiers()==1){
			collection = (Collection)clazz.newInstance();
		}else{
			if(List.class.isAssignableFrom(clazz)){
				collection = new ArrayList();
			}else if (Set.class.isAssignableFrom(clazz)) {
				collection = new HashSet();
			}else{
				collection = new ArrayList();
			}
		}
		return collection;
	}
}
