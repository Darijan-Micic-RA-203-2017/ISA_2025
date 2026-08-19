# REFERENCE: https://sliplane.io/blog/setting-up-redis-with-docker-compose
# REFERENCE: https://hub.docker.com/_/redis
# REFERENCE: https://stackoverflow.com/questions/70807705/how-to-use-redis-username-with-password-in-docker-compose
# REFERENCE: https://oneuptime.com/blog/post/2026-01-21-redis-docker-compose/view

# The Redis Alpine image, which is a lightweight version of the Redis image, is used.
FROM redis:alpine

# The default Redis port (6379) is exposed.
EXPOSE 6379

# REFERENCE: https://oneuptime.com/blog/post/2026-01-25-redis-noauth-authentication-error/view
# REFERENCE: https://oneuptime.com/blog/post/2026-01-25-fix-redis-authentication-failed-errors/view
# The command to configure the Redis server is provided in the Docker compose file.
CMD ["redis-server"]
