=========================================================================
RED metrics for a REST service with Prometheus, Grafana, and AlertManager 
=========================================================================

My goal is to convince you to start your projects with monitoring instead of jumping on centralized logging. To help you get started, I cover two most popular approaches in defining metrics - USE and RED. Based on my experience, I recommend you Prometheus and tell you why. The last part is a live demo of a java application with preconfigured Prometheus, Alertmanger, and Grafana. Naturally, the demo is on github, and works out-of-the-box with a single command. So, you can continue learning and experiencing on your own. 

Slides and live-coding demo from `Warsaw Java User Group Meetup <https://www.meetup.com/Warszawa-JUG/events/255428108/>`_ in Warsaw:

- `in pdf <slides/index.pdf>`_ (source: `slides/ <slides/>`_)
- `LinkedIN slideshare <https://www.slideshare.net/WojciechBarczyski/monitor-your-java-application-with-prometheus-stack/>`_
- `demo/ <demo/>`_ - an example REST application monitored with Prometheus, Alertmanager, and Grafana. Check `demo/README <demo/README.rst>`_.

If you find slides helpful, please give a LIKE to `my Linkedin post <https://www.linkedin.com/feed/update/urn:li:activity:6457577787816497152>`_ about the talk or a *STAR* to `this github repo <https://github.com/wojciech12/talk_java_2018_prometheus>`_.

Notice: The demo uses Spring 1.x, if you work with Spring 2.x, check `Jedrzej Serwa project  <https://github.com/jedrzejserwa/micrometer-spring-boot-sandbox>`_.

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
