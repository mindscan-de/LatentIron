/**
 * 
 * MIT License
 *
 * Copyright (c) 2019, 2022 Maxim Gansert, Mindscan
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
package de.mindscan.latentiron.classifiers;

import java.nio.file.Path;
import java.util.List;

import de.mindscan.latentiron.document.DocumentId;
import de.mindscan.latentiron.document.DocumentMetadata;

/**
 * This interface was firstly named Classifier, because it had just classified a file and given it some labels
 * but in reality this is more of a MetadataExtractor, this  MetadataExtractor can be implemented as a second
 * pass on an already indexed and cached file. Each DocumentId can be retrieved and be further analyzed and the
 * metadata then can be updated and operated on.
 * 
 * A First simple classifier / metadata extractor might deliver some hints about the file, such that it can then
 * be processed and filtered due to its preliminary metadata.
 * 
 * By building a secondary index over the metadata, we also can achieve metadata search combined with original 
 * search, also the metadata index can be build from the DocumentMetadata.
 */
public interface Classifier {

    /**
     * @param documentId
     * @param documentMetaData
     * @param fileToIndex
     */
    void classify( DocumentId documentId, DocumentMetadata documentMetaData, Path fileToIndex );

    /**
     * @param documentId
     * @param documentMetaData
     * @param uniqueWordlist
     */
    void classify( DocumentId documentId, DocumentMetadata documentMetaData, List<String> uniqueWordlist );

}
