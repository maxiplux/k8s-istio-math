#bash
docker buildx build --platform=linux/arm64,linux/amd64 --push --tag maxiplux/api-gate-way:1.8.0 -f ./Dockerfile .
