package io.ylab.intensive.lesson05.sqlquerybuilder;

import java.util.List;

public interface SQLQueryBuilder {
  String queryForTable(String tableName);
  
  List<String> getTables();
}
