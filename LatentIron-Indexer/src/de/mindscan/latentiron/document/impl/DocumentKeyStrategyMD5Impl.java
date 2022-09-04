/**
 * 
 * MIT License
 *
 * Copyright (c) 2021 Maxim Gansert, Mindscan
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
package de.mindscan.latentiron.document.impl;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import de.mindscan.latentiron.document.DocumentKeyStrategy;

/**
 * This DocumentKey Strategy converts the given Path to a document key using the MD5 hash algorithm.
 * 
 * The hash algorithm produces a hash of length 128 bits. 
 * 
 * - In Base16 encoding the document keys are of length 32.
 * - In Base36 encoding the document keys are of length 25. 
 *   - Base36 is reasonable for filenames under windows and
 *   - Base36 is the highest radix supported for BigIntegers
 * 
 */
public class DocumentKeyStrategyMD5Impl implements DocumentKeyStrategy {

    private int base;

    public DocumentKeyStrategyMD5Impl() {
        this( 16 );
    }

    public DocumentKeyStrategyMD5Impl( int base ) {
        this.base = base;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String generateDocumentKey( Path relativePath ) {
        try {
            byte[] relativePathAsBytes = relativePath.toString().getBytes( StandardCharsets.UTF_8 );

            MessageDigest md5sum = MessageDigest.getInstance( "MD5" );
            byte[] md5 = md5sum.digest( relativePathAsBytes );
            return convertBase( md5 );
        }
        catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException( e );
        }
    }

    private String convertBase( byte[] md5 ) {
        BigInteger md5bi = new BigInteger( 1, md5 );

        switch (base) {
            case 36: {
                String alphaNum = md5bi.toString( 36 );

                // ATTN: This happens 1/14 times (for once), 1/14*36 (for two rounds), 1/14*36*36 (for three times)
                // no need to optimize on this level
                while (alphaNum.length() < 25) {
                    alphaNum = "0" + alphaNum;
                }

                return alphaNum;
            }
            case 16:
            default: {
                String md5hex = md5bi.toString( 16 );

                // ATTN: This happens 1/16 times (for once), 1/256 (for two rounds), 1/4096(for three times)
                // no need to optimize on this level                
                while (md5hex.length() < 32) {
                    md5hex = "0" + md5hex;
                }

                return md5hex;
            }
        }
    }

    // TODO: this was an idea, when 
//    @SuppressWarnings( "unused" )
//    private String convertToHex2( byte[] md5 ) {
//        BigInteger md5bi = new BigInteger( 1, md5 );
//        return String.format( "%032x", md5bi );
//    }

}
