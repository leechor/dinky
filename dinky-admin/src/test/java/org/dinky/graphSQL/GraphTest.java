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
        "                \"x\": 140,\n" +
        "                \"y\": 400\n" +
        "            },\n" +
        "            \"size\": {\n" +
        "                \"width\": 70,\n" +
        "                \"height\": 50\n" +
        "            },\n" +
        "            \"view\": \"react-shape-view\",\n" +
        "            \"shape\": \"CustomerSourceOperator\",\n" +
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
        "            \"id\": \"6a655db1-7530-4552-8b18-d23147abe612\",\n" +
        "            \"name\": \"CustomerSourceOperator\",\n" +
        "            \"isStencil\": false,\n" +
        "            \"zIndex\": -1,\n" +
        "            \"data\": {\n" +
        "                \"parameters\": {\n" +
        "                    \"tableName\": \"DATAS\",\n" +
        "                    \"other\": [\n" +
        "                        {\n" +
        "                            \"key\": \"connector\",\n" +
        "                            \"values\": \"udp\"\n" +
        "                        },\n" +
        "                        {\n" +
        "                            \"key\": \"format\",\n" +
        "                            \"values\": \"json\"\n" +
        "                        },\n" +
        "                        {\n" +
        "                            \"key\": \"hostName\",\n" +
        "                            \"values\": \"225.0.0.1\"\n" +
        "                        },\n" +
        "                        {\n" +
        "                            \"key\": \"port\",\n" +
        "                            \"values\": \"6001\"\n" +
        "                        },\n" +
        "                        {\n" +
        "                            \"key\": \"json.timestamp-format.standard\",\n" +
        "                            \"values\": \"ISO-8601\"\n" +
        "                        }\n" +
        "                    ],\n" +
        "                    \"columns\": [\n" +
        "                        {\n" +
        "                            \"name\": \"protocal\",\n" +
        "                            \"type\": \"STRING\",\n" +
        "                            \"flag\": true,\n" +
        "                            \"decs\": \"\"\n" +
        "                        },\n" +
        "                        {\n" +
        "                            \"name\": \"type\",\n" +
        "                            \"type\": \"STRING\",\n" +
        "                            \"flag\": true,\n" +
        "                            \"decs\": \"\"\n" +
        "                        },\n" +
        "                        {\n" +
        "                            \"name\": \"subtype\",\n" +
        "                            \"type\": \"STRING\",\n" +
        "                            \"flag\": true,\n" +
        "                            \"decs\": \"\"\n" +
        "                        },\n" +
        "                        {\n" +
        "                            \"name\": \"time\",\n" +
        "                            \"type\": \"TIMESTAMP_LTZ(3)\",\n" +
        "                            \"flag\": true,\n" +
        "                            \"decs\": \"\"\n" +
        "                        },\n" +
        "                        {\n" +
        "                            \"name\": \"group_id\",\n" +
        "                            \"type\": \"STRING\",\n" +
        "                            \"flag\": true,\n" +
        "                            \"decs\": \"\"\n" +
        "                        },\n" +
        "                        {\n" +
        "                            \"name\": \"train_uuid\",\n" +
        "                            \"type\": \"STRING\",\n" +
        "                            \"flag\": true,\n" +
        "                            \"decs\": \"\"\n" +
        "                        },\n" +
        "                        {\n" +
        "                            \"name\": \"data\",\n" +
        "                            \"type\": \"STRING\",\n" +
        "                            \"flag\": true,\n" +
        "                            \"decs\": \"\"\n" +
        "                        }\n" +
        "                    ],\n" +
        "                    \"primary\": \"train_uuid\",\n" +
        "                    \"watermark\": {\n" +
        "                        \"column\": \"time\",\n" +
        "                        \"timeSpan\": 0,\n" +
        "                        \"timeUnit\": \"SECOND\"\n" +
        "                    }\n" +
        "                },\n" +
        "                \"config\": []\n" +
        "            },\n" +
        "            \"children\": [\n" +
        "                \"8fb8da24-b850-4b7e-bdf1-aa8aa6ab542e\"\n" +
        "            ]\n" +
        "        },\n" +
        "        {\n" +
        "            \"position\": {\n" +
        "                \"x\": 30,\n" +
        "                \"y\": 110\n" +
        "            },\n" +
        "            \"size\": {\n" +
        "                \"width\": 70,\n" +
        "                \"height\": 50\n" +
        "            },\n" +
        "            \"view\": \"react-shape-view\",\n" +
        "            \"shape\": \"AddJarOperator\",\n" +
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
        "                \"items\": []\n" +
        "            },\n" +
        "            \"id\": \"329dc367-02a0-428d-9c0c-804daf61e28c\",\n" +
        "            \"name\": \"AddJarOperator\",\n" +
        "            \"isStencil\": false,\n" +
        "            \"zIndex\": 1,\n" +
        "            \"data\": {\n" +
        "                \"parameters\": {\n" +
        "                    \"jars\": [\n" +
        "                        \"D:\\\\program\\\\gitprogram\\\\dinky\\\\flink-connector\\\\flink-connector-udp\\\\target\\\\flink-connector-udp-3.1-SNAPSHOT.jar\"\n" +
        "                    ]\n" +
        "                },\n" +
        "                \"config\": []\n" +
        "            }\n" +
        "        },\n" +
        "        {\n" +
        "            \"position\": {\n" +
        "                \"x\": 450,\n" +
        "                \"y\": 400\n" +
        "            },\n" +
        "            \"size\": {\n" +
        "                \"width\": 70,\n" +
        "                \"height\": 50\n" +
        "            },\n" +
        "            \"view\": \"react-shape-view\",\n" +
        "            \"shape\": \"CustomerSinkOperator\",\n" +
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
        "            \"id\": \"8dffaefc-e4ac-4fd4-96a4-ed06877171ad\",\n" +
        "            \"name\": \"CustomerSinkOperator\",\n" +
        "            \"isStencil\": false,\n" +
        "            \"zIndex\": 3,\n" +
        "            \"data\": {\n" +
        "                \"parameters\": {\n" +
        "                    \"tableName\": \"DATA_SINK\",\n" +
        "                    \"other\": [\n" +
        "                        {\n" +
        "                            \"key\": \"connector\",\n" +
        "                            \"values\": \"udp\"\n" +
        "                        },\n" +
        "                        {\n" +
        "                            \"key\": \"format\",\n" +
        "                            \"values\": \"json\"\n" +
        "                        },\n" +
        "                        {\n" +
        "                            \"key\": \"hostName\",\n" +
        "                            \"values\": \"225.0.0.1\"\n" +
        "                        },\n" +
        "                        {\n" +
        "                            \"key\": \"port\",\n" +
        "                            \"values\": \"6001\"\n" +
        "                        },\n" +
        "                        {\n" +
        "                            \"key\": \"sink.parallelism\",\n" +
        "                            \"values\": \"1\"\n" +
        "                        }\n" +
        "                    ],\n" +
        "                    \"columns\": [\n" +
        "                        {\n" +
        "                            \"name\": \"protocal\",\n" +
        "                            \"type\": \"STRING\",\n" +
        "                            \"flag\": true,\n" +
        "                            \"decs\": \"\"\n" +
        "                        },\n" +
        "                        {\n" +
        "                            \"name\": \"type\",\n" +
        "                            \"type\": \"STRING\",\n" +
        "                            \"flag\": true,\n" +
        "                            \"decs\": \"\"\n" +
        "                        },\n" +
        "                        {\n" +
        "                            \"name\": \"subtype\",\n" +
        "                            \"type\": \"STRING\",\n" +
        "                            \"flag\": true,\n" +
        "                            \"decs\": \"\"\n" +
        "                        },\n" +
        "                        {\n" +
        "                            \"name\": \"time\",\n" +
        "                            \"type\": \"TIMESTAMP_LTZ(3)\",\n" +
        "                            \"flag\": true,\n" +
        "                            \"decs\": \"\"\n" +
        "                        },\n" +
        "                        {\n" +
        "                            \"name\": \"group_id\",\n" +
        "                            \"type\": \"STRING\",\n" +
        "                            \"flag\": true,\n" +
        "                            \"decs\": \"\"\n" +
        "                        },\n" +
        "                        {\n" +
        "                            \"name\": \"train_uuid\",\n" +
        "                            \"type\": \"STRING\",\n" +
        "                            \"flag\": true,\n" +
        "                            \"decs\": \"\"\n" +
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
        "                    {\n" +
        "                        \"c6d8ad18-8657-4303-8446-3b3491b96afe&output_0 8dffaefc-e4ac-4fd4-96a4-ed06877171ad&input_0\": [\n" +
        "                            {\n" +
        "                                \"outName\": \"\",\n" +
        "                                \"function\": [\n" +
        "                                    {\n" +
        "                                        \"functionName\": \"\",\n" +
        "                                        \"recursionFunc\": [],\n" +
        "                                        \"recursionName\": [\n" +
        "                                            {\n" +
        "                                                \"name\": [\n" +
        "                                                    \"a\"\n" +
        "                                                ],\n" +
        "                                                \"recursionFunc\": [],\n" +
        "                                                \"recursionName\": []\n" +
        "                                            }\n" +
        "                                        ]\n" +
        "                                    }\n" +
        "                                ],\n" +
        "                                \"name\": \"a\",\n" +
        "                                \"flag\": true,\n" +
        "                                \"decs\": \"\"\n" +
        "                            }\n" +
        "                        ]\n" +
        "                    }\n" +
        "                ]\n" +
        "            }\n" +
        "        },\n" +
        "        {\n" +
        "            \"position\": {\n" +
        "                \"x\": 291,\n" +
        "                \"y\": 400\n" +
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
        "            \"id\": \"c6d8ad18-8657-4303-8446-3b3491b96afe\",\n" +
        "            \"name\": \"CommWindowOperator\",\n" +
        "            \"isStencil\": false,\n" +
        "            \"zIndex\": 4,\n" +
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
        "                            \"outName\": \"\",\n" +
        "                            \"function\": [\n" +
        "                                {\n" +
        "                                    \"functionName\": \"\",\n" +
        "                                    \"recursionFunc\": [],\n" +
        "                                    \"recursionName\": [\n" +
        "                                        {\n" +
        "                                            \"name\": [\n" +
        "                                                \"a\"\n" +
        "                                            ],\n" +
        "                                            \"recursionFunc\": [],\n" +
        "                                            \"recursionName\": []\n" +
        "                                        }\n" +
        "                                    ]\n" +
        "                                }\n" +
        "                            ],\n" +
        "                            \"name\": \"a\",\n" +
        "                            \"flag\": true,\n" +
        "                            \"decs\": \"\"\n" +
        "                        },\n" +
        "                        {\n" +
        "                            \"outName\": \"\",\n" +
        "                            \"function\": [\n" +
        "                                {\n" +
        "                                    \"functionName\": \"\",\n" +
        "                                    \"recursionFunc\": [\n" +
        "                                        {\n" +
        "                                            \"functionName\": \"\",\n" +
        "                                            \"recursionFunc\": [],\n" +
        "                                            \"recursionName\": [\n" +
        "                                                {\n" +
        "                                                    \"name\": [\n" +
        "                                                        \"b\"\n" +
        "                                                    ],\n" +
        "                                                    \"recursionFunc\": [],\n" +
        "                                                    \"recursionName\": []\n" +
        "                                                }\n" +
        "                                            ]\n" +
        "                                        }\n" +
        "                                    ],\n" +
        "                                    \"recursionName\": []\n" +
        "                                }\n" +
        "                            ],\n" +
        "                            \"name\": \"v\",\n" +
        "                            \"flag\": true,\n" +
        "                            \"decs\": \"\"\n" +
        "                        },\n" +
        "                        {\n" +
        "                            \"outName\": \"c\",\n" +
        "                            \"function\": [],\n" +
        "                            \"name\": \"c\",\n" +
        "                            \"flag\": true,\n" +
        "                            \"decs\": \"\"\n" +
        "                        }\n" +
        "                    ]\n" +
        "                },\n" +
        "                \"config\": [\n" +
        "                    {\n" +
        "                        \"6a655db1-7530-4552-8b18-d23147abe612&output_0 c6d8ad18-8657-4303-8446-3b3491b96afe&input_0\": [\n" +
        "                            {\n" +
        "                                \"name\": \"protocal\",\n" +
        "                                \"type\": \"STRING\",\n" +
        "                                \"flag\": true,\n" +
        "                                \"decs\": \"\"\n" +
        "                            },\n" +
        "                            {\n" +
        "                                \"name\": \"type\",\n" +
        "                                \"type\": \"STRING\",\n" +
        "                                \"flag\": true,\n" +
        "                                \"decs\": \"\"\n" +
        "                            },\n" +
        "                            {\n" +
        "                                \"name\": \"subtype\",\n" +
        "                                \"type\": \"STRING\",\n" +
        "                                \"flag\": true,\n" +
        "                                \"decs\": \"\"\n" +
        "                            },\n" +
        "                            {\n" +
        "                                \"name\": \"time\",\n" +
        "                                \"type\": \"TIMESTAMP_LTZ(3)\",\n" +
        "                                \"flag\": true,\n" +
        "                                \"decs\": \"\"\n" +
        "                            },\n" +
        "                            {\n" +
        "                                \"name\": \"group_id\",\n" +
        "                                \"type\": \"STRING\",\n" +
        "                                \"flag\": true,\n" +
        "                                \"decs\": \"\"\n" +
        "                            },\n" +
        "                            {\n" +
        "                                \"name\": \"train_uuid\",\n" +
        "                                \"type\": \"STRING\",\n" +
        "                                \"flag\": true,\n" +
        "                                \"decs\": \"\"\n" +
        "                            },\n" +
        "                            {\n" +
        "                                \"name\": \"data\",\n" +
        "                                \"type\": \"STRING\",\n" +
        "                                \"flag\": true,\n" +
        "                                \"decs\": \"\"\n" +
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
        "            \"id\": \"a477a545-c41b-4838-b48f-394f1edb3fd5\",\n" +
        "            \"source\": {\n" +
        "                \"cell\": \"6a655db1-7530-4552-8b18-d23147abe612\",\n" +
        "                \"port\": \"output_0\"\n" +
        "            },\n" +
        "            \"target\": {\n" +
        "                \"cell\": \"c6d8ad18-8657-4303-8446-3b3491b96afe\",\n" +
        "                \"port\": \"input_0\"\n" +
        "            },\n" +
        "            \"zIndex\": 5\n" +
        "        },\n" +
        "        {\n" +
        "            \"shape\": \"edge\",\n" +
        "            \"attrs\": {\n" +
        "                \"line\": {\n" +
        "                    \"stroke\": \"#b2a2e9\",\n" +
        "                    \"targetMarker\": \"\"\n" +
        "                }\n" +
        "            },\n" +
        "            \"id\": \"7d4689f0-9a26-4607-8475-7b591b043090\",\n" +
        "            \"source\": {\n" +
        "                \"cell\": \"c6d8ad18-8657-4303-8446-3b3491b96afe\",\n" +
        "                \"port\": \"output_0\"\n" +
        "            },\n" +
        "            \"target\": {\n" +
        "                \"cell\": \"8dffaefc-e4ac-4fd4-96a4-ed06877171ad\",\n" +
        "                \"port\": \"input_0\"\n" +
        "            },\n" +
        "            \"zIndex\": 6\n" +
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
