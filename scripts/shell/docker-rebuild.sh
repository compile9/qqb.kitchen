#!/bin/bash
docker container prune -f

dangling_images=$(docker images | grep "<none>" | awk '{print $3}')
if [ -n "$dangling_images" ]; then
  docker rmi -f $dangling_images;
else
  echo "No dangling images to remove."
fi

docker image prune -af
docker volume prune -f
docker compose down -v
docker compose build --no-cache
docker compose up -d

