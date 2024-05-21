package com.javaprojects.simple.lucene.search;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LuceneController {
  @GetMapping("/")
  public String hello() {
    return "Hello! Welcome to a Simple Lucene Search Application";
  }
}
