
=============
RED in Java
=============

The demo consists of a Spring 1.5 application and configured prometheus with grafana and alertmanager. To make it event easier, a request generator will let you stress the monitored endpoints with random errors and latency injections.

How-to
======

1. Start the appliction, prometheus, alertmanager, and grafana with docker-compose:

   ::

     make docker_build
     make start

2. Access the services:

   - 8080 - our application
   - 9090 - prometheus webgui
   - 9093 - alertmanager
   - 3000 - grafana

2. Single calls, see targets with prefix *srv_* in `<Makefile>`_

3. Generate trafic (open grafana dashboard to see the metrics):

   ::

     make srv_wrk_random

4. Questions:

   - What can we learn from the graphs?
   - Can we say sth about out random calls?
   - Naming? Is it good?

Modifing the default configuration
==================================

Docker-compose mounts all configuration from the git repo. You can change it locally on your laptop.

1. To reload prometheus configuration after changes:

   ::

     make prometheus_reload_config

2. To reload grafana configuration, restart the grafana docker:

   ::

     docker restart java-prom_grafana_1

Development
===========
    
- start the app and prometheus stack with docker-compose:

  ::
  
    make start

- check the Makefile for example of calls

- to use the traffic generator, you need to install first *wrk*:

  ::

    make srv_wrk_random

Example of Prometheus Queries
=============================

- simple:

  ::

    order_mgmt_duration_seconds_sum{status_code='200'}

  ::

    order_mgmt_duration_seconds_sum{job=~".*"}
    or
    order_mgmt_database_duration_seconds_sum{job=~".*"}
    or
    order_mgmt_audit_duration_seconds_sum{job=~".*"}

- based on weave blog (https://www.weave.works/blog/of-metrics-and-middleware/):

  - QPS:

    ::

      sum(irate(order_mgmt_duration_seconds_count{job=~".*"}[1m])) by (status_code)

  - will give you the rate of requests returning 500s:

    ::

      sum(irate(order_mgmt_duration_seconds_count{job=~".*", status_code=~"5.."}[1m]))

  - by status_code:

    ::

      sum(irate(order_mgmt_duration_seconds_count{job=~".*"}[1m])) by (status_code)

  - 500s:

    ::

      sum(irate(order_mgmt_duration_seconds_count{job=~".*", status_code=~"5.."}[1m]))
      
  - will give you the 5-min moving 99th percentile request latency:

    ::

      histogram_quantile(0.99, sum(rate(order_mgmt_duration_seconds_count{job=~".*",ws="false"}[5m])) by (le))

Related Work
============

- https://prometheus.io/docs/prometheus/latest/querying/functions/
- https://www.robustperception.io/combining-alert-conditions/
- https://github.com/prometheus/jmx_exporter
- https://www.callicoder.com/spring-boot-actuator/
- https://blog.kubernauts.io/https-blog-kubernauts-io-monitoring-java-spring-boot-applications-with-prometheus-part-1-c0512f2acd7b
