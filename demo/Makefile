PROJECT_NAME=java-prom

docker_build:
	docker-compose -f docker-compose.yaml --project-name $(PROJECT_NAME) build && \
	docker tag ${PROJECT_NAME}_order-manager wojciech12/$(PROJECT_NAME)

start:
	docker-compose -f docker-compose.yaml --project-name $(PROJECT_NAME) up --build -d

stop:
	docker-compose -f docker-compose.yaml --project-name $(PROJECT_NAME) stop

prometheus_reload_config:
	curl 127.0.0.1:9090/-/reload -X POST

srv_random_trafic:
	curl 127.0.0.1:8080/hello ; \
	curl 127.0.0.1:8080/world ;
	
srv_random_trafic_complex_failed_db:
	curl 127.0.0.1:8080/complex?is_db_error=True

srv_random_trafic_complex_slow_db_and_svc:
	curl '127.0.0.1:8080/complex?db_sleep=3&srv_sleep=2'

srv_random_trafic_complex_failed_third_party:
	curl 127.0.0.1:8080/complex?is_srv_error=True

srv_wrk_random:
	 wrk -t8 -c12 -d360s -s tools/load_generator/mixed.lua http://127.0.0.1:8080

# clean prometheus and grafana data
# mounted to host filesystem
clean:
	rm -rf prometheus/data/ && \
	rm -rf grafana/data && mkdir grafana/data && touch grafana/data/.gitkeep
