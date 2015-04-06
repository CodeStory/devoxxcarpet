#!/bin/bash

wget -q https://storage.googleapis.com/cloud-debugger/compute-java/format_env_gce.sh ; \
  chmod +x ./format_env_gce.sh

CDBG_ARGS="$( ./format_env_gce.sh --app_class_path=target --version=1 )"

echo ${CDBG_ARGS}

java ${CDBG_ARGS} -DPROD_MODE=true -Xmx2G -jar target/carpet.jar