package com.nanolaba.sugar;

import org.junit.jupiter.api.Test;

class CodeTest {

    @Test
    public void testCodeClassCanBeInherited() {

        class ExtendedCode extends Code {
        }

        Code myCode = new ExtendedCode();
    }

}