package org.dinky.graphSQL;

import com.zdpx.coder.ResultType;
import com.zdpx.coder.SceneCodeBuilder;
import com.zdpx.coder.graph.Scene;
import com.zdpx.coder.json.x6.X6ToInternalConvert;

public class GraphTest {

//static String x6_json1 = "";
static String x6_json1 = "{\n" +
        "    \"cells\": [\n" +
        "        {\n" +
        "            \"position\": {\n" +
        "                \"x\": 50,\n" +
        "                \"y\": 280\n" +
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
        "                    \"text\": \"MySQL数据源\",\n" +
        "                    \"fontSize\": 2\n" +
        "                }\n" +
        "            },\n" +
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
        "                            },\n" +
        "                            \"circle\": {\n" +
        "                                \"style\": {\n" +
        "                                    \"visibility\": \"hidden\"\n" +
        "                                }\n" +
        "                            }\n" +
        "                        },\n" +
        "                        \"label\": {\n" +
        "                            \"position\": \"bottom\"\n" +
        "                        }\n" +
        "                    }\n" +
        "                ]\n" +
        "            },\n" +
        "            \"id\": \"8e4aabf4-4f8c-4b4d-b4bd-598869d39342\",\n" +
        "            \"name\": \"MySQL数据源\",\n" +
        "            \"isStencil\": false,\n" +
        "            \"zIndex\": 1,\n" +
        "            \"data\": {\n" +
        "                \"parameters\": {\n" +
        "                    \"service\": {\n" +
        "                        \"tableName\": \"\",\n" +
        "                        \"connector\": \"jdbc\",\n" +
        "                        \"url\": \"jdbc:mysql://192.168.1.51:3306/dinky?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&useSSL=false&zeroDateTimeBehavior=convertToNull&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true\",\n" +
        "                        \"username\": \"root\",\n" +
        "                        \"password\": \"123456\",\n" +
        "                        \"other\": [],\n" +
        "                        \"primary\": \"\",\n" +
        "                        \"watermark\": {\n" +
        "                            \"column\": \"\",\n" +
        "                            \"timeSpan\": 0,\n" +
        "                            \"timeUnit\": \"SECOND\"\n" +
        "                        }\n" +
        "                    },\n" +
        "                    \"output\": {\n" +
        "                        \"columns\": [\n" +
        "                            {\n" +
        "                                \"outName\": \"\",\n" +
        "                                \"name\": \"a\",\n" +
        "                                \"type\": \"STRING\",\n" +
        "                                \"desc\": \"\"\n" +
        "                            },\n" +
        "                            {\n" +
        "                                \"outName\": \"\",\n" +
        "                                \"name\": \"b\",\n" +
        "                                \"type\": \"INT\",\n" +
        "                                \"desc\": \"\"\n" +
        "                            }\n" +
        "                        ],\n" +
        "                        \"source\": []\n" +
        "                    }\n" +
        "                },\n" +
        "                \"config\": []\n" +
        "            }\n" +
        "        },\n" +
        "        {\n" +
        "            \"position\": {\n" +
        "                \"x\": 440,\n" +
        "                \"y\": 280\n" +
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
        "                    \"text\": \"MysqlSink\",\n" +
        "                    \"fontSize\": 2\n" +
        "                }\n" +
        "            },\n" +
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
        "                            },\n" +
        "                            \"circle\": {\n" +
        "                                \"style\": {\n" +
        "                                    \"visibility\": \"hidden\"\n" +
        "                                }\n" +
        "                            }\n" +
        "                        },\n" +
        "                        \"label\": {\n" +
        "                            \"position\": \"bottom\"\n" +
        "                        }\n" +
        "                    }\n" +
        "                ]\n" +
        "            },\n" +
        "            \"id\": \"b22df654-3e01-4e2c-8e96-237a10e48a90\",\n" +
        "            \"name\": \"MysqlSink\",\n" +
        "            \"isStencil\": false,\n" +
        "            \"zIndex\": 2,\n" +
        "            \"data\": {\n" +
        "                \"parameters\": {\n" +
        "                    \"service\": {\n" +
        "                        \"tableName\": \"\",\n" +
        "                        \"connector\": \"jdbc\",\n" +
        "                        \"url\": \"jdbc:mysql://192.168.1.51:3306/dinky?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&useSSL=false&zeroDateTimeBehavior=convertToNull&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true\",\n" +
        "                        \"username\": \"root\",\n" +
        "                        \"password\": \"123456\",\n" +
        "                        \"other\": [],\n" +
        "                        \"primary\": \"\",\n" +
        "                        \"watermark\": {\n" +
        "                            \"column\": \"\",\n" +
        "                            \"timeSpan\": 0,\n" +
        "                            \"timeUnit\": \"SECOND\"\n" +
        "                        }\n" +
        "                    },\n" +
        "                    \"output\": {\n" +
        "                        \"source\": [\n" +
        "                            {\n" +
        "                                \"name\": \"a1\",\n" +
        "                                \"type\": \"STRING\",\n" +
        "                                \"desc\": \"\"\n" +
        "                            },\n" +
        "                            {\n" +
        "                                \"name\": \"a2\",\n" +
        "                                \"type\": \"STRING\",\n" +
        "                                \"desc\": \"\"\n" +
        "                            }\n" +
        "                        ]\n" +
        "                    }\n" +
        "                },\n" +
        "                \"config\": [\n" +
        "                    {\n" +
        "                        \"052d14e7-e609-49bd-872f-1b83caed417b&output_0 b22df654-3e01-4e2c-8e96-237a10e48a90&input_0\": [\n" +
        "                            {\n" +
        "                                \"outName\": \"\",\n" +
        "                                \"name\": \"a\",\n" +
        "                                \"function\": [],\n" +
        "                                \"type\": \"STRING\",\n" +
        "                                \"desc\": \"\"\n" +
        "                            },\n" +
        "                            {\n" +
        "                                \"outName\": \"o2\",\n" +
        "                                \"name\": \"o2\",\n" +
        "                                \"function\": [\n" +
        "                                    {\n" +
        "                                        \"functionName\": \"f1\",\n" +
        "                                        \"recursionFunc\": [],\n" +
        "                                        \"recursionName\": [\n" +
        "                                            {\n" +
        "                                                \"name\": [\n" +
        "                                                    \"a\",\n" +
        "                                                    \"b\"\n" +
        "                                                ],\n" +
        "                                                \"recursionFunc\": [],\n" +
        "                                                \"recursionName\": []\n" +
        "                                            }\n" +
        "                                        ]\n" +
        "                                    }\n" +
        "                                ],\n" +
        "                                \"type\": \"INT\",\n" +
        "                                \"desc\": \"\"\n" +
        "                            }\n" +
        "                        ]\n" +
        "                    }\n" +
        "                ]\n" +
        "            }\n" +
        "        },\n" +
        "        {\n" +
        "            \"position\": {\n" +
        "                \"x\": 234,\n" +
        "                \"y\": 280\n" +
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
        "                            },\n" +
        "                            \"circle\": {\n" +
        "                                \"style\": {\n" +
        "                                    \"visibility\": \"hidden\"\n" +
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
        "                            },\n" +
        "                            \"circle\": {\n" +
        "                                \"style\": {\n" +
        "                                    \"visibility\": \"hidden\"\n" +
        "                                }\n" +
        "                            }\n" +
        "                        },\n" +
        "                        \"label\": {\n" +
        "                            \"position\": \"bottom\"\n" +
        "                        }\n" +
        "                    }\n" +
        "                ]\n" +
        "            },\n" +
        "            \"id\": \"052d14e7-e609-49bd-872f-1b83caed417b\",\n" +
        "            \"name\": \"CommWindowOperator\",\n" +
        "            \"isStencil\": false,\n" +
        "            \"zIndex\": 3,\n" +
        "            \"data\": {\n" +
        "                \"parameters\": {\n" +
        "                    \"service\": {\n" +
        "                        \"tableName\": \"\",\n" +
        "                        \"orderBy\": [],\n" +
        "                        \"where\": \"\",\n" +
        "                        \"limit\": [],\n" +
        "                        \"group\": null,\n" +
        "                        \"window\": null,\n" +
        "                        \"options\": {\n" +
        "                            \"key\": \"\",\n" +
        "                            \"val\": \"\"\n" +
        "                        }\n" +
        "                    },\n" +
        "                    \"output\": {\n" +
        "                        \"columns\": [\n" +
        "                            {\n" +
        "                                \"outName\": \"\",\n" +
        "                                \"name\": \"a\",\n" +
        "                                \"function\": [],\n" +
        "                                \"type\": \"STRING\",\n" +
        "                                \"desc\": \"\"\n" +
        "                            },\n" +
        "                            {\n" +
        "                                \"outName\": \"o2\",\n" +
        "                                \"name\": \"f1(a,b)\",\n" +
        "                                \"function\": [\n" +
        "                                    {\n" +
        "                                        \"functionName\": \"f1\",\n" +
        "                                        \"recursionFunc\": [],\n" +
        "                                        \"recursionName\": [\n" +
        "                                            {\n" +
        "                                                \"name\": [\n" +
        "                                                    \"a\",\n" +
        "                                                    \"b\"\n" +
        "                                                ],\n" +
        "                                                \"recursionFunc\": [],\n" +
        "                                                \"recursionName\": []\n" +
        "                                            }\n" +
        "                                        ]\n" +
        "                                    }\n" +
        "                                ],\n" +
        "                                \"type\": \"INT\",\n" +
        "                                \"desc\": \"\"\n" +
        "                            }\n" +
        "                        ],\n" +
        "                        \"source\": []\n" +
        "                    }\n" +
        "                },\n" +
        "                \"config\": [\n" +
        "                    {\n" +
        "                        \"8e4aabf4-4f8c-4b4d-b4bd-598869d39342&output_0 052d14e7-e609-49bd-872f-1b83caed417b&input_0\": [\n" +
        "                            {\n" +
        "                                \"outName\": \"\",\n" +
        "                                \"name\": \"a\",\n" +
        "                                \"type\": \"STRING\",\n" +
        "                                \"desc\": \"\"\n" +
        "                            },\n" +
        "                            {\n" +
        "                                \"outName\": \"\",\n" +
        "                                \"name\": \"b\",\n" +
        "                                \"type\": \"INT\",\n" +
        "                                \"desc\": \"\"\n" +
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
        "            \"id\": \"975b1657-e6e9-4d06-b568-37655b1253a6\",\n" +
        "            \"source\": {\n" +
        "                \"cell\": \"8e4aabf4-4f8c-4b4d-b4bd-598869d39342\",\n" +
        "                \"port\": \"output_0\"\n" +
        "            },\n" +
        "            \"target\": {\n" +
        "                \"cell\": \"052d14e7-e609-49bd-872f-1b83caed417b\",\n" +
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
        "            \"id\": \"9454b487-a81e-4440-a5c7-4e7dfd0b800b\",\n" +
        "            \"source\": {\n" +
        "                \"cell\": \"052d14e7-e609-49bd-872f-1b83caed417b\",\n" +
        "                \"port\": \"output_0\"\n" +
        "            },\n" +
        "            \"target\": {\n" +
        "                \"cell\": \"b22df654-3e01-4e2c-8e96-237a10e48a90\",\n" +
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