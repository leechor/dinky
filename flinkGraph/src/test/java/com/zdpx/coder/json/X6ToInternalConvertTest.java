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
        "                \"y\": 120\n" +
        "            },\n" +
        "            \"size\": {\n" +
        "                \"width\": 70,\n" +
        "                \"height\": 50\n" +
        "            },\n" +
        "            \"view\": \"react-shape-view\",\n" +
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
        "            \"id\": \"833b1170-99cb-47f0-9169-7d51c5adc53f\",\n" +
        "            \"name\": \"MySQL数据源\",\n" +
        "            \"isStencil\": false,\n" +
        "            \"zIndex\": 1,\n" +
        "            \"data\": {\n" +
        "                \"parameters\": {\n" +
        "                    \"tableName\": \"sdf\",\n" +
        "                    \"connector\": \"jdbc\",\n" +
        "                    \"url\": \"sfd\",\n" +
        "                    \"username\": \"root\",\n" +
        "                    \"password\": \"123456\",\n" +
        "                    \"other\": [],\n" +
        "                    \"columns\": [\n" +
        "                        {\n" +
        "                            \"name\": \"sfd\",\n" +
        "                            \"type\": \"STRING\",\n" +
        "                            \"flag\": true\n" +
        "                        }\n" +
        "                    ],\n" +
        "                    \"primary\": \"fsd\",\n" +
        "                    \"watermark\": {\n" +
        "                        \"column\": \"fds\",\n" +
        "                        \"timeSpan\": 0,\n" +
        "                        \"timeUnit\": \"SECOND\"\n" +
        "                    }\n" +
        "                },\n" +
        "                \"config\": []\n" +
        "            }\n" +
        "        },\n" +
        "        {\n" +
        "            \"position\": {\n" +
        "                \"x\": 365,\n" +
        "                \"y\": 120\n" +
        "            },\n" +
        "            \"size\": {\n" +
        "                \"width\": 70,\n" +
        "                \"height\": 50\n" +
        "            },\n" +
        "            \"view\": \"react-shape-view\",\n" +
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
        "            \"id\": \"d011cb83-c8b2-4b88-97aa-0323b04ec15e\",\n" +
        "            \"name\": \"MysqlSink\",\n" +
        "            \"isStencil\": false,\n" +
        "            \"zIndex\": 2,\n" +
        "            \"data\": {\n" +
        "                \"parameters\": {\n" +
        "                    \"tableName\": \"fsd\",\n" +
        "                    \"connector\": \"jdbc\",\n" +
        "                    \"url\": \"fsd\",\n" +
        "                    \"username\": \"root\",\n" +
        "                    \"password\": \"123456\",\n" +
        "                    \"other\": [],\n" +
        "                    \"columns\": [\n" +
        "                        {\n" +
        "                            \"name\": \"fds\",\n" +
        "                            \"type\": \"STRING\",\n" +
        "                            \"flag\": true\n" +
        "                        }\n" +
        "                    ],\n" +
        "                    \"primary\": \"dfs\",\n" +
        "                    \"watermark\": {\n" +
        "                        \"column\": \"fds\",\n" +
        "                        \"timeSpan\": 0,\n" +
        "                        \"timeUnit\": \"SECOND\"\n" +
        "                    }\n" +
        "                },\n" +
        "                \"config\": [\n" +
        "                    {\n" +
        "                        \"e84ad2b5-7c7d-4620-9046-813023e58e02&output_0 d011cb83-c8b2-4b88-97aa-0323b04ec15e&input_0\": [\n" +
        "                            {\n" +
        "                                \"name\": \"f1\",\n" +
        "                                \"parameters\": [\n" +
        "                                    {\n" +
        "                                        \"functionName\": \"f2\",\n" +
        "                                        \"column\": [\n" +
        "                                            \"a\",\n" +
        "                                            \"b\",\n" +
        "                                            \"c\"\n" +
        "                                        ],\n" +
        "                                        \"recursion\": [\n" +
        "                                            {\n" +
        "                                                \"functionName\": \"f3\",\n" +
        "                                                \"column\": [\n" +
        "                                                    \"a1\",\n" +
        "                                                    \"a2\"\n" +
        "                                                ],\n" +
        "                                                \"recursion\": []\n" +
        "                                            }\n" +
        "                                        ]\n" +
        "                                    }\n" +
        "                                ],\n" +
        "                                \"flag\": true\n" +
        "                            },\n" +
        "                            {\n" +
        "                                \"name\": \"f2\",\n" +
        "                                \"parameters\": [\n" +
        "                                    {\n" +
        "                                        \"functionName\": \"\",\n" +
        "                                        \"column\": [\n" +
        "                                            \"b\"\n" +
        "                                        ],\n" +
        "                                        \"recursion\": []\n" +
        "                                    }\n" +
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
        "                \"x\": 208,\n" +
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
        "            \"id\": \"e84ad2b5-7c7d-4620-9046-813023e58e02\",\n" +
        "            \"name\": \"CepOperator\",\n" +
        "            \"isStencil\": false,\n" +
        "            \"zIndex\": 3,\n" +
        "            \"data\": {\n" +
        "                \"parameters\": {\n" +
        "                    \"tableName\": \"\",\n" +
        "                    \"partition\": \"a\",\n" +
        "                    \"orderBy\": \"a\",\n" +
        "                    \"outPutMode\": \"ONE\",\n" +
        "                    \"skipStrategy\": {\n" +
        "                        \"strategy\": \"LAST_ROW\",\n" +
        "                        \"variable\": 0\n" +
        "                    },\n" +
        "                    \"timeSpan\": 0,\n" +
        "                    \"timeUnit\": \"SECOND\",\n" +
        "                    \"patterns\": [\n" +
        "                        {\n" +
        "                            \"variable\": \"e1\",\n" +
        "                            \"quantifier\": \"+\"\n" +
        "                        }\n" +
        "                    ],\n" +
        "                    \"columns\": [\n" +
        "                        {\n" +
        "                            \"name\": \"f1\",\n" +
        "                            \"parameters\": [\n" +
        "                                {\n" +
        "                                    \"functionName\": \"f2\",\n" +
        "                                    \"column\": [\n" +
        "                                        \"a\",\n" +
        "                                        \"b\",\n" +
        "                                        \"c\"\n" +
        "                                    ],\n" +
        "                                    \"recursion\": [\n" +
        "                                        {\n" +
        "                                            \"functionName\": \"f3\",\n" +
        "                                            \"column\": [\n" +
        "                                                \"a1\",\n" +
        "                                                \"a2\"\n" +
        "                                            ],\n" +
        "                                            \"recursion\": []\n" +
        "                                        }\n" +
        "                                    ]\n" +
        "                                }\n" +
        "                            ],\n" +
        "                            \"flag\": true\n" +
        "                        },\n" +
        "                        {\n" +
        "                            \"name\": \"f2\",\n" +
        "                            \"parameters\": [\n" +
        "                                {\n" +
        "                                    \"functionName\": \"\",\n" +
        "                                    \"column\": [\n" +
        "                                        \"b\"\n" +
        "                                    ],\n" +
        "                                    \"recursion\": []\n" +
        "                                }\n" +
        "                            ],\n" +
        "                            \"flag\": true\n" +
        "                        }\n" +
        "                    ],\n" +
        "                    \"defines\": [\n" +
        "                        {\n" +
        "                            \"variable\": \"\",\n" +
        "                            \"condition\": \"\"\n" +
        "                        }\n" +
        "                    ]\n" +
        "                },\n" +
        "                \"config\": [\n" +
        "                    {\n" +
        "                        \"833b1170-99cb-47f0-9169-7d51c5adc53f&output_0 e84ad2b5-7c7d-4620-9046-813023e58e02&input_0\": [\n" +
        "                            {\n" +
        "                                \"name\": \"sfd\",\n" +
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
        "            \"id\": \"428be442-73a6-48f0-a8bc-fec005b68542\",\n" +
        "            \"source\": {\n" +
        "                \"cell\": \"833b1170-99cb-47f0-9169-7d51c5adc53f\",\n" +
        "                \"port\": \"output_0\"\n" +
        "            },\n" +
        "            \"target\": {\n" +
        "                \"cell\": \"e84ad2b5-7c7d-4620-9046-813023e58e02\",\n" +
        "                \"port\": \"input_0\"\n" +
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
        "            \"id\": \"b9318ea4-1e38-4c03-9ccb-6f972991c7d3\",\n" +
        "            \"source\": {\n" +
        "                \"cell\": \"e84ad2b5-7c7d-4620-9046-813023e58e02\",\n" +
        "                \"port\": \"output_0\"\n" +
        "            },\n" +
        "            \"target\": {\n" +
        "                \"cell\": \"d011cb83-c8b2-4b88-97aa-0323b04ec15e\",\n" +
        "                \"port\": \"input_0\"\n" +
        "            },\n" +
        "            \"zIndex\": 5\n" +
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
