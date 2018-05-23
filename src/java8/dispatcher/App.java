package java8.dispatcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class App
{

    public static void main( String[] args )
    {
        List<Long> numbers = new ArrayList<>();

        for (long l = 0; l <= 1000; l++)
        {
            numbers.add( l );
        }

        Worker w1 = new Worker( "/data/dispatcher/worker1.txt", 10 );
        Worker w2 = new Worker( "/data/dispatcher/worker2.txt", 20 );

        List<Worker> workers = new ArrayList<>();
        workers.add( w1 );
        workers.add( w2 );

        List<Thread> threads = new ArrayList<>();
        threads.add( new Thread( w1 ) );
        threads.add( new Thread( w2 ) );

        threads.forEach( Thread::start );

        for (Long l : numbers)
        {
            for (Worker w : workers)
            {
                w.accept( Arrays.asList( l ) );
            }
        }

        workers.forEach( w -> w.stop() );

    }

}
