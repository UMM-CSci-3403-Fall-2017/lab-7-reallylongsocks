#!/bin/bash

numCalls=$1

echo "--Small data load test--"
sleep 3

for (( i=1; i<=$numCalls; i++ ))
do
    echo "Doing run $i with short_text_file.txt"
    java echoserver.EchoClient < ../test/etc/short_text_file.txt > /dev/null &
done
echo "Now waiting"
date
wait
echo "Done"
date

echo "--Medium data load test--"
sleep 3

for (( i=1; i<=$numCalls; i++ ))
do
    echo "Doing run $i with words.txt"
    java echoserver.EchoClient < ../test/etc/words.txt > /dev/null &
done
echo "Now waiting"
date
wait
echo "Done"
date

echo "--Binary data/Highres photo load test--"
sleep 3

for (( i=1; i<=$numCalls; i++ ))
do
    echo "Doing run $i with words.txt"
    java echoserver.EchoClient < ../test/etc/french-polynesia.jpg > /dev/null &
done
echo "Now waiting"
date
wait
echo "Done"
date


