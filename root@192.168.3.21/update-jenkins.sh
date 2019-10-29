#!/bin/bash -
workpath=$(cd $(dirname $0); pwd)
cd "${workpath}"

while read -r line
do
 curl -X POST http://jenkins.ops.dm-ai.cn/job/${line}/job/master/build  -H "Content-Type: application/x-www-form-urlencoded" -d 'json={"parameter":[{"name":"GIT_VERSION","value":"update"}]}'
 sleep 60
 curl -X POST http://jenkins.ops.dm-ai.cn/job/${line}/job/dev/build  -H "Content-Type: application/x-www-form-urlencoded" -d 'json={"parameter":[{"name":"GIT_VERSION","value":"update"}]}'
done < "${workpath}/project"

## curl -X POST http://jenkins.ops.dm-ai.cn/job/prometheus-alertmanager/job/master/build  -H "Content-Type: application/x-www-form-urlencoded" -d 'json={"parameter":[{"name":"GIT_VERSION","value":"update"}]}'

## curl -X POST http://jenkins.ops.dm-ai.cn/job/x3-research/job/master/build -H 'Content-Type: application/x-www-form-urlencoded' -d 'json={"parameter":[{"name":"GIT_VERSION","value":"update"}]}'