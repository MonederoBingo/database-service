package com.monederobingo.database.database.common.db.util.concurrent;

public interface Computable<A, V> {
    V compute(A arg) throws InterruptedException;
}
