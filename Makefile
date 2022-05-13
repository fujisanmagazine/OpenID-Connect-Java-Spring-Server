app      = openid-connect-java-spring-server
version := 0.0.1

.PHONY: build
build:
	docker build \
		--build-arg APP_VERSION=$(version) \
		-t "$(app):$(version)" .

start-server: build
	docker run \
		-it \
		--rm \
		-p 8081:8080 \
		-v `pwd`:/var/local/app \
		"$(app):$(version)" /bin/bash -c "mvn clean install -Dmaven.test.skip=true && cd openid-connect-server-webapp/ && mvn jetty:run-war -Dmaven.test.skip=true"



.PHONY: start-console
start-console: build
	docker run \
		-it \
		--rm \
		-p 8081:8080 \
		-v `pwd`:/var/local/app \
		"$(app):$(version)" /bin/bash

