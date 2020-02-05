package ru.otus.gc;


import java.util.ArrayList;
import java.util.Random;

/**
 * Created by tully.
 * <p>
 * Updated by alena
 */
class Benchmark implements BenchmarkMBean {
    private final int loopCounter;
    private volatile int size = 0;

    public Benchmark( int loopCounter ) {
        this.loopCounter = loopCounter;
    }

    void run() throws InterruptedException {
        ArrayList<String[]> list = new ArrayList<>(size);
        Random random = new Random();
        for ( int idx = 0; idx < loopCounter; idx++ ) {
            int local = size;
            String[] array = new String[size];
            for (int i = 0; i < local; i++) {
                array[i] = new String("some text" + random.nextFloat());
            }
            list.add(array);
            long sum = 0;
            for ( int i = 0; i < local; i++ ) {
                sum += list.get(idx)[i].hashCode();
            }
            Thread.sleep( 10 ); //Label_1
            local = size / 2;
            for ( int i = 0; i < local; i++ ) {
                list.get(idx)[i] = null;
            }
            Thread.sleep( 10 ); //Label_2
        }
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public void setSize( int size ) {
        System.out.println( "new size:" + size );
        this.size = size;
    }

}
