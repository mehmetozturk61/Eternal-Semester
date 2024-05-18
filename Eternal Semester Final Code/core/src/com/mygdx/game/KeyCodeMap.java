package com.mygdx.game;

import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;

/**
 * Key map from com.badlogic.gdx.Input.Kyes to custom enumeration;
 * @author Marek Halmo (c) Maniacs Software
 * Got the code from gdx file and used replace
 * (.*)(Keys.(.*), "(.*)"),
 * $1(Keys.$1, "$1"),
 */
public enum KeyCodeMap {
    ANY_KEY(Keys.ANY_KEY, "N/A"),
    NUM_0(Keys.NUM_0, "NUM_0"),
    NUM_1(Keys.NUM_1, "NUM_1"),
    NUM_2(Keys.NUM_2, "NUM_2"),
    NUM_3(Keys.NUM_3, "NUM_3"),
    NUM_4(Keys.NUM_4, "NUM_4"),
    NUM_5(Keys.NUM_5, "NUM_5"),
    NUM_6(Keys.NUM_6, "NUM_6"),
    NUM_7(Keys.NUM_7, "NUM_7"),
    NUM_8(Keys.NUM_8, "NUM_8"),
    NUM_9(Keys.NUM_9, "NUM_9"),
    A(Keys.A, "A"),
    ALT_LEFT(Keys.ALT_LEFT, "ALT_LEFT"),
    ALT_RIGHT(Keys.ALT_RIGHT, "ALT_RIGHT"),
    APOSTROPHE(Keys.APOSTROPHE, "APOSTROPHE"),
    AT(Keys.AT, "AT"),
    B(Keys.B, "B"),
    BACK(Keys.BACK, "BACK"),
    BACKSLASH(Keys.BACKSLASH, "BACKSLASH"),
    C(Keys.C, "C"),
    CALL(Keys.CALL, "CALL"),
    CAMERA(Keys.CAMERA, "CAMERA"),
    CLEAR(Keys.CLEAR, "CLEAR"),
    COMMA(Keys.COMMA, "COMMA"),
    D(Keys.D, "D"),
    DEL(Keys.DEL, "DEL"),
    BACKSPACE(Keys.BACKSPACE, "BACKSPACE"),
    FORWARD_DEL(Keys.FORWARD_DEL, "FORWARD_DEL"),
    DPAD_CENTER(Keys.DPAD_CENTER, "DPAD_CENTER"),
    DPAD_DOWN(Keys.DPAD_DOWN, "DPAD_DOWN"),
    DPAD_LEFT(Keys.DPAD_LEFT, "DPAD_LEFT"),
    DPAD_RIGHT(Keys.DPAD_RIGHT, "DPAD_RIGHT"),
    DPAD_UP(Keys.DPAD_UP, "DPAD_UP"),
    CENTER(Keys.CENTER, "CENTER"),
    DOWN(Keys.DOWN, "DOWN"),
    LEFT(Keys.LEFT, "LEFT"),
    RIGHT(Keys.RIGHT, "RIGHT"),
    UP(Keys.UP, "UP"),
    E(Keys.E, "E"),
    ENDCALL(Keys.ENDCALL, "ENDCALL"),
    ENTER(Keys.ENTER, "ENTER"),
    ENVELOPE(Keys.ENVELOPE, "ENVELOPE"),
    EQUALS(Keys.EQUALS, "EQUALS"),
    EXPLORER(Keys.EXPLORER, "EXPLORER"),
    F(Keys.F, "F"),
    FOCUS(Keys.FOCUS, "FOCUS"),
    G(Keys.G, "G"),
    GRAVE(Keys.GRAVE, "GRAVE"),
    H(Keys.H, "H"),
    HEADSETHOOK(Keys.HEADSETHOOK, "HEADSETHOOK"),
    HOME(Keys.HOME, "HOME"),
    I(Keys.I, "I"),
    J(Keys.J, "J"),
    K(Keys.K, "K"),
    L(Keys.L, "L"),
    LEFT_BRACKET(Keys.LEFT_BRACKET, "LEFT_BRACKET"),
    M(Keys.M, "M"),
    MEDIA_FAST_FORWARD(Keys.MEDIA_FAST_FORWARD, "MEDIA_FAST_FORWARD"),
    MEDIA_NEXT(Keys.MEDIA_NEXT, "MEDIA_NEXT"),
    MEDIA_PLAY_PAUSE(Keys.MEDIA_PLAY_PAUSE, "MEDIA_PLAY_PAUSE"),
    MEDIA_PREVIOUS(Keys.MEDIA_PREVIOUS, "MEDIA_PREVIOUS"),
    MEDIA_REWIND(Keys.MEDIA_REWIND, "MEDIA_REWIND"),
    MEDIA_STOP(Keys.MEDIA_STOP, "MEDIA_STOP"),
    MENU(Keys.MENU, "MENU"),
    MINUS(Keys.MINUS, "MINUS"),
    MUTE(Keys.MUTE, "MUTE"),
    N(Keys.N, "N"),
    NOTIFICATION(Keys.NOTIFICATION, "NOTIFICATION"),
    NUM(Keys.NUM, "NUM"),
    O(Keys.O, "O"),
    P(Keys.P, "P"),
    PERIOD(Keys.PERIOD, "PERIOD"),
    PLUS(Keys.PLUS, "PLUS"),
    POUND(Keys.POUND, "POUND"),
    POWER(Keys.POWER, "POWER"),
    Q(Keys.Q, "Q"),
    R(Keys.R, "R"),
    RIGHT_BRACKET(Keys.RIGHT_BRACKET, "RIGHT_BRACKET"),
    S(Keys.S, "S"),
    SEARCH(Keys.SEARCH, "SEARCH"),
    SEMICOLON(Keys.SEMICOLON, "SEMICOLON"),
    SHIFT_LEFT(Keys.SHIFT_LEFT, "SHIFT_LEFT"),
    SHIFT_RIGHT(Keys.SHIFT_RIGHT, "SHIFT_RIGHT"),
    SLASH(Keys.SLASH, "SLASH"),
    SOFT_LEFT(Keys.SOFT_LEFT, "SOFT_LEFT"),
    SOFT_RIGHT(Keys.SOFT_RIGHT, "SOFT_RIGHT"),
    SPACE(Keys.SPACE, "SPACE"),
    STAR(Keys.STAR, "STAR"),
    SYM(Keys.SYM, "SYM"),
    T(Keys.T, "T"),
    TAB(Keys.TAB, "TAB"),
    U(Keys.U, "U"),
    UNKNOWN(Keys.UNKNOWN, "UNKNOWN"),
    V(Keys.V, "V"),
    VOLUME_DOWN(Keys.VOLUME_DOWN, "VOLUME_DOWN"),
    VOLUME_UP(Keys.VOLUME_UP, "VOLUME_UP"),
    W(Keys.W, "W"),
    X(Keys.X, "X"),
    Y(Keys.Y, "Y"),
    Z(Keys.Z, "Z"),
    META_ALT_LEFT_ON(Keys.META_ALT_LEFT_ON, "META_ALT_LEFT_ON"),
    META_ALT_ON(Keys.META_ALT_ON, "META_ALT_ON"),
    // META_ALT_RIGHT_ON(Keys.META_ALT_RIGHT_ON, "META_ALT_RIGHT_ON"),
    META_SHIFT_LEFT_ON(Keys.META_SHIFT_LEFT_ON, "META_SHIFT_LEFT_ON"),
    META_SHIFT_ON(Keys.META_SHIFT_ON, "META_SHIFT_ON"),
    META_SHIFT_RIGHT_ON(Keys.META_SHIFT_RIGHT_ON, "META_SHIFT_RIGHT_ON"),
    META_SYM_ON(Keys.META_SYM_ON, "META_SYM_ON"),
    CONTROL_LEFT(Keys.CONTROL_LEFT, "CONTROL_LEFT"),
    CONTROL_RIGHT(Keys.CONTROL_RIGHT, "CONTROL_RIGHT"),
    ESCAPE(Keys.ESCAPE, "ESCAPE"),
    END(Keys.END, "END"),
    INSERT(Keys.INSERT, "INSERT"),
    PAGE_UP(Keys.PAGE_UP, "PAGE_UP"),
    PAGE_DOWN(Keys.PAGE_DOWN, "PAGE_DOWN"),
    PICTSYMBOLS(Keys.PICTSYMBOLS, "PICTSYMBOLS"),
    SWITCH_CHARSET(Keys.SWITCH_CHARSET, "SWITCH_CHARSET"),
    BUTTON_CIRCLE(Keys.BUTTON_CIRCLE, "BUTTON_CIRCLE"),
    BUTTON_A(Keys.BUTTON_A, "BUTTON_A"),
    BUTTON_B(Keys.BUTTON_B, "BUTTON_B"),
    BUTTON_C(Keys.BUTTON_C, "BUTTON_C"),
    BUTTON_X(Keys.BUTTON_X, "BUTTON_X"),
    BUTTON_Y(Keys.BUTTON_Y, "BUTTON_Y"),
    BUTTON_Z(Keys.BUTTON_Z, "BUTTON_Z"),
    BUTTON_L1(Keys.BUTTON_L1, "BUTTON_L1"),
    BUTTON_R1(Keys.BUTTON_R1, "BUTTON_R1"),
    BUTTON_L2(Keys.BUTTON_L2, "BUTTON_L2"),
    BUTTON_R2(Keys.BUTTON_R2, "BUTTON_R2"),
    BUTTON_THUMBL(Keys.BUTTON_THUMBL, "BUTTON_THUMBL"),
    BUTTON_THUMBR(Keys.BUTTON_THUMBR, "BUTTON_THUMBR"),
    BUTTON_START(Keys.BUTTON_START, "BUTTON_START"),
    BUTTON_SELECT(Keys.BUTTON_SELECT, "BUTTON_SELECT"),
    BUTTON_MODE(Keys.BUTTON_MODE, "BUTTON_MODE"),

    //BACKTICK(Keys.BACKTICK, "BACKTICK"),
    //TILDE(Keys.TILDE, "TILDE"),
    //UNDERSCORE(Keys.UNDERSCORE, "UNDERSCORE"),
    //DOT(Keys.DOT, "DOT"),
    //BREAK(Keys.BREAK, "BREAK"),
    //PIPE(Keys.PIPE, "PIPE"),
    //EXCLAMATION(Keys.EXCLAMATION, "EXCLAMATION"),
    //QUESTIONMARK(Keys.QUESTIONMARK, "QUESTIONMARK"),

    //` | VK_BACKTICK
    //~ | VK_TILDE
    //: | VK_COLON
    //_ | VK_UNDERSCORE
    //. | VK_DOT
    //(break) | VK_BREAK
    //| | VK_PIPE
    //! | VK_EXCLAMATION
    //? | VK_QUESTION

    COLON(Keys.COLON, "COLON"),
    F1(Keys.F1, "F1"),
    F2(Keys.F2, "F2"),
    F3(Keys.F3, "F3"),
    F4(Keys.F4, "F4"),
    F5(Keys.F5, "F5"),
    F6(Keys.F6, "F6"),
    F7(Keys.F7, "F7"),
    F8(Keys.F8, "F8"),
    F9(Keys.F9, "F9"),
    F10(Keys.F10, "F10"),
    F11(Keys.F11, "F11"),
    F12(Keys.F12, "F12"),

    MB_LEFT(Buttons.LEFT, "MB_LEFT"),
    MB_RIGHT(Buttons.RIGHT, "MB_RIGHT"),
    MB_MIDDLE(Buttons.MIDDLE, "MB_MIDDLE");

    // On first run this cache will be populated with all keycodes >= 0
    private static KeyCodeMap[] kyecodeCache;

    static {
        // first we determine whats the highest keycode, in current build it's 256
        int highestInt = 0;
        for(KeyCodeMap code: KeyCodeMap.values()) {
            if (code.getKeyCode() > highestInt) {
                highestInt = code.keyCode;
            }
        }

        // we allocate keycodeCache which is direct link - keyCode as index gives value
        kyecodeCache = new KeyCodeMap[highestInt + 1];

        // populate keycode cache with all the codes
        for(KeyCodeMap code: KeyCodeMap.values()) {
            if(code.getKeyCode() >= 0)
                kyecodeCache[code.getKeyCode()] = code;
        }
    }


    private int keyCode;
    private String humanName;

    private KeyCodeMap(int keyCode, String humanName) {
        this.keyCode = keyCode;
        this.humanName = humanName;
    }

    /**
     * Returns KeyCodeMap enum based on the code from input.Keys
     * @param intCode
     * @return
     */
    public static KeyCodeMap valueOf(int intCode)
    {
        if(intCode == -1)
            return ANY_KEY;

        if(intCode < 0 || intCode > values().length 
                || kyecodeCache[intCode] == null)
            return UNKNOWN;

        return kyecodeCache[intCode]; 
    }

    /**
     * Returns the original keycode from input.Keys
     * @return
     */
    public int getKeyCode() {
        return keyCode;
    }

    /**
     * Returns the human name of this key code
     * you can also use .toString() but this value is fully customizable!
     * @return
     */
    public String getHumanName() {
        return humanName;
    }

    /**
     * Allows you to change the human name/text returned by getHumanName()
     * @param newHumanName
     */
    public void setHumanName(String newHumanName) {
        this.humanName = newHumanName;
    }
}