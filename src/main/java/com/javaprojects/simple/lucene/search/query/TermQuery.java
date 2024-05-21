package com.javaprojects.simple.lucene.search.query;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.Query;

public class TermQuery extends SearchRequest {
  private String fieldName;
  private String fieldValue;

  public String getFieldName() {
    return fieldName;
  }

  public void setFieldName(String fieldName) {
    this.fieldName = fieldName;
  }

  public String getFieldValue() {
    return fieldValue;
  }

  public void setFieldValue(String fieldValue) {
    this.fieldValue = fieldValue;
  }

  @Override
  public Query parseJsonToQuery() {
    return new org.apache.lucene.search.TermQuery(new Term(fieldName, fieldValue));
  }
}
