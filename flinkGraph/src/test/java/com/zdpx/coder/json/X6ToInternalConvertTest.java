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
        "                \"x\": 594,\n" +
        "                \"y\": 0\n" +
        "            },\n" +
        "            \"size\": {\n" +
        "                \"width\": 594,\n" +
        "                \"height\": 498\n" +
        "            },\n" +
        "            \"view\": \"react-shape-view\",\n" +
        "            \"attrs\": {\n" +
        "                \"body\": {\n" +
        "                    \"rx\": 7,\n" +
        "                    \"ry\": 6\n" +
        "                },\n" +
        "                \"fo\": {\n" +
        "                    \"visibility\": \"hidden\"\n" +
        "                },\n" +
        "                \"text\": {\n" +
        "                    \"text\": \"group-process\",\n" +
        "                    \"fontSize\": 2\n" +
        "                }\n" +
        "            },\n" +
        "            \"shape\": \"group-process\",\n" +
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
        "                    },\n" +
        "                    \"innerOutputs\": {\n" +
        "                        \"zIndex\": 999,\n" +
        "                        \"position\": \"left\",\n" +
        "                        \"markup\": {\n" +
        "                            \"tagName\": \"path\",\n" +
        "                            \"selector\": \"path\",\n" +
        "                            \"attrs\": {\n" +
        "                                \"d\": \"m-6,0,a5,5.5 0 0 1 12,0\",\n" +
        "                                \"fill\": \"#2ac0c4\",\n" +
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
        "                    \"innerInputs\": {\n" +
        "                        \"zIndex\": 999,\n" +
        "                        \"position\": \"right\",\n" +
        "                        \"markup\": {\n" +
        "                            \"tagName\": \"path\",\n" +
        "                            \"selector\": \"path\",\n" +
        "                            \"attrs\": {\n" +
        "                                \"d\": \"m-6,0,a5,5.5 0 0 1 12,0\",\n" +
        "                                \"fill\": \"#2ac0c4\",\n" +
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
        "                            \"path\": {\n" +
        "                                \"d\": \"m-6,2,a5,5.5 0 0 1 12,0\"\n" +
        "                            }\n" +
        "                        },\n" +
        "                        \"label\": {\n" +
        "                            \"position\": \"left\"\n" +
        "                        },\n" +
        "                        \"id\": \"input_0\"\n" +
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
        "                            \"path\": {\n" +
        "                                \"d\": \"m-6,2,a5,5.5 0 0 1 12,0\"\n" +
        "                            }\n" +
        "                        },\n" +
        "                        \"label\": {\n" +
        "                            \"position\": \"right\"\n" +
        "                        }\n" +
        "                    },\n" +
        "                    {\n" +
        "                        \"id\": \"input_0_in\",\n" +
        "                        \"group\": \"innerOutputs\",\n" +
        "                        \"attrs\": {\n" +
        "                            \"text\": {\n" +
        "                                \"text\": \"input_0_in\",\n" +
        "                                \"style\": {\n" +
        "                                    \"visibility\": \"hidden\",\n" +
        "                                    \"fontSize\": 10,\n" +
        "                                    \"fill\": \"#3B4351\"\n" +
        "                                }\n" +
        "                            }\n" +
        "                        },\n" +
        "                        \"args\": {\n" +
        "                            \"dx\": 2\n" +
        "                        },\n" +
        "                        \"label\": {\n" +
        "                            \"position\": \"right\"\n" +
        "                        }\n" +
        "                    },\n" +
        "                    {\n" +
        "                        \"id\": \"output_0_in\",\n" +
        "                        \"group\": \"innerInputs\",\n" +
        "                        \"attrs\": {\n" +
        "                            \"text\": {\n" +
        "                                \"text\": \"output_0_in\",\n" +
        "                                \"style\": {\n" +
        "                                    \"visibility\": \"hidden\",\n" +
        "                                    \"fontSize\": 10,\n" +
        "                                    \"fill\": \"#3B4351\"\n" +
        "                                }\n" +
        "                            }\n" +
        "                        },\n" +
        "                        \"args\": {\n" +
        "                            \"dx\": -2\n" +
        "                        },\n" +
        "                        \"label\": {\n" +
        "                            \"position\": \"left\"\n" +
        "                        }\n" +
        "                    }\n" +
        "                ]\n" +
        "            },\n" +
        "            \"id\": \"99f2ea63-c2e8-4311-90fd-0c9727b5e4ae\",\n" +
        "            \"zIndex\": 0,\n" +
        "            \"children\": [\n" +
        "                \"2da28e1b-7c44-4217-b86f-0cdeccbec699\",\n" +
        "                \"fd0c8d1f-36c2-454f-b5a1-7f9d57938069\"\n" +
        "            ],\n" +
        "            \"previousPosition\": {\n" +
        "                \"x\": 200,\n" +
        "                \"y\": 360\n" +
        "            },\n" +
        "            \"previousSize\": {\n" +
        "                \"width\": 70,\n" +
        "                \"height\": 50\n" +
        "            }\n" +
        "        },\n" +
        "        {\n" +
        "            \"position\": {\n" +
        "                \"x\": 40,\n" +
        "                \"y\": 360\n" +
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
        "                            }\n" +
        "                        },\n" +
        "                        \"label\": {\n" +
        "                            \"position\": \"bottom\"\n" +
        "                        }\n" +
        "                    }\n" +
        "                ]\n" +
        "            },\n" +
        "            \"id\": \"3513f8e3-376e-44fc-b046-c407dd7b7d3a\",\n" +
        "            \"name\": \"MySQL数据源\",\n" +
        "            \"zIndex\": 1,\n" +
        "            \"data\": {\n" +
        "                \"parameters\": {\n" +
        "                    \"tableName\": \"asdasas\",\n" +
        "                    \"connector\": \"jdbc\",\n" +
        "                    \"url\": \"asad\",\n" +
        "                    \"username\": \"root\",\n" +
        "                    \"password\": \"123456\",\n" +
        "                    \"other\": [],\n" +
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
        "                    ],\n" +
        "                    \"primary\": \"\",\n" +
        "                    \"watermark\": {\n" +
        "                        \"column\": \"\",\n" +
        "                        \"timeSpan\": 0,\n" +
        "                        \"timeUnit\": \"SECOND\"\n" +
        "                    }\n" +
        "                },\n" +
        "                \"config\": []\n" +
        "            }\n" +
        "        },\n" +
        "        {\n" +
        "            \"position\": {\n" +
        "                \"x\": 520,\n" +
        "                \"y\": 360\n" +
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
        "                            }\n" +
        "                        },\n" +
        "                        \"label\": {\n" +
        "                            \"position\": \"bottom\"\n" +
        "                        }\n" +
        "                    }\n" +
        "                ]\n" +
        "            },\n" +
        "            \"id\": \"0f1c1342-9950-4fec-a636-dd904ef7a5eb\",\n" +
        "            \"name\": \"MysqlSink\",\n" +
        "            \"zIndex\": 2,\n" +
        "            \"data\": {\n" +
        "                \"parameters\": {\n" +
        "                    \"tableName\": \"asdasd\",\n" +
        "                    \"connector\": \"jdbc\",\n" +
        "                    \"url\": \"asad\",\n" +
        "                    \"username\": \"root\",\n" +
        "                    \"password\": \"123456\",\n" +
        "                    \"other\": [],\n" +
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
        "                        \"fd0c8d1f-36c2-454f-b5a1-7f9d57938069&output_0 0f1c1342-9950-4fec-a636-dd904ef7a5eb&input_0\": [\n" +
        "                            {\n" +
        "                                \"functionName\": \"\",\n" +
        "                                \"name\": \"a2\",\n" +
        "                                \"parameters\": [\n" +
        "                                    \"a1\"\n" +
        "                                ],\n" +
        "                                \"flag\": true\n" +
        "                            },\n" +
        "                            {\n" +
        "                                \"functionName\": \"\",\n" +
        "                                \"name\": \"b1\",\n" +
        "                                \"parameters\": [\n" +
        "                                    \"b2\"\n" +
        "                                ],\n" +
        "                                \"flag\": true\n" +
        "                            }\n" +
        "                        ]\n" +
        "                    }\n" +
        "                ]\n" +
        "            }\n" +
        "        },\n" +
        "        {\n" +
        "            \"position\": {\n" +
        "                \"x\": 820,\n" +
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
        "            \"id\": \"2da28e1b-7c44-4217-b86f-0cdeccbec699\",\n" +
        "            \"name\": \"CommWindowOperator\",\n" +
        "            \"zIndex\": 3,\n" +
        "            \"data\": {\n" +
        "                \"parameters\": {\n" +
        "                    \"tableName\": \"asdasas\",\n" +
        "                    \"orderBy\": [],\n" +
        "                    \"where\": \"a=1;\",\n" +
        "                    \"limit\": [],\n" +
        "                    \"group\": null,\n" +
        "                    \"window\": null,\n" +
        "                    \"columns\": [\n" +
        "                        {\n" +
        "                            \"functionName\": \"\",\n" +
        "                            \"name\": \"a\",\n" +
        "                            \"parameters\": [\n" +
        "                                \"a1\"\n" +
        "                            ],\n" +
        "                            \"flag\": true\n" +
        "                        },\n" +
        "                        {\n" +
        "                            \"functionName\": \"\",\n" +
        "                            \"name\": \"b\",\n" +
        "                            \"parameters\": [\n" +
        "                                \"b1\"\n" +
        "                            ],\n" +
        "                            \"flag\": true\n" +
        "                        }\n" +
        "                    ]\n" +
        "                },\n" +
        "                \"config\": [\n" +
        "                    {\n" +
        "                        \"3513f8e3-376e-44fc-b046-c407dd7b7d3a&output_0 2da28e1b-7c44-4217-b86f-0cdeccbec699&input_0\": [\n" +
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
        "            },\n" +
        "            \"previousPosition\": {\n" +
        "                \"x\": 794,\n" +
        "                \"y\": 360\n" +
        "            },\n" +
        "            \"previousSize\": {\n" +
        "                \"width\": 70,\n" +
        "                \"height\": 50\n" +
        "            },\n" +
        "            \"visible\": false\n" +
        "        },\n" +
        "        {\n" +
        "            \"position\": {\n" +
        "                \"x\": 950,\n" +
        "                \"y\": 310\n" +
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
        "            \"id\": \"fd0c8d1f-36c2-454f-b5a1-7f9d57938069\",\n" +
        "            \"name\": \"CommWindowOperator\",\n" +
        "            \"zIndex\": 6,\n" +
        "            \"data\": {\n" +
        "                \"parameters\": {\n" +
        "                    \"tableName\": \"\",\n" +
        "                    \"orderBy\": [],\n" +
        "                    \"where\": \"a=aa;\",\n" +
        "                    \"limit\": [],\n" +
        "                    \"group\": null,\n" +
        "                    \"window\": null,\n" +
        "                    \"columns\": [\n" +
        "                        {\n" +
        "                            \"functionName\": \"\",\n" +
        "                            \"name\": \"a2\",\n" +
        "                            \"parameters\": [\n" +
        "                                \"a1\"\n" +
        "                            ],\n" +
        "                            \"flag\": true\n" +
        "                        },\n" +
        "                        {\n" +
        "                            \"functionName\": \"\",\n" +
        "                            \"name\": \"b1\",\n" +
        "                            \"parameters\": [\n" +
        "                                \"b2\"\n" +
        "                            ],\n" +
        "                            \"flag\": true\n" +
        "                        }\n" +
        "                    ]\n" +
        "                },\n" +
        "                \"config\": [\n" +
        "                    {\n" +
        "                        \"2da28e1b-7c44-4217-b86f-0cdeccbec699&output_0 fd0c8d1f-36c2-454f-b5a1-7f9d57938069&input_0\": [\n" +
        "                            {\n" +
        "                                \"functionName\": \"\",\n" +
        "                                \"name\": \"a\",\n" +
        "                                \"parameters\": [\n" +
        "                                    \"a1\"\n" +
        "                                ],\n" +
        "                                \"flag\": true\n" +
        "                            },\n" +
        "                            {\n" +
        "                                \"functionName\": \"\",\n" +
        "                                \"name\": \"b\",\n" +
        "                                \"parameters\": [\n" +
        "                                    \"b1\"\n" +
        "                                ],\n" +
        "                                \"flag\": true\n" +
        "                            }\n" +
        "                        ]\n" +
        "                    }\n" +
        "                ]\n" +
        "            },\n" +
        "            \"previousPosition\": {\n" +
        "                \"x\": 360,\n" +
        "                \"y\": 360\n" +
        "            },\n" +
        "            \"previousSize\": {\n" +
        "                \"width\": 70,\n" +
        "                \"height\": 50\n" +
        "            },\n" +
        "            \"visible\": true\n" +
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
        "            \"id\": \"ac94b833-6a5c-4f91-8827-8d331538cb4a\",\n" +
        "            \"source\": {\n" +
        "                \"cell\": \"3513f8e3-376e-44fc-b046-c407dd7b7d3a\",\n" +
        "                \"port\": \"output_0\"\n" +
        "            },\n" +
        "            \"target\": {\n" +
        "                \"cell\": \"99f2ea63-c2e8-4311-90fd-0c9727b5e4ae\",\n" +
        "                \"port\": \"input_0\"\n" +
        "            },\n" +
        "            \"zIndex\": 10,\n" +
        "            \"visible\": false\n" +
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
        "            \"id\": \"5a86a27e-4f49-40e8-82fa-ca2ca901db0b\",\n" +
        "            \"source\": {\n" +
        "                \"cell\": \"99f2ea63-c2e8-4311-90fd-0c9727b5e4ae\",\n" +
        "                \"port\": \"output_0\"\n" +
        "            },\n" +
        "            \"target\": {\n" +
        "                \"cell\": \"0f1c1342-9950-4fec-a636-dd904ef7a5eb\",\n" +
        "                \"port\": \"input_0\"\n" +
        "            },\n" +
        "            \"zIndex\": 12,\n" +
        "            \"visible\": false\n" +
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
        "            \"id\": \"0d31a99f-2dec-4932-9c53-f098b0ce9775\",\n" +
        "            \"router\": \"orth\",\n" +
        "            \"connector\": {\n" +
        "                \"name\": \"rounded\"\n" +
        "            },\n" +
        "            \"source\": {\n" +
        "                \"cell\": \"fd0c8d1f-36c2-454f-b5a1-7f9d57938069\",\n" +
        "                \"port\": \"output_0\"\n" +
        "            },\n" +
        "            \"target\": {\n" +
        "                \"cell\": \"99f2ea63-c2e8-4311-90fd-0c9727b5e4ae\",\n" +
        "                \"port\": \"output_0_in\"\n" +
        "            },\n" +
        "            \"zIndex\": 13,\n" +
        "            \"visible\": false\n" +
        "        },\n" +
        "        {\n" +
        "            \"position\": {\n" +
        "                \"x\": 820,\n" +
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
        "                    \"text\": \"group-process\",\n" +
        "                    \"fontSize\": 2\n" +
        "                }\n" +
        "            },\n" +
        "            \"shape\": \"group-process\",\n" +
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
        "                    },\n" +
        "                    \"innerOutputs\": {\n" +
        "                        \"zIndex\": 999,\n" +
        "                        \"position\": \"left\",\n" +
        "                        \"markup\": {\n" +
        "                            \"tagName\": \"path\",\n" +
        "                            \"selector\": \"path\",\n" +
        "                            \"attrs\": {\n" +
        "                                \"d\": \"m-6,0,a5,5.5 0 0 1 12,0\",\n" +
        "                                \"fill\": \"#2ac0c4\",\n" +
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
        "                    \"innerInputs\": {\n" +
        "                        \"zIndex\": 999,\n" +
        "                        \"position\": \"right\",\n" +
        "                        \"markup\": {\n" +
        "                            \"tagName\": \"path\",\n" +
        "                            \"selector\": \"path\",\n" +
        "                            \"attrs\": {\n" +
        "                                \"d\": \"m-6,0,a5,5.5 0 0 1 12,0\",\n" +
        "                                \"fill\": \"#2ac0c4\",\n" +
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
        "                            \"path\": {\n" +
        "                                \"d\": \"m-6,2,a5,5.5 0 0 1 12,0\"\n" +
        "                            }\n" +
        "                        },\n" +
        "                        \"label\": {\n" +
        "                            \"position\": \"left\"\n" +
        "                        },\n" +
        "                        \"id\": \"input_0\"\n" +
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
        "                            \"path\": {\n" +
        "                                \"d\": \"m-6,2,a5,5.5 0 0 1 12,0\"\n" +
        "                            }\n" +
        "                        },\n" +
        "                        \"label\": {\n" +
        "                            \"position\": \"right\"\n" +
        "                        }\n" +
        "                    },\n" +
        "                    {\n" +
        "                        \"id\": \"input_0_in\",\n" +
        "                        \"group\": \"innerOutputs\",\n" +
        "                        \"attrs\": {\n" +
        "                            \"text\": {\n" +
        "                                \"text\": \"input_0_in\",\n" +
        "                                \"style\": {\n" +
        "                                    \"visibility\": \"hidden\",\n" +
        "                                    \"fontSize\": 10,\n" +
        "                                    \"fill\": \"#3B4351\"\n" +
        "                                }\n" +
        "                            }\n" +
        "                        },\n" +
        "                        \"args\": {\n" +
        "                            \"dx\": 2\n" +
        "                        },\n" +
        "                        \"label\": {\n" +
        "                            \"position\": \"right\"\n" +
        "                        }\n" +
        "                    },\n" +
        "                    {\n" +
        "                        \"id\": \"output_0_in\",\n" +
        "                        \"group\": \"innerInputs\",\n" +
        "                        \"attrs\": {\n" +
        "                            \"text\": {\n" +
        "                                \"text\": \"output_0_in\",\n" +
        "                                \"style\": {\n" +
        "                                    \"visibility\": \"hidden\",\n" +
        "                                    \"fontSize\": 10,\n" +
        "                                    \"fill\": \"#3B4351\"\n" +
        "                                }\n" +
        "                            }\n" +
        "                        },\n" +
        "                        \"args\": {\n" +
        "                            \"dx\": -2\n" +
        "                        },\n" +
        "                        \"label\": {\n" +
        "                            \"position\": \"left\"\n" +
        "                        }\n" +
        "                    }\n" +
        "                ]\n" +
        "            },\n" +
        "            \"id\": \"353d8903-1e17-4d68-8565-1f6ebc60506a\",\n" +
        "            \"zIndex\": 14,\n" +
        "            \"children\": [\n" +
        "                \"2da28e1b-7c44-4217-b86f-0cdeccbec699\"\n" +
        "            ],\n" +
        "            \"previousPosition\": {\n" +
        "                \"x\": 794,\n" +
        "                \"y\": 360\n" +
        "            },\n" +
        "            \"previousSize\": {\n" +
        "                \"width\": 70,\n" +
        "                \"height\": 50\n" +
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
        "            \"id\": \"126939d1-6e87-46cd-bf7a-0493d0554795\",\n" +
        "            \"source\": {\n" +
        "                \"cell\": \"99f2ea63-c2e8-4311-90fd-0c9727b5e4ae\",\n" +
        "                \"port\": \"input_0_in\"\n" +
        "            },\n" +
        "            \"target\": {\n" +
        "                \"cell\": \"353d8903-1e17-4d68-8565-1f6ebc60506a\",\n" +
        "                \"port\": \"input_0\"\n" +
        "            },\n" +
        "            \"zIndex\": 15\n" +
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
        "            \"id\": \"d57ae1fa-44b4-4148-a204-2173313708ca\",\n" +
        "            \"router\": \"orth\",\n" +
        "            \"connector\": {\n" +
        "                \"name\": \"rounded\"\n" +
        "            },\n" +
        "            \"source\": {\n" +
        "                \"cell\": \"353d8903-1e17-4d68-8565-1f6ebc60506a\",\n" +
        "                \"port\": \"input_0_in\"\n" +
        "            },\n" +
        "            \"target\": {\n" +
        "                \"cell\": \"2da28e1b-7c44-4217-b86f-0cdeccbec699\",\n" +
        "                \"port\": \"input_0\"\n" +
        "            },\n" +
        "            \"zIndex\": 16,\n" +
        "            \"visible\": false\n" +
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
        "            \"id\": \"1018aad1-1857-4643-90c1-50c473e47dd2\",\n" +
        "            \"source\": {\n" +
        "                \"cell\": \"353d8903-1e17-4d68-8565-1f6ebc60506a\",\n" +
        "                \"port\": \"output_0\"\n" +
        "            },\n" +
        "            \"target\": {\n" +
        "                \"cell\": \"fd0c8d1f-36c2-454f-b5a1-7f9d57938069\",\n" +
        "                \"port\": \"input_0\"\n" +
        "            },\n" +
        "            \"zIndex\": 17\n" +
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
        "            \"id\": \"0b05c791-993c-4925-bd3a-998e9fa5dbe2\",\n" +
        "            \"router\": \"orth\",\n" +
        "            \"connector\": {\n" +
        "                \"name\": \"rounded\"\n" +
        "            },\n" +
        "            \"source\": {\n" +
        "                \"cell\": \"2da28e1b-7c44-4217-b86f-0cdeccbec699\",\n" +
        "                \"port\": \"output_0\"\n" +
        "            },\n" +
        "            \"target\": {\n" +
        "                \"cell\": \"353d8903-1e17-4d68-8565-1f6ebc60506a\",\n" +
        "                \"port\": \"output_0_in\"\n" +
        "            },\n" +
        "            \"zIndex\": 18,\n" +
        "            \"visible\": false\n" +
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

//            OperatorPreviewBuilder operatorPreviewBuilder = new OperatorPreviewBuilder(x6_json1);
//            String s = operatorPreviewBuilder.operatorPreview();
//            System.out.println(s);

        }
}
