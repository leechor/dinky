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
                    "                \"x\": -240,\n" +
                    "                \"y\": -20\n" +
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
                    "            \"id\": \"040824c6-a0ec-4c82-98ad-f6251b0f8c4c\",\n" +
                    "            \"zIndex\": 1,\n" +
                    "            \"data\": {\n" +
                    "                \"parameters\": {\n" +
                    "                    \"connector\": \"kafka\",\n" +
                    "                    \"topic\": \"asdasda\",\n" +
                    "                    \"properties.bootstrip.servers\": \"hadoop102:9092\",\n" +
                    "                    \"format\": \"json\",\n" +
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
                    "                    ]\n" +
                    "                },\n" +
                    "                \"config\": []\n" +
                    "            }\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"position\": {\n" +
                    "                \"x\": -59,\n" +
                    "                \"y\": -20\n" +
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
                    "            \"id\": \"5f1a2595-eacb-4049-bb37-4c709fd71322\",\n" +
                    "            \"zIndex\": 2,\n" +
                    "            \"data\": {\n" +
                    "                \"parameters\": {\n" +
                    "                    \"connector\": \"kafka\",\n" +
                    "                    \"topic\": \"adsasda\",\n" +
                    "                    \"properties.bootstrip.servers\": \"hadoop102:9092\",\n" +
                    "                    \"format\": \"json\",\n" +
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
                    "                    ]\n" +
                    "                },\n" +
                    "                \"config\": [\n" +
                    "                    {\n" +
                    "                        \"040824c6-a0ec-4c82-98ad-f6251b0f8c4c&output_0 5f1a2595-eacb-4049-bb37-4c709fd71322&input_0\": [\n" +
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
                    "            \"id\": \"75df62f2-a235-4cb2-8997-9967ca6ed666\",\n" +
                    "            \"source\": {\n" +
                    "                \"cell\": \"040824c6-a0ec-4c82-98ad-f6251b0f8c4c\",\n" +
                    "                \"port\": \"output_0\"\n" +
                    "            },\n" +
                    "            \"target\": {\n" +
                    "                \"cell\": \"5f1a2595-eacb-4049-bb37-4c709fd71322\",\n" +
                    "                \"port\": \"input_0\"\n" +
                    "            },\n" +
                    "            \"zIndex\": 3\n" +
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
        Scene result = x6.convert(x6_json1);
        result.getEnvironment().setResultType(ResultType.SQL);
        SceneCodeBuilder su = new SceneCodeBuilder(result);
        final String build = su.build();
        System.out.println(build);
}
}
