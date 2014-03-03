package com.hstyle.framework.mapper.support;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * 转换日期型数据
 * @author Administrator
 *
 */
public class JsonDateSerializer extends JsonSerializer<Date>{ 

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 

    @Override 
    public void serialize(Date date, JsonGenerator gen, SerializerProvider provider) 
            throws IOException, JsonProcessingException { 

        String formattedDate = dateFormat.format(date); 

        gen.writeString(formattedDate); 
    } 

}
