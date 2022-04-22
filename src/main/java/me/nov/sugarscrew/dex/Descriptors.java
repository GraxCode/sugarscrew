package me.nov.sugarscrew.dex;

import org.jf.dexlib2.dexbacked.DexBackedClassDef;

public class Descriptors {
  /**
   * Converts a type descriptor to a type name. Also works for arrays.
   */
  public static String toTypeName(String descriptor) {
    int arrays = 0;
    while (descriptor.charAt(0) == '[') {
      descriptor = descriptor.substring(1);
      arrays++;
    }
    StringBuilder name = new StringBuilder(descriptor.substring(1, descriptor.length() - 1));

    for (int i = 0; i < arrays; i++) {
      name.append("[]");
    }

    return name.toString().replace("/", ".");
  }


  /**
   * Converts a type name to a type descriptor. Also works for arrays.
   */
  public static String toDescriptor(String typeName) {
    int arrays = 0;
    while (typeName.endsWith("[]")) {
      typeName = typeName.substring(0, typeName.length() - 2);
      arrays++;
    }
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < arrays; i++) {
      sb.append("[");
    }
    sb.append('L');
    sb.append(typeName);
    sb.append(';');
    return sb.toString();
  }

  /**
   * Gets the simple name of a type.
   */
  public static String getSimpleName(String typeName) {
    if (typeName.endsWith(";"))
      throw new IllegalArgumentException("Type name is a descriptor: " + typeName);

    return typeName.substring(typeName.lastIndexOf('.') + 1);
  }

  public static String getSimpleName(DexBackedClassDef classDef) {
    return getSimpleName(toTypeName(classDef.getType()));
  }
}
