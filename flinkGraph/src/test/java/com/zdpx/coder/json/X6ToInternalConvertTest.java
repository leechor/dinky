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
                    "                \"x\": -140,\n" +
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
                    "                    \"text\": \"CustomerSourceOperator\",\n" +
                    "                    \"fontSize\": 2\n" +
                    "                }\n" +
                    "            },\n" +
                    "            \"shape\": \"CustomerSourceOperator\",\n" +
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
                    "            \"id\": \"da6ed1e3-a37f-4bd9-a074-df3826b6ac38\",\n" +
                    "            \"name\": \"CustomerSourceOperator\",\n" +
                    "            \"zIndex\": 1,\n" +
                    "            \"data\": {\n" +
                    "                \"parameters\": {\n" +
                    "                    \"other\": [\n" +
                    "                        {\n" +
                    "                            \"key\": \"connector\",\n" +
                    "                            \"values\": \"udp\"\n" +
                    "                        },\n" +
                    "                        {\n" +
                    "                            \"key\": \"format\",\n" +
                    "                            \"values\": \"json\"\n" +
                    "                        },\n" +
                    "                        {\n" +
                    "                            \"key\": \"hostName\",\n" +
                    "                            \"values\": \"225.0.0.1\"\n" +
                    "                        },\n" +
                    "                        {\n" +
                    "                            \"key\": \"port\",\n" +
                    "                            \"values\": \"6001\"\n" +
                    "                        },\n" +
                    "                        {\n" +
                    "                            \"key\": \"json.timestamp-format.standard\",\n" +
                    "                            \"values\": \"ISO-8601\"\n" +
                    "                        }\n" +
                    "                    ],\n" +
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
                    "                        },\n" +
                    "                        {\n" +
                    "                            \"name\": \"subtype\",\n" +
                    "                            \"type\": \"STRING\",\n" +
                    "                            \"flag\": true\n" +
                    "                        },\n" +
                    "                        {\n" +
                    "                            \"name\": \"time\",\n" +
                    "                            \"type\": \"TIMESTAMP_LTZ(3)\",\n" +
                    "                            \"flag\": true\n" +
                    "                        },\n" +
                    "                        {\n" +
                    "                            \"name\": \"group_id\",\n" +
                    "                            \"type\": \"INTEGER\",\n" +
                    "                            \"flag\": true\n" +
                    "                        },\n" +
                    "                        {\n" +
                    "                            \"name\": \"train_uuid\",\n" +
                    "                            \"type\": \"STRING\",\n" +
                    "                            \"flag\": true\n" +
                    "                        },\n" +
                    "                        {\n" +
                    "                            \"name\": \"description\",\n" +
                    "                            \"type\": \"STRING\",\n" +
                    "                            \"flag\": true\n" +
                    "                        },\n" +
                    "                        {\n" +
                    "                            \"name\": \"data\",\n" +
                    "                            \"type\": \"STRING\",\n" +
                    "                            \"flag\": true\n" +
                    "                        }\n" +
                    "                    ],\n" +
                    "                    \"primary\": \"train_uuid\",\n" +
                    "                    \"watermark\": {\n" +
                    "                        \"column\": \"time\",\n" +
                    "                        \"timeSpan\": 0,\n" +
                    "                        \"timeUnit\": \"SECOND\"\n" +
                    "                    }\n" +
                    "                },\n" +
                    "                \"config\": []\n" +
                    "            }\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"position\": {\n" +
                    "                \"x\": 23,\n" +
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
                    "            \"id\": \"32d2811f-bc0d-4e5e-9e99-ab312772d422\",\n" +
                    "            \"name\": \"CommWindowOperator\",\n" +
                    "            \"zIndex\": 2,\n" +
                    "            \"data\": {\n" +
                    "                \"parameters\": {\n" +
                    "                    \"orderBy\": [],\n" +
                    "                    \"where\": \"subtype='uav_crash'\",\n" +
                    "                    \"limit\": [],\n" +
                    "                    \"group\": null,\n" +
                    "                    \"window\": null,\n" +
                    "                    \"columns\": [\n" +
                    "                        {\n" +
                    "                            \"functionName\": \"\",\n" +
                    "                            \"name\": \"protocal\",\n" +
                    "                            \"parameters\": [\n" +
                    "                                \"protocal\"\n" +
                    "                            ],\n" +
                    "                            \"flag\": true\n" +
                    "                        },\n" +
                    "                        {\n" +
                    "                            \"functionName\": \"\",\n" +
                    "                            \"name\": \"type\",\n" +
                    "                            \"parameters\": [\n" +
                    "                                \"type\"\n" +
                    "                            ],\n" +
                    "                            \"flag\": true\n" +
                    "                        },\n" +
                    "                        {\n" +
                    "                            \"functionName\": \"\",\n" +
                    "                            \"name\": \"subtype\",\n" +
                    "                            \"parameters\": [\n" +
                    "                                \"subtype\"\n" +
                    "                            ],\n" +
                    "                            \"flag\": true\n" +
                    "                        },\n" +
                    "                        {\n" +
                    "                            \"functionName\": \"\",\n" +
                    "                            \"name\": \"time\",\n" +
                    "                            \"parameters\": [\n" +
                    "                                \"time\"\n" +
                    "                            ],\n" +
                    "                            \"flag\": true\n" +
                    "                        },\n" +
                    "                        {\n" +
                    "                            \"functionName\": \"\",\n" +
                    "                            \"name\": \"group_id\",\n" +
                    "                            \"parameters\": [\n" +
                    "                                \"group_id\"\n" +
                    "                            ],\n" +
                    "                            \"flag\": true\n" +
                    "                        },\n" +
                    "                        {\n" +
                    "                            \"functionName\": \"\",\n" +
                    "                            \"name\": \"train_uuid\",\n" +
                    "                            \"parameters\": [\n" +
                    "                                \"train_uuid\"\n" +
                    "                            ],\n" +
                    "                            \"flag\": true\n" +
                    "                        },\n" +
                    "                        {\n" +
                    "                            \"functionName\": \"\",\n" +
                    "                            \"name\": \"description\",\n" +
                    "                            \"parameters\": [\n" +
                    "                                \"description\"\n" +
                    "                            ],\n" +
                    "                            \"flag\": true\n" +
                    "                        },\n" +
                    "                        {\n" +
                    "                            \"functionName\": \"JSON_VALUE\",\n" +
                    "                            \"name\": \"ttime\",\n" +
                    "                            \"parameters\": [\n" +
                    "                                \"`data`\",\n" +
                    "                                \"'$.ttime'\"\n" +
                    "                            ],\n" +
                    "                            \"flag\": true\n" +
                    "                        },\n" +
                    "                        {\n" +
                    "                            \"functionName\": \"JSON_VALUE\",\n" +
                    "                            \"name\": \"crash_time\",\n" +
                    "                            \"parameters\": [\n" +
                    "                                \"`data`\",\n" +
                    "                                \"'$.crash_time'\"\n" +
                    "                            ],\n" +
                    "                            \"flag\": true\n" +
                    "                        }\n" +
                    "                    ]\n" +
                    "                },\n" +
                    "                \"config\": [\n" +
                    "                    {\n" +
                    "                        \"da6ed1e3-a37f-4bd9-a074-df3826b6ac38&output_0 32d2811f-bc0d-4e5e-9e99-ab312772d422&input_0\": [\n" +
                    "                            {\n" +
                    "                                \"name\": \"protocal\",\n" +
                    "                                \"type\": \"STRING\",\n" +
                    "                                \"flag\": true\n" +
                    "                            },\n" +
                    "                            {\n" +
                    "                                \"name\": \"type\",\n" +
                    "                                \"type\": \"STRING\",\n" +
                    "                                \"flag\": true\n" +
                    "                            },\n" +
                    "                            {\n" +
                    "                                \"name\": \"subtype\",\n" +
                    "                                \"type\": \"STRING\",\n" +
                    "                                \"flag\": true\n" +
                    "                            },\n" +
                    "                            {\n" +
                    "                                \"name\": \"time\",\n" +
                    "                                \"type\": \"TIMESTAMP_LTZ(3)\",\n" +
                    "                                \"flag\": true\n" +
                    "                            },\n" +
                    "                            {\n" +
                    "                                \"name\": \"group_id\",\n" +
                    "                                \"type\": \"STRING\",\n" +
                    "                                \"flag\": true\n" +
                    "                            },\n" +
                    "                            {\n" +
                    "                                \"name\": \"train_uuid\",\n" +
                    "                                \"type\": \"STRING\",\n" +
                    "                                \"flag\": true\n" +
                    "                            },\n" +
                    "                            {\n" +
                    "                                \"name\": \"description\",\n" +
                    "                                \"type\": \"STRING\",\n" +
                    "                                \"flag\": true\n" +
                    "                            },\n" +
                    "                            {\n" +
                    "                                \"name\": \"data`\",\n" +
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
                    "            \"id\": \"febb16af-aa7a-45e9-bb57-e8b4b1099394\",\n" +
                    "            \"zIndex\": 3,\n" +
                    "            \"source\": {\n" +
                    "                \"cell\": \"da6ed1e3-a37f-4bd9-a074-df3826b6ac38\",\n" +
                    "                \"port\": \"output_0\"\n" +
                    "            },\n" +
                    "            \"target\": {\n" +
                    "                \"cell\": \"32d2811f-bc0d-4e5e-9e99-ab312772d422\",\n" +
                    "                \"port\": \"input_0\"\n" +
                    "            }\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"position\": {\n" +
                    "                \"x\": -140,\n" +
                    "                \"y\": 48\n" +
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
                    "            \"id\": \"d266f0b5-60fe-4258-8436-871bee2d5dd9\",\n" +
                    "            \"name\": \"CepOperator\",\n" +
                    "            \"zIndex\": 4,\n" +
                    "            \"data\": {\n" +
                    "                \"parameters\": {\n" +
                    "                    \"partition\": \"group_id\",\n" +
                    "                    \"orderBy\": \"time\",\n" +
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
                    "                            \"quantifier\": \"{2}\"\n" +
                    "                        }\n" +
                    "                    ],\n" +
                    "                    \"columns\": [\n" +
                    "                        {\n" +
                    "                            \"functionName\": \"\",\n" +
                    "                            \"name\": \"protocal\",\n" +
                    "                            \"parameters\": [\n" +
                    "                                \"e1.protocal\"\n" +
                    "                            ],\n" +
                    "                            \"flag\": true\n" +
                    "                        },\n" +
                    "                        {\n" +
                    "                            \"functionName\": \"\",\n" +
                    "                            \"name\": \"type\",\n" +
                    "                            \"parameters\": [\n" +
                    "                                \"e1.type\"\n" +
                    "                            ],\n" +
                    "                            \"flag\": true\n" +
                    "                        },\n" +
                    "                        {\n" +
                    "                            \"functionName\": \"CONCAT\",\n" +
                    "                            \"name\": \"subtype\",\n" +
                    "                            \"parameters\": [\n" +
                    "                                \"'mark_report'\"\n" +
                    "                            ],\n" +
                    "                            \"flag\": true\n" +
                    "                        },\n" +
                    "                        {\n" +
                    "                            \"functionName\": \"\",\n" +
                    "                            \"name\": \"time\",\n" +
                    "                            \"parameters\": [\n" +
                    "                                \"e1.time\"\n" +
                    "                            ],\n" +
                    "                            \"flag\": true\n" +
                    "                        },\n" +
                    "                        {\n" +
                    "                            \"functionName\": \"\",\n" +
                    "                            \"name\": \"train_uuid\",\n" +
                    "                            \"parameters\": [\n" +
                    "                                \"e1.train_uuid\"\n" +
                    "                            ],\n" +
                    "                            \"flag\": true\n" +
                    "                        },\n" +
                    "                        {\n" +
                    "                            \"functionName\": \"\",\n" +
                    "                            \"name\": \"description\",\n" +
                    "                            \"parameters\": [\n" +
                    "                                \"e1.description\"\n" +
                    "                            ],\n" +
                    "                            \"flag\": true\n" +
                    "                        },\n" +
                    "                        {\n" +
                    "                            \"functionName\": \"\",\n" +
                    "                            \"name\": \"data\",\n" +
                    "                            \"parameters\": [\n" +
                    "                                \"e1.crash_time\"\n" +
                    "                            ],\n" +
                    "                            \"flag\": true\n" +
                    "                        },\n" +
                    "                        {\n" +
                    "                            \"functionName\": \"\",\n" +
                    "                            \"name\": \"group_id\",\n" +
                    "                            \"parameters\": [\n" +
                    "                                \"e1.group_id\"\n" +
                    "                            ],\n" +
                    "                            \"flag\": true\n" +
                    "                        }\n" +
                    "                    ],\n" +
                    "                    \"defines\": [\n" +
                    "                        {\n" +
                    "                            \"variable\": \"e1\",\n" +
                    "                            \"condition\": \"description = 'down'\"\n" +
                    "                        }\n" +
                    "                    ]\n" +
                    "                },\n" +
                    "                \"config\": [\n" +
                    "                    {\n" +
                    "                        \"32d2811f-bc0d-4e5e-9e99-ab312772d422&output_0 d266f0b5-60fe-4258-8436-871bee2d5dd9&input_0\": [\n" +
                    "                            {\n" +
                    "                                \"functionName\": \"\",\n" +
                    "                                \"name\": \"protocal\",\n" +
                    "                                \"parameters\": [\n" +
                    "                                    \"protocal\"\n" +
                    "                                ],\n" +
                    "                                \"flag\": true\n" +
                    "                            },\n" +
                    "                            {\n" +
                    "                                \"functionName\": \"\",\n" +
                    "                                \"name\": \"type\",\n" +
                    "                                \"parameters\": [\n" +
                    "                                    \"type\"\n" +
                    "                                ],\n" +
                    "                                \"flag\": true\n" +
                    "                            },\n" +
                    "                            {\n" +
                    "                                \"functionName\": \"\",\n" +
                    "                                \"name\": \"subtype\",\n" +
                    "                                \"parameters\": [\n" +
                    "                                    \"subtype\"\n" +
                    "                                ],\n" +
                    "                                \"flag\": true\n" +
                    "                            },\n" +
                    "                            {\n" +
                    "                                \"functionName\": \"\",\n" +
                    "                                \"name\": \"time\",\n" +
                    "                                \"parameters\": [\n" +
                    "                                    \"time\"\n" +
                    "                                ],\n" +
                    "                                \"flag\": true\n" +
                    "                            },\n" +
                    "                            {\n" +
                    "                                \"functionName\": \"\",\n" +
                    "                                \"name\": \"group_id\",\n" +
                    "                                \"parameters\": [\n" +
                    "                                    \"group_id\"\n" +
                    "                                ],\n" +
                    "                                \"flag\": true\n" +
                    "                            },\n" +
                    "                            {\n" +
                    "                                \"functionName\": \"\",\n" +
                    "                                \"name\": \"train_uuid\",\n" +
                    "                                \"parameters\": [\n" +
                    "                                    \"train_uuid\"\n" +
                    "                                ],\n" +
                    "                                \"flag\": true\n" +
                    "                            },\n" +
                    "                            {\n" +
                    "                                \"functionName\": \"\",\n" +
                    "                                \"name\": \"description\",\n" +
                    "                                \"parameters\": [\n" +
                    "                                    \"description\"\n" +
                    "                                ],\n" +
                    "                                \"flag\": true\n" +
                    "                            },\n" +
                    "                            {\n" +
                    "                                \"functionName\": \"JSON_VALUE\",\n" +
                    "                                \"name\": \"ttime\",\n" +
                    "                                \"parameters\": [\n" +
                    "                                    \"data\",\n" +
                    "                                    \"$.ttime\"\n" +
                    "                                ],\n" +
                    "                                \"flag\": true\n" +
                    "                            },\n" +
                    "                            {\n" +
                    "                                \"functionName\": \"JSON_VALUE\",\n" +
                    "                                \"name\": \"crash_time\",\n" +
                    "                                \"parameters\": [\n" +
                    "                                    \"data\",\n" +
                    "                                    \"$.crash_time\"\n" +
                    "                                ],\n" +
                    "                                \"flag\": true\n" +
                    "                            },\n" +
                    "\t\t\t\t\t\t\t{\n" +
                    "                                \"functionName\": \"\",\n" +
                    "                                \"name\": \"group_id\",\n" +
                    "                                \"parameters\": [\n" +
                    "                                    \"e1.group_id\"\n" +
                    "                                ],\n" +
                    "                                \"flag\": true\n" +
                    "                            },\n" +
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
                    "            \"id\": \"de74bfeb-3922-4772-a43b-7d599dc4706f\",\n" +
                    "            \"zIndex\": 5,\n" +
                    "            \"source\": {\n" +
                    "                \"cell\": \"32d2811f-bc0d-4e5e-9e99-ab312772d422\",\n" +
                    "                \"port\": \"output_0\"\n" +
                    "            },\n" +
                    "            \"target\": {\n" +
                    "                \"cell\": \"d266f0b5-60fe-4258-8436-871bee2d5dd9\",\n" +
                    "                \"port\": \"input_0\"\n" +
                    "            }\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"position\": {\n" +
                    "                \"x\": 36,\n" +
                    "                \"y\": 48\n" +
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
                    "            \"id\": \"f363374b-8a85-49bf-90be-b61f45a4c690\",\n" +
                    "            \"name\": \"CustomerSinkOperator\",\n" +
                    "            \"zIndex\": 6,\n" +
                    "            \"data\": {\n" +
                    "                \"parameters\": {\n" +
                    "                    \"other\": [\n" +
                    "                        {\n" +
                    "                            \"key\": \"connector\",\n" +
                    "                            \"values\": \"udp\"\n" +
                    "                        },\n" +
                    "                        {\n" +
                    "                            \"key\": \"format\",\n" +
                    "                            \"values\": \"json\"\n" +
                    "                        },\n" +
                    "                        {\n" +
                    "                            \"key\": \"hostName\",\n" +
                    "                            \"values\": \"225.0.0.1\"\n" +
                    "                        },\n" +
                    "                        {\n" +
                    "                            \"key\": \"port\",\n" +
                    "                            \"values\": \"6001\"\n" +
                    "                        },\n" +
                    "                        {\n" +
                    "                            \"key\": \"sink.parallelism\",\n" +
                    "                            \"values\": \"1\"\n" +
                    "                        }\n" +
                    "                    ],\n" +
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
                    "                        },\n" +
                    "                        {\n" +
                    "                            \"name\": \"subtype\",\n" +
                    "                            \"type\": \"STRING\",\n" +
                    "                            \"flag\": true\n" +
                    "                        },\n" +
                    "                        {\n" +
                    "                            \"name\": \"time\",\n" +
                    "                            \"type\": \"TIMESTAMP_LTZ(3)\",\n" +
                    "                            \"flag\": true\n" +
                    "                        },\n" +
                    "                        {\n" +
                    "                            \"name\": \"group_id\",\n" +
                    "                            \"type\": \"INTEGER\",\n" +
                    "                            \"flag\": true\n" +
                    "                        },\n" +
                    "                        {\n" +
                    "                            \"name\": \"train_uuid\",\n" +
                    "                            \"type\": \"STRING\",\n" +
                    "                            \"flag\": true\n" +
                    "                        },\n" +
                    "                        {\n" +
                    "                            \"name\": \"description\",\n" +
                    "                            \"type\": \"STRING\",\n" +
                    "                            \"flag\": true\n" +
                    "                        },\n" +
                    "                        {\n" +
                    "                            \"name\": \"data\",\n" +
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
                    "                        \"d266f0b5-60fe-4258-8436-871bee2d5dd9&output_0 f363374b-8a85-49bf-90be-b61f45a4c690&input_0\": [\n" +
                    "                            {\n" +
                    "                                \"functionName\": \"\",\n" +
                    "                                \"name\": \"protocal\",\n" +
                    "                                \"parameters\": [\n" +
                    "                                    \"e1.protocal\"\n" +
                    "                                ],\n" +
                    "                                \"flag\": true\n" +
                    "                            },\n" +
                    "                            {\n" +
                    "                                \"functionName\": \"\",\n" +
                    "                                \"name\": \"type\",\n" +
                    "                                \"parameters\": [\n" +
                    "                                    \"e1.type\"\n" +
                    "                                ],\n" +
                    "                                \"flag\": true\n" +
                    "                            },\n" +
                    "                            {\n" +
                    "                                \"functionName\": \"CONCAT\",\n" +
                    "                                \"name\": \"subtype\",\n" +
                    "                                \"parameters\": [\n" +
                    "                                    \"mark_report\"\n" +
                    "                                ],\n" +
                    "                                \"flag\": true\n" +
                    "                            },\n" +
                    "                            {\n" +
                    "                                \"functionName\": \"\",\n" +
                    "                                \"name\": \"time\",\n" +
                    "                                \"parameters\": [\n" +
                    "                                    \"e1.time\"\n" +
                    "                                ],\n" +
                    "                                \"flag\": true\n" +
                    "                            },\n" +
                    "                            {\n" +
                    "                                \"functionName\": \"\",\n" +
                    "                                \"name\": \"train_uuid\",\n" +
                    "                                \"parameters\": [\n" +
                    "                                    \"e1.train_uuid\"\n" +
                    "                                ],\n" +
                    "                                \"flag\": true\n" +
                    "                            },\n" +
                    "                            {\n" +
                    "                                \"functionName\": \"\",\n" +
                    "                                \"name\": \"description\",\n" +
                    "                                \"parameters\": [\n" +
                    "                                    \"e1.description\"\n" +
                    "                                ],\n" +
                    "                                \"flag\": true\n" +
                    "                            },\n" +
                    "                            {\n" +
                    "                                \"functionName\": \"\",\n" +
                    "                                \"name\": \"data\",\n" +
                    "                                \"parameters\": [\n" +
                    "                                    \"e1.crash_time\"\n" +
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
                    "            \"id\": \"865348d1-c594-497f-bb36-a4e7ad985fda\",\n" +
                    "            \"zIndex\": 7,\n" +
                    "            \"source\": {\n" +
                    "                \"cell\": \"d266f0b5-60fe-4258-8436-871bee2d5dd9\",\n" +
                    "                \"port\": \"output_0\"\n" +
                    "            },\n" +
                    "            \"target\": {\n" +
                    "                \"cell\": \"f363374b-8a85-49bf-90be-b61f45a4c690\",\n" +
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
