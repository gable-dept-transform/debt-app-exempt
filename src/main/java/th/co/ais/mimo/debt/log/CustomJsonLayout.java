package th.co.ais.mimo.debt.log;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

import org.slf4j.MDC;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.contrib.json.classic.JsonLayout;

public class CustomJsonLayout extends JsonLayout {

  @Override
  protected void addCustomDataToJsonMap(Map<String, Object> map, ILoggingEvent event) {
      map.put("application", MDC.get("application"));

      try {
          map.put("host", InetAddress.getLocalHost().getHostName());
              map.put("traceid", MDC.get("x-b3-traceid"));
              map.put("status", MDC.get("status"));
              map.put("executeTime", MDC.get("executeTime"));
              map.put("uri",MDC.get("uri"));
              map.put("method",MDC.get("method"));
              map.put("httpStatus",MDC.get("httpStatus"));
      } catch (UnknownHostException e) {
    	  e.printStackTrace();
      }
  }

}
