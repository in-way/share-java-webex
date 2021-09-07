package com.gbps.webex.core;

@FunctionalInterface
public interface ExceptionUnPacker<T extends Throwable, R extends Throwable> {

    R unPack(T t);

}
