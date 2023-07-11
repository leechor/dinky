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

    private static final String x6_json1 =
            "{\n" +
                    "    \"cells\": [\n" +
                    "        {\n" +
                    "            \"position\": {\n" +
                    "                \"x\": -210,\n" +
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
                    "            \"id\": \"15193530-4e92-40b8-aef5-cb43976a6f3b\",\n" +
                    "            \"name\": \"MySQL数据源\",\n" +
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
                    "                \"x\": 107,\n" +
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
                    "            \"id\": \"0fc29101-3e9d-40d2-8fb9-e4411203237f\",\n" +
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
                    "                            \"name\": \"out1\",\n" +
                    "                            \"type\": \"STRING\",\n" +
                    "                            \"flag\": true\n" +
                    "                        },\n" +
                    "                        {\n" +
                    "                            \"name\": \"out2\",\n" +
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
                    "                        \"cd9c9f9d-821c-400f-afd3-d344c57a6786&output_0 0fc29101-3e9d-40d2-8fb9-e4411203237f&input_0\": [\n" +
                    "                            {\n" +
                    "                                \"functionName\": \"\",\n" +
                    "                                \"name\": \"o1\",\n" +
                    "                                \"parameters\": [\n" +
                    "                                    \"a\"\n" +
                    "                                ],\n" +
                    "                                \"flag\": true\n" +
                    "                            },\n" +
                    "                            {\n" +
                    "                                \"functionName\": \"\",\n" +
                    "                                \"name\": \"o2\",\n" +
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
                    "                \"x\": -53,\n" +
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
                    "            \"id\": \"cd9c9f9d-821c-400f-afd3-d344c57a6786\",\n" +
                    "            \"name\": \"CepOperator\",\n" +
                    "            \"zIndex\": 5,\n" +
                    "            \"data\": {\n" +
                    "                \"parameters\": {\n" +
                    "                    \"tableName\": \"asdasas\",\n" +
                    "                    \"partition\": \"aa\",\n" +
                    "                    \"orderBy\": \"a\",\n" +
                    "                    \"outPutMode\": \"ONE\",\n" +
                    "                    \"skipStrategy\": {\n" +
                    "                        \"strategy\": \"LAST_ROW\",\n" +
                    "                        \"variable\": \"\"\n" +
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
                    "                            \"functionName\": \"\",\n" +
                    "                            \"name\": \"o1\",\n" +
                    "                            \"parameters\": [\n" +
                    "                                \"a\"\n" +
                    "                            ],\n" +
                    "                            \"flag\": true\n" +
                    "                        },\n" +
                    "                        {\n" +
                    "                            \"functionName\": \"\",\n" +
                    "                            \"name\": \"o2\",\n" +
                    "                            \"parameters\": [\n" +
                    "                                \"b\"\n" +
                    "                            ],\n" +
                    "                            \"flag\": true\n" +
                    "                        }\n" +
                    "                    ],\n" +
                    "                    \"defines\": [\n" +
                    "                        {\n" +
                    "                            \"variable\": \"e1\",\n" +
                    "                            \"condition\": \"a=aa\"\n" +
                    "                        }\n" +
                    "                    ]\n" +
                    "                },\n" +
                    "                \"config\": [\n" +
                    "                    {\n" +
                    "                        \"15193530-4e92-40b8-aef5-cb43976a6f3b&output_0 cd9c9f9d-821c-400f-afd3-d344c57a6786&input_0\": [\n" +
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
                    "            \"id\": \"654a3684-ad79-44b6-b4a6-05e435ea37ed\",\n" +
                    "            \"source\": {\n" +
                    "                \"cell\": \"15193530-4e92-40b8-aef5-cb43976a6f3b\",\n" +
                    "                \"port\": \"output_0\"\n" +
                    "            },\n" +
                    "            \"target\": {\n" +
                    "                \"cell\": \"cd9c9f9d-821c-400f-afd3-d344c57a6786\",\n" +
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
                    "            \"id\": \"37323cc5-8352-4ee6-9c05-dcbcbce0a8ca\",\n" +
                    "            \"source\": {\n" +
                    "                \"cell\": \"cd9c9f9d-821c-400f-afd3-d344c57a6786\",\n" +
                    "                \"port\": \"output_0\"\n" +
                    "            },\n" +
                    "            \"target\": {\n" +
                    "                \"cell\": \"0fc29101-3e9d-40d2-8fb9-e4411203237f\",\n" +
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
            final String build = su.build();
            System.out.println(build);

            OperatorPreviewBuilder operatorPreviewBuilder = new OperatorPreviewBuilder(x6_json1);
            String s = operatorPreviewBuilder.operatorPreview();
            System.out.println(s);

        }
}
