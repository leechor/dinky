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

import java.util.*;

class X6ToInternalConvertTest {

    private static final String x6_json1 =
            "{\n" +
                    "    \"cells\": [\n" +
                    "        {\n" +
                    "            \"position\": {\n" +
                    "                \"x\": -170,\n" +
                    "                \"y\": -160\n" +
                    "            },\n" +
                    "            \"size\": {\n" +
                    "                \"width\": 250,\n" +
                    "                \"height\": 240\n" +
                    "            },\n" +
                    "            \"shape\": \"group\",\n" +
                    "            \"attrs\": {\n" +
                    "                \"image\": {\n" +
                    "                    \"width\": 250,\n" +
                    "                    \"height\": 240\n" +
                    "                },\n" +
                    "                \"text\": {\n" +
                    "                    \"text\": \"Group Name\"\n" +
                    "                },\n" +
                    "                \"buttonSign\": {\n" +
                    "                    \"d\": \"M 2 5 8 5\"\n" +
                    "                }\n" +
                    "            },\n" +
                    "            \"id\": \"9a1d04da-6e02-4a26-aaa1-820920e88179\",\n" +
                    "            \"data\": {},\n" +
                    "            \"zIndex\": -1,\n" +
                    "            \"previousSize\": {\n" +
                    "                \"width\": 150,\n" +
                    "                \"height\": 40\n" +
                    "            },\n" +
                    "            \"children\": [\n" +
                    "                \"3665293d-6e7b-4eb9-bc82-316c3c43adf0\",\n" +
                    "                \"e8ed4082-5dbc-4597-ab72-b8c85cc1c81a\",\n" +
                    "                \"1975bce5-bae2-42ba-b20c-8ec1503d57f2\",\n" +
                    "                \"2e8ad85a-6d0f-4c33-9f68-b4e4244c0c70\",\n" +
                    "                \"afe6a8b5-8be6-4c75-914c-95595be071be\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"position\": {\n" +
                    "                \"x\": -310,\n" +
                    "                \"y\": -90\n" +
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
                    "            \"id\": \"03500f88-5ae0-4aef-ab6d-b4ae13163c8c\",\n" +
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
                    "                \"x\": 150,\n" +
                    "                \"y\": -100\n" +
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
                    "            \"id\": \"bceee3e8-8830-4305-bcbb-4f98e079b3ec\",\n" +
                    "            \"name\": \"MysqlSink\",\n" +
                    "            \"zIndex\": 2,\n" +
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
                    "                            \"name\": \"o1\",\n" +
                    "                            \"type\": \"STRING\",\n" +
                    "                            \"flag\": true\n" +
                    "                        },\n" +
                    "                        {\n" +
                    "                            \"name\": \"o2\",\n" +
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
                    "                        \"3665293d-6e7b-4eb9-bc82-316c3c43adf0&output_0 bceee3e8-8830-4305-bcbb-4f98e079b3ec&input_0\": [\n" +
                    "                            {\n" +
                    "                                \"functionName\": \"\",\n" +
                    "                                \"name\": \"out1\",\n" +
                    "                                \"parameters\": [\n" +
                    "                                    \"a\"\n" +
                    "                                ],\n" +
                    "                                \"flag\": true\n" +
                    "                            },\n" +
                    "                            {\n" +
                    "                                \"functionName\": \"\",\n" +
                    "                                \"name\": \"out2\",\n" +
                    "                                \"parameters\": [\n" +
                    "                                    \"b\"\n" +
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
                    "                \"x\": -140,\n" +
                    "                \"y\": -90.00000000000006\n" +
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
                    "                    \"text\": \"DuplicateOperator\",\n" +
                    "                    \"fontSize\": 2\n" +
                    "                }\n" +
                    "            },\n" +
                    "            \"shape\": \"DuplicateOperator\",\n" +
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
                    "                        \"id\": \"out1\",\n" +
                    "                        \"attrs\": {\n" +
                    "                            \"text\": {\n" +
                    "                                \"text\": \"out1\",\n" +
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
                    "                        \"id\": \"out2\",\n" +
                    "                        \"attrs\": {\n" +
                    "                            \"text\": {\n" +
                    "                                \"text\": \"out2\",\n" +
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
                    "            \"id\": \"e8ed4082-5dbc-4597-ab72-b8c85cc1c81a\",\n" +
                    "            \"name\": \"DuplicateOperator\",\n" +
                    "            \"zIndex\": 4,\n" +
                    "            \"data\": {\n" +
                    "                \"parameters\": {},\n" +
                    "                \"config\": [\n" +
                    "                    {\n" +
                    "                        \"03500f88-5ae0-4aef-ab6d-b4ae13163c8c&output_0 e8ed4082-5dbc-4597-ab72-b8c85cc1c81a&input_0\": [\n" +
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
                    "                        ],\n" +
                    "                        \"out1\": [\n" +
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
                    "                        ],\n" +
                    "                        \"out2\": [\n" +
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
                    "                        ],\n" +
                    "                        \"e8ed4082-5dbc-4597-ab72-b8c85cc1c81a&out1 3665293d-6e7b-4eb9-bc82-316c3c43adf0&input_0\": [\n" +
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
                    "                        ],\n" +
                    "                        \"e8ed4082-5dbc-4597-ab72-b8c85cc1c81a&out2 c317798a-4f7b-435f-a68a-d8b5ef0d4cb5&input_0\": [\n" +
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
                    "                        ],\n" +
                    "                        \"e8ed4082-5dbc-4597-ab72-b8c85cc1c81a&out2 2e8ad85a-6d0f-4c33-9f68-b4e4244c0c70&input_0\": [\n" +
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
                    "            \"parent\": \"9a1d04da-6e02-4a26-aaa1-820920e88179\"\n" +
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
                    "            \"id\": \"6b0b756e-787f-47f6-b81c-53f21b3e6af9\",\n" +
                    "            \"zIndex\": 5,\n" +
                    "            \"source\": {\n" +
                    "                \"cell\": \"03500f88-5ae0-4aef-ab6d-b4ae13163c8c\",\n" +
                    "                \"port\": \"output_0\"\n" +
                    "            },\n" +
                    "            \"target\": {\n" +
                    "                \"cell\": \"e8ed4082-5dbc-4597-ab72-b8c85cc1c81a\",\n" +
                    "                \"port\": \"input_0\"\n" +
                    "            }\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"position\": {\n" +
                    "                \"x\": -22,\n" +
                    "                \"y\": -110.00000000000006\n" +
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
                    "            \"id\": \"3665293d-6e7b-4eb9-bc82-316c3c43adf0\",\n" +
                    "            \"name\": \"CommWindowOperator\",\n" +
                    "            \"zIndex\": 6,\n" +
                    "            \"data\": {\n" +
                    "                \"parameters\": {\n" +
                    "                    \"orderBy\": [],\n" +
                    "                    \"where\": \"1=1\",\n" +
                    "                    \"limit\": [],\n" +
                    "                    \"group\": null,\n" +
                    "                    \"window\": null,\n" +
                    "                    \"columns\": [\n" +
                    "                        {\n" +
                    "                            \"functionName\": \"\",\n" +
                    "                            \"name\": \"out1\",\n" +
                    "                            \"parameters\": [\n" +
                    "                                \"a\"\n" +
                    "                            ],\n" +
                    "                            \"flag\": true\n" +
                    "                        },\n" +
                    "                        {\n" +
                    "                            \"functionName\": \"\",\n" +
                    "                            \"name\": \"out2\",\n" +
                    "                            \"parameters\": [\n" +
                    "                                \"b\"\n" +
                    "                            ],\n" +
                    "                            \"flag\": true\n" +
                    "                        }\n" +
                    "                    ]\n" +
                    "                },\n" +
                    "                \"config\": [\n" +
                    "                    {\n" +
                    "                        \"e8ed4082-5dbc-4597-ab72-b8c85cc1c81a&out1 3665293d-6e7b-4eb9-bc82-316c3c43adf0&input_0\": [\n" +
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
                    "            \"parent\": \"9a1d04da-6e02-4a26-aaa1-820920e88179\"\n" +
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
                    "            \"id\": \"1975bce5-bae2-42ba-b20c-8ec1503d57f2\",\n" +
                    "            \"zIndex\": 7,\n" +
                    "            \"source\": {\n" +
                    "                \"cell\": \"e8ed4082-5dbc-4597-ab72-b8c85cc1c81a\",\n" +
                    "                \"port\": \"out1\"\n" +
                    "            },\n" +
                    "            \"target\": {\n" +
                    "                \"cell\": \"3665293d-6e7b-4eb9-bc82-316c3c43adf0\",\n" +
                    "                \"port\": \"input_0\"\n" +
                    "            },\n" +
                    "            \"parent\": \"9a1d04da-6e02-4a26-aaa1-820920e88179\"\n" +
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
                    "            \"id\": \"a4dc82ef-8d50-4107-989f-8b884718213f\",\n" +
                    "            \"zIndex\": 8,\n" +
                    "            \"source\": {\n" +
                    "                \"cell\": \"3665293d-6e7b-4eb9-bc82-316c3c43adf0\",\n" +
                    "                \"port\": \"output_0\"\n" +
                    "            },\n" +
                    "            \"target\": {\n" +
                    "                \"cell\": \"bceee3e8-8830-4305-bcbb-4f98e079b3ec\",\n" +
                    "                \"port\": \"input_0\"\n" +
                    "            }\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"position\": {\n" +
                    "                \"x\": -22,\n" +
                    "                \"y\": -25.000000000000057\n" +
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
                    "            \"id\": \"2e8ad85a-6d0f-4c33-9f68-b4e4244c0c70\",\n" +
                    "            \"name\": \"CommWindowOperator\",\n" +
                    "            \"zIndex\": 9,\n" +
                    "            \"data\": {\n" +
                    "                \"parameters\": {\n" +
                    "                    \"orderBy\": [\n" +
                    "                        {\n" +
                    "                            \"order\": \"a\",\n" +
                    "                            \"sort\": \"ACS\"\n" +
                    "                        },\n" +
                    "                        {\n" +
                    "                            \"order\": \"b\",\n" +
                    "                            \"sort\": \"DESC\"\n" +
                    "                        }\n" +
                    "                    ],\n" +
                    "                    \"where\": \"2=2\",\n" +
                    "                    \"limit\": [],\n" +
                    "                    \"group\": null,\n" +
                    "                    \"window\": null,\n" +
                    "                    \"columns\": [\n" +
                    "                        {\n" +
                    "                            \"functionName\": \"\",\n" +
                    "                            \"name\": \"s2\",\n" +
                    "                            \"parameters\": [\n" +
                    "                                \"a\"\n" +
                    "                            ],\n" +
                    "                            \"flag\": true\n" +
                    "                        },\n" +
                    "                        {\n" +
                    "                            \"functionName\": \"\",\n" +
                    "                            \"name\": \"s3\",\n" +
                    "                            \"parameters\": [\n" +
                    "                                \"b\"\n" +
                    "                            ],\n" +
                    "                            \"flag\": true\n" +
                    "                        }\n" +
                    "                    ]\n" +
                    "                },\n" +
                    "                \"config\": [\n" +
                    "                    {\n" +
                    "                        \"e8ed4082-5dbc-4597-ab72-b8c85cc1c81a&out2 2e8ad85a-6d0f-4c33-9f68-b4e4244c0c70&input_0\": [\n" +
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
                    "            \"parent\": \"9a1d04da-6e02-4a26-aaa1-820920e88179\"\n" +
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
                    "            \"id\": \"afe6a8b5-8be6-4c75-914c-95595be071be\",\n" +
                    "            \"zIndex\": 10,\n" +
                    "            \"source\": {\n" +
                    "                \"cell\": \"e8ed4082-5dbc-4597-ab72-b8c85cc1c81a\",\n" +
                    "                \"port\": \"out2\"\n" +
                    "            },\n" +
                    "            \"target\": {\n" +
                    "                \"cell\": \"2e8ad85a-6d0f-4c33-9f68-b4e4244c0c70\",\n" +
                    "                \"port\": \"input_0\"\n" +
                    "            },\n" +
                    "            \"parent\": \"9a1d04da-6e02-4a26-aaa1-820920e88179\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"position\": {\n" +
                    "                \"x\": 150,\n" +
                    "                \"y\": -25\n" +
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
                    "            \"id\": \"ecf35703-61c4-4582-aa83-47010691740b\",\n" +
                    "            \"name\": \"MysqlSink\",\n" +
                    "            \"zIndex\": 11,\n" +
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
                    "                            \"name\": \"oo1\",\n" +
                    "                            \"type\": \"STRING\",\n" +
                    "                            \"flag\": true\n" +
                    "                        },\n" +
                    "                        {\n" +
                    "                            \"name\": \"oo2\",\n" +
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
                    "                        \"2e8ad85a-6d0f-4c33-9f68-b4e4244c0c70&output_0 ecf35703-61c4-4582-aa83-47010691740b&input_0\": [\n" +
                    "                            {\n" +
                    "                                \"functionName\": \"\",\n" +
                    "                                \"name\": \"s2\",\n" +
                    "                                \"parameters\": [\n" +
                    "                                    \"a\"\n" +
                    "                                ],\n" +
                    "                                \"flag\": true\n" +
                    "                            },\n" +
                    "                            {\n" +
                    "                                \"functionName\": \"\",\n" +
                    "                                \"name\": \"s3\",\n" +
                    "                                \"parameters\": [\n" +
                    "                                    \"b\"\n" +
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
                    "            \"id\": \"92e7fbf8-dbb5-4a14-b839-29967d276d3c\",\n" +
                    "            \"zIndex\": 12,\n" +
                    "            \"source\": {\n" +
                    "                \"cell\": \"2e8ad85a-6d0f-4c33-9f68-b4e4244c0c70\",\n" +
                    "                \"port\": \"output_0\"\n" +
                    "            },\n" +
                    "            \"target\": {\n" +
                    "                \"cell\": \"ecf35703-61c4-4582-aa83-47010691740b\",\n" +
                    "                \"port\": \"input_0\"\n" +
                    "            }\n" +
                    "        }\n" +
                    "    ]\n" +
                    "}";


        public static void main(String [] args){

            X6ToInternalConvert x6 = new X6ToInternalConvert();
            Scene result = x6.convert(x6_json1);
            result.getEnvironment().setResultType(ResultType.SQL);
            SceneCodeBuilder su = new SceneCodeBuilder(result);
            final String build = su.build();
            System.out.println(build);

        }
}
