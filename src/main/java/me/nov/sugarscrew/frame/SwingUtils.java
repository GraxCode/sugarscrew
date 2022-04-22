package me.nov.sugarscrew.frame;

import com.github.weisj.darklaf.components.border.DarkBorders;

import javax.swing.*;
import java.awt.*;

public class SwingUtils {
  public static JPanel withLineBorder(JComponent c) {
    JPanel p = new JPanel(new BorderLayout());
    p.setBorder(DarkBorders.createLineBorder(1, 1, 1, 1));
    p.add(c, BorderLayout.CENTER);
    return p;
  }

  public static JPanel withTopLineBorder(JComponent c) {
    JPanel p = new JPanel(new BorderLayout());
    p.setBorder(DarkBorders.createLineBorder(1, 0, 0, 0));
    p.add(c, BorderLayout.CENTER);
    return p;
  }
}
