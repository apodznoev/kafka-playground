package de.avpod.kafka.producerjava;

public interface CallbackListener {

    void onSuccess(long offset, long timestamp);

    void onError(Exception maybeException);
}
