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

/**
 * 
 */
public class LabelDataDatabaseIndex {

    private Path indexFolder;

    private final MetadataCache theMetadataCache;
    // TODO
    // private final InverseMetadataTrigramIndex theInverseMetadataTrigramIndex;

    // TODO: work with the meta data index and the inverse metadata index

    /**
     * @param indexFolder
     */
    public LabelDataDatabaseIndex( Path indexFolder ) {
        this.indexFolder = indexFolder;
        theMetadataCache = new MetadataCache( indexFolder );
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
    }

    /**
     * 
     */
    public void finish() {
        // TODO write current state of the Database and the DatabaseIndex
    }

    /**
     * @return
     */
    public Object getInverseMetadataTrigramIndex() {
        return null;
    }

    /**
     * @return
     */
    public MetadataCache getMetadataCache() {
        return theMetadataCache;
    }

}
