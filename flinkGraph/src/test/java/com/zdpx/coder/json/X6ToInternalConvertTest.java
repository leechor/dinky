package com.zdpx.coder.json;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zdpx.coder.json.preview.OperatorPreviewBuilder;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import com.zdpx.coder.SceneCodeBuilder;
import com.zdpx.coder.graph.Scene;
import com.zdpx.coder.json.x6.X6ToInternalConvert;

import java.util.*;

class X6ToInternalConvertTest {

//static String x6_json1 = "";

    static String x6_json1 = "{\n" +
            "    \"cells\": [\n" +
            "        {\n" +
            "            \"position\": {\n" +
            "                \"x\": 100,\n" +
            "                \"y\": 300\n" +
            "            },\n" +
            "            \"size\": {\n" +
            "                \"width\": 70,\n" +
            "                \"height\": 50\n" +
            "            },\n" +
            "            \"view\": \"react-shape-view\",\n" +
            "            \"shape\": \"MysqlSourceOperator\",\n" +
            "            \"ports\": {\n" +
            "                \"groups\": {\n" +
            "                    \"outputs\": {\n" +
            "                        \"zIndex\": 999,\n" +
            "                        \"position\": \"right\",\n" +
            "                        \"markup\": {\n" +
            "                            \"tagName\": \"path\",\n" +
            "                            \"selector\": \"path\",\n" +
            "                            \"attrs\": {\n" +
            "                                \"d\": \"m-6,0,a5,5.5 0 0 1 12,0\",\n" +
            "                                \"fill\": \"#b2a2e9\",\n" +
            "                                \"transform\": \"rotate(90)\",\n" +
            "                                \"strokeWidth\": 1,\n" +
            "                                \"stroke\": \"null\"\n" +
            "                            }\n" +
            "                        },\n" +
            "                        \"attrs\": {\n" +
            "                            \"path\": {\n" +
            "                                \"r\": 8,\n" +
            "                                \"magnet\": true,\n" +
            "                                \"style\": {\n" +
            "                                    \"visibility\": \"hidden\"\n" +
            "                                }\n" +
            "                            }\n" +
            "                        }\n" +
            "                    },\n" +
            "                    \"inputs\": {\n" +
            "                        \"zIndex\": 999,\n" +
            "                        \"position\": \"left\",\n" +
            "                        \"markup\": {\n" +
            "                            \"tagName\": \"path\",\n" +
            "                            \"selector\": \"path\",\n" +
            "                            \"attrs\": {\n" +
            "                                \"d\": \"m-6,0,a5,5.5 0 0 1 12,0\",\n" +
            "                                \"fill\": \"#b2a2e9\",\n" +
            "                                \"transform\": \"rotate(-90)\",\n" +
            "                                \"strokeWidth\": 1,\n" +
            "                                \"stroke\": \"null\"\n" +
            "                            }\n" +
            "                        },\n" +
            "                        \"attrs\": {\n" +
            "                            \"path\": {\n" +
            "                                \"r\": 10,\n" +
            "                                \"magnet\": true,\n" +
            "                                \"style\": {\n" +
            "                                    \"visibility\": \"hidden\"\n" +
            "                                }\n" +
            "                            }\n" +
            "                        }\n" +
            "                    }\n" +
            "                },\n" +
            "                \"items\": [\n" +
            "                    {\n" +
            "                        \"group\": \"outputs\",\n" +
            "                        \"zIndex\": 999,\n" +
            "                        \"id\": \"output_0\",\n" +
            "                        \"attrs\": {\n" +
            "                            \"text\": {\n" +
            "                                \"text\": \"output_0\",\n" +
            "                                \"style\": {\n" +
            "                                    \"visibility\": \"hidden\",\n" +
            "                                    \"fontSize\": 10,\n" +
            "                                    \"fill\": \"#3B4351\"\n" +
            "                                }\n" +
            "                            }\n" +
            "                        },\n" +
            "                        \"label\": {\n" +
            "                            \"position\": \"bottom\"\n" +
            "                        }\n" +
            "                    }\n" +
            "                ]\n" +
            "            },\n" +
            "            \"id\": \"0ce6bc11-1a69-4a5a-baa0-b062de9c5d4f\",\n" +
            "            \"name\": \"MySQL数据源\",\n" +
            "            \"isStencil\": false,\n" +
            "            \"zIndex\": 1,\n" +
            "            \"data\": {\n" +
            "                \"parameters\": {\n" +
            "                    \"tableName\": \"generate_result_record\",\n" +
            "                    \"connector\": \"jdbc\",\n" +
            "                    \"url\": \"jdbc:mysql://192.168.1.51:3306/dinky?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&useSSL=false&zeroDateTimeBehavior=convertToNull&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true\",\n" +
            "                    \"username\": \"root\",\n" +
            "                    \"password\": \"123456\",\n" +
            "                    \"other\": [],\n" +
            "                    \"columns\": [\n" +
            "                        {\n" +
            "                            \"name\": \"id\",\n" +
            "                            \"type\": \"INT\",\n" +
            "                            \"flag\": true\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"name\": \"mission_id\",\n" +
            "                            \"type\": \"STRING\",\n" +
            "                            \"flag\": true\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"name\": \"mission_name\",\n" +
            "                            \"type\": \"STRING\",\n" +
            "                            \"flag\": true\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"name\": \"scene_id\",\n" +
            "                            \"type\": \"STRING\",\n" +
            "                            \"flag\": true\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"name\": \"station_id\",\n" +
            "                            \"type\": \"STRING\",\n" +
            "                            \"flag\": true\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"name\": \"station_channel_id\",\n" +
            "                            \"type\": \"STRING\",\n" +
            "                            \"flag\": true\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"name\": \"monitor_missile_id\",\n" +
            "                            \"type\": \"STRING\",\n" +
            "                            \"flag\": true\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"name\": \"position_id\",\n" +
            "                            \"type\": \"STRING\",\n" +
            "                            \"flag\": true\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"name\": \"start_time\",\n" +
            "                            \"type\": \"TIMESTAMP\",\n" +
            "                            \"flag\": true\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"name\": \"end_time\",\n" +
            "                            \"type\": \"TIMESTAMP\",\n" +
            "                            \"flag\": true\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"name\": \"monitor_segment\",\n" +
            "                            \"type\": \"STRING\",\n" +
            "                            \"flag\": true\n" +
            "                        }\n" +
            "                    ],\n" +
            "                    \"primary\": \"\",\n" +
            "                    \"watermark\": {\n" +
            "                        \"column\": \"\",\n" +
            "                        \"timeSpan\": 0,\n" +
            "                        \"timeUnit\": \"SECOND\"\n" +
            "                    }\n" +
            "                },\n" +
            "                \"config\": []\n" +
            "            },\n" +
            "            \"previousNodeColor\": {\n" +
            "                \"background-color\": \"#c6e5ff\",\n" +
            "                \"border\": \"1px solid #949494\",\n" +
            "                \"border-radius\": \"2px\"\n" +
            "            }\n" +
            "        },\n" +
            "        {\n" +
            "            \"position\": {\n" +
            "                \"x\": 432,\n" +
            "                \"y\": 300\n" +
            "            },\n" +
            "            \"size\": {\n" +
            "                \"width\": 70,\n" +
            "                \"height\": 50\n" +
            "            },\n" +
            "            \"view\": \"react-shape-view\",\n" +
            "            \"shape\": \"MysqlSinkOperator\",\n" +
            "            \"ports\": {\n" +
            "                \"groups\": {\n" +
            "                    \"outputs\": {\n" +
            "                        \"zIndex\": 999,\n" +
            "                        \"position\": \"right\",\n" +
            "                        \"markup\": {\n" +
            "                            \"tagName\": \"path\",\n" +
            "                            \"selector\": \"path\",\n" +
            "                            \"attrs\": {\n" +
            "                                \"d\": \"m-6,0,a5,5.5 0 0 1 12,0\",\n" +
            "                                \"fill\": \"#b2a2e9\",\n" +
            "                                \"transform\": \"rotate(90)\",\n" +
            "                                \"strokeWidth\": 1,\n" +
            "                                \"stroke\": \"null\"\n" +
            "                            }\n" +
            "                        },\n" +
            "                        \"attrs\": {\n" +
            "                            \"path\": {\n" +
            "                                \"r\": 8,\n" +
            "                                \"magnet\": true,\n" +
            "                                \"style\": {\n" +
            "                                    \"visibility\": \"hidden\"\n" +
            "                                }\n" +
            "                            }\n" +
            "                        }\n" +
            "                    },\n" +
            "                    \"inputs\": {\n" +
            "                        \"zIndex\": 999,\n" +
            "                        \"position\": \"left\",\n" +
            "                        \"markup\": {\n" +
            "                            \"tagName\": \"path\",\n" +
            "                            \"selector\": \"path\",\n" +
            "                            \"attrs\": {\n" +
            "                                \"d\": \"m-6,0,a5,5.5 0 0 1 12,0\",\n" +
            "                                \"fill\": \"#b2a2e9\",\n" +
            "                                \"transform\": \"rotate(-90)\",\n" +
            "                                \"strokeWidth\": 1,\n" +
            "                                \"stroke\": \"null\"\n" +
            "                            }\n" +
            "                        },\n" +
            "                        \"attrs\": {\n" +
            "                            \"path\": {\n" +
            "                                \"r\": 10,\n" +
            "                                \"magnet\": true,\n" +
            "                                \"style\": {\n" +
            "                                    \"visibility\": \"hidden\"\n" +
            "                                }\n" +
            "                            }\n" +
            "                        }\n" +
            "                    }\n" +
            "                },\n" +
            "                \"items\": [\n" +
            "                    {\n" +
            "                        \"group\": \"inputs\",\n" +
            "                        \"id\": \"input_0\",\n" +
            "                        \"zIndex\": 999,\n" +
            "                        \"attrs\": {\n" +
            "                            \"text\": {\n" +
            "                                \"text\": \"input_0\",\n" +
            "                                \"style\": {\n" +
            "                                    \"visibility\": \"hidden\",\n" +
            "                                    \"fontSize\": 10,\n" +
            "                                    \"fill\": \"#3B4351\"\n" +
            "                                }\n" +
            "                            }\n" +
            "                        },\n" +
            "                        \"label\": {\n" +
            "                            \"position\": \"bottom\"\n" +
            "                        }\n" +
            "                    }\n" +
            "                ]\n" +
            "            },\n" +
            "            \"id\": \"eb208286-415c-4f1d-b0af-e45d110d45bf\",\n" +
            "            \"name\": \"MysqlSink\",\n" +
            "            \"isStencil\": false,\n" +
            "            \"zIndex\": 2,\n" +
            "            \"data\": {\n" +
            "                \"parameters\": {\n" +
            "                    \"tableName\": \"AO_0A5972_NOTIFICATION_SETTING\",\n" +
            "                    \"connector\": \"jdbc\",\n" +
            "                    \"url\": \"jdbc:mysql://192.168.1.51:3306/dinky?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&useSSL=false&zeroDateTimeBehavior=convertToNull&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true\",\n" +
            "                    \"username\": \"root\",\n" +
            "                    \"password\": \"123456\",\n" +
            "                    \"other\": [],\n" +
            "                    \"columns\": [\n" +
            "                        {\n" +
            "                            \"name\": \"ID\",\n" +
            "                            \"type\": \"CHAR\",\n" +
            "                            \"flag\": true\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"name\": \"KEY\",\n" +
            "                            \"type\": \"STRING\",\n" +
            "                            \"flag\": true\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"name\": \"TYPE\",\n" +
            "                            \"type\": \"STRING\",\n" +
            "                            \"flag\": true\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"name\": \"USER_KEY\",\n" +
            "                            \"type\": \"STRING\",\n" +
            "                            \"flag\": true\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"name\": \"VALUE\",\n" +
            "                            \"type\": \"STRING\",\n" +
            "                            \"flag\": true\n" +
            "                        }\n" +
            "                    ],\n" +
            "                    \"primary\": \"\",\n" +
            "                    \"watermark\": {\n" +
            "                        \"column\": \"\",\n" +
            "                        \"timeSpan\": 0,\n" +
            "                        \"timeUnit\": \"SECOND\"\n" +
            "                    }\n" +
            "                },\n" +
            "                \"config\": [\n" +
            "                    {}\n" +
            "                ]\n" +
            "            },\n" +
            "            \"previousNodeColor\": {\n" +
            "                \"background-color\": \"#c6e5ff\",\n" +
            "                \"border\": \"1px solid #949494\",\n" +
            "                \"border-radius\": \"2px\"\n" +
            "            }\n" +
            "        },\n" +
            "        {\n" +
            "            \"position\": {\n" +
            "                \"x\": 257,\n" +
            "                \"y\": 300\n" +
            "            },\n" +
            "            \"size\": {\n" +
            "                \"width\": 70,\n" +
            "                \"height\": 50\n" +
            "            },\n" +
            "            \"view\": \"react-shape-view\",\n" +
            "            \"attrs\": {\n" +
            "                \"body\": {\n" +
            "                    \"rx\": 7,\n" +
            "                    \"ry\": 6\n" +
            "                },\n" +
            "                \"text\": {\n" +
            "                    \"text\": \"CommWindowOperator\",\n" +
            "                    \"fontSize\": 2\n" +
            "                }\n" +
            "            },\n" +
            "            \"shape\": \"CommWindowOperator\",\n" +
            "            \"ports\": {\n" +
            "                \"groups\": {\n" +
            "                    \"outputs\": {\n" +
            "                        \"zIndex\": 999,\n" +
            "                        \"position\": \"right\",\n" +
            "                        \"markup\": {\n" +
            "                            \"tagName\": \"path\",\n" +
            "                            \"selector\": \"path\",\n" +
            "                            \"attrs\": {\n" +
            "                                \"d\": \"m-6,0,a5,5.5 0 0 1 12,0\",\n" +
            "                                \"fill\": \"#b2a2e9\",\n" +
            "                                \"transform\": \"rotate(90)\",\n" +
            "                                \"strokeWidth\": 1,\n" +
            "                                \"stroke\": \"null\"\n" +
            "                            }\n" +
            "                        },\n" +
            "                        \"attrs\": {\n" +
            "                            \"path\": {\n" +
            "                                \"r\": 8,\n" +
            "                                \"magnet\": true,\n" +
            "                                \"style\": {\n" +
            "                                    \"visibility\": \"hidden\"\n" +
            "                                }\n" +
            "                            }\n" +
            "                        }\n" +
            "                    },\n" +
            "                    \"inputs\": {\n" +
            "                        \"zIndex\": 999,\n" +
            "                        \"position\": \"left\",\n" +
            "                        \"markup\": {\n" +
            "                            \"tagName\": \"path\",\n" +
            "                            \"selector\": \"path\",\n" +
            "                            \"attrs\": {\n" +
            "                                \"d\": \"m-6,0,a5,5.5 0 0 1 12,0\",\n" +
            "                                \"fill\": \"#b2a2e9\",\n" +
            "                                \"transform\": \"rotate(-90)\",\n" +
            "                                \"strokeWidth\": 1,\n" +
            "                                \"stroke\": \"null\"\n" +
            "                            }\n" +
            "                        },\n" +
            "                        \"attrs\": {\n" +
            "                            \"path\": {\n" +
            "                                \"r\": 10,\n" +
            "                                \"magnet\": true,\n" +
            "                                \"style\": {\n" +
            "                                    \"visibility\": \"hidden\"\n" +
            "                                }\n" +
            "                            }\n" +
            "                        }\n" +
            "                    }\n" +
            "                },\n" +
            "                \"items\": [\n" +
            "                    {\n" +
            "                        \"group\": \"inputs\",\n" +
            "                        \"id\": \"input_0\",\n" +
            "                        \"zIndex\": 999,\n" +
            "                        \"attrs\": {\n" +
            "                            \"text\": {\n" +
            "                                \"text\": \"input_0\",\n" +
            "                                \"style\": {\n" +
            "                                    \"visibility\": \"hidden\",\n" +
            "                                    \"fontSize\": 10,\n" +
            "                                    \"fill\": \"#3B4351\"\n" +
            "                                }\n" +
            "                            }\n" +
            "                        },\n" +
            "                        \"label\": {\n" +
            "                            \"position\": \"bottom\"\n" +
            "                        }\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"group\": \"outputs\",\n" +
            "                        \"zIndex\": 999,\n" +
            "                        \"id\": \"output_0\",\n" +
            "                        \"attrs\": {\n" +
            "                            \"text\": {\n" +
            "                                \"text\": \"output_0\",\n" +
            "                                \"style\": {\n" +
            "                                    \"visibility\": \"hidden\",\n" +
            "                                    \"fontSize\": 10,\n" +
            "                                    \"fill\": \"#3B4351\"\n" +
            "                                }\n" +
            "                            }\n" +
            "                        },\n" +
            "                        \"label\": {\n" +
            "                            \"position\": \"bottom\"\n" +
            "                        }\n" +
            "                    }\n" +
            "                ]\n" +
            "            },\n" +
            "            \"id\": \"45d93365-26b3-459d-ba94-0a49396e4b72\",\n" +
            "            \"name\": \"CommWindowOperator\",\n" +
            "            \"isStencil\": false,\n" +
            "            \"zIndex\": 3,\n" +
            "            \"data\": {\n" +
            "                \"parameters\": {\n" +
            "                    \"tableName\": \"\",\n" +
            "                    \"orderBy\": [],\n" +
            "                    \"where\": \"a=aa;\",\n" +
            "                    \"limit\": [],\n" +
            "                    \"group\": null,\n" +
            "                    \"window\": null,\n" +
            "                    \"options\": {\n" +
            "                        \"key\": \"\",\n" +
            "                        \"val\": \"\"\n" +
            "                    },\n" +
            "                    \"columns\": [\n" +
            "                        {\n" +
            "                            \"outName\": \"o1\",\n" +
            "                            \"function\": [\n" +
            "                                {\n" +
            "                                    \"functionName\": \"to_timestamp\",\n" +
            "                                    \"recursionFunc\": [\n" +
            "                                        {\n" +
            "                                            \"functionName\": \"f1\",\n" +
            "                                            \"recursionFunc\": [],\n" +
            "                                            \"recursionName\": [\n" +
            "                                                {\n" +
            "                                                    \"name\": [\n" +
            "                                                        \"a\",\n" +
            "                                                        \"b\"\n" +
            "                                                    ],\n" +
            "                                                    \"recursionFunc\": [],\n" +
            "                                                    \"recursionName\": []\n" +
            "                                                }\n" +
            "                                            ]\n" +
            "                                        }\n" +
            "                                    ],\n" +
            "                                    \"recursionName\": [\n" +
            "                                        {\n" +
            "                                            \"name\": [\n" +
            "                                                \"a\",\n" +
            "                                                \"b\"\n" +
            "                                            ],\n" +
            "                                            \"recursionFunc\": [],\n" +
            "                                            \"recursionName\": []\n" +
            "                                        }\n" +
            "                                    ]\n" +
            "                                }\n" +
            "                            ],\n" +
            "                            \"name\": \"\",\n" +
            "                            \"flag\": true,\n" +
            "                            \"decs\": \"\"\n" +
            "                        }\n" +
            "                    ]\n" +
            "                },\n" +
            "                \"config\": [\n" +
            "                    {\n" +
            "                        \"0ce6bc11-1a69-4a5a-baa0-b062de9c5d4f&output_0 45d93365-26b3-459d-ba94-0a49396e4b72&input_0\": [\n" +
            "                            {\n" +
            "                                \"name\": \"id\",\n" +
            "                                \"type\": \"INT\",\n" +
            "                                \"flag\": true\n" +
            "                            },\n" +
            "                            {\n" +
            "                                \"name\": \"mission_id\",\n" +
            "                                \"type\": \"STRING\",\n" +
            "                                \"flag\": true\n" +
            "                            },\n" +
            "                            {\n" +
            "                                \"name\": \"mission_name\",\n" +
            "                                \"type\": \"STRING\",\n" +
            "                                \"flag\": true\n" +
            "                            },\n" +
            "                            {\n" +
            "                                \"name\": \"scene_id\",\n" +
            "                                \"type\": \"STRING\",\n" +
            "                                \"flag\": true\n" +
            "                            },\n" +
            "                            {\n" +
            "                                \"name\": \"station_id\",\n" +
            "                                \"type\": \"STRING\",\n" +
            "                                \"flag\": true\n" +
            "                            },\n" +
            "                            {\n" +
            "                                \"name\": \"station_channel_id\",\n" +
            "                                \"type\": \"STRING\",\n" +
            "                                \"flag\": true\n" +
            "                            },\n" +
            "                            {\n" +
            "                                \"name\": \"monitor_missile_id\",\n" +
            "                                \"type\": \"STRING\",\n" +
            "                                \"flag\": true\n" +
            "                            },\n" +
            "                            {\n" +
            "                                \"name\": \"position_id\",\n" +
            "                                \"type\": \"STRING\",\n" +
            "                                \"flag\": true\n" +
            "                            },\n" +
            "                            {\n" +
            "                                \"name\": \"start_time\",\n" +
            "                                \"type\": \"TIMESTAMP\",\n" +
            "                                \"flag\": true\n" +
            "                            },\n" +
            "                            {\n" +
            "                                \"name\": \"end_time\",\n" +
            "                                \"type\": \"TIMESTAMP\",\n" +
            "                                \"flag\": true\n" +
            "                            },\n" +
            "                            {\n" +
            "                                \"name\": \"monitor_segment\",\n" +
            "                                \"type\": \"STRING\",\n" +
            "                                \"flag\": true\n" +
            "                            }\n" +
            "                        ]\n" +
            "                    }\n" +
            "                ]\n" +
            "            }\n" +
            "        },\n" +
            "        {\n" +
            "            \"shape\": \"edge\",\n" +
            "            \"attrs\": {\n" +
            "                \"line\": {\n" +
            "                    \"stroke\": \"#b2a2e9\",\n" +
            "                    \"targetMarker\": \"\"\n" +
            "                }\n" +
            "            },\n" +
            "            \"id\": \"93280487-17c9-4fbd-ab60-d33ec6a751a2\",\n" +
            "            \"tools\": {\n" +
            "                \"items\": [\n" +
            "                    {\n" +
            "                        \"name\": \"vertices\",\n" +
            "                        \"args\": {\n" +
            "                            \"attrs\": {\n" +
            "                                \"fill\": \"#666\"\n" +
            "                            }\n" +
            "                        }\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"name\": \"rm-edge-btn\",\n" +
            "                        \"args\": {\n" +
            "                            \"distance\": 40.19\n" +
            "                        }\n" +
            "                    }\n" +
            "                ],\n" +
            "                \"name\": null\n" +
            "            },\n" +
            "            \"source\": {\n" +
            "                \"cell\": \"0ce6bc11-1a69-4a5a-baa0-b062de9c5d4f\",\n" +
            "                \"port\": \"output_0\"\n" +
            "            },\n" +
            "            \"target\": {\n" +
            "                \"cell\": \"45d93365-26b3-459d-ba94-0a49396e4b72\",\n" +
            "                \"port\": \"input_0\"\n" +
            "            },\n" +
            "            \"zIndex\": 4\n" +
            "        },\n" +
            "        {\n" +
            "            \"shape\": \"edge\",\n" +
            "            \"attrs\": {\n" +
            "                \"line\": {\n" +
            "                    \"stroke\": \"#b2a2e9\",\n" +
            "                    \"targetMarker\": \"\"\n" +
            "                }\n" +
            "            },\n" +
            "            \"id\": \"47344642-fed7-4dcd-b87c-e215002174a5\",\n" +
            "            \"source\": {\n" +
            "                \"cell\": \"45d93365-26b3-459d-ba94-0a49396e4b72\",\n" +
            "                \"port\": \"output_0\"\n" +
            "            },\n" +
            "            \"target\": {\n" +
            "                \"cell\": \"eb208286-415c-4f1d-b0af-e45d110d45bf\",\n" +
            "                \"port\": \"input_0\"\n" +
            "            },\n" +
            "            \"zIndex\": 5\n" +
            "        }\n" +
            "    ]\n" +
            "}";

        public static void main(String [] args){

            X6ToInternalConvert x6 = new X6ToInternalConvert();
            Scene result = x6.convert(x6_json1);
            result.getEnvironment().setResultType(ResultType.SQL);
            SceneCodeBuilder su = new SceneCodeBuilder(result);
            final String build = su.build().get("SQL").toString();
            System.out.println(build);

        }
}
