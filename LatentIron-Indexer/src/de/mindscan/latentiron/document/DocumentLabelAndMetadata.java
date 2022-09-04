/**
 * 
 * MIT License
 *
 * Copyright (c) 2022 Maxim Gansert, Mindscan
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
package de.mindscan.latentiron.document;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 
 */
public class DocumentLabelAndMetadata {

    private String documentKey;
    private String documentLocation;
    private String documentSimpleFilename;

    private long fileSize = 0L;

    private Map<String, String> labelAndMetadataMap;

    /**
     * 
     */
    public DocumentLabelAndMetadata( String documentKey, String documentLocation, String documentSimpleFilename ) {
        this.documentKey = documentKey;
        this.documentLocation = documentLocation;
        this.documentSimpleFilename = documentSimpleFilename;
        this.labelAndMetadataMap = new HashMap<>();
    }

    public String getDocumentKey() {
        return documentKey;
    }

    public String getDocumentLocation() {
        return documentLocation;
    }

    public String getDocumentSimpleFilename() {
        return documentSimpleFilename;
    }

    public void addLabel( String labelKey, String labelValue ) {
        this.labelAndMetadataMap.put( labelKey, labelValue );
    }

    public void containsLabel( String labelKey ) {
        this.labelAndMetadataMap.containsKey( labelKey );
    }

    public Map<String, String> getLabelAndMetadataMap() {
        return labelAndMetadataMap;
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return documentKey + "@" + documentLocation;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize( long fileSize ) {
        this.fileSize = fileSize;
    }

    public Collection<String> getAllValuesLowercase() {
        Set<String> result = new HashSet<>();

        result.add( getDocumentSimpleFilename().toLowerCase() );
        result.add( getDocumentLocation().toLowerCase() );
        result.add( Long.toString( getFileSize() ) );

        List<String> collectedLowercaseValues = labelAndMetadataMap.values().stream().map( s -> s.toLowerCase() ).collect( Collectors.toList() );
        result.addAll( collectedLowercaseValues );

        return result;
    }

    public Collection<String> getAllValuesOriginalcase() {
        Set<String> result = new HashSet<>();

        result.add( getDocumentSimpleFilename() );
        result.add( getDocumentLocation() );
        result.add( Long.toString( getFileSize() ) );
        result.addAll( labelAndMetadataMap.values() );

        return result;
    }

    public Map<String, String> getKeyValuesAsMap() {
        Map<String, String> result = new HashMap<>();

        result.put( "file.simplename", getDocumentSimpleFilename() );
        result.put( "file.path", getDocumentLocation() );
        result.put( "file.size", Long.toString( getFileSize() ) );

        result.putAll( labelAndMetadataMap );

        return result;
    }

}
