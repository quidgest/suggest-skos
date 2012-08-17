package pt.unl.fct.di.suggestskos.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Suggestions {
  private final long id;
  private final long hits;
  private final List<Map<String,String>> items;
  
  public Suggestions(long id, String[] content) {
    this.id = id;
    items = new ArrayList<Map<String,String>>();
    for (int i = 0; i < content.length - 1; i++) {
      Map<String,String> map = new HashMap<String,String>();
      map.put("value", content[i]);
      map.put("name", content[i]);
      items.add(map);
    }
    // last item is the number of hits
    hits = Long.parseLong(content[content.length - 1]);
  }
  
  public long getId() {
    return id;
  }
  
  public long getHits() {
    return hits;
  }
  
  public List<Map<String,String>> getItems() {
    return items;
  }
}
