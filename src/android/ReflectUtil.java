package org.apache.cordova.it200;

import android.annotation.SuppressLint;
import android.util.ArrayMap;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ReflectUtil {
    public static final String FULL_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_PATTERN_MINUTE = "yyyy-MM-dd HH:mm";
    public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";

    static final String[] tryParttern = new String[]{FULL_DATE_PATTERN, DATE_PATTERN_MINUTE, DEFAULT_DATE_PATTERN};

    public static Object newInstance(String className, Object... params) {
        try {
            Class<?> cls = Class.forName(className);
            return newInstance(cls, params);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public static Object newInstance(Class<?> cls, Object... params) {
        Class<?>[] parameterTypes = null;
        if (params != null) {
            parameterTypes = new Class<?>[params.length];
            for (int i = 0; i < params.length; i++) {
                parameterTypes[i] = params[i].getClass();
            }
        }
        return newInstance(cls, parameterTypes, params);
    }

    /**
     * 实例化对象
     */
    public static Object newInstance(Class<?> cls, Class<?>[] parameterTypes, Object[] params) {
        Object result = null;
        try {
            Constructor<?> constructor = getConstructor(cls, parameterTypes);
            result = constructor.newInstance(params);
        } catch (Exception e) {
            System.out.println(e);
        }
        return result;
    }

    /**
     * 获取Constructor
     */
    public static Constructor<?> getConstructor(Class<?> cls, Class<?>[] parameterTypes) throws Exception {
        Constructor<?> constructor = null;
        try {
            constructor = cls.getDeclaredConstructor(parameterTypes);
        } catch (Exception e) {
            constructor = cls.getConstructor(parameterTypes);
        }
        return constructor;
    }

    public static Object invokeMethod(Object obj, String methodName, Object... params) {
        Class<?>[] parameterTypes = null;
        if (params != null) {
            parameterTypes = new Class<?>[params.length];
            for (int i = 0; i < params.length; i++) {
                parameterTypes[i] = params[i].getClass();
            }
        }
        return invokeMethod(obj, methodName, parameterTypes, params);
    }

    /**
     * 反射调用方法
     */
    public static Object invokeMethod(Object obj, String methodName, Class<?>[] parameterTypes, Object[] params) {
        Class<?> cls = null;
        if (obj instanceof Class<?>) {
            cls = (Class<?>) obj;
        } else {
            cls = obj.getClass();
        }
        Object result = null;
        try {
            Method method = getMethod(cls, methodName, parameterTypes);
            method.setAccessible(true);
            result = method.invoke(obj, params);
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("invoke method:" + methodName + "failed");
        }
        return result;
    }

    /**
     * 获取Method
     */
    public static Method getMethod(Class<?> cls, String methodName, Class<?>[] parameterTypes) throws Exception {
        Method method = null;
        try {
            method = cls.getDeclaredMethod(methodName, parameterTypes);
        } catch (Exception e) {
            method = cls.getMethod(methodName, parameterTypes);
        }
        return method;
    }

    /**
     * 获取class对应所有字段
     */
    public static Field[] getFields(Class<?> cls) {
        Field[] result = cls.getDeclaredFields();
        if (result != null) {
            return result;
        }
        return new Field[]{};
    }

    /**
     * 获取field属性值
     */
    public static Object getFieldValue(Object target, Field field) {
        if (!field.isAccessible()) {
            field.setAccessible(true);
        }
        try {
            return field.get(target);
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    /**
     * 获取field属性值
     */
    public static Object getFieldValue(Object target, String fieldName) {
        try {
            Field field = target.getClass().getDeclaredField(fieldName);
            return getFieldValue(target, field);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    /**
     * 设置field属性值
     */
    public static boolean setFieldValue(Object target, Field field, Object value) {
        if (!field.isAccessible()) {
            field.setAccessible(true);
        }
        try {
            Object result = null;
            if (value != null) {
                Class<?> type = field.getType();
                if (type.equals(value.getClass()) || type.isAssignableFrom(value.getClass())) {
                    result = value;
                } else {
                    if (String.class.equals(type)) {
                        result = String.valueOf(value);
                    } else if (Integer.class.equals(type) || int.class.equals(type)) {
                        result = Double.valueOf(value + "").intValue();
                    } else if (Double.class.equals(type) || double.class.equals(type)) {
                        result = Double.valueOf(value + "");
                    } else if (Float.class.equals(type) || float.class.equals(type)) {
                        result = Double.valueOf(value + "").floatValue();
                    } else if (Long.class.equals(type) || long.class.equals(type)) {
                        result = Double.valueOf(value + "").longValue();
                    } else if (Date.class.equals(type)) {
                        result = getDate(value + "");
                    } else if (BigDecimal.class.equals(type)) {
                        result = new BigDecimal(value + "");
                    }
                }
            }
            field.set(target, result);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    /**
     * String转Date，尝试tryParttern中的各种格式
     */
    public static Date getDate(String dateStr) {
        Date date = null;
        SimpleDateFormat sf = new SimpleDateFormat();
        for (int i = 0; i < tryParttern.length; i++) {
            sf.applyPattern(tryParttern[i]);
            try {
                date = sf.parse(dateStr);
            } catch (Exception e) {

            }
            if (date != null) {
                return date;
            }
        }
        if (date == null) {
            System.out.println("trans date failed, dateStr : " + dateStr);
        }
        return date;
    }

    /**
     * map 映射到 Object，对象必须有默认无参构造,对象中不支持map字段
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T mapToObject(Class<T> cls, Map<?, ?> map) {
        T obj = (T) ReflectUtil.newInstance(cls);
        if (obj != null) {
            Field[] fields = getFields(cls);
            for (Field f : fields) {
                if (isSkip(f)) {
                    continue;
                }
                f.setAccessible(true);
                String name = f.getName();
                Object value = map.get(name);
                if (value != null) {
                    if (isBaseType(f)) {// 基础类型
                        setFieldValue(obj, f, value);
                    } else if (f.getType().isAssignableFrom(value.getClass())) {// value与字段类型一致
                        // List集合,必须有泛型
                        if (List.class.isAssignableFrom(f.getType())) {
                            List<Object> list = (List<Object>) value;
                            transMapListToObjList(list, f);
                        }
                        setFieldValue(obj, f, value);
                    } else if (Map.class.isAssignableFrom(value.getClass())) {// value为map类型，需要进行映射
                        setFieldValue(obj, f, mapToObject(f.getType(), (Map<?, ?>) value));
                    }
                }
            }
        }
        return obj;
    }

    static void transMapListToObjList(List<Object> list, Field f) {
        Type fc = f.getGenericType();
        if (fc == null) {
            return;
        }
        Class<?> parameterizedType = getParameterizedType(fc);
        if (parameterizedType != null) {
            for (int i = 0; i < list.size(); i++) {
                Object obj = list.get(i);
                if (parameterizedType.isAssignableFrom(obj.getClass())) {
                    continue;
                } else if (Map.class.isAssignableFrom(obj.getClass())) {
                    list.set(i, mapToObject(parameterizedType, (Map<?, ?>) obj));
                }
            }
        }
    }

    public static Class<?> getParameterizedType(Type type) {
        if (type instanceof ParameterizedType) {
            try {
                Class<?> pType = (Class<?>) ((ParameterizedType) type).getActualTypeArguments()[0];
                return pType;
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        return null;
    }

    static boolean isSkip(Field f) {
        int modifiers = f.getModifiers();
        // 过滤final static transient修饰的字段
        if (Modifier.isFinal(modifiers) || Modifier.isStatic(modifiers) || Modifier.isTransient(modifiers)) {
            return true;
        }
        return false;
    }

    static boolean isBaseType(Field f) {
        Class<?> type = f.getType();
        if (type.isPrimitive()) {
            return true;
        }
        if (type.getName().startsWith("java.lang") || Date.class.equals(type) || BigDecimal.class.equals(type) || byte[].class.equals(type)
                || int[].class.equals(type) || int[].class.equals(type) || double[].class.equals(type)) {
            return true;
        }
        return false;
    }

    /**
     * Object 转 Map,支持对象嵌套，对象中不支持Map类型
     */
    @SuppressLint("NewApi")
    @SuppressWarnings("unchecked")
    public static Map<String, Object> objectToMap(Object obj) {
        ArrayMap<String, Object> map = new ArrayMap<String, Object>();
        Field[] fields = getFields(obj.getClass());
        for (Field f : fields) {
            if (isSkip(f)) {
                continue;
            }
            f.setAccessible(true);
            Object value = getFieldValue(obj, f);
            if (value != null) {
                if (isBaseType(f)) {
                    map.put(f.getName(), value);
                } else if (List.class.isAssignableFrom(f.getType())) {// List类型
                    List<Map<String, Object>> result = transListToMapList((List<Object>) value, f);
                    if (result != null && !result.isEmpty()) {
                        map.put(f.getName(), result);
                    }
                } else {
                    map.put(f.getName(), objectToMap(value));
                }
            }
        }
        return map;
    }

    static List<Map<String, Object>> transListToMapList(List<Object> list, Field f) {
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        Type fc = f.getGenericType();
        if (fc == null) {
            return null;
        }
        Class<?> parameterizedType = getParameterizedType(fc);
        if (parameterizedType != null) {
            for (int i = 0; i < list.size(); i++) {
                Object obj = list.get(i);
                if (parameterizedType.isAssignableFrom(obj.getClass())) {
                    result.add(objectToMap(obj));
                }
            }
        }
        return result;
    }
}
