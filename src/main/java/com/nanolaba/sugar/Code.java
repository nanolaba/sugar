package com.nanolaba.sugar;

import java.util.Objects;
import java.util.function.Supplier;

public class Code {

    protected Code() {
    }

    public static void run(CodeAction runnable) {
        try {
            runnable.run();
        } catch (Exception t) {
            throw new RuntimeException(t);
        }
    }

    public static <T> T run(CodeSupplier<T> runnable) {
        try {
            return runnable.get();
        } catch (Exception t) {
            throw new RuntimeException(t);
        }
    }

    public static <T> T runQuietly(CodeSupplier<T> runnable, Supplier<T> defaultObject) {
        try {
            return runnable.get();
        } catch (Exception t) {
            return defaultObject.get();
        }
    }

    public static void runQuietly(CodeAction runnable) {
        try {
            runnable.run();
        } catch (Exception t) {/**/}
    }

    public static Boolean equalsAny(Object compared, Object... values) {
        if (values != null) {
            for (Object value : values) {
                if (Objects.equals(compared, value)) {
                    return true;
                }
            }
        }
        return compared == null && values == null;
    }

    public static <T> Supplier<T> memoize(Supplier<T> original) {

        if (original == null) {
            return null;
        }

        return new Supplier<T>() {
            private Supplier<T> delegate = this::firstTime;
            private boolean initialized;

            @Override
            public T get() {
                return delegate.get();
            }

            private synchronized T firstTime() {
                if (!initialized) {
                    T value = original.get();
                    delegate = () -> value;
                    initialized = true;
                }
                return delegate.get();
            }
        };
    }

    @FunctionalInterface
    public interface CodeAction {
        void run() throws Exception;
    }

    @FunctionalInterface
    public interface CodeSupplier<T> {
        T get() throws Exception;
    }
}
