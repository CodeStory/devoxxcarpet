#!/bin/bash

if [ ! -z "$GAE_MODULE_NAME" ]; then
  echo "Running inside GAE ($GAE_PARTITION)."

  if [ ! "$GAE_PARTITION" == "dev" ]; then
    echo "Production mode. Setup Debugger agent."

    wget -q https://storage.googleapis.com/cloud-debugger/compute-java/format_env_gce.sh
    chmod +x ./format_env_gce.sh

    CDBG_ARGS="$( ./format_env_gce.sh --app_class_path=target --version="$GAE_MODULE_VERSION" --module="$GAE_MODULE_NAME" )"
  fi
fi

exec java ${CDBG_ARGS} -DPROD_MODE=true -jar target/carpet.jar
