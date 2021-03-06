package com.tsingda.simple.util;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
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
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.ArrayType;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.TypeFactory;

public class JsonUtil {
    public final static ObjectMapper objectMapper = new ObjectMapper();
    public final static Logger logger = LoggerFactory.getLogger(JsonUtil.class);
    public final static TypeFactory typeFactory;

    static {
        objectMapper.setLocale(Locale.CHINA);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        objectMapper.setDateFormat(dateFormat);
        objectMapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false);// 默认不显示无JsonView注解的字段
        // objectMapper.enableDefaultTyping(DefaultTyping.NON_FINAL,
        // As.EXISTING_PROPERTY);// 启用默认类型作为属性@class
        objectMapper.disableDefaultTyping();//禁用默认类型作为属性
        // objectMapper.enableDefaultTypingAsProperty(DefaultTyping.NON_FINAL, "@demoType");
        objectMapper.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);// 允许使用非双引号属性名
        objectMapper.configure(Feature.ALLOW_SINGLE_QUOTES, true);// 允许单引号包信属性名
        objectMapper.configure(Feature.ALLOW_NUMERIC_LEADING_ZEROS, true);// 允许JSON整数以多个0开始
        objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, false);
        
        // SimpleModule module = new SimpleModule();
        // module.addSerializer(String.class, new StringSerializer());
        // objectMapper.registerModule(module);
        
        typeFactory = objectMapper.getTypeFactory();
        

    }

    public static String stringify(Object obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }

    public static <T> T parse(String json, Class<T> clazz) throws JsonParseException, JsonMappingException, IOException {
        return objectMapper.readValue(json, clazz);
    }

    @SuppressWarnings("unchecked")
    public static <T> T parse(String json, JavaType javaType) throws JsonParseException, JsonMappingException,
            IOException {
        return (T) objectMapper.readValue(json, javaType);
    }

    @SuppressWarnings("unchecked")
    public static <T> T parse(String json, TypeReference<?> typeReference) throws JsonParseException,
            JsonMappingException, IOException {
        return (T) objectMapper.readValue(json, typeReference);
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
    
    public static void main(String[] args) throws IOException {

        
        List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
        Map<String, Object> m1 = new HashMap<String, Object>();
        m1.put("name", "n1");
        m1.put("age", 12);
        Map<String, Object> m2 = new HashMap<String, Object>();
        m2.put("name", "n1");
        m2.put("age", 12);
        list.add(m1);
        list.add(m2);
        
        JavaType type = constructCollectionType(List.class, Map.class);
        String ss = "[{\"name\":\"n1\",\"age\":12},{\"name\":\"n1\",\"age\":12}]";
        List<Map<String, Object>> l = parse(ss, type);
        for (Map<String, Object> map : l) {
            System.out.println(map.get("name"));
            System.out.println(map.get("age"));
        }
        
    }
}
