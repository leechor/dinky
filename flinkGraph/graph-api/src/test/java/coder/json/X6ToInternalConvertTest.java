package coder.json;

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

//static String x6_json1 = "{\n" +
//        "    \"cells\": [\n" +
//        "        {\n" +
//        "            \"position\": {\n" +
//        "                \"x\": 150,\n" +
//        "                \"y\": 250\n" +
//        "            },\n" +
//        "            \"size\": {\n" +
//        "                \"width\": 70,\n" +
//        "                \"height\": 50\n" +
//        "            },\n" +
//        "            \"view\": \"react-shape-view\",\n" +
//        "            \"attrs\": {\n" +
//        "                \"body\": {\n" +
//        "                    \"rx\": 7,\n" +
//        "                    \"ry\": 6\n" +
//        "                },\n" +
//        "                \"text\": {\n" +
//        "                    \"text\": \"CepOperator\",\n" +
//        "                    \"fontSize\": 2\n" +
//        "                }\n" +
//        "            },\n" +
//        "            \"shape\": \"CepOperator\",\n" +
//        "            \"ports\": {\n" +
//        "                \"groups\": {\n" +
//        "                    \"outputs\": {\n" +
//        "                        \"zIndex\": 999,\n" +
//        "                        \"position\": \"right\",\n" +
//        "                        \"markup\": {\n" +
//        "                            \"tagName\": \"path\",\n" +
//        "                            \"selector\": \"path\",\n" +
//        "                            \"attrs\": {\n" +
//        "                                \"d\": \"m-6,0,a5,5.5 0 0 1 12,0\",\n" +
//        "                                \"fill\": \"#b2a2e9\",\n" +
//        "                                \"transform\": \"rotate(90)\",\n" +
//        "                                \"strokeWidth\": 1,\n" +
//        "                                \"stroke\": \"null\"\n" +
//        "                            }\n" +
//        "                        },\n" +
//        "                        \"attrs\": {\n" +
//        "                            \"path\": {\n" +
//        "                                \"r\": 8,\n" +
//        "                                \"magnet\": true,\n" +
//        "                                \"style\": {\n" +
//        "                                    \"visibility\": \"hidden\"\n" +
//        "                                }\n" +
//        "                            }\n" +
//        "                        }\n" +
//        "                    },\n" +
//        "                    \"inputs\": {\n" +
//        "                        \"zIndex\": 999,\n" +
//        "                        \"position\": \"left\",\n" +
//        "                        \"markup\": {\n" +
//        "                            \"tagName\": \"path\",\n" +
//        "                            \"selector\": \"path\",\n" +
//        "                            \"attrs\": {\n" +
//        "                                \"d\": \"m-6,0,a5,5.5 0 0 1 12,0\",\n" +
//        "                                \"fill\": \"#b2a2e9\",\n" +
//        "                                \"transform\": \"rotate(-90)\",\n" +
//        "                                \"strokeWidth\": 1,\n" +
//        "                                \"stroke\": \"null\"\n" +
//        "                            }\n" +
//        "                        },\n" +
//        "                        \"attrs\": {\n" +
//        "                            \"path\": {\n" +
//        "                                \"r\": 10,\n" +
//        "                                \"magnet\": true,\n" +
//        "                                \"style\": {\n" +
//        "                                    \"visibility\": \"hidden\"\n" +
//        "                                }\n" +
//        "                            }\n" +
//        "                        }\n" +
//        "                    }\n" +
//        "                },\n" +
//        "                \"items\": [\n" +
//        "                    {\n" +
//        "                        \"group\": \"inputs\",\n" +
//        "                        \"id\": \"input_0\",\n" +
//        "                        \"zIndex\": 999,\n" +
//        "                        \"attrs\": {\n" +
//        "                            \"text\": {\n" +
//        "                                \"text\": \"input_0\",\n" +
//        "                                \"style\": {\n" +
//        "                                    \"visibility\": \"hidden\",\n" +
//        "                                    \"fontSize\": 10,\n" +
//        "                                    \"fill\": \"#3B4351\"\n" +
//        "                                }\n" +
//        "                            }\n" +
//        "                        },\n" +
//        "                        \"label\": {\n" +
//        "                            \"position\": \"bottom\"\n" +
//        "                        }\n" +
//        "                    },\n" +
//        "                    {\n" +
//        "                        \"group\": \"outputs\",\n" +
//        "                        \"zIndex\": 999,\n" +
//        "                        \"id\": \"output_0\",\n" +
//        "                        \"attrs\": {\n" +
//        "                            \"text\": {\n" +
//        "                                \"text\": \"output_0\",\n" +
//        "                                \"style\": {\n" +
//        "                                    \"visibility\": \"hidden\",\n" +
//        "                                    \"fontSize\": 10,\n" +
//        "                                    \"fill\": \"#3B4351\"\n" +
//        "                                }\n" +
//        "                            }\n" +
//        "                        },\n" +
//        "                        \"label\": {\n" +
//        "                            \"position\": \"bottom\"\n" +
//        "                        }\n" +
//        "                    }\n" +
//        "                ]\n" +
//        "            },\n" +
//        "            \"id\": \"60675ca9-ccb4-45de-9188-4bacdcde4e27\",\n" +
//        "            \"name\": \"CepOperator\",\n" +
//        "            \"isStencil\": false,\n" +
//        "            \"zIndex\": 1,\n" +
//        "            \"data\": {\n" +
//        "                \"parameters\": {\n" +
//        "                    \"tableName\": \"\",\n" +
//        "                    \"partition\": \"a\",\n" +
//        "                    \"orderBy\": \"a\",\n" +
//        "                    \"outPutMode\": \"ONE\",\n" +
//        "                    \"skipStrategy\": {\n" +
//        "                        \"strategy\": \"LAST_ROW\",\n" +
//        "                        \"variable\": 0\n" +
//        "                    },\n" +
//        "                    \"timeSpan\": 0,\n" +
//        "                    \"timeUnit\": \"SECOND\",\n" +
//        "                    \"patterns\": [\n" +
//        "                        {\n" +
//        "                            \"variable\": \"e1\",\n" +
//        "                            \"quantifier\": \"+\"\n" +
//        "                        }\n" +
//        "                    ],\n" +
//        "                    \"columns\": [\n" +
//        "                        {\n" +
//        "                            \"outName\": \"out1\",\n" +
//        "                            \"function\": [\n" +
//        "                                {\n" +
//        "                                    \"functionName\": \"\",\n" +
//        "                                    \"recursionFunc\": [],\n" +
//        "                                    \"recursionName\": [\n" +
//        "                                        {\n" +
//        "                                            \"name\": [\n" +
//        "                                                \"a\"\n" +
//        "                                            ],\n" +
//        "                                            \"recursionFunc\": [],\n" +
//        "                                            \"recursionName\": []\n" +
//        "                                        }\n" +
//        "                                    ]\n" +
//        "                                }\n" +
//        "                            ],\n" +
//        "                            \"name\": \"asdasd\",\n" +
//        "                            \"flag\": true,\n" +
//        "                            \"decs\": \"true\"\n" +
//        "                        },\n" +
//        "                        {\n" +
//        "                            \"outName\": \"out2\",\n" +
//        "                            \"function\": [\n" +
//        "                                {\n" +
//        "                                    \"functionName\": \"f1\",\n" +
//        "                                    \"recursionFunc\": [\n" +
//        "                                        {\n" +
//        "                                            \"functionName\": \"f2\",\n" +
//        "                                            \"recursionFunc\": [\n" +
//        "                                                {\n" +
//        "                                                    \"functionName\": \"\",\n" +
//        "                                                    \"recursionFunc\": [],\n" +
//        "                                                    \"recursionName\": [\n" +
//        "                                                        {\n" +
//        "                                                            \"name\": [\n" +
//        "                                                                \"a\",\n" +
//        "                                                                \"b\"\n" +
//        "                                                            ],\n" +
//        "                                                            \"recursionFunc\": [],\n" +
//        "                                                            \"recursionName\": [\n" +
//        "                                                                {\n" +
//        "                                                                    \"name\": [\n" +
//        "                                                                        \"a!\",\n" +
//        "                                                                        \"b!\"\n" +
//        "                                                                    ],\n" +
//        "                                                                    \"recursionFunc\": [],\n" +
//        "                                                                    \"recursionName\": []\n" +
//        "                                                                }\n" +
//        "                                                            ]\n" +
//        "                                                        }\n" +
//        "                                                    ]\n" +
//        "                                                }\n" +
//        "                                            ],\n" +
//        "                                            \"recursionName\": []\n" +
//        "                                        }\n" +
//        "                                    ],\n" +
//        "                                    \"recursionName\": []\n" +
//        "                                }\n" +
//        "                            ],\n" +
//        "                            \"name\": \"aadsa\",\n" +
//        "                            \"flag\": true,\n" +
//        "                            \"decs\": \"true\"\n" +
//        "                        }\n" +
//        "                    ],\n" +
//        "                    \"defines\": [\n" +
//        "                        {\n" +
//        "                            \"variable\": \"\",\n" +
//        "                            \"condition\": \"\"\n" +
//        "                        }\n" +
//        "                    ]\n" +
//        "                },\n" +
//        "                \"config\": [\n" +
//        "                    {\n" +
//        "                        \"c36b2857-34cc-441f-93f6-a355fad391cc&output_0 60675ca9-ccb4-45de-9188-4bacdcde4e27&input_0\": [\n" +
//        "                            {\n" +
//        "                                \"name\": \"a\",\n" +
//        "                                \"type\": \"STRING\",\n" +
//        "                                \"flag\": true\n" +
//        "                            },\n" +
//        "                            {\n" +
//        "                                \"name\": \"b\",\n" +
//        "                                \"type\": \"STRING\",\n" +
//        "                                \"flag\": true\n" +
//        "                            }\n" +
//        "                        ]\n" +
//        "                    }\n" +
//        "                ]\n" +
//        "            }\n" +
//        "        },\n" +
//        "        {\n" +
//        "            \"position\": {\n" +
//        "                \"x\": 19,\n" +
//        "                \"y\": 250\n" +
//        "            },\n" +
//        "            \"size\": {\n" +
//        "                \"width\": 70,\n" +
//        "                \"height\": 50\n" +
//        "            },\n" +
//        "            \"view\": \"react-shape-view\",\n" +
//        "            \"attrs\": {\n" +
//        "                \"body\": {\n" +
//        "                    \"rx\": 7,\n" +
//        "                    \"ry\": 6\n" +
//        "                },\n" +
//        "                \"text\": {\n" +
//        "                    \"text\": \"MySQL数据源\",\n" +
//        "                    \"fontSize\": 2\n" +
//        "                }\n" +
//        "            },\n" +
//        "            \"shape\": \"MysqlSourceOperator\",\n" +
//        "            \"ports\": {\n" +
//        "                \"groups\": {\n" +
//        "                    \"outputs\": {\n" +
//        "                        \"zIndex\": 999,\n" +
//        "                        \"position\": \"right\",\n" +
//        "                        \"markup\": {\n" +
//        "                            \"tagName\": \"path\",\n" +
//        "                            \"selector\": \"path\",\n" +
//        "                            \"attrs\": {\n" +
//        "                                \"d\": \"m-6,0,a5,5.5 0 0 1 12,0\",\n" +
//        "                                \"fill\": \"#b2a2e9\",\n" +
//        "                                \"transform\": \"rotate(90)\",\n" +
//        "                                \"strokeWidth\": 1,\n" +
//        "                                \"stroke\": \"null\"\n" +
//        "                            }\n" +
//        "                        },\n" +
//        "                        \"attrs\": {\n" +
//        "                            \"path\": {\n" +
//        "                                \"r\": 8,\n" +
//        "                                \"magnet\": true,\n" +
//        "                                \"style\": {\n" +
//        "                                    \"visibility\": \"hidden\"\n" +
//        "                                }\n" +
//        "                            }\n" +
//        "                        }\n" +
//        "                    },\n" +
//        "                    \"inputs\": {\n" +
//        "                        \"zIndex\": 999,\n" +
//        "                        \"position\": \"left\",\n" +
//        "                        \"markup\": {\n" +
//        "                            \"tagName\": \"path\",\n" +
//        "                            \"selector\": \"path\",\n" +
//        "                            \"attrs\": {\n" +
//        "                                \"d\": \"m-6,0,a5,5.5 0 0 1 12,0\",\n" +
//        "                                \"fill\": \"#b2a2e9\",\n" +
//        "                                \"transform\": \"rotate(-90)\",\n" +
//        "                                \"strokeWidth\": 1,\n" +
//        "                                \"stroke\": \"null\"\n" +
//        "                            }\n" +
//        "                        },\n" +
//        "                        \"attrs\": {\n" +
//        "                            \"path\": {\n" +
//        "                                \"r\": 10,\n" +
//        "                                \"magnet\": true,\n" +
//        "                                \"style\": {\n" +
//        "                                    \"visibility\": \"hidden\"\n" +
//        "                                }\n" +
//        "                            }\n" +
//        "                        }\n" +
//        "                    }\n" +
//        "                },\n" +
//        "                \"items\": [\n" +
//        "                    {\n" +
//        "                        \"group\": \"outputs\",\n" +
//        "                        \"zIndex\": 999,\n" +
//        "                        \"id\": \"output_0\",\n" +
//        "                        \"attrs\": {\n" +
//        "                            \"text\": {\n" +
//        "                                \"text\": \"output_0\",\n" +
//        "                                \"style\": {\n" +
//        "                                    \"visibility\": \"hidden\",\n" +
//        "                                    \"fontSize\": 10,\n" +
//        "                                    \"fill\": \"#3B4351\"\n" +
//        "                                }\n" +
//        "                            }\n" +
//        "                        },\n" +
//        "                        \"label\": {\n" +
//        "                            \"position\": \"bottom\"\n" +
//        "                        }\n" +
//        "                    }\n" +
//        "                ]\n" +
//        "            },\n" +
//        "            \"id\": \"c36b2857-34cc-441f-93f6-a355fad391cc\",\n" +
//        "            \"name\": \"MySQL数据源\",\n" +
//        "            \"isStencil\": false,\n" +
//        "            \"zIndex\": 2,\n" +
//        "            \"data\": {\n" +
//        "                \"parameters\": {\n" +
//        "                    \"tableName\": \"\",\n" +
//        "                    \"connector\": \"jdbc\",\n" +
//        "                    \"url\": \"asad\",\n" +
//        "                    \"username\": \"root\",\n" +
//        "                    \"password\": \"123456\",\n" +
//        "                    \"other\": [],\n" +
//        "                    \"columns\": [\n" +
//        "                        {\n" +
//        "                            \"name\": \"a\",\n" +
//        "                            \"type\": \"STRING\",\n" +
//        "                            \"flag\": true\n" +
//        "                        },\n" +
//        "                        {\n" +
//        "                            \"name\": \"b\",\n" +
//        "                            \"type\": \"STRING\",\n" +
//        "                            \"flag\": true\n" +
//        "                        }\n" +
//        "                    ],\n" +
//        "                    \"primary\": \"\",\n" +
//        "                    \"watermark\": {\n" +
//        "                        \"column\": \"\",\n" +
//        "                        \"timeSpan\": 0,\n" +
//        "                        \"timeUnit\": \"SECOND\"\n" +
//        "                    }\n" +
//        "                },\n" +
//        "                \"config\": []\n" +
//        "            }\n" +
//        "        },\n" +
//        "        {\n" +
//        "            \"position\": {\n" +
//        "                \"x\": 306,\n" +
//        "                \"y\": 250\n" +
//        "            },\n" +
//        "            \"size\": {\n" +
//        "                \"width\": 70,\n" +
//        "                \"height\": 50\n" +
//        "            },\n" +
//        "            \"view\": \"react-shape-view\",\n" +
//        "            \"attrs\": {\n" +
//        "                \"body\": {\n" +
//        "                    \"rx\": 7,\n" +
//        "                    \"ry\": 6\n" +
//        "                },\n" +
//        "                \"text\": {\n" +
//        "                    \"text\": \"MysqlSink\",\n" +
//        "                    \"fontSize\": 2\n" +
//        "                }\n" +
//        "            },\n" +
//        "            \"shape\": \"MysqlSinkOperator\",\n" +
//        "            \"ports\": {\n" +
//        "                \"groups\": {\n" +
//        "                    \"outputs\": {\n" +
//        "                        \"zIndex\": 999,\n" +
//        "                        \"position\": \"right\",\n" +
//        "                        \"markup\": {\n" +
//        "                            \"tagName\": \"path\",\n" +
//        "                            \"selector\": \"path\",\n" +
//        "                            \"attrs\": {\n" +
//        "                                \"d\": \"m-6,0,a5,5.5 0 0 1 12,0\",\n" +
//        "                                \"fill\": \"#b2a2e9\",\n" +
//        "                                \"transform\": \"rotate(90)\",\n" +
//        "                                \"strokeWidth\": 1,\n" +
//        "                                \"stroke\": \"null\"\n" +
//        "                            }\n" +
//        "                        },\n" +
//        "                        \"attrs\": {\n" +
//        "                            \"path\": {\n" +
//        "                                \"r\": 8,\n" +
//        "                                \"magnet\": true,\n" +
//        "                                \"style\": {\n" +
//        "                                    \"visibility\": \"hidden\"\n" +
//        "                                }\n" +
//        "                            }\n" +
//        "                        }\n" +
//        "                    },\n" +
//        "                    \"inputs\": {\n" +
//        "                        \"zIndex\": 999,\n" +
//        "                        \"position\": \"left\",\n" +
//        "                        \"markup\": {\n" +
//        "                            \"tagName\": \"path\",\n" +
//        "                            \"selector\": \"path\",\n" +
//        "                            \"attrs\": {\n" +
//        "                                \"d\": \"m-6,0,a5,5.5 0 0 1 12,0\",\n" +
//        "                                \"fill\": \"#b2a2e9\",\n" +
//        "                                \"transform\": \"rotate(-90)\",\n" +
//        "                                \"strokeWidth\": 1,\n" +
//        "                                \"stroke\": \"null\"\n" +
//        "                            }\n" +
//        "                        },\n" +
//        "                        \"attrs\": {\n" +
//        "                            \"path\": {\n" +
//        "                                \"r\": 10,\n" +
//        "                                \"magnet\": true,\n" +
//        "                                \"style\": {\n" +
//        "                                    \"visibility\": \"hidden\"\n" +
//        "                                }\n" +
//        "                            }\n" +
//        "                        }\n" +
//        "                    }\n" +
//        "                },\n" +
//        "                \"items\": [\n" +
//        "                    {\n" +
//        "                        \"group\": \"inputs\",\n" +
//        "                        \"id\": \"input_0\",\n" +
//        "                        \"zIndex\": 999,\n" +
//        "                        \"attrs\": {\n" +
//        "                            \"text\": {\n" +
//        "                                \"text\": \"input_0\",\n" +
//        "                                \"style\": {\n" +
//        "                                    \"visibility\": \"hidden\",\n" +
//        "                                    \"fontSize\": 10,\n" +
//        "                                    \"fill\": \"#3B4351\"\n" +
//        "                                }\n" +
//        "                            }\n" +
//        "                        },\n" +
//        "                        \"label\": {\n" +
//        "                            \"position\": \"bottom\"\n" +
//        "                        }\n" +
//        "                    }\n" +
//        "                ]\n" +
//        "            },\n" +
//        "            \"id\": \"f2598597-2604-48b6-b8e1-87afb90b552d\",\n" +
//        "            \"name\": \"MysqlSink\",\n" +
//        "            \"isStencil\": false,\n" +
//        "            \"zIndex\": 3,\n" +
//        "            \"data\": {\n" +
//        "                \"parameters\": {\n" +
//        "                    \"tableName\": \"\",\n" +
//        "                    \"connector\": \"jdbc\",\n" +
//        "                    \"url\": \"asad\",\n" +
//        "                    \"username\": \"root\",\n" +
//        "                    \"password\": \"123456\",\n" +
//        "                    \"other\": [],\n" +
//        "                    \"columns\": [\n" +
//        "                        {\n" +
//        "                            \"name\": \"a1\",\n" +
//        "                            \"type\": \"STRING\",\n" +
//        "                            \"flag\": true,\n" +
//        "                            \"decs\": \"true\"\n" +
//        "                        },\n" +
//        "                        {\n" +
//        "                            \"name\": \"a2\",\n" +
//        "                            \"type\": \"STRING\",\n" +
//        "                            \"flag\": true,\n" +
//        "                            \"decs\": \"true\"\n" +
//        "                        }\n" +
//        "                    ],\n" +
//        "                    \"primary\": \"\",\n" +
//        "                    \"watermark\": {\n" +
//        "                        \"column\": \"\",\n" +
//        "                        \"timeSpan\": 0,\n" +
//        "                        \"timeUnit\": \"SECOND\"\n" +
//        "                    }\n" +
//        "                },\n" +
//        "                \"config\": [\n" +
//        "                    {\n" +
//        "                        \"60675ca9-ccb4-45de-9188-4bacdcde4e27&output_0 f2598597-2604-48b6-b8e1-87afb90b552d&input_0\": [\n" +
//        "                            {\n" +
//        "                                \"outName\": \"out1\",\n" +
//        "                                \"function\": [\n" +
//        "                                    {\n" +
//        "                                        \"functionName\": \"\",\n" +
//        "                                        \"recursionFunc\": [],\n" +
//        "                                        \"recursionName\": [\n" +
//        "                                            {\n" +
//        "                                                \"name\": [\n" +
//        "                                                    \"a\"\n" +
//        "                                                ],\n" +
//        "                                                \"recursionFunc\": [],\n" +
//        "                                                \"recursionName\": []\n" +
//        "                                            }\n" +
//        "                                        ]\n" +
//        "                                    }\n" +
//        "                                ],\n" +
//        "                                \"name\": \"asdasd\",\n" +
//        "                                \"flag\": true,\n" +
//        "                                \"decs\": \"true\"\n" +
//        "                            },\n" +
//        "                            {\n" +
//        "                                \"outName\": \"out2\",\n" +
//        "                                \"function\": [\n" +
//        "                                    {\n" +
//        "                                        \"functionName\": \"f1\",\n" +
//        "                                        \"recursionFunc\": [\n" +
//        "                                            {\n" +
//        "                                                \"functionName\": \"f2\",\n" +
//        "                                                \"recursionFunc\": [\n" +
//        "                                                    {\n" +
//        "                                                        \"functionName\": \"\",\n" +
//        "                                                        \"recursionFunc\": [],\n" +
//        "                                                        \"recursionName\": [\n" +
//        "                                                            {\n" +
//        "                                                                \"name\": [\n" +
//        "                                                                    \"a\",\n" +
//        "                                                                    \"b\"\n" +
//        "                                                                ],\n" +
//        "                                                                \"recursionFunc\": [],\n" +
//        "                                                                \"recursionName\": [\n" +
//        "                                                                    {\n" +
//        "                                                                        \"name\": [\n" +
//        "                                                                            \"a!\",\n" +
//        "                                                                            \"b!\"\n" +
//        "                                                                        ],\n" +
//        "                                                                        \"recursionFunc\": [],\n" +
//        "                                                                        \"recursionName\": []\n" +
//        "                                                                    }\n" +
//        "                                                                ]\n" +
//        "                                                            }\n" +
//        "                                                        ]\n" +
//        "                                                    }\n" +
//        "                                                ],\n" +
//        "                                                \"recursionName\": []\n" +
//        "                                            }\n" +
//        "                                        ],\n" +
//        "                                        \"recursionName\": []\n" +
//        "                                    }\n" +
//        "                                ],\n" +
//        "                                \"name\": \"aadsa\",\n" +
//        "                                \"flag\": true,\n" +
//        "                                \"decs\": \"true\"\n" +
//        "                            }\n" +
//        "                        ]\n" +
//        "                    }\n" +
//        "                ]\n" +
//        "            }\n" +
//        "        },\n" +
//        "        {\n" +
//        "            \"shape\": \"edge\",\n" +
//        "            \"attrs\": {\n" +
//        "                \"line\": {\n" +
//        "                    \"stroke\": \"#b2a2e9\",\n" +
//        "                    \"targetMarker\": \"\"\n" +
//        "                }\n" +
//        "            },\n" +
//        "            \"id\": \"677520e5-ec79-41b9-8592-985f71bc1861\",\n" +
//        "            \"source\": {\n" +
//        "                \"cell\": \"c36b2857-34cc-441f-93f6-a355fad391cc\",\n" +
//        "                \"port\": \"output_0\"\n" +
//        "            },\n" +
//        "            \"target\": {\n" +
//        "                \"cell\": \"60675ca9-ccb4-45de-9188-4bacdcde4e27\",\n" +
//        "                \"port\": \"input_0\"\n" +
//        "            },\n" +
//        "            \"zIndex\": 4\n" +
//        "        },\n" +
//        "        {\n" +
//        "            \"shape\": \"edge\",\n" +
//        "            \"attrs\": {\n" +
//        "                \"line\": {\n" +
//        "                    \"stroke\": \"#b2a2e9\",\n" +
//        "                    \"targetMarker\": \"\"\n" +
//        "                }\n" +
//        "            },\n" +
//        "            \"id\": \"209f7139-44f8-4704-9012-57b81c2acad9\",\n" +
//        "            \"source\": {\n" +
//        "                \"cell\": \"60675ca9-ccb4-45de-9188-4bacdcde4e27\",\n" +
//        "                \"port\": \"output_0\"\n" +
//        "            },\n" +
//        "            \"target\": {\n" +
//        "                \"cell\": \"f2598597-2604-48b6-b8e1-87afb90b552d\",\n" +
//        "                \"port\": \"input_0\"\n" +
//        "            },\n" +
//        "            \"zIndex\": 5\n" +
//        "        }\n" +
//        "    ]\n" +
//        "}";

//static String x6_json1 = "{\n" +
//        "    \"cells\": [\n" +
//        "        {\n" +
//        "            \"position\": {\n" +
//        "                \"x\": 50,\n" +
//        "                \"y\": 210\n" +
//        "            },\n" +
//        "            \"size\": {\n" +
//        "                \"width\": 70,\n" +
//        "                \"height\": 50\n" +
//        "            },\n" +
//        "            \"view\": \"react-shape-view\",\n" +
//        "            \"attrs\": {\n" +
//        "                \"body\": {\n" +
//        "                    \"rx\": 7,\n" +
//        "                    \"ry\": 6\n" +
//        "                },\n" +
//        "                \"text\": {\n" +
//        "                    \"text\": \"MySQL数据源\",\n" +
//        "                    \"fontSize\": 2\n" +
//        "                }\n" +
//        "            },\n" +
//        "            \"shape\": \"MysqlSourceOperator\",\n" +
//        "            \"ports\": {\n" +
//        "                \"groups\": {\n" +
//        "                    \"outputs\": {\n" +
//        "                        \"zIndex\": 999,\n" +
//        "                        \"position\": \"right\",\n" +
//        "                        \"markup\": {\n" +
//        "                            \"tagName\": \"path\",\n" +
//        "                            \"selector\": \"path\",\n" +
//        "                            \"attrs\": {\n" +
//        "                                \"d\": \"m-6,0,a5,5.5 0 0 1 12,0\",\n" +
//        "                                \"fill\": \"#b2a2e9\",\n" +
//        "                                \"transform\": \"rotate(90)\",\n" +
//        "                                \"strokeWidth\": 1,\n" +
//        "                                \"stroke\": \"null\"\n" +
//        "                            }\n" +
//        "                        },\n" +
//        "                        \"attrs\": {\n" +
//        "                            \"path\": {\n" +
//        "                                \"r\": 8,\n" +
//        "                                \"magnet\": true,\n" +
//        "                                \"style\": {\n" +
//        "                                    \"visibility\": \"hidden\"\n" +
//        "                                }\n" +
//        "                            }\n" +
//        "                        }\n" +
//        "                    },\n" +
//        "                    \"inputs\": {\n" +
//        "                        \"zIndex\": 999,\n" +
//        "                        \"position\": \"left\",\n" +
//        "                        \"markup\": {\n" +
//        "                            \"tagName\": \"path\",\n" +
//        "                            \"selector\": \"path\",\n" +
//        "                            \"attrs\": {\n" +
//        "                                \"d\": \"m-6,0,a5,5.5 0 0 1 12,0\",\n" +
//        "                                \"fill\": \"#b2a2e9\",\n" +
//        "                                \"transform\": \"rotate(-90)\",\n" +
//        "                                \"strokeWidth\": 1,\n" +
//        "                                \"stroke\": \"null\"\n" +
//        "                            }\n" +
//        "                        },\n" +
//        "                        \"attrs\": {\n" +
//        "                            \"path\": {\n" +
//        "                                \"r\": 10,\n" +
//        "                                \"magnet\": true,\n" +
//        "                                \"style\": {\n" +
//        "                                    \"visibility\": \"hidden\"\n" +
//        "                                }\n" +
//        "                            }\n" +
//        "                        }\n" +
//        "                    }\n" +
//        "                },\n" +
//        "                \"items\": [\n" +
//        "                    {\n" +
//        "                        \"group\": \"outputs\",\n" +
//        "                        \"zIndex\": 999,\n" +
//        "                        \"id\": \"output_0\",\n" +
//        "                        \"attrs\": {\n" +
//        "                            \"text\": {\n" +
//        "                                \"text\": \"output_0\",\n" +
//        "                                \"style\": {\n" +
//        "                                    \"visibility\": \"hidden\",\n" +
//        "                                    \"fontSize\": 10,\n" +
//        "                                    \"fill\": \"#3B4351\"\n" +
//        "                                }\n" +
//        "                            }\n" +
//        "                        },\n" +
//        "                        \"label\": {\n" +
//        "                            \"position\": \"bottom\"\n" +
//        "                        }\n" +
//        "                    }\n" +
//        "                ]\n" +
//        "            },\n" +
//        "            \"id\": \"af58e0ea-f72d-48a2-97cc-5c59802d5ab5\",\n" +
//        "            \"name\": \"MySQL数据源\",\n" +
//        "            \"isStencil\": false,\n" +
//        "            \"zIndex\": 1,\n" +
//        "            \"data\": {\n" +
//        "                \"parameters\": {\n" +
//        "                    \"tableName\": \"\",\n" +
//        "                    \"connector\": \"jdbc\",\n" +
//        "                    \"url\": \"asad\",\n" +
//        "                    \"username\": \"root\",\n" +
//        "                    \"password\": \"123456\",\n" +
//        "                    \"other\": [],\n" +
//        "                    \"columns\": [\n" +
//        "                        {\n" +
//        "                            \"name\": \"a\",\n" +
//        "                            \"type\": \"STRING\",\n" +
//        "                            \"flag\": true\n" +
//        "                        },\n" +
//        "                        {\n" +
//        "                            \"name\": \"b\",\n" +
//        "                            \"type\": \"STRING\",\n" +
//        "                            \"flag\": true\n" +
//        "                        }\n" +
//        "                    ],\n" +
//        "                    \"primary\": \"\",\n" +
//        "                    \"watermark\": {\n" +
//        "                        \"column\": \"\",\n" +
//        "                        \"timeSpan\": 0,\n" +
//        "                        \"timeUnit\": \"SECOND\"\n" +
//        "                    }\n" +
//        "                },\n" +
//        "                \"config\": []\n" +
//        "            }\n" +
//        "        },\n" +
//        "        {\n" +
//        "            \"position\": {\n" +
//        "                \"x\": 410,\n" +
//        "                \"y\": 210\n" +
//        "            },\n" +
//        "            \"size\": {\n" +
//        "                \"width\": 70,\n" +
//        "                \"height\": 50\n" +
//        "            },\n" +
//        "            \"view\": \"react-shape-view\",\n" +
//        "            \"attrs\": {\n" +
//        "                \"body\": {\n" +
//        "                    \"rx\": 7,\n" +
//        "                    \"ry\": 6\n" +
//        "                },\n" +
//        "                \"text\": {\n" +
//        "                    \"text\": \"OracleSinkOperator\",\n" +
//        "                    \"fontSize\": 2\n" +
//        "                }\n" +
//        "            },\n" +
//        "            \"shape\": \"OracleSinkOperator\",\n" +
//        "            \"ports\": {\n" +
//        "                \"groups\": {\n" +
//        "                    \"outputs\": {\n" +
//        "                        \"zIndex\": 999,\n" +
//        "                        \"position\": \"right\",\n" +
//        "                        \"markup\": {\n" +
//        "                            \"tagName\": \"path\",\n" +
//        "                            \"selector\": \"path\",\n" +
//        "                            \"attrs\": {\n" +
//        "                                \"d\": \"m-6,0,a5,5.5 0 0 1 12,0\",\n" +
//        "                                \"fill\": \"#b2a2e9\",\n" +
//        "                                \"transform\": \"rotate(90)\",\n" +
//        "                                \"strokeWidth\": 1,\n" +
//        "                                \"stroke\": \"null\"\n" +
//        "                            }\n" +
//        "                        },\n" +
//        "                        \"attrs\": {\n" +
//        "                            \"path\": {\n" +
//        "                                \"r\": 8,\n" +
//        "                                \"magnet\": true,\n" +
//        "                                \"style\": {\n" +
//        "                                    \"visibility\": \"hidden\"\n" +
//        "                                }\n" +
//        "                            }\n" +
//        "                        }\n" +
//        "                    },\n" +
//        "                    \"inputs\": {\n" +
//        "                        \"zIndex\": 999,\n" +
//        "                        \"position\": \"left\",\n" +
//        "                        \"markup\": {\n" +
//        "                            \"tagName\": \"path\",\n" +
//        "                            \"selector\": \"path\",\n" +
//        "                            \"attrs\": {\n" +
//        "                                \"d\": \"m-6,0,a5,5.5 0 0 1 12,0\",\n" +
//        "                                \"fill\": \"#b2a2e9\",\n" +
//        "                                \"transform\": \"rotate(-90)\",\n" +
//        "                                \"strokeWidth\": 1,\n" +
//        "                                \"stroke\": \"null\"\n" +
//        "                            }\n" +
//        "                        },\n" +
//        "                        \"attrs\": {\n" +
//        "                            \"path\": {\n" +
//        "                                \"r\": 10,\n" +
//        "                                \"magnet\": true,\n" +
//        "                                \"style\": {\n" +
//        "                                    \"visibility\": \"hidden\"\n" +
//        "                                }\n" +
//        "                            }\n" +
//        "                        }\n" +
//        "                    }\n" +
//        "                },\n" +
//        "                \"items\": [\n" +
//        "                    {\n" +
//        "                        \"group\": \"inputs\",\n" +
//        "                        \"id\": \"input_0\",\n" +
//        "                        \"zIndex\": 999,\n" +
//        "                        \"attrs\": {\n" +
//        "                            \"text\": {\n" +
//        "                                \"text\": \"input_0\",\n" +
//        "                                \"style\": {\n" +
//        "                                    \"visibility\": \"hidden\",\n" +
//        "                                    \"fontSize\": 10,\n" +
//        "                                    \"fill\": \"#3B4351\"\n" +
//        "                                }\n" +
//        "                            }\n" +
//        "                        },\n" +
//        "                        \"label\": {\n" +
//        "                            \"position\": \"bottom\"\n" +
//        "                        }\n" +
//        "                    }\n" +
//        "                ]\n" +
//        "            },\n" +
//        "            \"id\": \"98374b61-f025-4423-b9cc-cb599e0a6ce5\",\n" +
//        "            \"name\": \"OracleSinkOperator\",\n" +
//        "            \"isStencil\": false,\n" +
//        "            \"zIndex\": 2,\n" +
//        "            \"data\": {\n" +
//        "                \"parameters\": {\n" +
//        "                    \"tableName\": \"aa\",\n" +
//        "                    \"connector\": \"oracle-cdc\",\n" +
//        "                    \"hostname\": \"aa\",\n" +
//        "                    \"port\": 1521,\n" +
//        "                    \"username\": \"asasd\",\n" +
//        "                    \"password\": \"asdas\",\n" +
//        "                    \"database-name\": \"adasd\",\n" +
//        "                    \"other\": [],\n" +
//        "                    \"columns\": [\n" +
//        "                        {\n" +
//        "                            \"name\": \"aa\",\n" +
//        "                            \"type\": \"STRING\",\n" +
//        "                            \"flag\": true\n" +
//        "                        },\n" +
//        "                        {\n" +
//        "                            \"name\": \"bb\",\n" +
//        "                            \"type\": \"STRING\",\n" +
//        "                            \"flag\": true\n" +
//        "                        }\n" +
//        "                    ],\n" +
//        "                    \"primary\": \"\",\n" +
//        "                    \"watermark\": {\n" +
//        "                        \"column\": \"\",\n" +
//        "                        \"timeSpan\": 0,\n" +
//        "                        \"timeUnit\": \"SECOND\"\n" +
//        "                    }\n" +
//        "                },\n" +
//        "                \"config\": [\n" +
//        "                    {\n" +
//        "                        \"e1789214-b444-4e20-be68-07bc5bae3be6&output_0 98374b61-f025-4423-b9cc-cb599e0a6ce5&input_0\": [\n" +
//        "                            {\n" +
//        "                                \"outName\": \"a1\",\n" +
//        "                                \"function\": [\n" +
//        "                                    {\n" +
//        "                                        \"functionName\": \"f1\",\n" +
//        "                                        \"recursionFunc\": [],\n" +
//        "                                        \"recursionName\": [\n" +
//        "                                            {\n" +
//        "                                                \"name\": [\n" +
//        "                                                    \"a\",\n" +
//        "                                                    \"b\",\n" +
//        "                                                    \"c\"\n" +
//        "                                                ],\n" +
//        "                                                \"recursionFunc\": [\n" +
//        "                                                    {\n" +
//        "                                                        \"functionName\": \"f2\",\n" +
//        "                                                        \"recursionFunc\": [],\n" +
//        "                                                        \"recursionName\": [\n" +
//        "                                                            {\n" +
//        "                                                                \"name\": [\n" +
//        "                                                                    \"a~\",\n" +
//        "                                                                    \"b~\"\n" +
//        "                                                                ],\n" +
//        "                                                                \"recursionFunc\": [\n" +
//        "                                                                    {\n" +
//        "                                                                        \"functionName\": \"F3\",\n" +
//        "                                                                        \"recursionFunc\": [],\n" +
//        "                                                                        \"recursionName\": [\n" +
//        "                                                                            {\n" +
//        "                                                                                \"name\": [\n" +
//        "                                                                                    \"A\",\n" +
//        "                                                                                    \"B\"\n" +
//        "                                                                                ],\n" +
//        "                                                                                \"recursionFunc\": [],\n" +
//        "                                                                                \"recursionName\": []\n" +
//        "                                                                            }\n" +
//        "                                                                        ]\n" +
//        "                                                                    }\n" +
//        "                                                                ],\n" +
//        "                                                                \"recursionName\": []\n" +
//        "                                                            }\n" +
//        "                                                        ]\n" +
//        "                                                    }\n" +
//        "                                                ],\n" +
//        "                                                \"recursionName\": []\n" +
//        "                                            }\n" +
//        "                                        ]\n" +
//        "                                    }\n" +
//        "                                ],\n" +
//        "                                \"name\": \"aaaaaaaaaaaaa\",\n" +
//        "                                \"flag\": true,\n" +
//        "                                \"decs\": \"true\"\n" +
//        "                            }\n" +
//        "                        ]\n" +
//        "                    }\n" +
//        "                ]\n" +
//        "            }\n" +
//        "        },\n" +
//        "        {\n" +
//        "            \"position\": {\n" +
//        "                \"x\": 196,\n" +
//        "                \"y\": 210\n" +
//        "            },\n" +
//        "            \"size\": {\n" +
//        "                \"width\": 70,\n" +
//        "                \"height\": 50\n" +
//        "            },\n" +
//        "            \"view\": \"react-shape-view\",\n" +
//        "            \"attrs\": {\n" +
//        "                \"body\": {\n" +
//        "                    \"rx\": 7,\n" +
//        "                    \"ry\": 6\n" +
//        "                },\n" +
//        "                \"text\": {\n" +
//        "                    \"text\": \"CepOperator\",\n" +
//        "                    \"fontSize\": 2\n" +
//        "                }\n" +
//        "            },\n" +
//        "            \"shape\": \"CepOperator\",\n" +
//        "            \"ports\": {\n" +
//        "                \"groups\": {\n" +
//        "                    \"outputs\": {\n" +
//        "                        \"zIndex\": 999,\n" +
//        "                        \"position\": \"right\",\n" +
//        "                        \"markup\": {\n" +
//        "                            \"tagName\": \"path\",\n" +
//        "                            \"selector\": \"path\",\n" +
//        "                            \"attrs\": {\n" +
//        "                                \"d\": \"m-6,0,a5,5.5 0 0 1 12,0\",\n" +
//        "                                \"fill\": \"#b2a2e9\",\n" +
//        "                                \"transform\": \"rotate(90)\",\n" +
//        "                                \"strokeWidth\": 1,\n" +
//        "                                \"stroke\": \"null\"\n" +
//        "                            }\n" +
//        "                        },\n" +
//        "                        \"attrs\": {\n" +
//        "                            \"path\": {\n" +
//        "                                \"r\": 8,\n" +
//        "                                \"magnet\": true,\n" +
//        "                                \"style\": {\n" +
//        "                                    \"visibility\": \"hidden\"\n" +
//        "                                }\n" +
//        "                            }\n" +
//        "                        }\n" +
//        "                    },\n" +
//        "                    \"inputs\": {\n" +
//        "                        \"zIndex\": 999,\n" +
//        "                        \"position\": \"left\",\n" +
//        "                        \"markup\": {\n" +
//        "                            \"tagName\": \"path\",\n" +
//        "                            \"selector\": \"path\",\n" +
//        "                            \"attrs\": {\n" +
//        "                                \"d\": \"m-6,0,a5,5.5 0 0 1 12,0\",\n" +
//        "                                \"fill\": \"#b2a2e9\",\n" +
//        "                                \"transform\": \"rotate(-90)\",\n" +
//        "                                \"strokeWidth\": 1,\n" +
//        "                                \"stroke\": \"null\"\n" +
//        "                            }\n" +
//        "                        },\n" +
//        "                        \"attrs\": {\n" +
//        "                            \"path\": {\n" +
//        "                                \"r\": 10,\n" +
//        "                                \"magnet\": true,\n" +
//        "                                \"style\": {\n" +
//        "                                    \"visibility\": \"hidden\"\n" +
//        "                                }\n" +
//        "                            }\n" +
//        "                        }\n" +
//        "                    }\n" +
//        "                },\n" +
//        "                \"items\": [\n" +
//        "                    {\n" +
//        "                        \"group\": \"inputs\",\n" +
//        "                        \"id\": \"input_0\",\n" +
//        "                        \"zIndex\": 999,\n" +
//        "                        \"attrs\": {\n" +
//        "                            \"text\": {\n" +
//        "                                \"text\": \"input_0\",\n" +
//        "                                \"style\": {\n" +
//        "                                    \"visibility\": \"hidden\",\n" +
//        "                                    \"fontSize\": 10,\n" +
//        "                                    \"fill\": \"#3B4351\"\n" +
//        "                                }\n" +
//        "                            }\n" +
//        "                        },\n" +
//        "                        \"label\": {\n" +
//        "                            \"position\": \"bottom\"\n" +
//        "                        }\n" +
//        "                    },\n" +
//        "                    {\n" +
//        "                        \"group\": \"outputs\",\n" +
//        "                        \"zIndex\": 999,\n" +
//        "                        \"id\": \"output_0\",\n" +
//        "                        \"attrs\": {\n" +
//        "                            \"text\": {\n" +
//        "                                \"text\": \"output_0\",\n" +
//        "                                \"style\": {\n" +
//        "                                    \"visibility\": \"hidden\",\n" +
//        "                                    \"fontSize\": 10,\n" +
//        "                                    \"fill\": \"#3B4351\"\n" +
//        "                                }\n" +
//        "                            }\n" +
//        "                        },\n" +
//        "                        \"label\": {\n" +
//        "                            \"position\": \"bottom\"\n" +
//        "                        }\n" +
//        "                    }\n" +
//        "                ]\n" +
//        "            },\n" +
//        "            \"id\": \"e1789214-b444-4e20-be68-07bc5bae3be6\",\n" +
//        "            \"name\": \"CepOperator\",\n" +
//        "            \"isStencil\": false,\n" +
//        "            \"zIndex\": 3,\n" +
//        "            \"data\": {\n" +
//        "                \"parameters\": {\n" +
//        "                    \"tableName\": \"\",\n" +
//        "                    \"partition\": \"a\",\n" +
//        "                    \"orderBy\": \"a\",\n" +
//        "                    \"outPutMode\": \"ONE\",\n" +
//        "                    \"skipStrategy\": {\n" +
//        "                        \"strategy\": \"LAST_ROW\",\n" +
//        "                        \"variable\": 0\n" +
//        "                    },\n" +
//        "                    \"timeSpan\": 0,\n" +
//        "                    \"timeUnit\": \"SECOND\",\n" +
//        "                    \"patterns\": [\n" +
//        "                        {\n" +
//        "                            \"variable\": \"e1\",\n" +
//        "                            \"quantifier\": \"+\"\n" +
//        "                        }\n" +
//        "                    ],\n" +
//        "                    \"columns\": [\n" +
//        "                        {\n" +
//        "                            \"outName\": \"a1\",\n" +
//        "                            \"function\": [\n" +
//        "                                {\n" +
//        "                                    \"functionName\": \"f1\",\n" +
//        "                                    \"recursionFunc\": [],\n" +
//        "                                    \"recursionName\": [\n" +
//        "                                        {\n" +
//        "                                            \"name\": [\n" +
//        "                                                \"a\",\n" +
//        "                                                \"b\",\n" +
//        "                                                \"c\"\n" +
//        "                                            ],\n" +
//        "                                            \"recursionFunc\": [\n" +
//        "                                                {\n" +
//        "                                                    \"functionName\": \"f2\",\n" +
//        "                                                    \"recursionFunc\": [],\n" +
//        "                                                    \"recursionName\": [\n" +
//        "                                                        {\n" +
//        "                                                            \"name\": [\n" +
//        "                                                                \"a~\",\n" +
//        "                                                                \"b~\"\n" +
//        "                                                            ],\n" +
//        "                                                            \"recursionFunc\": [\n" +
//        "                                                                {\n" +
//        "                                                                    \"functionName\": \"F3\",\n" +
//        "                                                                    \"recursionFunc\": [],\n" +
//        "                                                                    \"recursionName\": [\n" +
//        "                                                                        {\n" +
//        "                                                                            \"name\": [\n" +
//        "                                                                                \"A\",\n" +
//        "                                                                                \"B\"\n" +
//        "                                                                            ],\n" +
//        "                                                                            \"recursionFunc\": [],\n" +
//        "                                                                            \"recursionName\": []\n" +
//        "                                                                        }\n" +
//        "                                                                    ]\n" +
//        "                                                                }\n" +
//        "                                                            ],\n" +
//        "                                                            \"recursionName\": []\n" +
//        "                                                        }\n" +
//        "                                                    ]\n" +
//        "                                                }\n" +
//        "                                            ],\n" +
//        "                                            \"recursionName\": []\n" +
//        "                                        }\n" +
//        "                                    ]\n" +
//        "                                }\n" +
//        "                            ],\n" +
//        "                            \"name\": \"aaaaaaaaaaaaa\",\n" +
//        "                            \"flag\": true,\n" +
//        "                            \"decs\": \"true\"\n" +
//        "                        }\n" +
//        "                    ],\n" +
//        "                    \"defines\": [\n" +
//        "                        {\n" +
//        "                            \"variable\": \"e1\",\n" +
//        "                            \"condition\": \"eeeeeeeee\"\n" +
//        "                        }\n" +
//        "                    ]\n" +
//        "                },\n" +
//        "                \"config\": [\n" +
//        "                    {\n" +
//        "                        \"af58e0ea-f72d-48a2-97cc-5c59802d5ab5&output_0 e1789214-b444-4e20-be68-07bc5bae3be6&input_0\": [\n" +
//        "                            {\n" +
//        "                                \"name\": \"a\",\n" +
//        "                                \"type\": \"STRING\",\n" +
//        "                                \"flag\": true\n" +
//        "                            },\n" +
//        "                            {\n" +
//        "                                \"name\": \"b\",\n" +
//        "                                \"type\": \"STRING\",\n" +
//        "                                \"flag\": true\n" +
//        "                            }\n" +
//        "                        ]\n" +
//        "                    }\n" +
//        "                ]\n" +
//        "            }\n" +
//        "        },\n" +
//        "        {\n" +
//        "            \"shape\": \"edge\",\n" +
//        "            \"attrs\": {\n" +
//        "                \"line\": {\n" +
//        "                    \"stroke\": \"#b2a2e9\",\n" +
//        "                    \"targetMarker\": \"\"\n" +
//        "                }\n" +
//        "            },\n" +
//        "            \"id\": \"233cc0d0-3e26-48dc-9dd2-2867b4e7c1cc\",\n" +
//        "            \"source\": {\n" +
//        "                \"cell\": \"af58e0ea-f72d-48a2-97cc-5c59802d5ab5\",\n" +
//        "                \"port\": \"output_0\"\n" +
//        "            },\n" +
//        "            \"target\": {\n" +
//        "                \"cell\": \"e1789214-b444-4e20-be68-07bc5bae3be6\",\n" +
//        "                \"port\": \"input_0\"\n" +
//        "            },\n" +
//        "            \"zIndex\": 4\n" +
//        "        },\n" +
//        "        {\n" +
//        "            \"shape\": \"edge\",\n" +
//        "            \"attrs\": {\n" +
//        "                \"line\": {\n" +
//        "                    \"stroke\": \"#b2a2e9\",\n" +
//        "                    \"targetMarker\": \"\"\n" +
//        "                }\n" +
//        "            },\n" +
//        "            \"id\": \"b447eeed-8925-4f94-98b5-141879bb55e2\",\n" +
//        "            \"source\": {\n" +
//        "                \"cell\": \"e1789214-b444-4e20-be68-07bc5bae3be6\",\n" +
//        "                \"port\": \"output_0\"\n" +
//        "            },\n" +
//        "            \"target\": {\n" +
//        "                \"cell\": \"98374b61-f025-4423-b9cc-cb599e0a6ce5\",\n" +
//        "                \"port\": \"input_0\"\n" +
//        "            },\n" +
//        "            \"zIndex\": 5\n" +
//        "        }\n" +
//        "    ]\n" +
//        "}";
static String x6_json1 = "";
        public static void main(String [] args){

            X6ToInternalConvert x6 = new X6ToInternalConvert();
            Scene result = x6.convert(x6_json1);
            result.getEnvironment().setResultType(ResultType.SQL);
            SceneCodeBuilder su = new SceneCodeBuilder(result);
            final String build = su.build().get("SQL").toString();
            System.out.println(build);

        }
}
