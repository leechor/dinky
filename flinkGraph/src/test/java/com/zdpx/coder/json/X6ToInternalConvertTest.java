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
            "{\n" +
                    "    \"cells\": [\n" +
                    "        {\n" +
                    "            \"position\": {\n" +
                    "                \"x\": 90,\n" +
                    "                \"y\": 260\n" +
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
                    "            \"id\": \"033ca49c-a236-43b3-a744-d5dd900a6ec3\",\n" +
                    "            \"zIndex\": 1,\n" +

                    "            \"data\": {\n" +
                    "                \"parameters\": {\n" +
                    "                    \"tableName\": \"aaaaa\",\n" +
                    "                    \"connector\": \"jdbc\",\n" +
                    "                    \"url\": \"a\",\n" +
                    "                    \"username\": \"root\",\n" +
                    "                    \"password\": \"123456\",\n" +
                    "                    \"other\": [],\n" +
                    "                    \"columns\": [\n" +
                    "                        {\n" +
                    "                            \"name\": \"a\",\n" +
                    "                            \"type\": \"STRING\",\n" +
                    "                            \"flag\": true\n" +
                    "                        }\n" +
                    "                    ]\n" +
                    "                },\n" +
                    "                \"config\": []\n" +
                    "            }"+

                    "        },\n" +
                    "        {\n" +
                    "            \"position\": {\n" +
                    "                \"x\": 420,\n" +
                    "                \"y\": 290\n" +
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
                    "            \"id\": \"c41ba12f-2825-41a2-b369-42e1e30d6c76\",\n" +
                    "            \"zIndex\": 2,\n" +
                    "            \"data\": {\n" +
                    "                \"parameters\": [\n" +
                    "                    {\n" +
                    "                        \"dataSink\": {\n" +
                    "                            \"tableName\": \"s3\",\n" +
                    "                            \"connector\": \"jdbc\",\n" +
                    "                            \"url\": \"url\",\n" +
                    "                            \"username\": \"root\",\n" +
                    "                            \"password\": \"123456\"\n" +
                    "                        },\n" +
                    "                        \"columns\": [\n" +
                    "                            {\n" +
                    "                                \"name\": \"aaa\",\n" +
                    "                                \"type\": \"STRING\",\n" +
                    "                                \"flag\": true\n" +
                    "                            },\n" +
                    "                            {\n" +
                    "                                \"name\": \"bbb\",\n" +
                    "                                \"type\": \"STRING\",\n" +
                    "                                \"flag\": true\n" +
                    "                            },\n" +
                    "                            {\n" +
                    "                                \"name\": \"ccc\",\n" +
                    "                                \"type\": \"STRING\",\n" +
                    "                                \"flag\": true\n" +
                    "                            }\n" +
                    "                        ]\n" +
                    "                    }\n" +
                    "                ],\n" +
                    "                \"config\": [\n" +
                    "                    {\n" +
                    "                        \"27303e91-0bed-4648-a09c-d009e96a9c7c&output_0 c41ba12f-2825-41a2-b369-42e1e30d6c76&input_0\": [\n" +
                    "                            {\n" +
                    "                                \"functionName\": \"f1\",\n" +
                    "                                \"name\": \"out1\",\n" +
                    "                                \"parameters\": [\n" +
                    "                                    \"a1\"\n" +
                    "                                ],\n" +
                    "                                \"inputTable\": \"primaryInput\",\n" +
                    "                                \"flag\": true\n" +
                    "                            }\n" +
                    "                        ]\n" +
                    "                    }\n" +
                    "                ]\n" +
                    "            }\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"position\": {\n" +
                    "                \"x\": 90,\n" +
                    "                \"y\": 340\n" +
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
                    "            \"id\": \"e6dcb268-db9c-42d2-a7de-01802d7982d4\",\n" +
                    "            \"zIndex\": 3,\n" +
                    "            \"data\": {\n" +
                    "                \"parameters\": [\n" +
                    "                    {\n" +
                    "                        \"dataSource\": {\n" +
                    "                            \"tableName\": \"s2\",\n" +
                    "                            \"connector\": \"jdbc\",\n" +
                    "                            \"url\": \"url\",\n" +
                    "                            \"username\": \"root\",\n" +
                    "                            \"password\": \"123456\"\n" +
                    "                        },\n" +
                    "                        \"columns\": [\n" +
                    "                            {\n" +
                    "                                \"name\": \"a1\",\n" +
                    "                                \"type\": \"STRING\",\n" +
                    "                                \"flag\": true\n" +
                    "                            },\n" +
                    "                            {\n" +
                    "                                \"name\": \"a2\",\n" +
                    "                                \"type\": \"STRING\",\n" +
                    "                                \"flag\": true\n" +
                    "                            },\n" +
                    "                            {\n" +
                    "                                \"name\": \"a3\",\n" +
                    "                                \"type\": \"STRING\",\n" +
                    "                                \"flag\": true\n" +
                    "                            }\n" +
                    "                        ]\n" +
                    "                    }\n" +
                    "                ],\n" +
                    "                \"config\": []\n" +
                    "            }\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"position\": {\n" +
                    "                \"x\": 255,\n" +
                    "                \"y\": 290\n" +
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
                    "            \"id\": \"27303e91-0bed-4648-a09c-d009e96a9c7c\",\n" +
                    "            \"zIndex\": 4,\n" +
                    "            \"data\": {\n" +
                    "                \"parameters\": [\n" +
                    "                    {\n" +
                    "                        \"joinType\": \"INNER\",\n" +
                    "                        \"systemTimeColumn\": \"systemTimeColumn\",\n" +
                    "                        \"onLeftColumn\": \"a1\",\n" +
                    "                        \"onRightColumn\": \"a\",\n" +
                    "                        \"where\": \"a=a1;b=b1;\",\n" +
                    "                        \"columns\": [\n" +
                    "                            {\n" +
                    "                                \"functionName\": \"f1\",\n" +
                    "                                \"name\": \"out1\",\n" +
                    "                                \"parameters\": [\n" +
                    "                                    \"a1\"\n" +
                    "                                ],\n" +
                    "                                \"inputTable\": \"primaryInput\",\n" +
                    "                                \"flag\": true\n" +
                    "                            }\n" +
                    "                        ]\n" +
                    "                    }\n" +
                    "                ],\n" +
                    "                \"config\": [\n" +
                    "                    {\n" +
                    "                        \"033ca49c-a236-43b3-a744-d5dd900a6ec3&output_0 27303e91-0bed-4648-a09c-d009e96a9c7c&primaryInput\": [\n" +
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
                    "                            },\n" +
                    "                            {\n" +
                    "                                \"name\": \"d\",\n" +
                    "                                \"type\": \"STRING\",\n" +
                    "                                \"flag\": true\n" +
                    "                            }\n" +
                    "                        ],\n" +
                    "                        \"e6dcb268-db9c-42d2-a7de-01802d7982d4&output_0 27303e91-0bed-4648-a09c-d009e96a9c7c&secondInput\": [\n" +
                    "                            {\n" +
                    "                                \"name\": \"a1\",\n" +
                    "                                \"type\": \"STRING\",\n" +
                    "                                \"flag\": true\n" +
                    "                            },\n" +
                    "                            {\n" +
                    "                                \"name\": \"a2\",\n" +
                    "                                \"type\": \"STRING\",\n" +
                    "                                \"flag\": true\n" +
                    "                            },\n" +
                    "                            {\n" +
                    "                                \"name\": \"a3\",\n" +
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
                    "            \"id\": \"25f4ab79-e698-4ba5-8fdd-2c0392e1eba2\",\n" +
                    "            \"source\": {\n" +
                    "                \"cell\": \"033ca49c-a236-43b3-a744-d5dd900a6ec3\",\n" +
                    "                \"port\": \"output_0\"\n" +
                    "            },\n" +
                    "            \"target\": {\n" +
                    "                \"cell\": \"27303e91-0bed-4648-a09c-d009e96a9c7c\",\n" +
                    "                \"port\": \"primaryInput\"\n" +
                    "            },\n" +
                    "            \"zIndex\": 5\n" +
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
                    "            \"id\": \"155c3d48-5ed1-4d69-ab49-a19fd638ebbe\",\n" +
                    "            \"source\": {\n" +
                    "                \"cell\": \"e6dcb268-db9c-42d2-a7de-01802d7982d4\",\n" +
                    "                \"port\": \"output_0\"\n" +
                    "            },\n" +
                    "            \"target\": {\n" +
                    "                \"cell\": \"27303e91-0bed-4648-a09c-d009e96a9c7c\",\n" +
                    "                \"port\": \"secondInput\"\n" +
                    "            },\n" +
                    "            \"zIndex\": 6\n" +
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
                    "            \"id\": \"2f96edf9-0d85-419a-9e92-a36222348780\",\n" +
                    "            \"source\": {\n" +
                    "                \"cell\": \"27303e91-0bed-4648-a09c-d009e96a9c7c\",\n" +
                    "                \"port\": \"output_0\"\n" +
                    "            },\n" +
                    "            \"target\": {\n" +
                    "                \"cell\": \"c41ba12f-2825-41a2-b369-42e1e30d6c76\",\n" +
                    "                \"port\": \"input_0\"\n" +
                    "            },\n" +
                    "            \"zIndex\": 7\n" +
                    "        }\n" +
                    "    ]\n" +
                    "}";


    private static final String x6_json2 =
            "{\n" +
                    "    \"cells\": [\n" +
                    "        {\n" +
                    "            \"position\": {\n" +
                    "                \"x\": 50,\n" +
                    "                \"y\": 250\n" +
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
                    "            \"id\": \"4784bd8d-40b1-4d90-be63-8826b4349b72\",\n" +
                    "            \"zIndex\": 1,\n" +
                    "            \"data\": {\n" +
                    "                \"parameters\": {\n" +
                    "                    \"tableName\": \"n1\",\n" +
                    "                    \"connector\": \"jdbc\",\n" +
                    "                    \"url\": \"url\",\n" +
                    "                    \"username\": \"root\",\n" +
                    "                    \"password\": \"123456\",\n" +
                    "                    \"other\": [\n" +
                    "                        {\n" +
                    "                            \"key\": \"port\",\n" +
                    "                            \"values\": \"123123\"\n" +
                    "                        },\n" +
                    "                        {\n" +
                    "                            \"key\": \"ip\",\n" +
                    "                            \"values\": \"225.225.225.225\"\n" +
                    "                        }\n" +
                    "                    ],\n" +
                    "                    \"columns\": [\n" +
                    "                        {\n" +
                    "                            \"name\": \"a\",\n" +
                    "                            \"type\": \"STRING\",\n" +
                    "                            \"flag\": true\n" +
                    "                        },\n" +
                    "                        {\n" +
                    "                            \"name\": \"b\",\n" +
                    "                            \"type\": \"STRING\",\n" +
                    "                            \"flag\": true\n" +
                    "                        }\n" +
                    "                    ]\n" +
                    "                },\n" +
                    "                \"config\": []\n" +
                    "            }\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"position\": {\n" +
                    "                \"x\": 300,\n" +
                    "                \"y\": 250\n" +
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
                    "            \"id\": \"90c741fe-3ea3-4c5a-bf58-ef206f23be09\",\n" +
                    "            \"zIndex\": 2,\n" +
                    "            \"data\": {\n" +
                    "                \"parameters\": {\n" +
                    "                    \"tableName\": \"asd\",\n" +
                    "                    \"connector\": \"jdbc\",\n" +
                    "                    \"url\": \"asd\",\n" +
                    "                    \"username\": \"root\",\n" +
                    "                    \"password\": \"123456\",\n" +
                    "                    \"other\": [\n" +
                    "                        {\n" +
                    "                            \"key\": \"aaaaaaa\",\n" +
                    "                            \"values\": \"f\"\n" +
                    "                        },\n" +
                    "                        {\n" +
                    "                            \"key\": \"bbbbbbb\",\n" +
                    "                            \"values\": \"v\"\n" +
                    "                        }\n" +
                    "                    ],\n" +
                    "                    \"columns\": [\n" +
                    "                        {\n" +
                    "                            \"name\": \"aa\",\n" +
                    "                            \"type\": \"STRING\",\n" +
                    "                            \"flag\": true\n" +
                    "                        },\n" +
                    "                        {\n" +
                    "                            \"name\": \"bb\",\n" +
                    "                            \"type\": \"STRING\",\n" +
                    "                            \"flag\": true\n" +
                    "                        }\n" +
                    "                    ]\n" +
                    "                },\n" +
                    "                \"config\": [\n" +
                    "                    {\n" +
                    "                        \"4784bd8d-40b1-4d90-be63-8826b4349b72&output_0 90c741fe-3ea3-4c5a-bf58-ef206f23be09&input_0\": [\n" +
                    "                            {\n" +
                    "                                \"name\": \"a\",\n" +
                    "                                \"type\": \"STRING\",\n" +
                    "                                \"flag\": true\n" +
                    "                            },\n" +
                    "                            {\n" +
                    "                                \"name\": \"b\",\n" +
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
                    "            \"id\": \"d8b50986-0530-4aa2-b654-7d9396774e64\",\n" +
                    "            \"zIndex\": 3,\n" +
                    "            \"source\": {\n" +
                    "                \"cell\": \"4784bd8d-40b1-4d90-be63-8826b4349b72\",\n" +
                    "                \"port\": \"output_0\"\n" +
                    "            },\n" +
                    "            \"target\": {\n" +
                    "                \"cell\": \"90c741fe-3ea3-4c5a-bf58-ef206f23be09\",\n" +
                    "                \"port\": \"input_0\"\n" +
                    "            }\n" +
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
