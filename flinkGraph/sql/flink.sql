CREATE TABLE DT_addSource_1 (
typ STRING,
 taskId STRING,
 id STRING,
 longitude DOUBLE,
 latitude DOUBLE,
 dt TIMESTAMP(0),
 va DOUBLE,
 WATERMARK FOR dt AS dt - INTERVAL '15' SECOND)
WITH ('connector' = 'gbuzl', 'table-name' = 'DT');

CREATE TABLE TS_addSource_2 (
typ STRING,
 taskId STRING,
 taskStatus INT,
 dt TIMESTAMP(0),
 WATERMARK FOR dt AS dt - INTERVAL '15' SECOND,
 PRIMARY KEY(taskId) NOT ENFORCED)
WITH ('connector' = 'task', 'table-name' = 'TS');

CREATE VIEW V_GBU_addSource_1 AS
SELECT typ, taskId, id, longitude, latitude, dt, va
FROM DT_addSource_1 WHERE typ = 'gbu';

CREATE VIEW V_ZL_addSource_1 AS
SELECT typ, taskId, id, longitude, latitude, dt, va
FROM DT_addSource_1 WHERE typ = 'zl';

CREATE VIEW JoinOperator16 AS
SELECT id, V_GBU_addSource_1.taskId, taskStatus, V_GBU_addSource_1.dt AS gbu_time, TS_addSource_2.dt AS task_time
FROM V_GBU_addSource_1 LEFT JOIN TS_addSource_2
FOR SYSTEM_TIME AS OF V_GBU_addSource_1.dt
ON V_GBU_addSource_1.taskId = TS_addSource_2.taskId;

CREATE TABLE ts_mysqlSink_1 (id STRING, taskId STRING, taskStatus INT, gbu_time TIMESTAMP(3), task_time TIMESTAMP(3))
WITH (
 'password' = '123456',
 'connector' = 'jdbc',
 'url' = 'jdbc:mysql://192.168.1.88:3306/flink?allowPublicKeyRetrieval=true',
 'table-name' = 'gbu_data',
 'username' = 'root');

INSERT INTO ts_mysqlSink_1 (id, taskId, taskStatus, gbu_time, task_time)
SELECT id, taskId, taskStatus, gbu_time, task_time
FROM JoinOperator16;

select * from JoinOperator16;
