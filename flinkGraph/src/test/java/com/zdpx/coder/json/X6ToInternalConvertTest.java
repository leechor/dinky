package com.zdpx.coder.json;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import com.zdpx.coder.SceneCodeBuilder;
import com.zdpx.coder.graph.Scene;
import com.zdpx.coder.json.x6.X6ToInternalConvert;

import java.util.HashMap;
import java.util.Map;

class X6ToInternalConvertTest {

    private static final String x6_json1 =
                    "                \"config\": [\n" +
                    "                    {\n" +
                    "                        \"a0236f91-59ce-4d68-9de8-18f75e9a48de&output_0 30ca4681-8227-4d5d-8763-7ad3095470ef&input_0\": [\n" +
                    "                            {\n" +
                    "                                \"name\": \"aadd\",\n" +
                    "                                \"type\": \"STRING\",\n" +
                    "                                \"flag\": true\n" +
                    "                            },\n" +
                    "                            {\n" +
                    "                                \"name\": \"aacc\",\n" +
                    "                                \"type\": \"STRING\",\n" +
                    "                                \"flag\": false\n" +
                    "                            }\n" +
                    "                        ]\n" +
                    "                    }\n" +
                    "                ]\n" ;


    private static final String x6_json2 =
            "{\n" +
                    "    \"cells\": [\n" +
                    "        {\n" +
                    "            \"position\": {\n" +
                    "                \"x\": 50,\n" +
                    "                \"y\": 70\n" +
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
                    "                    \"text\": \"MysqlSourceOperator\",\n" +
                    "                    \"fontSize\": 2\n" +
                    "                }\n" +
                    "            },\n" +
                    "            \"shape\": \"MysqlSourceOperator\",\n" +
                    "            \"ports\": {\n" +
                    "                \"groups\": {\n" +
                    "                    \"outputs\": {\n" +
                    "                        \"zIndex\": 999,\n" +
                    "                        \"position\": \"right\",\n" +
                    "                        \"attrs\": {\n" +
                    "                            \"circle\": {\n" +
                    "                                \"r\": 8,\n" +
                    "                                \"magnet\": true,\n" +
                    "                                \"stroke\": \"#818181\",\n" +
                    "                                \"strokeWidth\": 1,\n" +
                    "                                \"fill\": \"#b2a2e9\",\n" +
                    "                                \"style\": {\n" +
                    "                                    \"visibility\": \"hidden\"\n" +
                    "                                }\n" +
                    "                            }\n" +
                    "                        }\n" +
                    "                    },\n" +
                    "                    \"inputs\": {\n" +
                    "                        \"zIndex\": 999,\n" +
                    "                        \"position\": \"left\",\n" +
                    "                        \"attrs\": {\n" +
                    "                            \"circle\": {\n" +
                    "                                \"r\": 8,\n" +
                    "                                \"magnet\": true,\n" +
                    "                                \"stroke\": \"#818181\",\n" +
                    "                                \"strokeWidth\": 1,\n" +
                    "                                \"fill\": \"#915dac\",\n" +
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
                    "            \"id\": \"285941d5-c949-4f0d-bd45-ac51e8b32515\",\n" +
                    "            \"zIndex\": 1,\n" +
                    "            \"data\": {\n" +
                    "                \"parameters\": [\n" +
                    "                    {\n" +
                    "                        \"dataSource\": {\n" +
                    "                            \"tableName\": \"n1\",\n" +
                    "                            \"connector\": \"jdbc\",\n" +
                    "                            \"url\": \"u\",\n" +
                    "                            \"username\": \"root\",\n" +
                    "                            \"password\": \"123456\"\n" +
                    "                        },\n" +
                    "                        \"columns\": [\n" +
                    "                            {\n" +
                    "                                \"name\": \"a\",\n" +
                    "                                \"type\": \"STRING\",\n" +
                    "                                \"flag\": true\n" +
                    "                            },\n" +
                    "                            {\n" +
                    "                                \"name\": \"b\",\n" +
                    "                                \"type\": \"STRING\",\n" +
                    "                                \"flag\": true\n" +
                    "                            },\n" +
                    "                            {\n" +
                    "                                \"name\": \"c\",\n" +
                    "                                \"type\": \"STRING\",\n" +
                    "                                \"flag\": true\n" +
                    "                            }\n" +
                    "                        ]\n" +
                    "                    }\n" +
                    "                ]\n" +
                    "            }\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"position\": {\n" +
                    "                \"x\": 50,\n" +
                    "                \"y\": 162\n" +
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
                    "                    \"text\": \"MysqlSourceOperator\",\n" +
                    "                    \"fontSize\": 2\n" +
                    "                }\n" +
                    "            },\n" +
                    "            \"shape\": \"MysqlSourceOperator\",\n" +
                    "            \"ports\": {\n" +
                    "                \"groups\": {\n" +
                    "                    \"outputs\": {\n" +
                    "                        \"zIndex\": 999,\n" +
                    "                        \"position\": \"right\",\n" +
                    "                        \"attrs\": {\n" +
                    "                            \"circle\": {\n" +
                    "                                \"r\": 8,\n" +
                    "                                \"magnet\": true,\n" +
                    "                                \"stroke\": \"#818181\",\n" +
                    "                                \"strokeWidth\": 1,\n" +
                    "                                \"fill\": \"#b2a2e9\",\n" +
                    "                                \"style\": {\n" +
                    "                                    \"visibility\": \"hidden\"\n" +
                    "                                }\n" +
                    "                            }\n" +
                    "                        }\n" +
                    "                    },\n" +
                    "                    \"inputs\": {\n" +
                    "                        \"zIndex\": 999,\n" +
                    "                        \"position\": \"left\",\n" +
                    "                        \"attrs\": {\n" +
                    "                            \"circle\": {\n" +
                    "                                \"r\": 8,\n" +
                    "                                \"magnet\": true,\n" +
                    "                                \"stroke\": \"#818181\",\n" +
                    "                                \"strokeWidth\": 1,\n" +
                    "                                \"fill\": \"#915dac\",\n" +
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
                    "            \"id\": \"9ff19373-9026-4df2-8231-c1dd8793808e\",\n" +
                    "            \"zIndex\": 2,\n" +
                    "            \"data\": {\n" +
                    "                \"parameters\": [\n" +
                    "                    {\n" +
                    "                        \"dataSource\": {\n" +
                    "                            \"tableName\": \"n2\",\n" +
                    "                            \"connector\": \"jdbc\",\n" +
                    "                            \"url\": \"u\",\n" +
                    "                            \"username\": \"root\",\n" +
                    "                            \"password\": \"123456\"\n" +
                    "                        },\n" +
                    "                        \"columns\": [\n" +
                    "                            {\n" +
                    "                                \"name\": \"aa\",\n" +
                    "                                \"type\": \"STRING\",\n" +
                    "                                \"flag\": true\n" +
                    "                            },\n" +
                    "                            {\n" +
                    "                                \"name\": \"bb\",\n" +
                    "                                \"type\": \"STRING\",\n" +
                    "                                \"flag\": true\n" +
                    "                            },\n" +
                    "                            {\n" +
                    "                                \"name\": \"cc\",\n" +
                    "                                \"type\": \"STRING\",\n" +
                    "                                \"flag\": true\n" +
                    "                            }\n" +
                    "                        ]\n" +
                    "                    }\n" +
                    "                ]\n" +
                    "            }\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"position\": {\n" +
                    "                \"x\": 190,\n" +
                    "                \"y\": 120\n" +
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
                    "                    \"text\": \"JoinOperator\",\n" +
                    "                    \"fontSize\": 2\n" +
                    "                }\n" +
                    "            },\n" +
                    "            \"shape\": \"JoinOperator\",\n" +
                    "            \"ports\": {\n" +
                    "                \"groups\": {\n" +
                    "                    \"outputs\": {\n" +
                    "                        \"zIndex\": 999,\n" +
                    "                        \"position\": \"right\",\n" +
                    "                        \"attrs\": {\n" +
                    "                            \"circle\": {\n" +
                    "                                \"r\": 8,\n" +
                    "                                \"magnet\": true,\n" +
                    "                                \"stroke\": \"#818181\",\n" +
                    "                                \"strokeWidth\": 1,\n" +
                    "                                \"fill\": \"#b2a2e9\",\n" +
                    "                                \"style\": {\n" +
                    "                                    \"visibility\": \"hidden\"\n" +
                    "                                }\n" +
                    "                            }\n" +
                    "                        }\n" +
                    "                    },\n" +
                    "                    \"inputs\": {\n" +
                    "                        \"zIndex\": 999,\n" +
                    "                        \"position\": \"left\",\n" +
                    "                        \"attrs\": {\n" +
                    "                            \"circle\": {\n" +
                    "                                \"r\": 8,\n" +
                    "                                \"magnet\": true,\n" +
                    "                                \"stroke\": \"#818181\",\n" +
                    "                                \"strokeWidth\": 1,\n" +
                    "                                \"fill\": \"#915dac\",\n" +
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
                    "                        \"id\": \"primaryInput\",\n" +
                    "                        \"zIndex\": 999,\n" +
                    "                        \"attrs\": {\n" +
                    "                            \"text\": {\n" +
                    "                                \"text\": \"primaryInput\",\n" +
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
                    "                        \"group\": \"inputs\",\n" +
                    "                        \"id\": \"secondInput\",\n" +
                    "                        \"zIndex\": 999,\n" +
                    "                        \"attrs\": {\n" +
                    "                            \"text\": {\n" +
                    "                                \"text\": \"secondInput\",\n" +
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
                    "            \"id\": \"35d67e0a-7f67-470b-93e3-3ec77d2c7652\",\n" +
                    "            \"zIndex\": 3,\n" +
                    "            \"data\": {\n" +
                    "                \"parameters\": [\n" +
                    "                    {\n" +
                    "                        \"joinType\": \"INNER\",\n" +
                    "                        \"systemTimeColumn\": \"\",\n" +
                    "                        \"onLeftColumn\": \"left\",\n" +
                    "                        \"onRightColumn\": \"right\",\n" +
                    "                        \"where\": \"a=aa;b=bb\",\n" +
                    "                        \"columns\": [\n" +
                    "                            {\n" +
                    "                                \"functionName\": \"a1\",\n" +
                    "                                \"name\": \"test1\",\n" +
                    "                                \"parameters\": [\n" +
                    "                                    \"a\"\n" +
                    "                                ],\n" +
                    "                                \"flag\": true\n" +
                    "                            },\n" +
                    "                            {\n" +
                    "                                \"functionName\": \"a2\",\n" +
                    "                                \"name\": \"test2\",\n" +
                    "                                \"parameters\": [\n" +
                    "                                    \"2\"\n" +
                    "                                ],\n" +
                    "                                \"flag\": true\n" +
                    "                            }\n" +
                    "                        ]\n" +
                    "                    }\n" +
                    "                ],\n" +
                    "                \"config\": [\n" +
                    "                    {\n" +
                    "                        \"285941d5-c949-4f0d-bd45-ac51e8b32515&output_0 35d67e0a-7f67-470b-93e3-3ec77d2c7652&primaryInput\": [\n" +
                    "                            {\n" +
                    "                                \"name\": \"a\",\n" +
                    "                                \"type\": \"STRING\",\n" +
                    "                                \"flag\": true\n" +
                    "                            },\n" +
                    "                            {\n" +
                    "                                \"name\": \"b\",\n" +
                    "                                \"type\": \"STRING\",\n" +
                    "                                \"flag\": true\n" +
                    "                            },\n" +
                    "                            {\n" +
                    "                                \"name\": \"c\",\n" +
                    "                                \"type\": \"STRING\",\n" +
                    "                                \"flag\": true\n" +
                    "                            }\n" +
                    "                        ],\n" +
                    "                        \"9ff19373-9026-4df2-8231-c1dd8793808e&output_0 35d67e0a-7f67-470b-93e3-3ec77d2c7652&secondInput\": [\n" +
                    "                            {\n" +
                    "                                \"name\": \"aa\",\n" +
                    "                                \"type\": \"STRING\",\n" +
                    "                                \"flag\": true\n" +
                    "                            },\n" +
                    "                            {\n" +
                    "                                \"name\": \"bb\",\n" +
                    "                                \"type\": \"STRING\",\n" +
                    "                                \"flag\": true\n" +
                    "                            },\n" +
                    "                            {\n" +
                    "                                \"name\": \"cc\",\n" +
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
                    "                    \"targetMarker\": {\n" +
                    "                        \"name\": \"classic\",\n" +
                    "                        \"size\": 10\n" +
                    "                    }\n" +
                    "                }\n" +
                    "            },\n" +
                    "            \"id\": \"10faea17-d620-47c7-b2a1-ef60fc754262\",\n" +
                    "            \"source\": {\n" +
                    "                \"cell\": \"285941d5-c949-4f0d-bd45-ac51e8b32515\",\n" +
                    "                \"port\": \"output_0\"\n" +
                    "            },\n" +
                    "            \"target\": {\n" +
                    "                \"cell\": \"35d67e0a-7f67-470b-93e3-3ec77d2c7652\",\n" +
                    "                \"port\": \"primaryInput\"\n" +
                    "            },\n" +
                    "            \"zIndex\": 4\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"shape\": \"edge\",\n" +
                    "            \"attrs\": {\n" +
                    "                \"line\": {\n" +
                    "                    \"stroke\": \"#b2a2e9\",\n" +
                    "                    \"targetMarker\": {\n" +
                    "                        \"name\": \"classic\",\n" +
                    "                        \"size\": 10\n" +
                    "                    }\n" +
                    "                }\n" +
                    "            },\n" +
                    "            \"id\": \"4f3aa944-558f-47c0-aa72-54dff8318973\",\n" +
                    "            \"source\": {\n" +
                    "                \"cell\": \"9ff19373-9026-4df2-8231-c1dd8793808e\",\n" +
                    "                \"port\": \"output_0\"\n" +
                    "            },\n" +
                    "            \"target\": {\n" +
                    "                \"cell\": \"35d67e0a-7f67-470b-93e3-3ec77d2c7652\",\n" +
                    "                \"port\": \"secondInput\"\n" +
                    "            },\n" +
                    "            \"zIndex\": 5\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"position\": {\n" +
                    "                \"x\": 328,\n" +
                    "                \"y\": 120\n" +
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
                    "                    \"text\": \"MysqlSinkOperator\",\n" +
                    "                    \"fontSize\": 2\n" +
                    "                }\n" +
                    "            },\n" +
                    "            \"shape\": \"MysqlSinkOperator\",\n" +
                    "            \"ports\": {\n" +
                    "                \"groups\": {\n" +
                    "                    \"outputs\": {\n" +
                    "                        \"zIndex\": 999,\n" +
                    "                        \"position\": \"right\",\n" +
                    "                        \"attrs\": {\n" +
                    "                            \"circle\": {\n" +
                    "                                \"r\": 8,\n" +
                    "                                \"magnet\": true,\n" +
                    "                                \"stroke\": \"#818181\",\n" +
                    "                                \"strokeWidth\": 1,\n" +
                    "                                \"fill\": \"#b2a2e9\",\n" +
                    "                                \"style\": {\n" +
                    "                                    \"visibility\": \"hidden\"\n" +
                    "                                }\n" +
                    "                            }\n" +
                    "                        }\n" +
                    "                    },\n" +
                    "                    \"inputs\": {\n" +
                    "                        \"zIndex\": 999,\n" +
                    "                        \"position\": \"left\",\n" +
                    "                        \"attrs\": {\n" +
                    "                            \"circle\": {\n" +
                    "                                \"r\": 8,\n" +
                    "                                \"magnet\": true,\n" +
                    "                                \"stroke\": \"#818181\",\n" +
                    "                                \"strokeWidth\": 1,\n" +
                    "                                \"fill\": \"#915dac\",\n" +
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
                    "            \"id\": \"36fd714f-c1b0-476b-85ee-d10ced644f22\",\n" +
                    "            \"zIndex\": 6,\n" +
                    "            \"data\": {\n" +
                    "                \"parameters\": [\n" +
                    "                    {\n" +
                    "                        \"dataSink\": {\n" +
                    "                            \"tableName\": \"out\",\n" +
                    "                            \"connector\": \"jdbc\",\n" +
                    "                            \"url\": \"u\",\n" +
                    "                            \"username\": \"root\",\n" +
                    "                            \"password\": \"123456\"\n" +
                    "                        },\n" +
                    "                        \"columns\": [\n" +
                    "                            {\n" +
                    "                                \"name\": \"ab\",\n" +
                    "                                \"type\": \"STRING\",\n" +
                    "                                \"flag\": true\n" +
                    "                            },\n" +
                    "                            {\n" +
                    "                                \"name\": \"bb\",\n" +
                    "                                \"type\": \"STRING\",\n" +
                    "                                \"flag\": true\n" +
                    "                            },\n" +
                    "                            {\n" +
                    "                                \"name\": \"cb\",\n" +
                    "                                \"type\": \"STRING\",\n" +
                    "                                \"flag\": true\n" +
                    "                            }\n" +
                    "                        ]\n" +
                    "                    }\n" +
                    "                ],\n" +
                    "                \"config\": [\n" +
                    "                    {\n" +
                    "                        \"35d67e0a-7f67-470b-93e3-3ec77d2c7652&output_0 36fd714f-c1b0-476b-85ee-d10ced644f22&input_0\": [\n" +
                    "                            {\n" +
                    "                                \"functionName\": \"a1\",\n" +
                    "                                \"name\": \"test1\",\n" +
                    "                                \"parameters\": [\n" +
                    "                                    \"a\"\n" +
                    "                                ],\n" +
                    "                                \"flag\": true\n" +
                    "                            },\n" +
                    "                            {\n" +
                    "                                \"functionName\": \"a2\",\n" +
                    "                                \"name\": \"test2\",\n" +
                    "                                \"parameters\": [\n" +
                    "                                    \"2\"\n" +
                    "                                ],\n" +
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
                    "                    \"targetMarker\": {\n" +
                    "                        \"name\": \"classic\",\n" +
                    "                        \"size\": 10\n" +
                    "                    }\n" +
                    "                }\n" +
                    "            },\n" +
                    "            \"id\": \"e524efc6-1c03-4cdf-8cf9-3108bf300a8f\",\n" +
                    "            \"source\": {\n" +
                    "                \"cell\": \"35d67e0a-7f67-470b-93e3-3ec77d2c7652\",\n" +
                    "                \"port\": \"output_0\"\n" +
                    "            },\n" +
                    "            \"target\": {\n" +
                    "                \"cell\": \"36fd714f-c1b0-476b-85ee-d10ced644f22\",\n" +
                    "                \"port\": \"input_0\"\n" +
                    "            },\n" +
                    "            \"zIndex\": 7\n" +
                    "        }\n" +
                    "    ]\n" +
                    "}";

    @SneakyThrows
    public static void main(String [] args){

        X6ToInternalConvert x6 = new X6ToInternalConvert();
        Scene result = x6.convert(x6_json2);
        result.getEnvironment().setResultType(ResultType.SQL);
        SceneCodeBuilder su = new SceneCodeBuilder(result);
        final String build = su.build();
        System.out.println(build);
}
}
