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
import java.util.Deque;

import de.mindscan.latentiron.classifiers.Classifier;
import de.mindscan.latentiron.classifiers.MediaFileClassifier;
import de.mindscan.latentiron.document.DocumentId;
import de.mindscan.latentiron.document.DocumentIdFactory;
import de.mindscan.latentiron.document.DocumentMetadata;
import de.mindscan.latentiron.document.DocumentMetadataFactory;
import de.mindscan.latentiron.index.LabelDataDatabaseIndex;

/**
 * 
 */
public class SimpleFileContentIndexer implements FileContentIndexer {

    private LabelDataDatabaseIndex databaseIndex;
    private Classifier classifier;

    /** 
     * {@inheritDoc}
     */
    @Override
    public void buildIndex( Deque<Path> filesToBeIndexed, Path crawlFolder, Path indexFolder ) {
        setDatabaseIndex( new LabelDataDatabaseIndex( indexFolder ) );

        // where is tha base foler of the Data we are labelling
        databaseIndex.setLabelDataSourcedataFolder( crawlFolder );

        // do we need Classifers?
        setClassifier( new MediaFileClassifier() );

        databaseIndex.init();

        // initialize index on disk
        // 
        // Because the file contents is binary in nature, we actually only want to create an
        // initial label index, where we create a kind of empty metadata / labeldata
        // then later we want to operate on the pre-initialized label data, which then needs 
        // to be reindexed all the time. 

        for (Path fileToIndex : filesToBeIndexed) {
            try {
                updateIndexWithSingleFile( fileToIndex, crawlFolder, indexFolder );
            }
            catch (Exception ignore) {
                // intentionally left blank 
            }
        }

        databaseIndex.finish();

    }

    private void setClassifier( Classifier classifier ) {
        this.classifier = classifier;
    }

    public Classifier getClassifier() {
        return classifier;
    }

    private void updateIndexWithSingleFile( Path fileToIndex, Path crawlFolder, Path indexFolder ) {
        DocumentId documentId = DocumentIdFactory.createDocumentID( fileToIndex, crawlFolder );
        DocumentMetadata documentMetaData = DocumentMetadataFactory.createDocumentLabelAndMetadata( documentId, fileToIndex );

        // add some metadata labels
        // Still Todo: document.key

        getClassifier().classify( documentId, documentMetaData, fileToIndex );
        // getClassifier().classify(documentid, documentmetadata, uniqueWordlist);

        // save the meta data object to disk
        databaseIndex.getMetadataCache().addDocumentMetadata( documentId, documentMetaData );
    }

    public void setDatabaseIndex( LabelDataDatabaseIndex databaseIndex ) {
        this.databaseIndex = databaseIndex;
    }

}
