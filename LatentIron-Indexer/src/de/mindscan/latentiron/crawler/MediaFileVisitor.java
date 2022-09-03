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
package de.mindscan.latentiron.crawler;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.function.Consumer;

/**
 * 
 */
public class MediaFileVisitor<T extends Path> implements FileVisitor<Path> {

    private final PathMatcher commonVideoFileMatcher = FileSystems.getDefault().getPathMatcher( "glob:**.{webm,mkv,mp4,mp2,mpeg,mpg,avi,ts,vob}" );
    private final PathMatcher commonImageFileMatcher = FileSystems.getDefault().getPathMatcher( "glob:**.{webp,png,jpg,jpeg,gif}" );

    private Consumer<Path> pathCollector;

    /**
     * 
     */
    public MediaFileVisitor( Consumer<Path> pathCollector ) {
        this.pathCollector = pathCollector;
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public FileVisitResult visitFile( Path file, BasicFileAttributes attrs ) throws IOException {

        if (commonImageFileMatcher.matches( file )) {
            pathCollector.accept( file );
        }
        if (commonVideoFileMatcher.matches( file )) {
            pathCollector.accept( file );
        }

        return FileVisitResult.CONTINUE;
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public FileVisitResult visitFileFailed( Path file, IOException exc ) throws IOException {
        return FileVisitResult.CONTINUE;
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public FileVisitResult preVisitDirectory( Path dir, BasicFileAttributes attrs ) throws IOException {
        return FileVisitResult.CONTINUE;
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public FileVisitResult postVisitDirectory( Path dir, IOException exc ) throws IOException {
        return FileVisitResult.CONTINUE;
    }

}
