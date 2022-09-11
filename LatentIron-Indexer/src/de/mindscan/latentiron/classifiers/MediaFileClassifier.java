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
package de.mindscan.latentiron.classifiers;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.List;

import de.mindscan.latentiron.document.CommonLabelNames;
import de.mindscan.latentiron.document.DocumentId;
import de.mindscan.latentiron.document.DocumentMetadata;

/**
 * 
 */
public class MediaFileClassifier implements Classifier {

    private final PathMatcher commonVideoFileMatcher = FileSystems.getDefault().getPathMatcher( "glob:**.{webm,mkv,mp4,mp2,mpeg,mpg,avi,ts,vob}" );
    private final PathMatcher commonImageFileMatcher = FileSystems.getDefault().getPathMatcher( "glob:**.{webp,png,jpg,jpeg,gif}" );

    /** 
     * {@inheritDoc}
     */
    @Override
    public void classify( DocumentId documentId, DocumentMetadata documentMetaData, Path fileToIndex ) {
        if (commonImageFileMatcher.matches( fileToIndex )) {
            documentMetaData.addMetadata( CommonLabelNames.FILE_TYPE, "image" );
        }
        else if (commonVideoFileMatcher.matches( fileToIndex )) {
            documentMetaData.addMetadata( CommonLabelNames.FILE_TYPE, "video" );
        }
        else {
            documentMetaData.addMetadata( CommonLabelNames.FILE_TYPE, "unknown" );
        }

        // TODO
        // file.type (image, video, data, image.screenshot)
        // file.date
        // file.time
        // file.checksum / content checksum
        // file.timestamp
        // file.originalname
        // file.originaltimestamp
        // file.createddate
        // file.modifieddate

    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public void classify( DocumentId documentId, DocumentMetadata documentMetaData, List<String> uniqueWordlist ) {

    }

}
