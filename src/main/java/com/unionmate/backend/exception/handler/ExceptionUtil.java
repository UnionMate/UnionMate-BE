package com.unionmate.backend.exception.handler;

public abstract class ExceptionUtil {

  public static boolean isClientAbort(Throwable e) {
    int depth = 0;
    while (e != null && depth < 8) {
      if (e instanceof org.apache.catalina.connector.ClientAbortException) {
        return true;
      }
      e = e.getCause();
      depth++;
    }
    return false;
  }
}
