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
package de.mindscan.latentiron.index;

import java.nio.file.Path;

import de.mindscan.latentiron.index.cache.MetadataCache;
import de.mindscan.latentiron.index.hfb.InverseMetadataHFBFilterIndex;
import de.mindscan.latentiron.index.trigram.InverseMetadataTrigramIndex;

/**
 * 
 */
public class LabelDataDatabaseIndex {

    private final LabelDataDatabaseDescription theLabelDatabaseDescription;
    private final MetadataCache theMetadataCache;
    private final InverseMetadataTrigramIndex theInverseMetadataTrigramIndex;
    private final InverseMetadataHFBFilterIndex theInverseMetadataHFBFilterIndex;

    // TODO what to continue next... We want to create a content description for each file, with labels and such
    // private final ContentLabelCache theContentLabelCache;
    // private final InverseContentLabelTrigramIndex theInverseContentLabelTrigramIndex;

    // TODO: we might also manage relations between files, like same place or same event

    // TODO: we might want to support collections != labels
    // TODO: might be built from queries of the data
    // TODO: we might want to order them manually and persist this order in the collection

    // TODO: work with the meta data index and the inverse metadata index

    /**
     * @param indexFolder
     */
    public LabelDataDatabaseIndex( Path indexFolder ) {
        // TODO: actually we want to use the current file if Description is already available 
        // otherwise we want to create a new LabelDatabaseDescription 
        theLabelDatabaseDescription = new LabelDataDatabaseDescription( indexFolder.toAbsolutePath().toString() );

        theMetadataCache = new MetadataCache( indexFolder );
        theInverseMetadataTrigramIndex = new InverseMetadataTrigramIndex( indexFolder );
        theInverseMetadataHFBFilterIndex = new InverseMetadataHFBFilterIndex( indexFolder );
    }

    /**
     * @param crawlFolder
     */
    public void setLabelDataSourcedataFolder( Path labelDataSourcedataFolder ) {

    }

    /**
     * 
     */
    public void init() {
        // TODO Create file and directory structure and such
        theLabelDatabaseDescription.setDatabaseDescription( "New Unnamed LabelDatabase" );

    }

    /**
     * 
     */
    public void finish() {
        // TODO write current state of the Database and the DatabaseIndex
        theLabelDatabaseDescription.save();
    }

    /**
     * @return the meta data cache access (document id -> metadata)
     */
    public MetadataCache getMetadataCache() {
        return theMetadataCache;
    }

    /**
     * @return the (inverse) metadata trigram index access (trigram -> document id)
     */
    public InverseMetadataTrigramIndex getInverseMetadataTrigramIndex() {
        return theInverseMetadataTrigramIndex;
    }

    /**
     * @return the (inverse) metadata trigram index filter access (trigram -> hfb filter bank)
     */
    public InverseMetadataHFBFilterIndex getInverseMetadataHFBFilterIndex() {
        return theInverseMetadataHFBFilterIndex;
    }

}
