package me.nov.sugarscrew.frame.tabs.classes;

import me.nov.sugarscrew.decompiler.Decompiler;
import me.nov.sugarscrew.frame.decompiler.DecompilerTextArea;
import org.fife.ui.rtextarea.RTextScrollPane;
import org.jf.dexlib2.dexbacked.DexBackedClassDef;

import javax.swing.*;
import java.awt.*;

public class ClassTabBody extends JPanel {
  public final DexBackedClassDef classDef;

  public ClassTabBody(DexBackedClassDef classDef) {
    super(new BorderLayout());

    this.classDef = classDef;

    // add decompiler text area
    var textArea = new DecompilerTextArea();
    textArea.setText(Decompiler.decompileClassDef(classDef));
    add(new RTextScrollPane(textArea), BorderLayout.CENTER);
  }
}
