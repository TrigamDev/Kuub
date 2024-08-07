package dev.trigam.kuub.client.input.key;

public enum Keys {

    // Don't ask me, I have to conform to jNativeHook's shitass keycodes
    UNDEFINED ( 0 ),
    ESCAPE ( 1 ),

    NUM_1 ( 2 ),
    NUM_2 ( 3 ),
    NUM_3 ( 4 ),
    NUM_4 ( 5 ),
    NUM_5 ( 6 ),
    NUM_6 ( 7 ),
    NUM_7 ( 8 ),
    NUM_8 ( 9 ),
    NUM_9 ( 10 ),
    NUM_0 ( 11 ),

    MINUS ( 12 ),
    BACKSPACE ( 13 ),
    EQUALS ( 14 ),
    TAB ( 15 ),

    Q ( 16 ),
    W ( 17 ),
    E ( 18 ),
    R ( 19 ),
    T ( 20 ),
    Y ( 21 ),
    U ( 22 ),
    I ( 23 ),
    O ( 24 ),
    P ( 25 ),

    OPEN_BRACKET ( 26 ),
    CLOSE_BRACKET ( 27 ),
    ENTER ( 28 ),
    CONTROL ( 29 ),

    A ( 30 ),
    S ( 31 ),
    D ( 32 ),
    F ( 33 ),
    G ( 34 ),
    H ( 35 ),
    J ( 36 ),
    K ( 37 ),
    L ( 38 ),

    SEMICOLON ( 39 );

    public final int keyCode;
    private Keys ( int keyCode ) { this.keyCode = keyCode; }

}
