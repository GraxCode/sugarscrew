package me.nov.sugarscrew.frame.list;

import org.jf.dexlib2.dexbacked.DexBackedMethod;

import javax.swing.*;

/**
 * List to select fields and methods of a class.
 */
public class MemberList extends JList<String> {
  private static final long serialVersionUID = 1L;

  public MemberList() {
    super();
    setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    setLayoutOrientation(JList.VERTICAL);
  }
}
