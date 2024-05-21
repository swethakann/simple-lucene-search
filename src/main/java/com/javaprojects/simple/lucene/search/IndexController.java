package com.javaprojects.simple.lucene.search;

import java.io.*;
import java.nio.file.Paths;
import java.util.Objects;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

  private static final String INDEX_DIR = "index";
  private final Analyzer analyzer = new StandardAnalyzer();

  @GetMapping("/index")
  public String createIndex() throws IOException {
    Directory indexDirectory = FSDirectory.open(Paths.get(INDEX_DIR));
    IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
    IndexWriter indexWriter = new IndexWriter(indexDirectory, indexWriterConfig);

    File[] filesToIndex = getFilesToIndex();
    if (filesToIndex != null) {
      for (File file : filesToIndex) {
        indexDocument(indexWriter, file);
      }
      indexWriter.close();
      return filesToIndex.length + " files were indexed by the lucene indexer";
    } else {
      return "No files to index";
    }
  }

  @DeleteMapping("/delete")
  public ResponseEntity<String> deleteIndex() throws IOException {
    Directory indexDirectory = FSDirectory.open(Paths.get(INDEX_DIR));
    IndexWriterConfig writerConfig = new IndexWriterConfig(analyzer);

    try (IndexWriter indexWriter = new IndexWriter(indexDirectory, writerConfig)) {
      indexWriter.deleteAll();
      indexWriter.close();
      return ResponseEntity.ok("Index deleted successfully!");
    } catch (IOException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("Failed to delete index: " + e.getMessage());
    }
  }

  private File[] getFilesToIndex() {
    String resourcesFolder =
        Objects.requireNonNull(IndexController.class.getClassLoader().getResource("")).getPath();
    return new File(resourcesFolder)
        .listFiles((dir, name) -> name.endsWith(".txt")); // Index all .txt files
  }

  private void indexDocument(IndexWriter indexWriter, File file) throws IOException {
    Document doc = new Document();
    doc.add(new StoredField("fileName", file.getName()));
    doc.add(new TextField("fileContent", new FileReader(file)));
    indexWriter.addDocument(doc);
  }
}
