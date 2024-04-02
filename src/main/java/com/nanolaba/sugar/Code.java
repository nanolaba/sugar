package com.nanolaba.sugar;

import java.util.function.Supplier;

public interface Code {

    static void run(CodeAction runnable) {
        try {
            runnable.run();
        } catch (Exception t) {
            throw new RuntimeException(t);
        }
    }

    static <T> T run(CodeSupplier<T> runnable) {
        try {
            return runnable.get();
        } catch (Exception t) {
            throw new RuntimeException(t);
        }
    }

    static <T> T runQuietly(CodeSupplier<T> runnable, Supplier<T> defaultObject) {
        try {
            return runnable.get();
        } catch (Exception t) {
            return defaultObject.get();
        }
    }

    static void runQuietly(CodeAction runnable) {
        try {
            runnable.run();
        } catch (Exception t) {/**/}
    }

    @FunctionalInterface
    interface CodeAction {
        void run() throws Exception;
    }

    @FunctionalInterface
    interface CodeSupplier<T> {
        T get() throws Exception;
    }
}
