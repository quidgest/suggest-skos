package pt.unl.fct.di;

import java.io.IOException;
import java.util.Scanner;

import org.apache.lucene.util.Version;

import at.ac.univie.mminf.luceneSKOS.search.SKOSAutocompleter;
import at.ac.univie.mminf.luceneSKOS.search.spell.SKOSSuggester;

public class Main {
  
  public static void main(String[] args) throws IOException {
    String skosFile = "../../skos/eurovoc_skos.rdf.zip";
    
    SKOSSuggester skosSuggester = new SKOSSuggester(Version.LUCENE_40,
        skosFile, "en");
    SKOSAutocompleter skosAutocompleter = new SKOSAutocompleter(Version.LUCENE_40,
        skosFile, "en");
    
    Scanner sc = new Scanner(System.in);
    int numSug = 5;
    
    while (sc.hasNextLine()) {
      
      String word = sc.nextLine();
      
      String[] suggestSimilar = skosSuggester.suggestSimilar(word, 1);
      
      if (suggestSimilar != null && suggestSimilar.length > 0) {
        String[] suggestSimilar2 = skosAutocompleter.suggestSimilar(suggestSimilar[0], numSug);
        for (String sug : suggestSimilar2) {
          System.out.println(sug);
        }
      }
    }
    
  }
  
}
