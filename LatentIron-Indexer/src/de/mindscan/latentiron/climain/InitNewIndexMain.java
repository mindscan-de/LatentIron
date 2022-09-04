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
package de.mindscan.latentiron.climain;

import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.Deque;

import de.mindscan.latentiron.crawler.SimpleFileCrawler;
import de.mindscan.latentiron.indexers.SimpleFileContentIndexer;
import picocli.CommandLine;

/**
 * Steps of 
 * 
 * ---- Metadata ----
 * 1. InitNewIndexMain
 * 2. Update File Metadata, with additional information and classifiers
 *    - maybe with re scan, if files are added removed, or moved
 *    - maybe implement some add/move/remove operations on file basis.
 *    - maybe use first 4k hash value / to identify move op etc.
 *    - also simple filename / filesize are also good indicators
 * 3. Build File Metadata index and inverse file metadata index
 * 
 * 
 * --------------------------------------------------------
 * Use metadata index as base for content/document labeling
 * --------------------------------------------------------
 * 
 * ---- Content data ----
 * X+1 Update Content Labels, with additional information
 * X+2 Build content labels
 * 
 * 
 * --------------------------------------------------------
 * Use the content/labels to build collections for datasets
 * --------------------------------------------------------
 * 
 * ---- Collections ----
 * add / remove from collection / forward
 * ----
 * 
 */
public class InitNewIndexMain {

    public void run( InitNewIndexParameters parameters ) {
        Path crawlFolder = parameters.getCrawlFolder();
        Path indexFolder = parameters.getLabelDataFolder();

        Deque<Path> filesToBeIndexed = new ArrayDeque<Path>();
        SimpleFileCrawler crawler = new SimpleFileCrawler();
        crawler.crawl( filesToBeIndexed::add, crawlFolder );

        System.out.println( String.format( "%d files found for label data index.", filesToBeIndexed.size() ) );

        SimpleFileContentIndexer simpleFileContentIndexer = new SimpleFileContentIndexer();
        simpleFileContentIndexer.buildIndex( filesToBeIndexed, crawlFolder, indexFolder );
    }

    public static void main( String[] args ) {
        int exitCode = new CommandLine( new InitNewIndexParameters() ).execute( args );
        System.exit( exitCode );
    }
}
