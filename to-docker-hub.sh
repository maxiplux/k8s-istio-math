#bash
docker buildx build --platform=linux/arm64,linux/amd64 --push --tag maxiplux/math-add-subtract:1.0.0 -f ./Dockerfile .