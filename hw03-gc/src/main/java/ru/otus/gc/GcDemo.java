package ru.otus.gc;

import com.sun.management.GarbageCollectionNotificationInfo;

import javax.management.MBeanServer;
import javax.management.NotificationEmitter;
import javax.management.NotificationListener;
import javax.management.ObjectName;
import javax.management.openmbean.CompositeData;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.List;

/**
 * Created by tully.
 * <p>
 * Updated by Sergey
 * <p>
 * Updated by alena
 */
/*
О формате логов
http://openjdk.java.net/jeps/158


-Xms512m
-Xmx512m
-Xlog:gc=debug:file=./hw03-gc/logs/gc-%p-%t.log:tags,uptime,time,level:filecount=5,filesize=10m
-XX:+HeapDumpOnOutOfMemoryError
-XX:HeapDumpPath=./hw03-gc/logs/dump
-XX:+UseG1GC
*/

/*
1)
    default, time: 83 sec (82 without Label_1)
2)
    -XX:MaxGCPauseMillis=100000, time: 82 sec //Sets a target for the maximum GC pause time.
3)
    -XX:MaxGCPauseMillis=10, time: 91 sec

4)
-Xms2048m
-Xmx2048m
    time: 81 sec

5)
-Xms5120m
-Xmx5120m
    time: 80 sec

5)
-Xms20480m
-Xmx20480m
    time: 81 sec (72 without Label_1)

*/

public class GcDemo {

    private static final String youngAction = "end of minor GC";
    private static final String oldAction = "end of major GC";

    private static long gcDuration = 0;
    private static long gcYoungCounter= 0;
    private static long gcOldCounter= 0;

    public static void main( String... args ) throws Exception {
        System.out.println( "Starting pid: " + ManagementFactory.getRuntimeMXBean().getName() );
        switchOnMonitoring();
        long beginTime = System.currentTimeMillis();

        int size = 1000;
        //int loopCounter = 1000;
        int loopCounter = 100000;
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName( "ru.otus:type=Benchmark" );

        Benchmark mbean = new Benchmark( loopCounter );
        mbs.registerMBean( mbean, name );
        mbean.setSize( size );
        try {
            mbean.run();

        } catch (OutOfMemoryError error) {

            float runDuration = ( System.currentTimeMillis() - beginTime );
            float gcTotalDuration = gcDuration; // remember totals immediately after OutOfMemoryError
            long gcYoungTotal = gcYoungCounter;
            long gcOldTotal = gcOldCounter;
            float efficiency = (gcTotalDuration / runDuration )* 60;

            System.out.println( error + "\n--------------------------------------------------\n");
            System.out.println( "--- run time: " + runDuration + " ms");
            System.out.println( "--- gc total duration: " + gcTotalDuration + " ms");
            System.out.format( "--- total GC duration (sec) in a minute: %5.2f%n" , efficiency );
            System.out.format( "--- percentage of GC duration: %5.2f%n" , efficiency / 60 * 100 );
            System.out.println( "--- young cycles counter: " + gcYoungTotal);
            System.out.println( "--- old cycles counter: " + gcOldTotal);
        }
    }

    private static void switchOnMonitoring() {
        List<GarbageCollectorMXBean> gcbeans = java.lang.management.ManagementFactory.getGarbageCollectorMXBeans();
        for ( GarbageCollectorMXBean gcbean : gcbeans ) {
            System.out.println( "GC name:" + gcbean.getName() );
            NotificationEmitter emitter = (NotificationEmitter) gcbean;
            NotificationListener listener = (notification, handback ) -> {
                if ( notification.getType().equals( GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION ) ) {
                    GarbageCollectionNotificationInfo info = GarbageCollectionNotificationInfo.from( (CompositeData) notification.getUserData() );
                    String gcName = info.getGcName();
                    String gcAction = info.getGcAction();
                    String gcCause = info.getGcCause();

                    long startTime = info.getGcInfo().getStartTime();
                    long duration = info.getGcInfo().getDuration();

                    gcDuration += duration;
                    float currentEfficiency = gcDuration;
                    currentEfficiency = currentEfficiency / (startTime + duration) * 60;

                    if (gcAction.equals(youngAction)){
                        gcYoungCounter++;
                    } else if (gcAction.equals(oldAction)) {
                        gcOldCounter++;
                    } else {
                        throw new IllegalArgumentException( "[ERROR] gcAction is not recognized: " + gcAction);
                    }

                    System.out.println( "start:" + startTime + " Name:" + gcName + ", action:" + gcAction + ", gcCause:" + gcCause + "(" + duration + " ms)" );
                    System.out.println( " counters. Young: " + gcYoungCounter + ", Old: " + gcOldCounter + ", Total duration: " + gcDuration + " ms" );
                    System.out.format( " current efficiency: %5.2f%n", currentEfficiency);
                }
            };
            emitter.addNotificationListener( listener, null, null );
        }
    }

}
