package com.hb.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.hb.sensitive.Sensitive;
import com.hb.sensitive.SensitiveStrategy;

import java.io.IOException;
import java.util.Objects;

/**
 * @author zhaochengshui
 * @description 自定义脱敏注解
 * @date 2022/9/30
 */
public class SensitiveJsonSerializer extends JsonSerializer<String> implements ContextualSerializer {
    private SensitiveStrategy strategy;

    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        //strategy.desensitizer() 返一个Function
        // Function.apply(value) 执行枚举里面定义的脱敏方法
        gen.writeString(strategy.desensitize().apply(value));
    }

    /**
     * 返回一个JsonSerializer对象，这边返回的是this
     *
     * @param prov     Serializer provider to use for accessing config, other serializers
     * @param property 被标记字段，可以获取其属性，注解
     * @return
     * @throws JsonMappingException
     */
    @Override
    public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) throws JsonMappingException {
        Sensitive annotation = property.getAnnotation(Sensitive.class);
        // 不为空，且字段类型是String
        if (Objects.nonNull(annotation) && Objects.equals(String.class, property.getType().getRawClass())) {
            this.strategy = annotation.strategy();
            return this;
        }
        return prov.findValueSerializer(property.getType(), property);
    }
}

