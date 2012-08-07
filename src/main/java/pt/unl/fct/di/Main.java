package pt.unl.fct.di;

import java.io.IOException;
import java.util.Scanner;

import org.apache.lucene.util.Version;

import at.ac.univie.mminf.luceneSKOS.search.SKOSAutocompleter;

public class Main {
  
  public static void main(String[] args) throws IOException {
    String skosFile = "../../skos/eurovoc_skos.rdf.zip";
    
    // SKOSSuggester skosSuggester = new SKOSSuggester(Version.LUCENE_40,
    // skosFile, "en");
    SKOSAutocompleter skosAutocompleter = new SKOSAutocompleter(
        Version.LUCENE_40, skosFile, "en");
    
    Scanner sc = new Scanner(System.in);
    int numSug = 5;
    
    while (sc.hasNextLine()) {
      
      String word = sc.nextLine();
      
      String[] suggestSimilar = skosAutocompleter.suggestSimilar(word, numSug);
      
      if (suggestSimilar != null && suggestSimilar.length > 0) {
        for (String sug : suggestSimilar) {
          System.out.println(sug);
        }
      }
    }
    
  }
  
}
