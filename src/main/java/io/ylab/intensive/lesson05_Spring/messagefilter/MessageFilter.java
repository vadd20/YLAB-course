package io.ylab.intensive.lesson05_Spring.messagefilter;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public interface MessageFilter {
    public void doFilter() throws IOException, TimeoutException;
}
