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

/**
 * Contains the common names of all Metadata label names.
 */
public class CommonLabelNames {

    public static final String FILE_SIMPLENAME = "file.simplename";
    public static final String FILE_PATH = "file.path";
    public static final String FILE_SIZE = "file.size";
    public static final String FILE_TYPE = "file.type";

    // in case of a download a file origin would be cool, if this is somehow possible.

    // TODO + maybe a classifier according to the filename 
    // e.g. twitter, instagram, facebook, screenshot, unknown
    // maybe some additional information can be extracted from the filename
    // e.g. screenshot with date and time stamp info
    public static final String FILE_ORIGIN = "file.origin";
    public static final String FILE_DOWNLOAD_TIMSTAMP = "file.downloadts";

    // content descripton might be a differnent thing...
    // , caption
    // description(s), 
    // content.labels, content.descriptions (e.g. texts, bounding boxes)
    // content.height and content.width content.pixelcount (height*width)
}
