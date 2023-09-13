<#macro fieldFunction fo>
    <@compress single_line=true>
        <#if fo.functionName??>${fo.functionName?no_esc}</#if>
        <#list fo.parameters as parameter>
            <#if instanceOf(parameter, "com.zdpx.coder.operator.FieldFunction")>
                <@fieldFunction parameter/>
            <#elseif parameter?is_number>
                ${parameter?c}
            <#else>
                ${parameter?no_esc}
            </#if>
            <#sep>${fo.delimiter!","}
        </#list>
        <#if fo.functionName??></#if>
    </@compress>
</#macro>

<#macro fieldsProcess fos single=true>
    <@compress single_line=single>
        <#list fos as fo>
            <#assign x><@fieldFunction fo/></#assign>
            <#if x?has_content> ${x}</#if><#if fo.outName?? && fo.functionName??> AS </#if><#if fo.outName??>`${fo.outName?no_esc}`</#if><#sep>,</#sep>
        </#list>
    </@compress>
</#macro>

<#macro cepFunction cep>
    SELECT *
    FROM ${cep.inputTableName}
    MATCH_RECOGNIZE(
    <#if cep.partition??>PARTITION BY `${cep.partition}` </#if>
    <#if cep.orderBy??>ORDER BY `${cep.orderBy}` </#if>
    MEASURES
    <@fieldsProcess cep.columns false/>

    <#if cep.outPutMode??>${cep.outPutMode} ROW PER MATCH</#if>
    AFTER MATCH SKIP ${cep.skipStrategy}
    PATTERN (<#list cep.patterns as pattern>${pattern}<#sep> </#sep><#rt></#list>) <#if cep.timeSpan??>WITHIN INTERVAL '${cep.timeSpan}' ${cep.timeUnit}</#if>
    DEFINE
    <#list cep.defines as define>
        ${define?no_esc}<#sep>,</#sep>
    </#list>
    )
</#macro>

