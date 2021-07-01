package ru.eforward.express_testing.testingProcess;

/**
* This class is used within testing.jsp to stop the test
* and to give js time-counter correct info to render it to view.
*/
public class Stopper {
    long startTime;
    long duration;

    public Stopper(double durationInMinutes){
        startTime = System.currentTimeMillis();
        this.duration = getMillisFromMinutes(durationInMinutes);
    }

    /**
     * Counts amount of milliseconds from start of the test by current student.
     * Compares it with the duration given for this testing.
     * @return true if time of the test is over. Otherwise returns false.
     */
    public boolean shouldBeStopped(){
        long currentTime = System.currentTimeMillis();
        long currentDuration = currentTime - startTime;

        if(currentDuration >= duration){
            return true;
        }
        return false;
    }

    /**
     * Returns amount of milliseconds left to the end of test;
     * @return long amount of milliseconds left to the end of test.
     */
    public long getMillisLeft(){
        long currentTime = System.currentTimeMillis();
        long endTime = startTime + duration;
        return endTime - currentTime;
    }

    /**
     * Returns amount of milliseconds passed from the beginning of test;
     * @return long amount of milliseconds passed from the beginning of test;
     */
    public long getMillisPassed(){
        long current = System.currentTimeMillis();
        return current - startTime;
    }

    /**
     * Takes minutes and turns it to milliseconds
     * @param minutes - the given amount of minutes of duration of current testing;
     * @return amount of milliseconds for this given amount of minutes
     */
    private long getMillisFromMinutes(double minutes){
        return (long) minutes * 60 * 1000;
    }
}
