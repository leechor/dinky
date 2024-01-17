package com.zdpx.coder.general;

import java.util.List;
import java.util.Map;

public interface GeneralProcess {

    List<String> getFunction(Map<String, Map<String, Object>> parametersLocal);

    List<Object> getOutPutColumn(Map<String, Map<String, Object>> parametersLocal);
}
