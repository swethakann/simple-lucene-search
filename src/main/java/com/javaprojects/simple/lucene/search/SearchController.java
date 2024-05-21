package com.javaprojects.simple.lucene.search;

import com.javaprojects.simple.lucene.search.query.SearchRequest;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class SearchController {

  private static final String INDEX_DIR = "index";

  @RequestMapping(
      value = "/search",
      method = RequestMethod.POST,
      produces = "application/json; charset=utf-8",
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public List<String> search(@RequestBody SearchRequest searchRequest) throws IOException {
    List<String> results = new ArrayList<>();

    Directory indexDirectory = FSDirectory.open(Paths.get(INDEX_DIR));
    IndexReader indexReader = DirectoryReader.open(indexDirectory);
    IndexSearcher indexSearcher = new IndexSearcher(indexReader);

    Query query = searchRequest.parseJsonToQuery();
    int maxHits = searchRequest.getMaxHits();

    ScoreDoc[] hits = indexSearcher.search(query, maxHits).scoreDocs;
    for (ScoreDoc hit : hits) {
      Document doc = indexSearcher.doc(hit.doc);
      results.add(doc.get("fileName"));
    }
    return results;
  }
}
