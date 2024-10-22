package de.avpod.kafka.producerjava;

public class PublishControl {
    private volatile boolean terminated = false;
    private volatile long pauseMs = 2000;
    private volatile Long oneTimePause = null;
    public void terminate() {
        terminated = true;
    }

    public void setPeriodPause(long millis) {
        pauseMs = millis;
    }

    public void setOneTimePause(long millis) {
        oneTimePause = millis;
    }

    public boolean terminated() {
        return terminated;
    }

    public void waitBeforePublishIfNeeded() {
        try {
            if(oneTimePause != null) {
                Thread.sleep(oneTimePause);
                oneTimePause = null;
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void waitAfterPublishIfNeeded() {
        try {
            if(oneTimePause != null) {
                Thread.sleep(oneTimePause);
                oneTimePause = null;
            } else {
                Thread.sleep(pauseMs);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
