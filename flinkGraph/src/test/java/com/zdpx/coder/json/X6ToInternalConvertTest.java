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
        "                \"x\": 80,\n" +
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
        "            \"id\": \"d15ce0c3-210c-441d-8bd5-d9b47097660c\",\n" +
        "            \"name\": \"MySQL数据源\",\n" +
        "            \"zIndex\": 1,\n" +
        "            \"data\": {\n" +
        "                \"parameters\": {\n" +
        "                    \"tableName\": \"\",\n" +
        "                    \"connector\": \"jdbc\",\n" +
        "                    \"url\": \"asad\",\n" +
        "                    \"username\": \"root\",\n" +
        "                    \"password\": \"123456\",\n" +
        "                    \"other\": [],\n" +
        "                    \"columns\": [\n" +
        "                        {\n" +
        "                            \"name\": \"protocal\",\n" +
        "                            \"type\": \"STRING\",\n" +
        "                            \"flag\": true\n" +
        "                        },\n" +
        "                        {\n" +
        "                            \"name\": \"type\",\n" +
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
        "                \"x\": 194,\n" +
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
        "            \"id\": \"a26ae299-d553-4809-a42d-b7f70d108cfa\",\n" +
        "            \"name\": \"DuplicateOperator\",\n" +
        "            \"zIndex\": 2,\n" +
        "            \"data\": {\n" +
        "                \"parameters\": {},\n" +
        "                \"config\": [\n" +
        "                    {\n" +
        "                        \"out1\": [\n" +
        "                            {\n" +
        "                                \"name\": \"protocal\",\n" +
        "                                \"type\": \"STRING\",\n" +
        "                                \"flag\": true\n" +
        "                            },\n" +
        "                            {\n" +
        "                                \"name\": \"type\",\n" +
        "                                \"type\": \"STRING\",\n" +
        "                                \"flag\": true\n" +
        "                            }\n" +
        "                        ],\n" +
        "                        \"out2\": [\n" +
        "                            {\n" +
        "                                \"name\": \"protocal\",\n" +
        "                                \"type\": \"STRING\",\n" +
        "                                \"flag\": true\n" +
        "                            },\n" +
        "                            {\n" +
        "                                \"name\": \"type\",\n" +
        "                                \"type\": \"STRING\",\n" +
        "                                \"flag\": true\n" +
        "                            }\n" +
        "                        ],\n" +
        "                        \"d15ce0c3-210c-441d-8bd5-d9b47097660c&output_0 a26ae299-d553-4809-a42d-b7f70d108cfa&input_0\": [\n" +
        "                            {\n" +
        "                                \"name\": \"protocal\",\n" +
        "                                \"type\": \"STRING\",\n" +
        "                                \"flag\": true\n" +
        "                            },\n" +
        "                            {\n" +
        "                                \"name\": \"type\",\n" +
        "                                \"type\": \"STRING\",\n" +
        "                                \"flag\": true\n" +
        "                            }\n" +
        "                        ],\n" +
        "                        \"a26ae299-d553-4809-a42d-b7f70d108cfa&out1 0b0e2d67-4204-442b-a137-4ac1d107d0fe&input_0\": [\n" +
        "                            {\n" +
        "                                \"name\": \"protocal\",\n" +
        "                                \"type\": \"STRING\",\n" +
        "                                \"flag\": true\n" +
        "                            },\n" +
        "                            {\n" +
        "                                \"name\": \"type\",\n" +
        "                                \"type\": \"STRING\",\n" +
        "                                \"flag\": true\n" +
        "                            }\n" +
        "                        ],\n" +
        "                        \"a26ae299-d553-4809-a42d-b7f70d108cfa&out2 0db0e10a-1193-4930-b28e-14cf14a6a311&input_0\": [\n" +
        "                            {\n" +
        "                                \"name\": \"protocal\",\n" +
        "                                \"type\": \"STRING\",\n" +
        "                                \"flag\": true\n" +
        "                            },\n" +
        "                            {\n" +
        "                                \"name\": \"type\",\n" +
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
        "                \"x\": 340,\n" +
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
        "            \"id\": \"0b0e2d67-4204-442b-a137-4ac1d107d0fe\",\n" +
        "            \"name\": \"MysqlSink\",\n" +
        "            \"zIndex\": 3,\n" +
        "            \"data\": {\n" +
        "                \"parameters\": {\n" +
        "                    \"tableName\": \"\",\n" +
        "                    \"connector\": \"jdbc\",\n" +
        "                    \"url\": \"asad\",\n" +
        "                    \"username\": \"root\",\n" +
        "                    \"password\": \"123456\",\n" +
        "                    \"other\": [],\n" +
        "                    \"columns\": [\n" +
        "                        {\n" +
        "                            \"name\": \"protocal\",\n" +
        "                            \"type\": \"STRING\",\n" +
        "                            \"flag\": true\n" +
        "                        },\n" +
        "                        {\n" +
        "                            \"name\": \"type\",\n" +
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
        "                        \"a26ae299-d553-4809-a42d-b7f70d108cfa&out1 0b0e2d67-4204-442b-a137-4ac1d107d0fe&input_0\": [\n" +
        "                            {\n" +
        "                                \"name\": \"protocal\",\n" +
        "                                \"type\": \"STRING\",\n" +
        "                                \"flag\": true\n" +
        "                            },\n" +
        "                            {\n" +
        "                                \"name\": \"type\",\n" +
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
        "                \"x\": 350,\n" +
        "                \"y\": 390\n" +
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
        "                    \"text\": \"KafKaSinkOperator\",\n" +
        "                    \"fontSize\": 2\n" +
        "                }\n" +
        "            },\n" +
        "            \"shape\": \"KafKaSinkOperator\",\n" +
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
        "            \"id\": \"0db0e10a-1193-4930-b28e-14cf14a6a311\",\n" +
        "            \"name\": \"KafKaSinkOperator\",\n" +
        "            \"zIndex\": 4,\n" +
        "            \"data\": {\n" +
        "                \"parameters\": {\n" +
        "                    \"connector\": \"kafka\",\n" +
        "                    \"topic\": \"asdsad\",\n" +
        "                    \"properties.bootstrip.servers\": \"hadoop102:9092\",\n" +
        "                    \"format\": \"json\",\n" +
        "                    \"tableName\": \"\",\n" +
        "                    \"other\": [],\n" +
        "                    \"columns\": [\n" +
        "                        {\n" +
        "                            \"name\": \"protocal\",\n" +
        "                            \"type\": \"STRING\",\n" +
        "                            \"flag\": true\n" +
        "                        },\n" +
        "                        {\n" +
        "                            \"name\": \"type\",\n" +
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
        "                        \"a26ae299-d553-4809-a42d-b7f70d108cfa&out2 0db0e10a-1193-4930-b28e-14cf14a6a311&input_0\": [\n" +
        "                            {\n" +
        "                                \"name\": \"protocal\",\n" +
        "                                \"type\": \"STRING\",\n" +
        "                                \"flag\": true\n" +
        "                            },\n" +
        "                            {\n" +
        "                                \"name\": \"type\",\n" +
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
        "            \"id\": \"0d987835-f455-414c-8daf-425595ef43cd\",\n" +
        "            \"source\": {\n" +
        "                \"cell\": \"d15ce0c3-210c-441d-8bd5-d9b47097660c\",\n" +
        "                \"port\": \"output_0\"\n" +
        "            },\n" +
        "            \"target\": {\n" +
        "                \"cell\": \"a26ae299-d553-4809-a42d-b7f70d108cfa\",\n" +
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
        "            \"id\": \"f3f4a3ce-a559-487a-8bd7-e97e27e7ca03\",\n" +
        "            \"source\": {\n" +
        "                \"cell\": \"a26ae299-d553-4809-a42d-b7f70d108cfa\",\n" +
        "                \"port\": \"out1\"\n" +
        "            },\n" +
        "            \"target\": {\n" +
        "                \"cell\": \"0b0e2d67-4204-442b-a137-4ac1d107d0fe\",\n" +
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
        "            \"id\": \"2babfe6d-5b35-456e-8a6b-590fb62cc11c\",\n" +
        "            \"source\": {\n" +
        "                \"cell\": \"a26ae299-d553-4809-a42d-b7f70d108cfa\",\n" +
        "                \"port\": \"out2\"\n" +
        "            },\n" +
        "            \"target\": {\n" +
        "                \"cell\": \"0db0e10a-1193-4930-b28e-14cf14a6a311\",\n" +
        "                \"port\": \"input_0\"\n" +
        "            },\n" +
        "            \"zIndex\": 7,\n" +
        "            \"tools\": {\n" +
        "                \"items\": [\n" +
        "                    {\n" +
        "                        \"name\": \"rm-edge-btn\",\n" +
        "                        \"args\": {\n" +
        "                            \"distance\": 56.19866420822356\n" +
        "                        }\n" +
        "                    }\n" +
        "                ],\n" +
        "                \"name\": null\n" +
        "            }\n" +
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
