NAME   := jrmcdonald/podcasts-downloader
IMG    := "${NAME}:$(TAG)"
LATEST := "${NAME}:latest"

deploy: install tag-latest push push-latest

install:
	@docker build -t ${IMG} .

tag-latest:
	@docker tag ${IMG} ${LATEST}
 
push:
	@docker push ${IMG}

push-latest:
	@docker push ${LATEST}

