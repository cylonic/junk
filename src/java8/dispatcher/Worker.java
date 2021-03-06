package java8.dispatcher;

import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Worker implements Dispatch, Runnable
{

    private Queue<Long> queue = new ArrayDeque<>();
    private boolean done = false;

    private final PrintWriter writer;
    private final long offset;

    public Worker( String file, long offset )
    {
        try
        {
            this.writer = new PrintWriter( new BufferedWriter( new FileWriter( new File( file ) ) ) );
        } catch ( IOException e )
        {
            throw new RuntimeException( e );
        }
        this.offset = offset;
    }

    @Override public void accept( List<Long> batch )
    {
        queue.addAll( batch );
    }

    @Override public void stop()
    {
        this.done = true;

    }

    @Override public void run()
    {
        while ( !( done && queue.isEmpty() ) )
        {
            if ( queue.size() == 0 )
            {
                sleep();
                continue;
            }

            long item = queue.poll() + offset;

            writer.println( item );

        }

        writer.close();

    }

    private void sleep()
    {
        try
        {
            TimeUnit.SECONDS.sleep( 1 );
        } catch ( InterruptedException e )
        {
            e.printStackTrace();
        }
    }
}