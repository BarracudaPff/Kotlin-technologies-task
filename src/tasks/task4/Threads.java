package tasks.task4;

public class Threads {
    private final String myLock = "lock";
    private boolean shouldWait = true;

    public void foo() {
        synchronized (myLock) {
            while (shouldWait) {
                try {
                    myLock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // do some stuff
        }
    }

    public void continueWork() {
        synchronized (myLock) {
            shouldWait = false;
            myLock.notifyAll();
        }

    }
}