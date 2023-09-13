package com.zdpx.coder.graph;

public class OperatorSpecializationFieldConfig {

    private OperatorSpecializationFieldConfig() {
    }

    /**
     * Json源文件，高层级结构化信息
     * */
    //标记算子填写的参数信息，同级别的参数还有：config
    public static final String PARAMETERS = "parameters";

    //存放输入输出勾选情况的标记
    public static final String CONFIG = "config";

    //算子业务信息，将输出字段参数从中剥离
    public static final String SERVICE = "service";
    //算子输出参数，包括原始输出和映射输出
    public static final String OUTPUT = "output";
    //映射输出字段名称标记
    public static final String COLUMNS = "columns";
    //原始输出字段名称标记
    public static final String SOURCE = "source";

    /**
     * Json源文件，解析时使用的参数信息
     * */
    //特指算子id，算子校验中需要使用
    public static final String ID = "id";
    //标记用户输入的表名称，可为null或""
    public static final String TABLE_NAME = "tableName";

    /**
     * 不属于Json源文件中的，解析时使用的自定义参数
     * */
    //当表名称未指定时，使用该字段作为默认表名
    public static final String TABLE_NAME_DEFAULT = "tableNameDefault";
    //用于传递tableInfo中的信息
    public static final String TABLE_INFO = "tableInfo";

    //hints动态表功能增强，出现在window算子中
    public static final String HINTS_OPTIONS = "options";
    //hints动态表功能增强，出现在join算子中,最外层的字段
    public static final String HINTS = "hints";
    //hints动态表功能增强，出现在join算子中,指定连接方式
    public static final String JOIN_HINTS = "joinHints";
    //hints动态表功能增强，出现在join算子中,参与连接的列
    public static final String JOIN_COLUMN = "joinColumn";

    /**
     * 输入输出
     * */
    //单输入算子，默认输入连接桩名称
    public static final String INPUT_0 = "input_0";
    //单输出算子，默认输出连接桩名称
    public static final String OUTPUT_0 = "output_0";

    //多输入算子，首个输入连接桩名称
    public static final String PRIMARY_INPUT = "primaryInput";
    //多输入算子，第二个输入连接桩名称
    public static final String SECOND_INPUT = "secondInput";

    /**
     * 算子校验时使用到的参数标记
     * */
    //算子通过校验
    public static final String GREEN = "green";
    //校验时出现异常信息
    public static final String RED = "red";

    /**
     * 数据源中参数的特殊标记 和 columns中包含的参数名称
     * */
    //源算子中直接展示字段名称，中间算子中展示嵌套过后的函数
    public static final String NAME = "name";
    //中间算子别名
    public static final String OUT_NAME = "outName";
    //字段类型
    public static final String TYPE = "type";

    //给一个字段添加事件时间语义
    public static final String WATERMARK = "watermark";
    //标记事件时间的列
    public static final String COLUMN = "column";
    //时间长度。出现在两个位置： 1、watermark中，指事件时间字段的最大乱序时间  2、CEP算子中，指事件匹配时的最大跨度
    public static final String TIME_SPAN = "timeSpan";
    //时间单位。同上
    public static final String TIME_UNIT = "timeUnit"; //时间跨度单位

    //设置主键
    public static final String PRIMARY = "primary";
    //自定义数据源中的参数名称，包含key和value
    public static final String OTHER = "other";
    //自定义数据源中自定义参数的键
    public static final String KEY = "key";
    //自定义数据源中自定义参数的值
    public static final String VALUES = "values";

    //函数嵌套功能实现，该字段为最外层字段
    public static final String FUNCTION = "function";
    //函数嵌套功能实现，嵌套时定义的函数名称
    public static final String FUNCTION_NAME = "functionName";
    //函数嵌套功能实现，该字段不为空表示嵌套了函数
    public static final String RECURSION_FUNC = "recursionFunc";
    //函数嵌套功能实现，该字段不为空表示嵌套了字段
    public static final String RECURSION_NAME = "recursionName";


    /**
     * 函数字段名常量
     */
    public static final String WHERE = "where";
    public static final String GROUP = "group";
    public static final String ORDER_BY = "orderBy";
    public static final String WINDOW = "window";

    public static final String SIZE = "size";
    public static final String SLIDE = "slide";
    public static final String STEP = "step";
    public static final String LIMIT = "limit";

    public static final String JOIN_TYPE = "joinType";
    public static final String PARTITION = "partition";
    public static final String INPUT_TABLE_NAME = "inputTableName";
    public static final String OUTPUT_TABLE_NAME = "outputTableName";
    public static final String ANOTHER_TABLE_NAME = "anotherTableName";

    public static final String DEFINES = "defines";
    public static final String PATTERNS = "patterns";
    public static final String SKIP_STRATEGY = "skipStrategy";
    public static final String CEP = "CEP";
    public static final String OUT_PUT_MODE = "outPutMode";//输出规则

    public static final String SYSTEM_TIME_COLUMN = "systemTimeColumn";

}
