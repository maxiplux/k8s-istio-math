#bash
docker buildx build --platform=linux/arm64 --push --tag maxiplux/api-gate-way:1.8.3 -f ./Dockerfile .
