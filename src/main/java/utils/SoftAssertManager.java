package utils;


import org.testng.asserts.SoftAssert;

public class SoftAssertManager {

    //NOte for me: this creates a Thread-Local SoftAssert that is shared across the class (static) hence across all thread
    private static final ThreadLocal<SoftAssert> softly =
            ThreadLocal.withInitial(SoftAssert::new);

    public static SoftAssert softly() {
        return softly.get();
    }

    public static void reset() {
        softly.set(new SoftAssert());
    }

    public static void unload() {
        softly.remove();
    }
}
