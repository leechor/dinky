
CREATE TABLE DT_addSource_1 (t STRING, data STRING) WITH ('connector' = 'gbuzl', 'table-name' = 'DT');

CREATE TABLE TS_addSource_2 (t STRING, data STRING) WITH ('connector' = 'task', 'table-name' = 'TS');

CREATE VIEW V_DT_addSource_1 AS
SELECT 'task' AS task,
        t AS type,
       JSON_VALUE( data , '$.id' ) AS id,
       JSON_VALUE( data , '$.longitude' ) AS longitude,
       JSON_VALUE( data , '$.latitude' ) AS latitude,
       JSON_VALUE( data , '$.dt[1]' ) AS dt,
       JSON_VALUE( data , '$.value' ) AS v,
       PROCTIME() AS proc_time
FROM DT_addSource_1;

CREATE VIEW V_TS_addSource_2 AS
SELECT CAST( t AS STRING ) AS task,
        JSON_VALUE( data , '$.taskId' ) AS taskId,
        CAST( JSON_VALUE( data , '$.taskStatus' ) AS INT ) AS taskStatus,
        PROCTIME() AS proc_time
FROM TS_addSource_2;

CREATE TTF temporal AS SELECT proc_time, taskId FROM V_TS_addSource_2;

CREATE VIEW _JoinOperator16 AS
SELECT type, id, longitude, latitude, dt, v, d.task, d.taskId, d.taskStatus, d.proc_time
FROM V_DT_addSource_1, LATERAL TABLE (temporal(proc_time))
WHERE V_DT_addSource_1.task = V_TS_addSource_2.taskId;



select * from _JoinOperator16;

