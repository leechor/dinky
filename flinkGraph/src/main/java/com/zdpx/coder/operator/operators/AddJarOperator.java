package com.zdpx.coder.operator.operators;

import com.zdpx.coder.Specifications;
import com.zdpx.coder.graph.CheckInformationModel;
import com.zdpx.coder.operator.Operator;
import com.zdpx.coder.utils.TemplateUtils;
import lombok.SneakyThrows;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 引入jar或 udf
 */
public class AddJarOperator extends Operator {

    public static final String TEMPLATE =
            String.format(
                    "<#list jars as jar>ADD JAR '${jar}'<#sep>;</#sep><#rt></#list>"
                    ,
                    Specifications.TEMPLATE_FILE);

    @Override
    protected void initialize() {

    }

    @Override
    protected Map<String, String> declareUdfFunction() {
        return new HashMap<>();
    }

    @Override
    protected boolean applies() {
        return true;
    }

    @Override
    protected Map<String, Object> formatOperatorParameter() {
        return getFirstParameterMap();
    }

    /**
     * 校验内容：
     * jar的路径是否存在
     * 是否为一个jar
     */
    @Override
    protected void generateCheckInformation(Map<String, Object> map) {

        CheckInformationModel model = new CheckInformationModel();
        model.setOperatorId(map.get(ID).toString());
        model.setColor(GREEN);
        model.setTableName("ADD JAR");

        List<String> msg = new ArrayList<>();

        @SuppressWarnings("unchecked")
        List<String> jars = (List<String>) map.get("jars");
        for (String s : jars) {
            File file = new File(s);
            if (!file.exists()) {
                msg.add(s + " 不存在");
            }
            String[] split = s.split("\\.");
            if (!split[split.length - 1].equals("jar")) {
                msg.add(s + " 不是一个可添加jar");
            }
        }

        if(!msg.isEmpty()){
            model.setColor(RED);
            model.setOperatorErrorMsg(msg);
        }
        this.getSchemaUtil().getGenerateResult().addCheckInformation(model);
    }

    @Override
    protected void execute(Map<String, Object> map) {
        String sqlStr = TemplateUtils.format("AddJarOperator", map, TEMPLATE);
        this.getSchemaUtil().getGenerateResult().generate(sqlStr);
    }

    //动态加载Jar
    public static void loadJar(URL jarUrl) {
        //从URLClassLoader类加载器中获取类的addURL方法
        Method method = null;
        try {
            method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
        } catch (NoSuchMethodException | SecurityException e1) {
            e1.printStackTrace();
        }
        // 获取方法的访问权限
        boolean accessible = method.isAccessible();
        try {
            //修改访问权限为可写
            if (accessible == false) {
                method.setAccessible(true);
            }
            // 获取系统类加载器
            URLClassLoader classLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
            //jar路径加入到系统url路径里
            method.invoke(classLoader, jarUrl);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            method.setAccessible(accessible);
        }
    }
}
