/**
 * 
 * MIT License
 *
 * Copyright (c) 2019-2022 Maxim Gansert, Mindscan
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 * 
 */
package de.mindscan.latentiron.index;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * TODO: for latent iron we want to redo this and not use the trigrams and calculate an md5/sha1/hmac of the trigram
 * 
 * some utf8 stuff is still just a hassle/pain 
 */
public class TrigramSubPathCalculator {

    public static Path getPathForTrigram( Path basePath, String trigram, String suffix ) {
        String[] convertCharsToUHex = convertCharsToUHex( trigram );

        return basePath.resolve( buildPathAndName( convertCharsToUHex, suffix ) );
    }

    public static String[] convertCharsToUHex( String trigram ) {
        int[] chars = trigram.chars().toArray();
        String[] result = new String[chars.length];

        for (int i = 0; i < chars.length; i++) {
            int currentChar = chars[i];

            if (currentChar <= 0x7f) {
                result[i] = String.format( "%02x", currentChar );
            }
            else if (currentChar < 0x10000) {
                result[i] = String.format( "u%04x", currentChar );
            }
            else {
                // actually never used, but encoded as series of characters <= 0xffff
                result[i] = String.format( "u%08x", currentChar );
            }
        }

        return result;
    }

    private static Path buildPathAndName( String[] convertCharsToUHex, String suffix ) {
        // Maybe later should be removing the first few elements and starting from level, where directory ends
        String filename = String.join( "_", Arrays.copyOfRange( convertCharsToUHex, 0, convertCharsToUHex.length ) ) + suffix;
        String pathName = String.join( "/", Arrays.copyOfRange( convertCharsToUHex, 0, 2 ) );

        return Paths.get( pathName, filename );
    }
}
