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
import java.util.concurrent.Callable;

import picocli.CommandLine.Option;

/**
 * 
 */
public class MetadataIndexerParameters implements Callable<Integer> {

    @Option( names = CommonParameters.crawlFolder, defaultValue = "", description = "The folder, where your data to label is located." )
    private Path crawlFolder;

    @Option( names = CommonParameters.labelDataFolder, defaultValue = "", description = "The folder, where you want to keep the data labels." )
    private Path labelDataFolder;

    /** 
     * {@inheritDoc}
     */
    @Override
    public Integer call() throws Exception {
        MetadataIndexerMain main = new MetadataIndexerMain();
        main.run( this );
        return 0;
    }

    /**
     * @return
     */
    public Path getCrawlFolder() {
        return crawlFolder;
    }

    /**
     * @return
     */
    public Path getLabelDataFolder() {
        return labelDataFolder;
    }

}
