package com.uhd;

public final class Convert {
    private static final char[] hexArray = "0123456789ABCDEF".toCharArray();

    public static String toHexString(final byte[] bytes) {
        return toHexString(bytes, true);
    }

    public static String toHexString(final byte[] bytes, final boolean separate) {
        // Represents the number of characters that will be placed each iteration.
        int skip = 2;
        int length = bytes.length * 2;

        if (separate) {
            // Edit to comply with separation functionality
            // Add the spaces needed for each space
            length += bytes.length - 1;
            // When adding spaces, the loop will add the two hex characters and the space.
            // Therefore the loop will need to count by threes.
            skip = 3;
        }

        char[] hexCharacters = new char[length];
        for (int i = 0; i < bytes.length; i++) {
            int v = bytes[i] & 0xFF;
            hexCharacters[i * skip] = hexArray[v >>> 4];
            hexCharacters[i * skip + 1] = hexArray[v & 0x0F];

            // Adds a space between every hex character
            // Important to check if loop is currently iterating over last byte.
            if (separate && i * skip + 2 < length) {
                hexCharacters[i * skip + 2] = ' ';
            }
        }
        return new String(hexCharacters);
    }

    public static String[] toHexStrings(final byte[][] bytes) {
        return toHexStrings(bytes, true);
    }

    public static String[] toHexStrings(final byte[][] bytes, final boolean separate) {
        String[] hexArray = new String[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            hexArray[i] = toHexString(bytes[i], separate);
        }
        return hexArray;
    }
}
