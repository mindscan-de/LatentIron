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
public class MetadataIndexHFBCompilerParameters implements Callable<Integer> {

    @Option( names = CommonParameters.crawlFolder, defaultValue = DefaultValues.INVERSE_METADATA_INDEX_FOLDER, description = "The trigrams folder to compile." )
    private Path crawlFolder;

    @Option( names = CommonParameters.labelDataFolder, defaultValue = DefaultValues.LABEL_FOLDER_DEFAULT, description = "The folder where the index shall be stored." )
    private Path indexFolder;

    /** 
     * {@inheritDoc}
     */
    @Override
    public Integer call() throws Exception {
        MetadataIndexHFBCompilerMain hfbCompilerMain = new MetadataIndexHFBCompilerMain();
        hfbCompilerMain.run( this );
        return 0;
    }

    /**
     * @return the crawlFolder
     */
    public Path getCrawlFolder() {
        return crawlFolder;
    }

    /**
     * @return the indexFolder
     */
    public Path getIndexFolder() {
        return indexFolder;
    }

}
