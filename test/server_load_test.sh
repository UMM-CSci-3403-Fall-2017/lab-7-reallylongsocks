#!/bin/bash

numCalls=$1

for (( i=1; i<=$numCalls; i++ ))
do
    echo "Doing run $i"
    java echo.EchoClient < ./etc/short_text_file.txt > /dev/null &
done
echo "Now waiting"
date
wait
echo "Done"
date
