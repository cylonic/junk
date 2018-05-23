package java8.dispatcher;

import java.util.List;

public interface Dispatch
{
    void accept( List<Long> batch );

    void stop();
}
