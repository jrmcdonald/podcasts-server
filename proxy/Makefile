NAME   := "${REPOSITORY}/${ARTIFACT}"
IMG    := "${NAME}:$(TAG)"
LATEST := "${NAME}:latest"

all: install deploy

install:
	@docker build -t ${IMG} .
	@docker tag ${IMG} ${LATEST}
 
deploy:
	@docker push ${IMG}
	@docker push ${LATEST}

