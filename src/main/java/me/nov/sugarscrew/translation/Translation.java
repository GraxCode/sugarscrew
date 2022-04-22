package me.nov.sugarscrew.translation;

import me.nov.sugarscrew.logging.Log;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import java.util.HashMap;

public class Translation {
  private static final HashMap<String, String> TRANSLATIONS = new HashMap<>();

  static {
    readLanguageXML("en");
    readLanguageXML(getLanguage());
    if (TRANSLATIONS.isEmpty()) {
      Log.error("Language loading failed.");
      System.exit(-1);
    }
  }

  public static String get(String id) {
    String s = TRANSLATIONS.get(id);
    if (s == null) {
      Log.warn("Resource string \"%s\" not defined.", id);
      return id;
    }
    return s;
  }

  public static String get(String id, Object... args) {
    return String.format(get(id), args);
  }

  private static void readLanguageXML(String lang) {
    var is = Translation.class.getResourceAsStream(lang + ".xml");
    if (is == null) {
      Log.warn("Language XML for \"%s\" not found.", lang);
      return;
    }

    try {
      var dbFactory = DocumentBuilderFactory.newInstance();
      var dBuilder = dbFactory.newDocumentBuilder();
      var doc = dBuilder.parse(is);
      doc.getDocumentElement().normalize();
      var resources = doc.getDocumentElement();
      var nodes = resources.getChildNodes();
      for (var i = 0; i < nodes.getLength(); i++) {
        var e = nodes.item(i);
        if (e.getNodeName().equals("string")) {
          Element el = (Element) e;
          TRANSLATIONS.put(el.getAttribute("name"), e.getTextContent());
        }
      }
    } catch (Exception e) {
      Log.error(e, "Language XML \"%s\" parsing failed.", lang);
    }
  }

  private static String getLanguage() {
    return System.getProperty("user.language").toLowerCase();
  }
}
