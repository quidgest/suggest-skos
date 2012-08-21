package pt.unl.fct.di.suggestskos.core;

public class Expansions {
  private final long id;
  private final String[] items;
  
  public Expansions(long id, String[] content) {
    this.id = id;
    items = content;
  }
  
  public long getId() {
    return id;
  }
  
  public String[] getItems() {
    return items;
  }
}
