=========================================================================
RED metrics for a REST service with Prometheus, Grafana, and AlertManager 
=========================================================================

How to monitor your micro-service with Prometheus? How to design metrics, what is USE and RED? Metrics for a REST service with Prometheus, AlertManager, and Grafana.

Slides and live-coding demo from `Warsaw Java User Group Meetup <https://www.meetup.com/Warszawa-JUG/events/255428108/>`_ in Warsaw:

- `in pdf <slides/index.pdf>`_ (source: `slides/ <slides/>`_)
- `LinkedIN Slideshare <https://www.slideshare.net/WojciechBarczyski/monitor-your-java-application-with-prometheus-stack/>`_
- src/ - an example REST application monitored with Prometheus, Alertmanager, and Grafana. Check `demo/README.rst <demo/README.rst>`_.

The demo consists of a Spring 1.5 application and configured prometheus with grafana and alertmanager. To make it event easier, a request generator will let you stress the monitored endpoints with random errors and latency injections. Check the READMEs.

If you find slides helpful, please give a LIKE to `my Linkedin post <https://www.linkedin.com/feed/update/urn:li:activity:6457577787816497152>`_ about this talk.

Related Work
============

- https://github.com/prometheus/jmx_exporter
- https://www.callicoder.com/spring-boot-actuator/
- https://blog.kubernauts.io/https-blog-kubernauts-io-monitoring-java-spring-boot-applications-with-prometheus-part-1-c0512f2acd7b
- https://www.weave.works/blog/the-red-method-key-metrics-for-microservices-architecture/
- http://www.brendangregg.com/usemethod.html
- https://github.com/prometheus/client_golang/blob/master/examples/random/main.go
- https://medium.com/@copyconstruct/logs-and-metrics-6d34d3026e38
- https://github.com/vegasbrianc/prometheus#post-configuration
- http://web.mit.edu/2.75/resources/random/How%20Complex%20Systems%20Fail.pdf
- https://www.slideshare.net/roidelapluie/prometheus-for-the-traditional-datacenter
- https://medium.com/@copyconstruct/logs-and-metrics-6d34d3026e38
- https://www.slideshare.net/brianbrazil/evolving-prometheus-for-the-cloud-native-world-fosdem-2018-brussels
