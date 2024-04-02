package com.nanolaba.sugar;

import org.junit.jupiter.api.Test;

import static com.nanolaba.sugar.Code.run;
import static com.nanolaba.sugar.Code.runQuietly;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SuppressWarnings({"DataFlowIssue", "unused"})
class CodeTest {

    @Test
    public void testCodeCanBeInherited() {

        class ExtendedCode extends Code {
        }

        Code myCode = new ExtendedCode();
    }

    @Test
    public void testRunWithThrowingAnNPE() {
        assertThrows(RuntimeException.class, () ->
                run(() -> {
                    Long t = null;
                    long l = t / 10L;
                })
        );
    }

    @Test
    public void testRunWithThrowingAnError() {
        assertThrows(InternalError.class, () ->
                run(() -> {
                    throw new InternalError();
                })
        );
    }

    @Test
    public void testRunWithThrowingAnException() {
        assertThrows(RuntimeException.class, () ->
                run(() -> {
                    throw new Exception();
                })
        );
    }

    @Test
    public void testRunSupplierWithThrowingAnException() {
        assertThrows(RuntimeException.class, () -> {
            class foo {
                public long bar() {
                    return run(() -> {
                        Long t = null;
                        return t / 10L;
                    });
                }
            }

            long test = new foo().bar();
        });
    }

    @Test
    public void testRunQuietlyAndCatchNPE() {
        runQuietly(() -> {
            Long t = null;
            long l = t / 10L;
        });
    }

    @Test
    public void testRunQuietlyWithSupplierAndCatchNPE() {
        long defaultValue = 100L;

        Long v = runQuietly(() -> {
            Long t = null;
            return t / 10L;
        }, () -> defaultValue);

        assertEquals(defaultValue, (long) v);
    }
}