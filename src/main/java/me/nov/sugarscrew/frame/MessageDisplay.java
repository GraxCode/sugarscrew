package me.nov.sugarscrew.frame;

import javax.swing.*;

public class MessageDisplay {

  /**
   * Show an error message as JDialog
   */
  public static void showError(String message, String title) {
    JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
  }

  /**
   * Show an exception as JDialog
   */
  public static void showException(Exception e) {
    showException(e, "An exception occurred:\n%s");
  }

  /**
   * Show an exception with a message formatted using String.format as JDialog
   */
  public static void showException(Exception e, String format, Object... params) {
    JOptionPane.showMessageDialog(null, String.format(format, params) + "\n\n" + exceptionToStacktraceString(e), e.getClass().getName(), JOptionPane.ERROR_MESSAGE);
  }

  /**
   * Show a message as JDialog
   */
  public static void showMessage(String message, String title) {
    JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
  }

  public static String exceptionToStacktraceString(Exception e) {
    var sb = new StringBuilder();
    sb.append(e.getMessage()).append("\n\n");
    for (StackTraceElement ste : e.getStackTrace()) {
      sb.append(ste).append("\n");
    }
    return sb.toString();
  }
}
