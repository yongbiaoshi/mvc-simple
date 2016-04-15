package com.tsingda.simple.util;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.type.ArrayType;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class XmlUtil {
    public final static XmlMapper xmlMapper = new XmlMapper();
    public final static Logger logger = LoggerFactory.getLogger(XmlUtil.class);
    public final static TypeFactory typeFactory;

    static {
        xmlMapper.setLocale(Locale.CHINA);
        DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        xmlMapper.setDateFormat(dateFormat);
        // xmlMapper.enableDefaultTyping(DefaultTyping.NON_FINAL, As.PROPERTY);
        xmlMapper.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);// 允许使用非双引号属性名
        xmlMapper.configure(Feature.ALLOW_SINGLE_QUOTES, true);// 允许单引号包信属性名
        xmlMapper.configure(Feature.ALLOW_NUMERIC_LEADING_ZEROS, true);// 允许JSON整数以多个0开始
        xmlMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
        xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        xmlMapper.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, false);

        typeFactory = xmlMapper.getTypeFactory();
    }

    public static String stringify(Object obj) throws JsonProcessingException {
        return xmlMapper.writeValueAsString(obj);
    }

    public static <T> T parse(String xml, Class<T> clazz) throws JsonParseException, JsonMappingException, IOException {
        return xmlMapper.readValue(xml, clazz);
    }

    @SuppressWarnings("unchecked")
    public static <T> T parse(String xml, JavaType javaType) throws JsonParseException, JsonMappingException,
            IOException {
        return (T) xmlMapper.readValue(xml, javaType);
    }

    @SuppressWarnings("unchecked")
    public static <T> T parse(String xml, TypeReference<?> typeReference) throws JsonParseException,
            JsonMappingException, IOException {
        return (T) xmlMapper.readValue(xml, typeReference);
    }

    public static <T> T changeType(Object obj, Class<T> clazz) throws JsonParseException, JsonMappingException,
            IOException {
        return parse(stringify(obj), clazz);
    }

    public static ArrayType constructArrayType(Class<?> elementType) {
        return typeFactory.constructArrayType(elementType);
    }

    public static CollectionType constructCollectionType(
            @SuppressWarnings("rawtypes") Class<? extends Collection> collectionClass, Class<?> elementClass) {
        return typeFactory.constructCollectionType(collectionClass, elementClass);
    }

    public MapType constructMapType(@SuppressWarnings("rawtypes") Class<? extends Map> mapClass, Class<?> keyClass,
            Class<?> valueClass) {
        return typeFactory.constructMapType(mapClass, keyClass, valueClass);
    }

}
