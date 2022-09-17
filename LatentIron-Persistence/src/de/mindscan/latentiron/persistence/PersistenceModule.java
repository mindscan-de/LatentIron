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
package de.mindscan.latentiron.persistence;

import java.util.Collection;
import java.util.function.Predicate;

/**
 * 
 */
public interface PersistenceModule {

    int getNamespaceId();

    String getNamespaceName();

    void resetToDefault( String key );

    void resetAllToDefault();

    // ---
    // int
    // ---    

    void setDefaultIntValue( String key, int defaultValue );

    void setIntValue( String key, int newValue );

    int getIntValue( String key );

    // ------
    // String
    // ------

    void setDefaultStringValue( String key, String defaultValue );

    void setStringValue( String key, String newValue );

    String getStringValue( String key );

    // TODO: setDefaultStringArrayValue

    String[] getStringArrayValue( String key );

    // ----
    // long
    // ----

    long getLongValue( String key );

    void setLongValue( String key, long newValue );

    void setDefaultLongValue( String key, long defaultValue );

    // void addPersistenceValueUpdateListener( PersistenceValueUpdateListener listener );
    // void removePersistenceValueUpdateListener( PersistenceValueUpdateListener listener );
    // void clearPersistenceValueUpdateListeners();

    Collection<String> enumerateKeys( Predicate<String> keyPredicate );

}
