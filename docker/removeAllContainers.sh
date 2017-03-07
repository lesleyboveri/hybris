#!/bin/bash
set -e

sudo docker stop $(docker ps -q)
sudo docker rm $(docker ps -q -a)
