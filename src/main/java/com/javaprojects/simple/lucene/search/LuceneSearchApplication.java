package com.javaprojects.simple.lucene.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/** A simple Lucene Search application that indexes and searches on data */
@SpringBootApplication
public class LuceneSearchApplication {
  public static void main(String[] args) {
    SpringApplication.run(LuceneSearchApplication.class, args);
  }
}
