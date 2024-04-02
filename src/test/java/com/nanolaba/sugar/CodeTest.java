package com.nanolaba.sugar;

import org.junit.jupiter.api.Test;

class CodeTest {

    @Test
    public void testCodeClassCanBeImplemented() {

        class ExtendedCode implements Code {
        }

        Code myCode = new ExtendedCode();
    }

}