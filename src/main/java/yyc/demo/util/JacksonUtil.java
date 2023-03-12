package yyc.demo.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.catalina.core.ApplicationContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.GenericTypeResolver;
import org.springframework.lang.Nullable;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import javax.annotation.Resource;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class JacksonUtil implements InitializingBean {
    private static ObjectMapper om = new ObjectMapper();
    @Resource(name = "baseObjectMapper")
    private ObjectMapper mapper;
    @Nullable
    private ApplicationContext applicationContext;

    public JacksonUtil() {

    }

    public void afterPropertiesSet() {
        om = this.mapper;
    }

    public static ObjectMapper getObjectMapper() {
        return om;
    }


    public static <T> String toJson(T obj) {
        String json = null;
        if (obj != null) {
            try {
                json = om.writeValueAsString(obj);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                throw new IllegalArgumentException(e.getMessage());
            }
        }
        return json;
    }

    public static <T> T parse(String json, Class<T> clazz) {
        return parse(json, clazz, (TypeReference<T>) null);

    }

    public static <T> T parse(String json, JavaType type) throws IOException{
        return om.readValue(json,type);

    }


    public static <T> T parse(String json, TypeReference<T> type) {
        return parse(json, (Class<T>) null, type);

    }

    public static <T> T parse(String json, Class<T> clazz, TypeReference<T> type) {
        T obj = null;
        if (!StringUtils.isEmpty(json)) {
            try {
                if (clazz != null) {
                    obj = om.readValue(json, clazz);
                } else {
                    obj = om.readValue(json, type);
                }
            } catch (IOException vat5) {
                vat5.printStackTrace();
                throw new IllegalArgumentException(vat5.getMessage());
            }
        }
        return obj;
    }

    public static <T> T decoderValue(byte[] body, Class<T> clazz) {
        T obj = null;
        if (body == null) {
            return obj;
        } else {
            try {
                obj = om.readValue(body, clazz);
                return obj;
            } catch (IOException var4) {
                var4.printStackTrace();
                throw new IllegalArgumentException(var4.getMessage());
            }
        }
    }

    public static byte[] encoderBytes(Object value) {
        byte[] bytes = new byte[1024];
        if (value == null) {
            return bytes;
        } else {
            try {
                bytes = om.writeValueAsBytes(value);
                return bytes;
            } catch (IOException var3) {
                var3.printStackTrace();
                throw new IllegalArgumentException(var3.getMessage());
            }
        }
    }

    public static Map<String, Object> parseMap(String jsonStr) throws IOException {
        Map obj = new LinkedHashMap();
        if (StringUtils.isEmpty(jsonStr)) {
            return obj;
        } else {
            try {
                jsonStr = jsonStr.trim();
                return (Map) om.readValue(jsonStr, LinkedHashMap.class);

            } catch (Exception var3) {
                throw var3;
            }
        }
    }

    public static Map<String, Object> parseHashMap(String jsonStr) throws IOException {
        Map obj = new HashMap();
        if (StringUtils.isEmpty(jsonStr)) {
            return obj;
        } else {
            try {
                jsonStr = jsonStr.trim();
                return (Map) om.readValue(jsonStr, HashMap.class);
            } catch (Exception var3) {
                throw var3;
            }
        }
    }


    public static JSONObject parseStrAsJSONbject(String jsonStr) {
        if (StringUtils.isEmpty(jsonStr)) {
            return new JSONObject();
        } else {
            try {
                jsonStr = jsonStr.trim();
                return new JSONObject(jsonStr);
            } catch (Exception var2) {
                var2.printStackTrace();
                ;
                return new JSONObject();
            }
        }
    }


    public static JSONObject newParseStrAsJSONObj(String jsonStr) throws Exception {
        if (StringUtils.isEmpty(jsonStr)) {
            return new JSONObject();
        } else {
            try {
                jsonStr = jsonStr.trim();
                return new JSONObject(jsonStr);
            } catch (Exception var2) {
                throw var2;
            }
        }
    }

    public static boolean checkSupportJsonObject(String jsonStr) {
        boolean result = Boolean.FALSE;
        if (StringUtils.isNoneBlank(jsonStr)) {
            jsonStr = jsonStr.trim();
            JsonNode reqNode = parseStrAsJsonNode(jsonStr);
            if (reqNode.elements().hasNext() && reqNode.isObject()) {
                result = Boolean.TRUE;
            } else {
                result = Boolean.FALSE;
            }
        }
        return result;
    }

    public static JsonNode parseStrAsJsonNode(String jsonStr) {
        if (StringUtils.isEmpty(jsonStr)) {
            return new TextNode("");
        } else {
            jsonStr = jsonStr.trim();
            Object node = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true), new HashMap<>());
            try {
                node = om.readTree(jsonStr);
            } catch (Exception var3) {
                var3.printStackTrace();
            }
            return (JsonNode) node;
        }
    }

    public static JavaType getJavaType(Type type, Class<?> contextClass) {
        TypeFactory typeFactory = om.getTypeFactory();
        return typeFactory.constructType(GenericTypeResolver.resolveType(type, contextClass));
    }


    static {
        om.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        om.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        om.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        om.registerModule(new JavaTimeModule());
        om.enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS);
        om.enable(new JsonGenerator.Feature[]{JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN});
    }

}








