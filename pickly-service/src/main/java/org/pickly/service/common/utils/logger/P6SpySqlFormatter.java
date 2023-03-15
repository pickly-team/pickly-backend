package org.pickly.service.common.utils.logger;

import com.p6spy.engine.logging.Category;
import com.p6spy.engine.spy.P6SpyOptions;
import com.p6spy.engine.spy.appender.MessageFormattingStrategy;
import jakarta.annotation.PostConstruct;
import java.util.ArrayDeque;
import org.hibernate.engine.jdbc.internal.FormatStyle;
import org.springframework.context.annotation.Configuration;

@Configuration
public class P6SpySqlFormatter implements MessageFormattingStrategy {

  private static String PACKAGE = "org.pickly.service";
  private static String P6SPY_PACKAGE = "P6SpySqlFormatter";

  @PostConstruct
  public void setLogMessageFormat() {
    P6SpyOptions.getActiveInstance().setLogMessageFormat(this.getClass().getName());
  }

  @Override
  public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared,
      String sql, String url) {
    sql = formatSql(category, sql);
    return String.format("[%s] | %d ms | %s | %s", category, elapsed, createStack(), formatSql(category, sql));
  }

  private String formatSql(String category, String sql) {
    if (sql != null && !sql.trim().isEmpty() && Category.STATEMENT.getName().equals(category)) {
      return FormatStyle.BASIC.getFormatter().format(sql);
    }
    return sql;
  }

  private String createStack() {
    ArrayDeque<String> callStack = new ArrayDeque<>();
    StackTraceElement[] stackTrace = new Throwable().getStackTrace();
    for (StackTraceElement stackTraceElement : stackTrace) {
      String trace = stackTraceElement.toString();
      if (trace.startsWith(PACKAGE) && !(trace.contains(P6SPY_PACKAGE))) {
        callStack.push(trace);
      }
    }

    StringBuilder sb = new StringBuilder();
    int order = 1;
    while (!callStack.isEmpty()) {
      sb.append("\n\t\t").append(order++).append(".").append(callStack.pop());
    }

    return sb.toString();
  }

}
