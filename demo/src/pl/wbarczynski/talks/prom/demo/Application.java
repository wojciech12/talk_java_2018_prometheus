package pl.wbarczynski.talks.prom.demo;

import io.prometheus.client.Histogram;
import io.prometheus.client.spring.boot.EnablePrometheusEndpoint;
import io.prometheus.client.spring.boot.EnableSpringBootMetricsCollector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

// Declare the Standard Spring boot annotation for instantiation
@SpringBootApplication

// Add route annotations below
@RestController

// Pull all metrics from Actuator and expose them as Prometheus metrics.
// Need to disable security feature in properties file.
@EnableSpringBootMetricsCollector


//Add a Prometheus metrics endpoint to the route `/prometheus`.
// `/metrics` is already taken by Actuator.
@EnablePrometheusEndpoint
@Configuration
public class Application {

  static Histogram aboutMe;
  static Histogram aboutDb;
  static Histogram aboutAudit;

  @RequestMapping("/hello")
  String home() {
    return "Hello!";
  }

  @RequestMapping("/world")
  ResponseEntity<String> world() {
    return ResponseEntity.status(HttpStatus.OK).body("World!");
  }


  @RequestMapping(value = "/complex", method = RequestMethod.GET)
  @ResponseBody
  ResponseEntity<String> complexOperation(
      @RequestParam(value = "db_sleep", required = false) Float dbSleep,
      @RequestParam(value = "is_db_error", required = false) Boolean isDbError,
      @RequestParam(value = "srv_sleep", required = false) Float auditSleep,
      @RequestParam(value = "is_srv_error", required = false) Boolean isAuditError,
      @RequestParam(value = "sleep", required = false) Float doSleep,
      @RequestParam(value = "is_error", required = false) Boolean doError

  ) {
	long start = System.currentTimeMillis();
    
    ResponseEntity<String> r = null;
    if (doWorkWithDb(dbSleep, isDbError)) {
      r = ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Db failed!");
    }
        
    if (doMyWork(doSleep, doError)) {
      r = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("We failed!");
    }
        
    if (doWorkWithAudit(auditSleep, isAuditError)) {
      r = ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Audit failed!");
    }
        
    if  (r == null) {
      r = ResponseEntity.status(HttpStatus.OK).body("Success!");
    }
        
    final double total = System.currentTimeMillis() - start;
    observeMyself(r.getStatusCodeValue(), total);
    return r;
  }

  public void observeMyself(int code, double durationMilis) {
    final String asStr = String.valueOf(code);
    final double inSec = durationMilis / 1000;
        
    aboutMe.labels("/complex", "GET", asStr).observe(inSec);
  }

  public boolean doWorkWithDb(Float dbSleep, Boolean isError) {
    //Histogram.Timer requestTimer = aboutDb.labels("100", "200").startTimer();
    boolean failed = false;
    try {
      doWork(dbSleep, isError, "database");
    } catch (RuntimeException e) {
      System.err.println("Database failed!");
      failed = true;
    }

    if (failed) {
      aboutDb.labels("1001", "HY000").observe(dbSleep);
    } else {
      aboutDb.labels("0", "0").observe(dbSleep);
    }
    return failed;
  }
    
  boolean doMyWork(Float dbSleep, Boolean isError) {
    boolean failed = false;
    try {
      doWork(dbSleep, isError, "me");
    } catch (RuntimeException e) {
      System.err.println("My business logic does not work!");
      failed = true;
    }
    return failed;
  }
    
  public boolean doWorkWithAudit(Float sleep, Boolean isError) {
    boolean failed = false;

    try {
      doWork(sleep, isError, "audit");
    } catch (RuntimeException e) {
      System.err.println("Audit service down!");
      failed = true;
    }
    
    if (failed) {
      aboutAudit.labels("/record", "POST", "500").observe(sleep);
    } else {
      aboutAudit.labels("/record", "POST", "200").observe(sleep);
    }
    return failed;
  }


  void doWork(Float sleep, Boolean isError, String failedComponent) {
    if (sleep != null) {
      long miliseconds = (long) (sleep * 1000);
      try {
        Thread.sleep(miliseconds);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }
    if (isError != null && isError.booleanValue()) {
      throw new RuntimeException(failedComponent);
    }
  }

  public static void main(String[] args) throws Exception {
    prepareCollectors("order-mgmt");
    SpringApplication.run(Application.class, args);    
  }

  public static void prepareCollectors(String serviceName) {
    final String prefix = serviceName.replace("-", "_");
    final String statusCode = "status_code";
    final String path = "path";
    final String httpMethod = "method";

    aboutMe = Histogram.build()
         .name(prefix + "_duration_seconds")
         .labelNames(path, httpMethod, statusCode)
         .help("my latency request distribution").register();
    
    aboutDb = Histogram.build()
      .name(prefix + "_database_duration_seconds")
      .labelNames(statusCode, "sql_state")
      .help("database latency request distribution").register();

    aboutAudit = Histogram.build()
      .name(prefix + "_audit_duration_seconds")
      .labelNames(path, httpMethod, statusCode)
      .help("audit service latency request distribution").register();
  }
}
