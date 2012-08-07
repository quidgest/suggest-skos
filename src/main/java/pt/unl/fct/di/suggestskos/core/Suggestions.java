package pt.unl.fct.di.suggestskos.core;

public class Suggestions {
  private final long id;
  private final String[] content;
  
  public Suggestions(long id, String[] content) {
    this.id = id;
    this.content = content;
  }
  
  public long getId() {
    return id;
  }
  
  public String[] getContent() {
    return content;
  }
}
