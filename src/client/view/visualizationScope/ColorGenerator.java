package client.view.visualizationScope;

import java.util.zip.CRC32;

public final class ColorGenerator {
    private static final CRC32 crcGen = new CRC32();

    private ColorGenerator() {}

    public static Integer generateColorShift(Integer a) {
        crcGen.reset();
        crcGen.update(a);
        return (int)(crcGen.getValue() % 37) * 10;
    }
}
