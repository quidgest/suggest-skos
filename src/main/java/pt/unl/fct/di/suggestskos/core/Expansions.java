package pt.unl.fct.di.suggestskos.core;

import java.util.List;

public class Expansions {
  private final long id;
  private final List<String> items;
  
  public Expansions(long id, List<String> content) {
    this.id = id;
    items = content;
  }
  
  public long getId() {
    return id;
  }
  
  public List<String> getItems() {
    return items;
  }
}
