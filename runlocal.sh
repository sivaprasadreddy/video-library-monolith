#!/bin/bash

declare jar_name='videolibrary/build/libs/videolibrary-0.0.1.jar'

function build_docker_image() {
    ./gradlew :video-library:jibDockerBuild
}

function build_api() {
    ./gradlew :video-library:build
}

function start() {
  build_api
  profiles="local"
  #vmargs="-Xlog:gc*=debug:stdout -Xlog:gc*=debug:file=gc.log"
  vmargs="-Xlog:gc*=debug:file=gc.log"
  if [[ "$1" != ""  ]]
  then
      profiles="$1"
  fi
  nohup java $vmargs -jar $jar_name \
        --spring.profiles.active=$profiles \
        -Xms1G -Xmx3G > nohup.out &
  echo "VideoLibrary application started"
  tail -f nohup.out
}

function stop() {
    kill -9 $(ps aux | grep $jar_name | grep -v grep | awk '{print $2}')
}

function restart() {
    stop
    sleep 5
    start $1
}

action="restart"

if [[ "$#" != "0"  ]]
then
    action=$@
fi

eval ${action}
