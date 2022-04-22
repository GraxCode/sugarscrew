package me.nov.sugarscrew.logging;

public class Log {

  public static void info(String format, Object... param) {
    System.out.printf(format + "\n", param);
  }

  public static void warn(String format, Object... param) {
    System.err.printf(format + "\n", param);
  }

  public static void error(String format, Object... param) {
    System.err.printf(format + "\n", param);
  }

  public static void error(Throwable t, String format, Object... param) {
    System.err.printf(format + "\n", param);
    t.printStackTrace();
  }
}
