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
        "                \"x\": 450,\n" +
        "                \"y\": 450\n" +
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
        "                    \"primary\": \"\",\n" +
        "                    \"watermark\": {\n" +
        "                        \"column\": \"\",\n" +
        "                        \"timeSpan\": 0,\n" +
        "                        \"timeUnit\": \"SECOND\"\n" +
        "                    }\n" +
        "                },\n" +
        "                \"config\": [\n" +
        "                    {\n" +
        "                        \"8fd34176-d735-4a6f-a562-eb661345e049&output_0 c9df1602-6a1c-4b8c-8bc1-828e6dd0dc9f&input_0\": [\n" +
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
        "                        ]\n" +
        "                    }\n" +
        "                ]\n" +
        "            }\n" +
        "        },\n" +
        "        {\n" +
        "            \"position\": {\n" +
        "                \"x\": 187,\n" +
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
        "                \"x\": 305,\n" +
        "                \"y\": 450\n" +
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
        "            \"id\": \"8fd34176-d735-4a6f-a562-eb661345e049\",\n" +
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
        "                    \"timeSpan\": 10,\n" +
        "                    \"timeUnit\": \"SECOND\",\n" +
        "                    \"patterns\": [\n" +
        "                        {\n" +
        "                            \"variable\": \"e\",\n" +
        "                            \"quantifier\": \"{+}\"\n" +
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
        "                            \"condition\": \"aa=1\"\n" +
        "                        }\n" +
        "                    ]\n" +
        "                },\n" +
        "                \"config\": [\n" +
        "                    {\n" +
        "                        \"d1d41089-5e33-44b8-80e8-9a953926f3f5&output_0 8fd34176-d735-4a6f-a562-eb661345e049&input_0\": [\n" +
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
        "            \"id\": \"b15dbc24-d422-4ffc-bcee-93d80b615e6a\",\n" +
        "            \"source\": {\n" +
        "                \"cell\": \"d1d41089-5e33-44b8-80e8-9a953926f3f5\",\n" +
        "                \"port\": \"output_0\"\n" +
        "            },\n" +
        "            \"target\": {\n" +
        "                \"cell\": \"8fd34176-d735-4a6f-a562-eb661345e049\",\n" +
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
        "            \"id\": \"b71d9338-f691-43ec-b1d3-c8e8c1838902\",\n" +
        "            \"source\": {\n" +
        "                \"cell\": \"8fd34176-d735-4a6f-a562-eb661345e049\",\n" +
        "                \"port\": \"output_0\"\n" +
        "            },\n" +
        "            \"target\": {\n" +
        "                \"cell\": \"c9df1602-6a1c-4b8c-8bc1-828e6dd0dc9f\",\n" +
        "                \"port\": \"input_0\"\n" +
        "            },\n" +
        "            \"zIndex\": 7\n" +
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
