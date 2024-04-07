package com.nanolaba.sugar;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static com.nanolaba.sugar.Code.run;
import static com.nanolaba.sugar.Code.runQuietly;
import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings({"DataFlowIssue", "unused"})
class CodeTest {

    private static final Long NULL = null;

    @Test
    public void testCodeCanBeInherited() {

        class ExtendedCode extends Code {
        }

        Code myCode = new ExtendedCode();
    }

    @Test
    public void testRunWithReturnValue() {
        assertEquals(4, run(() -> 4));
        assertEquals(4, run(() -> 2 + 2));
        assertEquals("4", run(() -> "4"));
        assertEquals(NULL, run(() -> NULL));
    }

    @Test
    public void testRunWithDefaultReturnValue() {
        assertEquals(4, runQuietly(() -> 4, () -> 0));
        assertEquals(4, runQuietly(() -> 2 + 2, () -> 0));
        assertEquals("4", runQuietly(() -> "4", () -> 0));
        assertEquals(NULL, runQuietly(() -> NULL, () -> 0));
    }

    @Test
    public void testRunWithThrowingAnNPE() {
        AtomicBoolean started = new AtomicBoolean(false);
        assertThrows(RuntimeException.class, () ->
                run(() -> {
                    started.set(true);
                    long l = NULL / 10L;
                })
        );
        assertTrue(started.get());
    }

    @Test
    public void testRunWithThrowingAnError() {
        AtomicBoolean started = new AtomicBoolean(false);
        assertThrows(InternalError.class, () ->
                run(() -> {
                    started.set(true);
                    throw new InternalError();
                })
        );
        assertTrue(started.get());
    }

    @Test
    public void testRunWithThrowingAnException() {
        AtomicBoolean started = new AtomicBoolean(false);

        assertThrows(RuntimeException.class, () ->
                run(() -> {
                    started.set(true);
                    throw new Exception();
                })
        );

        assertTrue(started.get());
    }

    @Test
    public void testRunSupplierWithThrowingAnException() {
        AtomicBoolean started = new AtomicBoolean(false);

        assertThrows(RuntimeException.class, () -> {
            class foo {
                public long bar() {
                    return run(() -> {
                        started.set(true);
                        return NULL / 10L;
                    });
                }
            }

            long test = new foo().bar();

            fail("The test should throw an exception before this line");
        });

        assertTrue(started.get());
    }

    @Test
    public void testRunQuietlyAndCatchNPE() {
        AtomicBoolean started = new AtomicBoolean(false);

        runQuietly(() -> {
            started.set(true);
            long l = NULL / 10L;
        });

        assertTrue(started.get());
    }

    @Test
    public void testRunQuietlyWithSupplierAndCatchNPE() {
        AtomicBoolean started = new AtomicBoolean(false);
        long defaultValue = 100L;

        Long v = runQuietly(() -> {
            started.set(true);
            return NULL / 10L;
        }, () -> defaultValue);

        assertEquals(defaultValue, (long) v);
        assertTrue(started.get());
    }
}