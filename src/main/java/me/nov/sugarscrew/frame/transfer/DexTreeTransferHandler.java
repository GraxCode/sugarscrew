package me.nov.sugarscrew.frame.transfer;

import me.nov.sugarscrew.io.Import;
import me.nov.sugarscrew.logging.Log;

import javax.swing.*;
import java.awt.datatransfer.DataFlavor;
import java.io.File;
import java.util.List;
import java.util.function.Consumer;

public class DexTreeTransferHandler extends TransferHandler {
  private static final long serialVersionUID = 1L;
  private final Consumer<File> loadEvent;

  public DexTreeTransferHandler(Consumer<File> loadEvent) {
    this.loadEvent = loadEvent;
  }

  @Override
  public boolean canImport(TransferSupport support) {
    return support.isDrop() && support.isDataFlavorSupported(DataFlavor.javaFileListFlavor);
  }

  @Override
  public boolean importData(TransferSupport support) {
    if (!canImport(support) || !support.isDrop()) {
      return false;
    }
    var t = support.getTransferable();
    try {
      List<File> data = (List<File>) t.getTransferData(DataFlavor.javaFileListFlavor);
      for (File jar : data) {
        if (Import.isSupportedFile(jar, Import.SUPPORTED_FILE_TYPES)) {
          loadEvent.accept(jar);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
    return false;
  }

}
