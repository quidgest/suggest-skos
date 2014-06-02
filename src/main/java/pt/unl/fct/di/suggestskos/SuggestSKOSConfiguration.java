package pt.unl.fct.di.suggestskos;

import io.dropwizard.Configuration;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;


public class SuggestSKOSConfiguration extends Configuration {
  @NotEmpty
  @JsonProperty
  private String fileName;

  @JsonProperty
  private String languages;
  
  public String getLanguages() {
    return languages;
  }
  
  public String getFileName() {
    return fileName;
  }
}
