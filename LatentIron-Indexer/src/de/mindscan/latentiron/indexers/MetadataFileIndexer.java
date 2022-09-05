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
package de.mindscan.latentiron.indexers;

import java.nio.file.Path;
import java.util.Collection;
import java.util.Deque;

import de.mindscan.latentiron.document.DocumentId;
import de.mindscan.latentiron.document.DocumentIdFactory;
import de.mindscan.latentiron.document.DocumentMetadata;
import de.mindscan.latentiron.index.LabelDataDatabaseIndex;

/**
 * 
 */
public class MetadataFileIndexer implements FileContentIndexer {

    private LabelDataDatabaseIndex index;

    /** 
     * {@inheritDoc}
     */
    @Override
    public void buildIndex( Deque<Path> filesToBeIndexed, Path crawlFolder, Path indexFolder ) {
        setIndex( new LabelDataDatabaseIndex( indexFolder ) );

        // TODO
        // index.getInverseMetadataTrigramIndex().init();

        for (Path fileToIndex : filesToBeIndexed) {
            try {
                updateMetaIndexWithSingleFile( fileToIndex, crawlFolder, indexFolder );
            }
            catch (Exception ignore) {
                // intentionally left blank
            }
        }

        // TODO
        // index.getInverseMetadataTrigramIndex().save();
    }

    private void updateMetaIndexWithSingleFile( Path fileToIndex, Path crawlFolder, Path indexFolder ) {
        String documentKey = extractDocumentKeyFromPath( fileToIndex );
        DocumentId documentId = DocumentIdFactory.createDocumentIDFromDocumentKey( documentKey );

        // load known metadata for document
        DocumentMetadata documentMetaData = index.getMetadataCache().loadMetadata( documentKey );

        logDocumentMetadata( documentMetaData );

        // get all "values" and treat them as words
        Collection<String> uniqueWordlist = documentMetaData.getAllValuesLowercase();

        // TODO: 
        // Set<String> uniqueTrigramlist = SimpleWordUtils.getUniqueTrigramsFromWordList( uniqueWordlist );

        // TODO: 
        // index.getInverseMetadataTrigramIndex().addTrigramsForMetadata( documentId, uniqueTrigramlist );
    }

    private String extractDocumentKeyFromPath( Path fileToIndex ) {
        String simpleFilename = fileToIndex.getFileName().toString();
        return simpleFilename.substring( 0, simpleFilename.length() - ".metadata".length() );
    }

    private void logDocumentMetadata( DocumentMetadata documentMetaData ) {
        System.out.println( documentMetaData.getDocumentKey() );
        System.out.println( documentMetaData.getDocumentLocation() );
    }

    private void setIndex( LabelDataDatabaseIndex labelDataDatabaseIndex ) {
        this.index = labelDataDatabaseIndex;
    }

}
