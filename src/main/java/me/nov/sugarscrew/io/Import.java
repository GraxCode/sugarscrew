package me.nov.sugarscrew.io;

import me.nov.sugarscrew.translation.Translation;
import org.jf.dexlib2.DexFileFactory;
import org.jf.dexlib2.Opcodes;
import org.jf.dexlib2.dexbacked.DexBackedClassDef;
import org.jf.dexlib2.dexbacked.DexBackedDexFile;

import javax.swing.filechooser.FileFilter;
import java.io.File;
import java.io.IOException;

public class Import {

  public static final String[] SUPPORTED_FILE_TYPES = {"dex", "apk", "zip"};

  /**
   * Use dexlib2 to convert input file to dex tree structure.
   * Accepts .zip, or .dex.
   */
  public static DexBackedDexFile loadDexFile(File file) throws IOException {
    if (!isSupportedFile(file, SUPPORTED_FILE_TYPES)) {
      throw new IOException("Unsupported file type: " + file.getName());
    }
    return DexFileFactory.loadDexFile(file, Opcodes.forApi(52));
  }

  /**
   * Write DexBackedDexFile to output file.
   */
  public static void writeDexFile(DexBackedDexFile dex, File file) throws IOException {
    // TODO zip / apk export logic.
    String extension = getExtension(file);
    if (extension == null || !extension.equals("dex")) {
      throw new IOException("Unsupported file type: " + file.getName());
    }
    DexFileFactory.writeDexFile(file.getAbsolutePath(), dex);
  }

  /**
   * Check if file has extension of the supported file types
   */
  public static boolean isSupportedFile(File file, String... extensions) {
    String extension = getExtension(file);
    if (extension != null) {
      for (String ext : extensions) {
        if (extension.toLowerCase().equals(ext)) {
          return true;
        }
      }
    }
    return false;
  }

  public static String getExtension(File file) {
    if (file.isDirectory()) return null;

    String name = file.getName();
    int i = name.lastIndexOf('.');
    if (i > 0 && i < name.length() - 1) {
      return name.substring(i + 1).toLowerCase();
    }
    return null;
  }

  public static class ImportFileFilter extends FileFilter {
    @Override
    public boolean accept(File f) {
      return isSupportedFile(f, SUPPORTED_FILE_TYPES);
    }

    @Override
    public String getDescription() {
      return String.format(Translation.get("filechooser_supported_types"), String.join(", ", SUPPORTED_FILE_TYPES));
    }
  }
}
