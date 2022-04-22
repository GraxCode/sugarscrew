package me.nov.sugarscrew.listener;

import me.nov.sugarscrew.translation.Translation;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ExitListener extends WindowAdapter {
  private JFrame frame;

  public ExitListener(JFrame frame) {
    this.frame = frame;
  }

  @Override
  public void windowClosing(WindowEvent we) {
    if (JOptionPane
            .showConfirmDialog(frame, Translation.get("dialog_exit"), Translation.get("dialog_confirm"), JOptionPane.YES_NO_OPTION) ==
            JOptionPane.YES_OPTION) {
      Runtime.getRuntime().exit(0);
    }
  }
}
