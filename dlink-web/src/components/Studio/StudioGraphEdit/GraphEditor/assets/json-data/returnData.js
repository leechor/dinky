const data = [
    {
        "operatorId": "5387ccfd-a53a-4336-aa48-872169fe14db",
        "tableName": "ADD JAR",
        "portInformation": null,
        "color": "red",
        "sqlErrorMsg": "org.apache.flink.table.api.ValidationException: The registering or unregistering jar resource [C:/Users/1/Downloads/aa_2023-07-19 16_32_22.json] must ends with '.jar' suffix.\r\n\tat org.apache.flink.table.resource.ResourceManager.checkJarPath(ResourceManager.java:228)\r\n\tat org.apache.flink.table.resource.ResourceManager.checkJarResources(ResourceManager.java:219)\r\n\tat org.apache.flink.table.resource.ResourceManager.registerJarResources(ResourceManager.java:93)\r\n\tat org.apache.flink.table.api.internal.TableEnvironmentImpl.addJar(TableEnvironmentImpl.java:471)\r\n\tat org.apache.flink.table.api.internal.TableEnvironmentImpl.executeInternal(TableEnvironmentImpl.java:1168)\r\n\tat org.apache.flink.table.api.internal.TableEnvironmentImpl.executeSql(TableEnvironmentImpl.java:730)\r\n\tat org.dinky.executor.DefaultTableEnvironment.executeSql(DefaultTableEnvironment.java:258)\r\n\tat org.dinky.executor.Executor.executeSql(Executor.java:228)\r\n\tat org.dinky.explainer.Explainer.explainSql(Explainer.java:202)\r\n\tat org.dinky.job.JobManager.explainSql(JobManager.java:750)\r\n\tat org.dinky.service.impl.StudioServiceImpl.explainFlinkSql(StudioServiceImpl.java:278)\r\n\tat org.dinky.service.impl.StudioServiceImpl.explainSql(StudioServiceImpl.java:260)\r\n\tat org.dinky.service.impl.StudioServiceImpl$$FastClassBySpringCGLIB$$2cea1b8b.invoke(<generated>)\r\n\tat org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218)\r\n\tat org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:793)\r\n\tat org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)\r\n\tat org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:763)\r\n\tat org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint.proceed(MethodInvocationProceedingJoinPoint.java:89)\r\n\tat org.dinky.aop.UdfClassLoaderAspect.round(UdfClassLoaderAspect.java:58)\r\n\tat sun.reflect.GeneratedMethodAccessor191.invoke(Unknown Source)\r\n\tat sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\r\n\tat java.lang.reflect.Method.invoke(Method.java:498)\r\n\tat org.springframework.aop.aspectj.AbstractAspectJAdvice.invokeAdviceMethodWithGivenArgs(AbstractAspectJAdvice.java:634)\r\n\tat org.springframework.aop.aspectj.AbstractAspectJAdvice.invokeAdviceMethod(AbstractAspectJAdvice.java:624)\r\n\tat org.springframework.aop.aspectj.AspectJAroundAdvice.invoke(AspectJAroundAdvice.java:72)\r\n\tat org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\r\n\tat org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:763)\r\n\tat org.springframework.aop.interceptor.ExposeInvocationInterceptor.invoke(ExposeInvocationInterceptor.java:97)\r\n\tat org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\r\n\tat org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:763)\r\n\tat org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:708)\r\n\tat org.dinky.service.impl.StudioServiceImpl$$EnhancerBySpringCGLIB$$dff8d928.explainSql(<generated>)\r\n\tat com.zdpx.service.impl.TaskTaskFlowGraphServiceImpl.convertConfigToSource(TaskTaskFlowGraphServiceImpl.java:139)\r\n\tat com.zdpx.service.impl.TaskTaskFlowGraphServiceImpl.saveOrUpdateTask(TaskTaskFlowGraphServiceImpl.java:82)\r\n\tat com.zdpx.service.impl.TaskTaskFlowGraphServiceImpl$$FastClassBySpringCGLIB$$7d534165.invoke(<generated>)\r\n\tat org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218)\r\n\tat org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:793)\r\n\tat org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)\r\n\tat org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:763)\r\n\tat org.springframework.transaction.interceptor.TransactionInterceptor$1.proceedWithInvocation(TransactionInterceptor.java:123)\r\n\tat org.springframework.transaction.interceptor.TransactionAspectSupport.invokeWithinTransaction(TransactionAspectSupport.java:388)\r\n\tat org.springframework.transaction.interceptor.TransactionInterceptor.invoke(TransactionInterceptor.java:119)\r\n\tat org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\r\n\tat org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:763)\r\n\tat org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:708)\r\n\tat com.zdpx.service.impl.TaskTaskFlowGraphServiceImpl$$EnhancerBySpringCGLIB$$d3330dc.saveOrUpdateTask(<generated>)\r\n\tat com.zdpx.controller.TaskFlowGraphController.submitSql(TaskFlowGraphController.java:53)\r\n\tat sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\r\n\tat sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\r\n\tat sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\r\n\tat java.lang.reflect.Method.invoke(Method.java:498)\r\n\tat org.springframework.web.method.support.InvocableHandlerMethod.doInvoke(InvocableHandlerMethod.java:205)\r\n\tat org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:150)\r\n\tat org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:117)\r\n\tat org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandlerMethod(RequestMappingHandlerAdapter.java:895)\r\n\tat org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:808)\r\n\tat org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter.handle(AbstractHandlerMethodAdapter.java:87)\r\n\tat org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:1072)\r\n\tat org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:965)\r\n\tat org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:1006)\r\n\tat org.springframework.web.servlet.FrameworkServlet.doPut(FrameworkServlet.java:920)\r\n\tat javax.servlet.http.HttpServlet.service(HttpServlet.java:558)\r\n\tat org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:883)\r\n\tat javax.servlet.http.HttpServlet.service(HttpServlet.java:623)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:209)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:153)\r\n\tat org.apache.tomcat.websocket.server.WsFilter.doFilter(WsFilter.java:51)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:178)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:153)\r\n\tat com.alibaba.druid.support.http.WebStatFilter.doFilter(WebStatFilter.java:124)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:178)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:153)\r\n\tat org.springframework.web.filter.RequestContextFilter.doFilterInternal(RequestContextFilter.java:100)\r\n\tat org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:117)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:178)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:153)\r\n\tat org.springframework.web.filter.FormContentFilter.doFilterInternal(FormContentFilter.java:93)\r\n\tat org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:117)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:178)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:153)\r\n\tat org.springframework.boot.actuate.metrics.web.servlet.WebMvcMetricsFilter.doFilterInternal(WebMvcMetricsFilter.java:96)\r\n\tat org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:117)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:178)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:153)\r\n\tat org.springframework.web.filter.CharacterEncodingFilter.doFilterInternal(CharacterEncodingFilter.java:201)\r\n\tat org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:117)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:178)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:153)\r\n\tat org.apache.catalina.core.StandardWrapperValve.invoke(StandardWrapperValve.java:167)\r\n\tat org.apache.catalina.core.StandardContextValve.invoke(StandardContextValve.java:90)\r\n\tat org.apache.catalina.authenticator.AuthenticatorBase.invoke(AuthenticatorBase.java:481)\r\n\tat org.apache.catalina.core.StandardHostValve.invoke(StandardHostValve.java:130)\r\n\tat org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.java:93)\r\n\tat org.apache.catalina.core.StandardEngineValve.invoke(StandardEngineValve.java:74)\r\n\tat org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.java:343)\r\n\tat org.apache.coyote.http11.Http11Processor.service(Http11Processor.java:389)\r\n\tat org.apache.coyote.AbstractProcessorLight.process(AbstractProcessorLight.java:63)\r\n\tat org.apache.coyote.AbstractProtocol$ConnectionHandler.process(AbstractProtocol.java:926)\r\n\tat org.apache.tomcat.util.net.NioEndpoint$SocketProcessor.doRun(NioEndpoint.java:1791)\r\n\tat org.apache.tomcat.util.net.SocketProcessorBase.run(SocketProcessorBase.java:52)\r\n\tat org.apache.tomcat.util.threads.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1191)\r\n\tat org.apache.tomcat.util.threads.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:659)\r\n\tat org.apache.tomcat.util.threads.TaskThread$WrappingRunnable.run(TaskThread.java:61)\r\n\tat java.lang.Thread.run(Thread.java:748)\r\n",
        "operatorErrorMsg": [
            "C:\\Users\\1\\Downloads\\aa_2023-07-19 16_32_22.json 不存在",
            "C:\\Users\\1\\Downloads\\aa_2023-07-19 16_32_22.json 不是一个可添加jar"
        ],
        "edge": null
    },
    {
        "operatorId": "480e6018-8d4a-41ec-94c1-bbdad5cf9fa6",
        "tableName": "MysqlSource_bbdad5cf9fa6",
        "portInformation": null,
        "color": "green",
        "sqlErrorMsg": null,
        "operatorErrorMsg": null,
        "edge": null
    },
    {
        "operatorId": "d1d41089-5e33-44b8-80e8-9a953926f3f5",
        "tableName": "_CommWindowFunctionOperator4",
        "portInformation": {
            "input_0": [
                "算子输入不包含该字段, 未匹配的字段名： aa",
                "算子输入不包含该字段, 未匹配的字段名： bb"
            ]
        },
        "color": "red",
        "sqlErrorMsg": null,
        "operatorErrorMsg": null,
        "edge": [
            "61ebf235-96bc-4bea-a44d-9bab98bf60a7"
        ]
    },
    {
        "operatorId": "ed662a81-acc4-49c9-95a7-4c0fa25e023d",
        "tableName": "KafKaSource_4c0fa25e023d",
        "portInformation": null,
        "color": "green",
        "sqlErrorMsg": null,
        "operatorErrorMsg": null,
        "edge": null
    },
    {
        "operatorId": "b4931ba7-f62e-480e-828a-08140cc0bd9b",
        "tableName": "_CommWindowFunctionOperator4",
        "portInformation": {
            "input_0": [
                "ORDER BY 对应的字段，必须是时间属性字段"
            ]
        },
        "color": "red",
        "sqlErrorMsg": null,
        "operatorErrorMsg": null,
        "edge": [
            "2e9ca94b-f8e0-4f77-824f-e8796e199f7b"
        ]
    },
    {
        "operatorId": "e7501108-923c-4fc4-bfa1-7f4699af2ef5",
        "tableName": "_JoinOperator6",
        "portInformation": {
            "primaryInput": [
                "columnList中的onLeftColumn需要主表中的字段,当前字段值： `k2`",
                "systemTimeColumn需要主表中的字段,当前字段值： k1",
                "输入参数中不包含字段 k1，请检查 primaryInput 连接桩的入参",
                "输入参数中不包含字段 k2，请检查 primaryInput 连接桩的入参"
            ],
            "secondInput": [
                "columnList中的onRightColumn需要副表中的字段,当前字段值： `o1`"
            ]
        },
        "color": "red",
        "sqlErrorMsg": null,
        "operatorErrorMsg": null,
        "edge": [
            "ac4587e1-9e22-4ede-a5be-0d1d74fb3393",
            "794ba9a6-2990-498c-b047-32ed70e8817f"
        ]
    },
    {
        "operatorId": "44a0780a-c584-4198-869c-0b83e3583144",
        "tableName": null,
        "portInformation": null,
        "color": "green",
        "sqlErrorMsg": null,
        "operatorErrorMsg": null,
        "edge": null
    },
    {
        "operatorId": "c9df1602-6a1c-4b8c-8bc1-828e6dd0dc9f",
        "tableName": "MysqlSink_828e6dd0dc9f",
        "portInformation": {
            "input_0": [
                "WATERMARK中指定的列不包含时间属性字段",
                "输入字段和输出表中指定的字段数量不匹配",
                "输入字段和输出表中指定参数类型不匹配 ，参数名称： aa 参数类型 ： null -> STRING",
                "输入字段和输出表中指定参数类型不匹配 ，参数名称： bb 参数类型 ： null -> STRING"
            ]
        },
        "color": "red",
        "sqlErrorMsg": null,
        "operatorErrorMsg": null,
        "edge": [
            "ad53917e-1ec6-4dfb-bf65-b5527ffd8efc"
        ]
    },
    {
        "operatorId": "41e33297-edba-42c6-817c-be73ff9eb107",
        "tableName": "customerSink_be73ff9eb107",
        "portInformation": {
            "input_0": [
                "输入字段和输出表中指定参数类型不匹配 ，参数名称： aaa 参数类型 ： null -> STRING",
                "输入字段和输出表中指定参数类型不匹配 ，参数名称： bbb 参数类型 ： null -> STRING"
            ]
        },
        "color": "red",
        "sqlErrorMsg": null,
        "operatorErrorMsg": null,
        "edge": [
            "ec0ca3ac-d5c7-40fe-8054-3904663da616"
        ]
    }
]