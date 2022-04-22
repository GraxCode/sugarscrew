package me.nov.sugarscrew.decompiler;

import jadx.api.JadxArgs;
import jadx.api.JadxDecompiler;
import jadx.api.impl.NoOpCodeCache;
import me.nov.sugarscrew.translation.Translation;
import org.jf.dexlib2.Opcodes;
import org.jf.dexlib2.dexbacked.DexBackedClassDef;
import org.jf.dexlib2.iface.ClassDef;
import org.jf.dexlib2.iface.DexFile;
import org.jf.dexlib2.writer.io.MemoryDataStore;
import org.jf.dexlib2.writer.pool.DexPool;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Set;

/**
 * Jadx decompiler implementation
 */
public class Decompiler {

  /**
   * Decompiles the given class def to java source.
   * It first writes the class def to a temporary file as smali, then decompiles it using jadx.
   *
   * @param classDef the class def to decompile
   */
  public static String decompileClassDef(DexBackedClassDef classDef) {
    try {

      var tempDex = new TemporarySoloDexFile(classDef.dexFile, classDef);
      var dataStore = new MemoryDataStore();
      DexPool.writeTo(dataStore, tempDex);

      var tempDexFile = File.createTempFile("sugarscrew", ".dex");
      // write dataStore data to temp file
      Files.write(tempDexFile.toPath(), dataStore.getData());

      // TODO when latest jadx is released use input stream instead of file

      var jadxArgs = new JadxArgs();
      jadxArgs.setInputFile(tempDexFile); // TODO remove this when jadx supports input stream

      jadxArgs.setShowInconsistentCode(true);
      jadxArgs.setDebugInfo(false);
      // as we don't want to cache the code
      jadxArgs.setCodeCache(new NoOpCodeCache());

      try (var jadx = new JadxDecompiler(jadxArgs)) {
        // TODO jadx.addCustomLoad(new DexInputPlugin().loadDexFromInputStream(in, "input"));
        jadx.load();

        return jadx.getClasses().get(0).getCode();
      } catch (Exception e) {
        e.printStackTrace();
        return Translation.get("decompiler_error", e.getMessage());
      }
    } catch (IOException e) {
      e.printStackTrace();
      return Translation.get("decompiler_error_io", e.getMessage());
    }
  }

  public static class TemporarySoloDexFile implements DexFile {

    private final DexFile original;
    private final ClassDef classDef;

    public TemporarySoloDexFile(DexFile original, ClassDef classDef) {
      this.original = original;
      this.classDef = classDef;
    }

    @Override
    public Set<? extends ClassDef> getClasses() {
      return Set.of(classDef);
    }

    @Override
    public Opcodes getOpcodes() {
      return original.getOpcodes();
    }
  }
}
