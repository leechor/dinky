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
        "                \"x\": 30,\n" +
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
        "            \"id\": \"480e6018-8d4a-41ec-94c1-bbdad5cf9fa6\",\n" +
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
        "                            \"name\": \"a\",\n" +
        "                            \"type\": \"STRING\",\n" +
        "                            \"flag\": true\n" +
        "                        },\n" +
        "                        {\n" +
        "                            \"name\": \"b\",\n" +
        "                            \"type\": \"TIMESTAMP(3)\",\n" +
        "                            \"flag\": true\n" +
        "                        }\n" +
        "                    ],\n" +
        "                    \"primary\": \"a\",\n" +
        "                    \"watermark\": {\n" +
        "                        \"column\": \"b\",\n" +
        "                        \"timeSpan\": 0,\n" +
        "                        \"timeUnit\": \"SECOND\"\n" +
        "                    }\n" +
        "                },\n" +
        "                \"config\": []\n" +
        "            }\n" +
        "        },\n" +
        "        {\n" +
        "            \"position\": {\n" +
        "                \"x\": 430,\n" +
        "                \"y\": 560\n" +
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
        "            \"id\": \"c9df1602-6a1c-4b8c-8bc1-828e6dd0dc9f\",\n" +
        "            \"name\": \"MysqlSink\",\n" +
        "            \"zIndex\": 2,\n" +
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
        "                    \"primary\": \"bb\",\n" +
        "                    \"watermark\": {\n" +
        "                        \"column\": \"aa\",\n" +
        "                        \"timeSpan\": 10,\n" +
        "                        \"timeUnit\": \"SECOND\"\n" +
        "                    }\n" +
        "                },\n" +
        "                \"config\": [\n" +
        "                    {\n" +
        "                        \"44a0780a-c584-4198-869c-0b83e3583144&out2 c9df1602-6a1c-4b8c-8bc1-828e6dd0dc9f&input_0\": [\n" +
        "                            {\n" +
        "                                \"functionName\": \"\",\n" +
        "                                \"name\": \"out1\",\n" +
        "                                \"parameters\": [\n" +
        "                                    \"k1\"\n" +
        "                                ],\n" +
        "                                \"inputTable\": \"primaryInput\",\n" +
        "                                \"flag\": true\n" +
        "                            },\n" +
        "                            {\n" +
        "                                \"functionName\": \"\",\n" +
        "                                \"name\": \"out2\",\n" +
        "                                \"parameters\": [\n" +
        "                                    \"k2\"\n" +
        "                                ],\n" +
        "                                \"inputTable\": \"primaryInput\",\n" +
        "                                \"flag\": true\n" +
        "                            },\n" +
        "                            {\n" +
        "                                \"functionName\": \"\",\n" +
        "                                \"name\": \"out3\",\n" +
        "                                \"parameters\": [\n" +
        "                                    \"aa\"\n" +
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
        "                \"x\": 180,\n" +
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
        "            \"id\": \"d1d41089-5e33-44b8-80e8-9a953926f3f5\",\n" +
        "            \"name\": \"CommWindowOperator\",\n" +
        "            \"zIndex\": 3,\n" +
        "            \"data\": {\n" +
        "                \"parameters\": {\n" +
        "                    \"tableName\": \"\",\n" +
        "                    \"orderBy\": [\n" +
        "                        {\n" +
        "                            \"order\": \"a\",\n" +
        "                            \"sort\": \"ACS\"\n" +
        "                        }\n" +
        "                    ],\n" +
        "                    \"where\": \"a=aa;\",\n" +
        "                    \"limit\": [],\n" +
        "                    \"group\": null,\n" +
        "                    \"window\": null,\n" +
        "                    \"columns\": [\n" +
        "                        {\n" +
        "                            \"functionName\": \"\",\n" +
        "                            \"name\": \"aa\",\n" +
        "                            \"parameters\": [\n" +
        "                                \"a\"\n" +
        "                            ],\n" +
        "                            \"flag\": true\n" +
        "                        },\n" +
        "                        {\n" +
        "                            \"functionName\": \"\",\n" +
        "                            \"name\": \"bb\",\n" +
        "                            \"parameters\": [\n" +
        "                                \"b\"\n" +
        "                            ],\n" +
        "                            \"flag\": true\n" +
        "                        }\n" +
        "                    ]\n" +
        "                },\n" +
        "                \"config\": [\n" +
        "                    {\n" +
        "                        \"480e6018-8d4a-41ec-94c1-bbdad5cf9fa6&output_0 d1d41089-5e33-44b8-80e8-9a953926f3f5&input_0\": [\n" +
        "                            {\n" +
        "                                \"name\": \"a\",\n" +
        "                                \"type\": \"STRING\",\n" +
        "                                \"flag\": true\n" +
        "                            },\n" +
        "                            {\n" +
        "                                \"name\": \"b\",\n" +
        "                                \"type\": \"TIMESTAMP(3)\",\n" +
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
        "            \"id\": \"61ebf235-96bc-4bea-a44d-9bab98bf60a7\",\n" +
        "            \"zIndex\": 4,\n" +
        "            \"source\": {\n" +
        "                \"cell\": \"480e6018-8d4a-41ec-94c1-bbdad5cf9fa6\",\n" +
        "                \"port\": \"output_0\"\n" +
        "            },\n" +
        "            \"target\": {\n" +
        "                \"cell\": \"d1d41089-5e33-44b8-80e8-9a953926f3f5\",\n" +
        "                \"port\": \"input_0\"\n" +
        "            }\n" +
        "        },\n" +
        "        {\n" +
        "            \"position\": {\n" +
        "                \"x\": 30,\n" +
        "                \"y\": 500\n" +
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
        "                    \"text\": \"CepOperator\",\n" +
        "                    \"fontSize\": 2\n" +
        "                }\n" +
        "            },\n" +
        "            \"shape\": \"CepOperator\",\n" +
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
        "            \"id\": \"b4931ba7-f62e-480e-828a-08140cc0bd9b\",\n" +
        "            \"name\": \"CepOperator\",\n" +
        "            \"zIndex\": 5,\n" +
        "            \"data\": {\n" +
        "                \"parameters\": {\n" +
        "                    \"tableName\": \"\",\n" +
        "                    \"partition\": \"aa\",\n" +
        "                    \"orderBy\": \"aa\",\n" +
        "                    \"outPutMode\": \"ONE\",\n" +
        "                    \"skipStrategy\": {\n" +
        "                        \"strategy\": \"LAST_ROW\",\n" +
        "                        \"variable\": \"\"\n" +
        "                    },\n" +
        "                    \"timeSpan\": 0,\n" +
        "                    \"timeUnit\": \"SECOND\",\n" +
        "                    \"patterns\": [\n" +
        "                        {\n" +
        "                            \"variable\": \"e\",\n" +
        "                            \"quantifier\": \"1\"\n" +
        "                        }\n" +
        "                    ],\n" +
        "                    \"columns\": [\n" +
        "                        {\n" +
        "                            \"functionName\": \"\",\n" +
        "                            \"name\": \"o1\",\n" +
        "                            \"parameters\": [\n" +
        "                                \"aa\"\n" +
        "                            ],\n" +
        "                            \"flag\": true\n" +
        "                        },\n" +
        "                        {\n" +
        "                            \"functionName\": \"\",\n" +
        "                            \"name\": \"o2\",\n" +
        "                            \"parameters\": [\n" +
        "                                \"bb\"\n" +
        "                            ],\n" +
        "                            \"flag\": true\n" +
        "                        }\n" +
        "                    ],\n" +
        "                    \"defines\": [\n" +
        "                        {\n" +
        "                            \"variable\": \"e\",\n" +
        "                            \"condition\": \"e as 123\"\n" +
        "                        }\n" +
        "                    ]\n" +
        "                },\n" +
        "                \"config\": [\n" +
        "                    {\n" +
        "                        \"d1d41089-5e33-44b8-80e8-9a953926f3f5&output_0 b4931ba7-f62e-480e-828a-08140cc0bd9b&input_0\": [\n" +
        "                            {\n" +
        "                                \"functionName\": \"\",\n" +
        "                                \"name\": \"aa\",\n" +
        "                                \"parameters\": [\n" +
        "                                    \"a\"\n" +
        "                                ],\n" +
        "                                \"flag\": true\n" +
        "                            },\n" +
        "                            {\n" +
        "                                \"functionName\": \"\",\n" +
        "                                \"name\": \"bb\",\n" +
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
        "            \"id\": \"2e9ca94b-f8e0-4f77-824f-e8796e199f7b\",\n" +
        "            \"zIndex\": 6,\n" +
        "            \"source\": {\n" +
        "                \"cell\": \"d1d41089-5e33-44b8-80e8-9a953926f3f5\",\n" +
        "                \"port\": \"output_0\"\n" +
        "            },\n" +
        "            \"target\": {\n" +
        "                \"cell\": \"b4931ba7-f62e-480e-828a-08140cc0bd9b\",\n" +
        "                \"port\": \"input_0\"\n" +
        "            }\n" +
        "        },\n" +
        "        {\n" +
        "            \"position\": {\n" +
        "                \"x\": 170,\n" +
        "                \"y\": 550\n" +
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
        "            \"id\": \"e7501108-923c-4fc4-bfa1-7f4699af2ef5\",\n" +
        "            \"name\": \"JoinOperator\",\n" +
        "            \"zIndex\": 8,\n" +
        "            \"data\": {\n" +
        "                \"parameters\": {\n" +
        "                    \"tableName\": \"\",\n" +
        "                    \"joinType\": \"LEFT\",\n" +
        "                    \"systemTimeColumn\": \"k1\",\n" +
        "                    \"columnList\": [\n" +
        "                        {\n" +
        "                            \"onLeftColumn\": \"k2\",\n" +
        "                            \"onRightColumn\": \"o1\"\n" +
        "                        }\n" +
        "                    ],\n" +
        "                    \"where\": \"k2=aa;\",\n" +
        "                    \"columns\": [\n" +
        "                        {\n" +
        "                            \"functionName\": \"\",\n" +
        "                            \"name\": \"out1\",\n" +
        "                            \"parameters\": [\n" +
        "                                \"k1\"\n" +
        "                            ],\n" +
        "                            \"inputTable\": \"secondInput\",\n" +
        "                            \"flag\": true\n" +
        "                        },\n" +
        "                        {\n" +
        "                            \"functionName\": \"\",\n" +
        "                            \"name\": \"out2\",\n" +
        "                            \"parameters\": [\n" +
        "                                \"k2\"\n" +
        "                            ],\n" +
        "                            \"inputTable\": \"secondInput\",\n" +
        "                            \"flag\": true\n" +
        "                        },\n" +
        "                        {\n" +
        "                            \"functionName\": \"\",\n" +
        "                            \"name\": \"out3\",\n" +
        "                            \"parameters\": [\n" +
        "                                \"aa\"\n" +
        "                            ],\n" +
        "                            \"inputTable\": \"primaryInput\",\n" +
        "                            \"flag\": true\n" +
        "                        }\n" +
        "                    ]\n" +
        "                },\n" +
        "                \"config\": [\n" +
        "                    {\n" +
        "                        \"b4931ba7-f62e-480e-828a-08140cc0bd9b&output_0 e7501108-923c-4fc4-bfa1-7f4699af2ef5&primaryInput\": [\n" +
        "                            {\n" +
        "                                \"functionName\": \"\",\n" +
        "                                \"name\": \"o1\",\n" +
        "                                \"parameters\": [\n" +
        "                                    \"aa\"\n" +
        "                                ],\n" +
        "                                \"flag\": true\n" +
        "                            },\n" +
        "                            {\n" +
        "                                \"functionName\": \"\",\n" +
        "                                \"name\": \"o2\",\n" +
        "                                \"parameters\": [\n" +
        "                                    \"bb\"\n" +
        "                                ],\n" +
        "                                \"flag\": true\n" +
        "                            }\n" +
        "                        ],\n" +
        "                        \"ed662a81-acc4-49c9-95a7-4c0fa25e023d&output_0 e7501108-923c-4fc4-bfa1-7f4699af2ef5&secondInput\": [\n" +
        "                            {\n" +
        "                                \"name\": \"k1\",\n" +
        "                                \"type\": \"TIMESTAMP(3)\",\n" +
        "                                \"flag\": true\n" +
        "                            },\n" +
        "                            {\n" +
        "                                \"name\": \"k2\",\n" +
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
        "                \"x\": 30,\n" +
        "                \"y\": 570\n" +
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
        "                    \"text\": \"KafKaSourceOperator\",\n" +
        "                    \"fontSize\": 2\n" +
        "                }\n" +
        "            },\n" +
        "            \"shape\": \"KafKaSourceOperator\",\n" +
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
        "            \"id\": \"ed662a81-acc4-49c9-95a7-4c0fa25e023d\",\n" +
        "            \"name\": \"KafKaSourceOperator\",\n" +
        "            \"zIndex\": 9,\n" +
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
        "                            \"name\": \"k1\",\n" +
        "                            \"type\": \"TIMESTAMP(3)\",\n" +
        "                            \"flag\": true\n" +
        "                        },\n" +
        "                        {\n" +
        "                            \"name\": \"k2\",\n" +
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
        "            \"id\": \"ac4587e1-9e22-4ede-a5be-0d1d74fb3393\",\n" +
        "            \"zIndex\": 10,\n" +
        "            \"source\": {\n" +
        "                \"cell\": \"b4931ba7-f62e-480e-828a-08140cc0bd9b\",\n" +
        "                \"port\": \"output_0\"\n" +
        "            },\n" +
        "            \"target\": {\n" +
        "                \"cell\": \"e7501108-923c-4fc4-bfa1-7f4699af2ef5\",\n" +
        "                \"port\": \"primaryInput\"\n" +
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
        "            \"id\": \"794ba9a6-2990-498c-b047-32ed70e8817f\",\n" +
        "            \"zIndex\": 11,\n" +
        "            \"source\": {\n" +
        "                \"cell\": \"ed662a81-acc4-49c9-95a7-4c0fa25e023d\",\n" +
        "                \"port\": \"output_0\"\n" +
        "            },\n" +
        "            \"target\": {\n" +
        "                \"cell\": \"e7501108-923c-4fc4-bfa1-7f4699af2ef5\",\n" +
        "                \"port\": \"secondInput\"\n" +
        "            }\n" +
        "        },\n" +
        "        {\n" +
        "            \"position\": {\n" +
        "                \"x\": 30,\n" +
        "                \"y\": 199\n" +
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
        "                    \"text\": \"AddJarOperator\",\n" +
        "                    \"fontSize\": 2\n" +
        "                }\n" +
        "            },\n" +
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
        "            \"id\": \"5387ccfd-a53a-4336-aa48-872169fe14db\",\n" +
        "            \"name\": \"AddJarOperator\",\n" +
        "            \"zIndex\": 12,\n" +
        "            \"data\": {\n" +
        "                \"parameters\": {\n" +
        "                    \"jars\": [\n" +
        "                        \"C:\\\\Users\\\\1\\\\Downloads\\\\aa_2023-07-19 16_32_22.json\"\n" +
        "                    ]\n" +
        "                },\n" +
        "                \"config\": []\n" +
        "            }\n" +
        "        },\n" +
        "        {\n" +
        "            \"position\": {\n" +
        "                \"x\": 297,\n" +
        "                \"y\": 550\n" +
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
        "            \"id\": \"44a0780a-c584-4198-869c-0b83e3583144\",\n" +
        "            \"name\": \"DuplicateOperator\",\n" +
        "            \"zIndex\": 13,\n" +
        "            \"data\": {\n" +
        "                \"parameters\": {},\n" +
        "                \"config\": [\n" +
        "                    {\n" +
        "                        \"out1\": [\n" +
        "                            {\n" +
        "                                \"functionName\": \"\",\n" +
        "                                \"name\": \"out1\",\n" +
        "                                \"parameters\": [\n" +
        "                                    \"k1\"\n" +
        "                                ],\n" +
        "                                \"inputTable\": \"primaryInput\",\n" +
        "                                \"flag\": true\n" +
        "                            },\n" +
        "                            {\n" +
        "                                \"functionName\": \"\",\n" +
        "                                \"name\": \"out2\",\n" +
        "                                \"parameters\": [\n" +
        "                                    \"k2\"\n" +
        "                                ],\n" +
        "                                \"inputTable\": \"primaryInput\",\n" +
        "                                \"flag\": true\n" +
        "                            },\n" +
        "                            {\n" +
        "                                \"functionName\": \"\",\n" +
        "                                \"name\": \"out3\",\n" +
        "                                \"parameters\": [\n" +
        "                                    \"aa\"\n" +
        "                                ],\n" +
        "                                \"inputTable\": \"primaryInput\",\n" +
        "                                \"flag\": true\n" +
        "                            }\n" +
        "                        ],\n" +
        "                        \"out2\": [\n" +
        "                            {\n" +
        "                                \"functionName\": \"\",\n" +
        "                                \"name\": \"out1\",\n" +
        "                                \"parameters\": [\n" +
        "                                    \"k1\"\n" +
        "                                ],\n" +
        "                                \"inputTable\": \"primaryInput\",\n" +
        "                                \"flag\": true\n" +
        "                            },\n" +
        "                            {\n" +
        "                                \"functionName\": \"\",\n" +
        "                                \"name\": \"out2\",\n" +
        "                                \"parameters\": [\n" +
        "                                    \"k2\"\n" +
        "                                ],\n" +
        "                                \"inputTable\": \"primaryInput\",\n" +
        "                                \"flag\": true\n" +
        "                            },\n" +
        "                            {\n" +
        "                                \"functionName\": \"\",\n" +
        "                                \"name\": \"out3\",\n" +
        "                                \"parameters\": [\n" +
        "                                    \"aa\"\n" +
        "                                ],\n" +
        "                                \"inputTable\": \"primaryInput\",\n" +
        "                                \"flag\": true\n" +
        "                            }\n" +
        "                        ],\n" +
        "                        \"e7501108-923c-4fc4-bfa1-7f4699af2ef5&output_0 44a0780a-c584-4198-869c-0b83e3583144&input_0\": [\n" +
        "                            {\n" +
        "                                \"functionName\": \"\",\n" +
        "                                \"name\": \"out1\",\n" +
        "                                \"parameters\": [\n" +
        "                                    \"k1\"\n" +
        "                                ],\n" +
        "                                \"inputTable\": \"primaryInput\",\n" +
        "                                \"flag\": true\n" +
        "                            },\n" +
        "                            {\n" +
        "                                \"functionName\": \"\",\n" +
        "                                \"name\": \"out2\",\n" +
        "                                \"parameters\": [\n" +
        "                                    \"k2\"\n" +
        "                                ],\n" +
        "                                \"inputTable\": \"primaryInput\",\n" +
        "                                \"flag\": true\n" +
        "                            },\n" +
        "                            {\n" +
        "                                \"functionName\": \"\",\n" +
        "                                \"name\": \"out3\",\n" +
        "                                \"parameters\": [\n" +
        "                                    \"aa\"\n" +
        "                                ],\n" +
        "                                \"inputTable\": \"primaryInput\",\n" +
        "                                \"flag\": true\n" +
        "                            }\n" +
        "                        ],\n" +
        "                        \"44a0780a-c584-4198-869c-0b83e3583144&out1 41e33297-edba-42c6-817c-be73ff9eb107&input_0\": [\n" +
        "                            {\n" +
        "                                \"functionName\": \"\",\n" +
        "                                \"name\": \"out1\",\n" +
        "                                \"parameters\": [\n" +
        "                                    \"k1\"\n" +
        "                                ],\n" +
        "                                \"inputTable\": \"primaryInput\",\n" +
        "                                \"flag\": true\n" +
        "                            },\n" +
        "                            {\n" +
        "                                \"functionName\": \"\",\n" +
        "                                \"name\": \"out2\",\n" +
        "                                \"parameters\": [\n" +
        "                                    \"k2\"\n" +
        "                                ],\n" +
        "                                \"inputTable\": \"primaryInput\",\n" +
        "                                \"flag\": true\n" +
        "                            },\n" +
        "                            {\n" +
        "                                \"functionName\": \"\",\n" +
        "                                \"name\": \"out3\",\n" +
        "                                \"parameters\": [\n" +
        "                                    \"aa\"\n" +
        "                                ],\n" +
        "                                \"inputTable\": \"primaryInput\",\n" +
        "                                \"flag\": true\n" +
        "                            }\n" +
        "                        ],\n" +
        "                        \"44a0780a-c584-4198-869c-0b83e3583144&out2 c9df1602-6a1c-4b8c-8bc1-828e6dd0dc9f&input_0\": [\n" +
        "                            {\n" +
        "                                \"functionName\": \"\",\n" +
        "                                \"name\": \"out1\",\n" +
        "                                \"parameters\": [\n" +
        "                                    \"k1\"\n" +
        "                                ],\n" +
        "                                \"inputTable\": \"primaryInput\",\n" +
        "                                \"flag\": true\n" +
        "                            },\n" +
        "                            {\n" +
        "                                \"functionName\": \"\",\n" +
        "                                \"name\": \"out2\",\n" +
        "                                \"parameters\": [\n" +
        "                                    \"k2\"\n" +
        "                                ],\n" +
        "                                \"inputTable\": \"primaryInput\",\n" +
        "                                \"flag\": true\n" +
        "                            },\n" +
        "                            {\n" +
        "                                \"functionName\": \"\",\n" +
        "                                \"name\": \"out3\",\n" +
        "                                \"parameters\": [\n" +
        "                                    \"aa\"\n" +
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
        "            \"id\": \"bbb33f69-bbcd-4142-bf23-288c834288de\",\n" +
        "            \"zIndex\": 14,\n" +
        "            \"source\": {\n" +
        "                \"cell\": \"e7501108-923c-4fc4-bfa1-7f4699af2ef5\",\n" +
        "                \"port\": \"output_0\"\n" +
        "            },\n" +
        "            \"target\": {\n" +
        "                \"cell\": \"44a0780a-c584-4198-869c-0b83e3583144\",\n" +
        "                \"port\": \"input_0\"\n" +
        "            }\n" +
        "        },\n" +
        "        {\n" +
        "            \"position\": {\n" +
        "                \"x\": 430,\n" +
        "                \"y\": 488\n" +
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
        "                    \"text\": \"CustomerSinkOperator\",\n" +
        "                    \"fontSize\": 2\n" +
        "                }\n" +
        "            },\n" +
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
        "            \"id\": \"41e33297-edba-42c6-817c-be73ff9eb107\",\n" +
        "            \"name\": \"CustomerSinkOperator\",\n" +
        "            \"zIndex\": 15,\n" +
        "            \"data\": {\n" +
        "                \"parameters\": {\n" +
        "                    \"tableName\": \"\",\n" +
        "                    \"other\": [\n" +
        "                        {\n" +
        "                            \"key\": \"connector\",\n" +
        "                            \"values\": \"udp\"\n" +
        "                        },\n" +
        "                        {\n" +
        "                            \"key\": \"format\",\n" +
        "                            \"values\": \"json\"\n" +
        "                        }\n" +
        "                    ],\n" +
        "                    \"columns\": [\n" +
        "                        {\n" +
        "                            \"name\": \"aaa\",\n" +
        "                            \"type\": \"STRING\",\n" +
        "                            \"flag\": true\n" +
        "                        },\n" +
        "                        {\n" +
        "                            \"name\": \"bbb\",\n" +
        "                            \"type\": \"STRING\",\n" +
        "                            \"flag\": true\n" +
        "                        },\n" +
        "                        {\n" +
        "                            \"name\": \"ccc\",\n" +
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
        "                        \"44a0780a-c584-4198-869c-0b83e3583144&out1 41e33297-edba-42c6-817c-be73ff9eb107&input_0\": [\n" +
        "                            {\n" +
        "                                \"functionName\": \"\",\n" +
        "                                \"name\": \"out1\",\n" +
        "                                \"parameters\": [\n" +
        "                                    \"k1\"\n" +
        "                                ],\n" +
        "                                \"inputTable\": \"primaryInput\",\n" +
        "                                \"flag\": true\n" +
        "                            },\n" +
        "                            {\n" +
        "                                \"functionName\": \"\",\n" +
        "                                \"name\": \"out2\",\n" +
        "                                \"parameters\": [\n" +
        "                                    \"k2\"\n" +
        "                                ],\n" +
        "                                \"inputTable\": \"primaryInput\",\n" +
        "                                \"flag\": true\n" +
        "                            },\n" +
        "                            {\n" +
        "                                \"functionName\": \"\",\n" +
        "                                \"name\": \"out3\",\n" +
        "                                \"parameters\": [\n" +
        "                                    \"aa\"\n" +
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
        "            \"id\": \"ec0ca3ac-d5c7-40fe-8054-3904663da616\",\n" +
        "            \"zIndex\": 16,\n" +
        "            \"source\": {\n" +
        "                \"cell\": \"44a0780a-c584-4198-869c-0b83e3583144\",\n" +
        "                \"port\": \"out1\"\n" +
        "            },\n" +
        "            \"target\": {\n" +
        "                \"cell\": \"41e33297-edba-42c6-817c-be73ff9eb107\",\n" +
        "                \"port\": \"input_0\"\n" +
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
        "            \"id\": \"ad53917e-1ec6-4dfb-bf65-b5527ffd8efc\",\n" +
        "            \"zIndex\": 17,\n" +
        "            \"source\": {\n" +
        "                \"cell\": \"44a0780a-c584-4198-869c-0b83e3583144\",\n" +
        "                \"port\": \"out2\"\n" +
        "            },\n" +
        "            \"target\": {\n" +
        "                \"cell\": \"c9df1602-6a1c-4b8c-8bc1-828e6dd0dc9f\",\n" +
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
            final String build = su.build().get("SQL").toString();
            System.out.println(build);

            OperatorPreviewBuilder operatorPreviewBuilder = new OperatorPreviewBuilder(x6_json1);
            String s = operatorPreviewBuilder.operatorPreview();
            System.out.println(s);

        }
}
