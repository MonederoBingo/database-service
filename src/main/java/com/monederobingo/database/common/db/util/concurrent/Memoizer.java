package com.monederobingo.database.common.db.util.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class Memoizer<A, V> implements Computable<A, V> {

    private final ConcurrentMap<A, Future<V>> cache = new ConcurrentHashMap<>();
    private final Computable<A, V> computable;

    public Memoizer(Computable<A, V> computable) {
        this.computable = computable;
    }

    @Override
    public V compute(final A arg) throws InterruptedException {
        while (true) {
            Future<V> value = cache.get(arg);
            if (value == null) {
                FutureTask<V> futureTask = createFutureTask(arg);
                value = cache.putIfAbsent(arg, futureTask);
                if (value == null) {
                    value = futureTask;
                    futureTask.run();
                }
            }
            try {
                return value.get();
            } catch (CancellationException e) {
                cache.remove(arg, value);
            } catch (ExecutionException e) {
                throw launderThrowable(e);
            }
        }
    }

    private static RuntimeException launderThrowable(Throwable throwable) {
        if (throwable instanceof RuntimeException) {
            return (RuntimeException) throwable;
        } else if (throwable instanceof Error) {
            throw (Error) throwable;
        } else {
            throw new IllegalArgumentException("Error when getting object from memoizer", throwable);
        }
    }

    FutureTask<V> createFutureTask(final A arg) {
        Callable<V> eval = () -> computable.compute(arg);
        return new FutureTask<>(eval);
    }
}
