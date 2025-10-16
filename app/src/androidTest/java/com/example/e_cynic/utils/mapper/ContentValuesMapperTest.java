package com.example.e_cynic.utils.mapper;

import android.content.ContentValues;

import com.example.e_cynic.entityTests.TestEntity;
import com.example.e_cynic.utils.LoggingUtil;

import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ContentValuesMapperTest {
    private String string = "this is a string";
    private Float flt = 12.345f;
    private Integer integer = 123;
    private Long lng = new Date().getTime();
    private Boolean bool = true;
    private Double dbl = 12.4443434d;

    private TestEntity testEntity = new TestEntity(string, flt, integer, lng, bool, dbl);

    @Test
    public void mapFieldsToContentValues() throws IllegalAccessException, NoSuchMethodException {
        List<Field> fields = Arrays.asList(testEntity.getClass().getDeclaredFields());
        ContentValues cv = ContentValuesMapper.mapFieldsToContentValues(fields, testEntity);
        LoggingUtil.printMessage("map fields to content values", String.valueOf(cv));
    }
}