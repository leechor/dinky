package com.zdpx.coder.operator;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/** 用于定义算子特性属性，如外观等。 */
@Jacksonized
@Builder
@Value
public class OperatorFeature {
    String icon;
}
