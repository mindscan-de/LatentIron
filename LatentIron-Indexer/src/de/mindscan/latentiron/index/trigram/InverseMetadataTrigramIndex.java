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
package de.mindscan.latentiron.index.trigram;

import java.nio.file.Path;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import de.mindscan.latentiron.document.DocumentId;
import de.mindscan.latentiron.index.TrigramIndex;

/**
 * 
 */
public class InverseMetadataTrigramIndex {

    private static final String TRIGRAM_INVERSE_METADATA_INDEX = "inverseMetadataTrigram.index";

    private Map<String, TrigramIndex> inverseIndex = new HashMap<>();
    private final Path inverseTrigramsPath;

    /**
     * @param indexFolder
     */
    public InverseMetadataTrigramIndex( Path indexFolder ) {
        this.inverseTrigramsPath = indexFolder.resolve( TRIGRAM_INVERSE_METADATA_INDEX );
    }

    public static String getLocalIndexFolder() {
        return TRIGRAM_INVERSE_METADATA_INDEX;
    }

    /**
     * 
     */
    public void init() {
        inverseIndex = new HashMap<>();
    }

    /**
     * @param documentId the document id to add to each trigram
     * @param uniqueTrigramlist the collection of trigrams contained in the document metadata
     */
    public void addTrigramsForMetadata( DocumentId documentId, Collection<String> uniqueTrigramlist ) {
        for (String trigramKey : uniqueTrigramlist) {
            inverseIndex.computeIfAbsent( trigramKey, this::createEmptyTrigramIndex ).add( documentId.getDocumentKey() );
        }
    }

    /**
     * This method implements the save operation for the whole inverse index. 
     */
    public void save() {
        for (Entry<String, TrigramIndex> entry : inverseIndex.entrySet()) {
            try {
                entry.getValue().save();
            }
            catch (Exception ex) {
                // ignore
            }
        }
    }

    private TrigramIndex createEmptyTrigramIndex( String trigram ) {
        return new TrigramIndex( trigram, 0, inverseTrigramsPath );
    }

}
