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
                    "                \"x\": -290,\n" +
                    "                \"y\": -10\n" +
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
                    "            \"id\": \"5fc5a34a-ff18-4509-9d20-3149f8cf0c18\",\n" +
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
                    "                \"x\": -141,\n" +
                    "                \"y\": -10\n" +
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
                    "            \"id\": \"3ea62dc9-cab4-49a0-a3e2-608eaf960ab2\",\n" +
                    "            \"name\": \"DuplicateOperator\",\n" +
                    "            \"zIndex\": 2,\n" +
                    "            \"data\": {\n" +
                    "                \"config\": [\n" +
                    "                    {\n" +
                    "                        \"5fc5a34a-ff18-4509-9d20-3149f8cf0c18&output_0 3ea62dc9-cab4-49a0-a3e2-608eaf960ab2&input_0\": [\n" +
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
                    "                                \"flag\": false\n" +
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
                    "                        \"3ea62dc9-cab4-49a0-a3e2-608eaf960ab2&out1 30144850-cc01-4841-bc4f-e8e14a47cf2b&input_0\": [\n" +
                    "                            {\n" +
                    "                                \"name\": \"a\",\n" +
                    "                                \"type\": \"STRING\",\n" +
                    "                                \"flag\": true\n" +
                    "                            },\n" +
                    "                            {\n" +
                    "                                \"name\": \"b\",\n" +
                    "                                \"type\": \"STRING\",\n" +
                    "                                \"flag\": false\n" +
                    "                            }\n" +
                    "                        ],\n" +
                    "                        \"3ea62dc9-cab4-49a0-a3e2-608eaf960ab2&out2 a6f09551-d13a-452d-8494-5f7f031c5ac8&input_0\": [\n" +
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
                    "            \"position\": {\n" +
                    "                \"x\": 40,\n" +
                    "                \"y\": -60\n" +
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
                    "            \"id\": \"30144850-cc01-4841-bc4f-e8e14a47cf2b\",\n" +
                    "            \"name\": \"MysqlSink\",\n" +
                    "            \"zIndex\": 3,\n" +
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
                    "                            \"name\": \"aa1\",\n" +
                    "                            \"type\": \"STRING\",\n" +
                    "                            \"flag\": true\n" +
                    "                        },\n" +
                    "                        {\n" +
                    "                            \"name\": \"bb1\",\n" +
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
                    "                        \"3ea62dc9-cab4-49a0-a3e2-608eaf960ab2&out1 30144850-cc01-4841-bc4f-e8e14a47cf2b&input_0\": [\n" +
                    "                            {\n" +
                    "                                \"name\": \"a\",\n" +
                    "                                \"type\": \"STRING\",\n" +
                    "                                \"flag\": true\n" +
                    "                            },\n" +
                    "                            {\n" +
                    "                                \"name\": \"b\",\n" +
                    "                                \"type\": \"STRING\",\n" +
                    "                                \"flag\": false\n" +
                    "                            }\n" +
                    "                        ]\n" +
                    "                    }\n" +
                    "                ]\n" +
                    "            }\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"position\": {\n" +
                    "                \"x\": 40,\n" +
                    "                \"y\": 40\n" +
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
                    "            \"id\": \"a6f09551-d13a-452d-8494-5f7f031c5ac8\",\n" +
                    "            \"name\": \"MysqlSink\",\n" +
                    "            \"zIndex\": 4,\n" +
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
                    "                            \"name\": \"aa2\",\n" +
                    "                            \"type\": \"STRING\",\n" +
                    "                            \"flag\": true\n" +
                    "                        },\n" +
                    "                        {\n" +
                    "                            \"name\": \"bb2\",\n" +
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
                    "                        \"3ea62dc9-cab4-49a0-a3e2-608eaf960ab2&out2 a6f09551-d13a-452d-8494-5f7f031c5ac8&input_0\": [\n" +
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
                    "            \"id\": \"1b017035-2b4b-4e3d-bbc4-51c2c65ba20e\",\n" +
                    "            \"source\": {\n" +
                    "                \"cell\": \"5fc5a34a-ff18-4509-9d20-3149f8cf0c18\",\n" +
                    "                \"port\": \"output_0\"\n" +
                    "            },\n" +
                    "            \"target\": {\n" +
                    "                \"cell\": \"3ea62dc9-cab4-49a0-a3e2-608eaf960ab2\",\n" +
                    "                \"port\": \"input_0\"\n" +
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
                    "            \"id\": \"59329704-e65a-4742-bbfe-9cf5bd239d2a\",\n" +
                    "            \"source\": {\n" +
                    "                \"cell\": \"3ea62dc9-cab4-49a0-a3e2-608eaf960ab2\",\n" +
                    "                \"port\": \"out1\"\n" +
                    "            },\n" +
                    "            \"target\": {\n" +
                    "                \"cell\": \"30144850-cc01-4841-bc4f-e8e14a47cf2b\",\n" +
                    "                \"port\": \"input_0\"\n" +
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
                    "            \"id\": \"19705152-3e8d-415c-9c2b-524d70ddd564\",\n" +
                    "            \"source\": {\n" +
                    "                \"cell\": \"3ea62dc9-cab4-49a0-a3e2-608eaf960ab2\",\n" +
                    "                \"port\": \"out2\"\n" +
                    "            },\n" +
                    "            \"target\": {\n" +
                    "                \"cell\": \"a6f09551-d13a-452d-8494-5f7f031c5ac8\",\n" +
                    "                \"port\": \"input_0\"\n" +
                    "            },\n" +
                    "            \"zIndex\": 7\n" +
                    "        }\n" +
                    "    ]\n" +
                    "}";

        @SneakyThrows
        public static void main(String [] args){

            X6ToInternalConvert x6 = new X6ToInternalConvert();
            Scene result = x6.convert(x6_json1);
            result.getEnvironment().setResultType(ResultType.SQL);
            SceneCodeBuilder su = new SceneCodeBuilder(result);
            final String build = su.build();
            System.out.println(build);
    }
}
