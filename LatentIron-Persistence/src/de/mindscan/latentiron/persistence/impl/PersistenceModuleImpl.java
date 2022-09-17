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
package de.mindscan.latentiron.persistence.impl;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeSet;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import de.mindscan.latentiron.persistence.PersistenceModule;

/**
 * 
 */
public class PersistenceModuleImpl implements PersistenceModule {

    private int namespaceId;
    private String namespaceName;

    // TODO: maybe we want to set and register the type information too...

    private Map<String, Object> currentPersistenceData = new LinkedHashMap<>();
    private Map<String, Object> defaultPersistenceData = new LinkedHashMap<>();

    /**
     * 
     */
    public PersistenceModuleImpl( int namespaceId, String namespaceName ) {
        this.namespaceId = namespaceId;
        this.namespaceName = namespaceName;
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public int getNamespaceId() {
        return namespaceId;
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public String getNamespaceName() {
        return namespaceName;
    }

    // --------------------------

//    public Object getObjectValue( String key ) {
//        return null;
//    }
//
//    public void setObjectValue( String key, Object newValue ) {
//
//    }

    // int values

    @Override
    public int getIntValue( String key ) {
        if (currentPersistenceData.containsKey( key )) {
            return ((Integer) currentPersistenceData.get( key )).intValue();
        }

        return ((Integer) defaultPersistenceData.get( key )).intValue();
    }

    @Override
    public void setIntValue( String key, int newValue ) {
        currentPersistenceData.put( key, Integer.valueOf( newValue ) );
    }

    @Override
    public void setDefaultIntValue( String key, int defaultValue ) {
        defaultPersistenceData.put( key, Integer.valueOf( defaultValue ) );
    }

    // Reset operations

    @Override
    public void resetToDefault( String key ) {
        if (currentPersistenceData.containsKey( key )) {
            currentPersistenceData.remove( key );
        }
    }

    @Override
    public void resetAllToDefault() {
        currentPersistenceData.clear();
    }

    // String values

    @Override
    public String getStringValue( String key ) {
        if (currentPersistenceData.containsKey( key )) {
            return ((String) currentPersistenceData.get( key ));
        }
        return ((String) defaultPersistenceData.get( key ));
    }

    @Override
    public void setStringValue( String key, String newValue ) {
        currentPersistenceData.put( key, newValue );
    }

    @Override
    public void setDefaultStringValue( String key, String defaultValue ) {
        defaultPersistenceData.put( key, defaultValue.intern() );
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public String[] getStringArrayValue( String key ) {
        String keyLengthName = key + ".array.length";
        int arrayLength = getIntValue( keyLengthName );

        if (arrayLength == 0) {
            return new String[0];
        }

        String[] result = new String[arrayLength];
        for (int i = 0; i < arrayLength; i++) {
            result[i] = getStringValue( key + "." + Integer.toString( i ) );
        }

        return result;
    }

    // long values
    @Override
    public long getLongValue( String key ) {
        if (currentPersistenceData.containsKey( key )) {
            return ((Long) currentPersistenceData.get( key )).longValue();
        }
        return ((Long) defaultPersistenceData.get( key )).longValue();
    }

    @Override
    public void setLongValue( String key, long newValue ) {
        currentPersistenceData.put( key, Long.valueOf( newValue ) );
    }

    @Override
    public void setDefaultLongValue( String key, long defaultValue ) {
        defaultPersistenceData.put( key, Long.valueOf( defaultValue ) );
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public Collection<String> enumerateKeys( Predicate<String> keyPredicate ) {
        TreeSet<String> allKeys = new TreeSet<>( defaultPersistenceData.keySet() );
        allKeys.addAll( currentPersistenceData.keySet() );

        return allKeys.stream().filter( keyPredicate ).collect( Collectors.toList() );
    }
}
