docker container prune -f
docker rmi -f $(docker images | grep "<none>" | awk '{print $3}')
docker image prune -af
docker volume prune -f
docker compose down -v
docker compose build --no-cache
docker compose up -d

