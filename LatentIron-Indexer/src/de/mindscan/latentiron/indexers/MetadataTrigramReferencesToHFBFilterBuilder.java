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

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Deque;
import java.util.Set;
import java.util.TreeSet;

import com.google.gson.Gson;

import de.mindscan.furiousiron.hfb.HFBFilterBank;
import de.mindscan.furiousiron.hfb.HFBFilterBankCompiler;
import de.mindscan.latentiron.index.LabelDataDatabaseIndex;
import de.mindscan.latentiron.index.TrigramSubPathCalculator;
import de.mindscan.latentiron.index.trigram.TrigramOccurrence;
import de.mindscan.latentiron.index.trigram.model.TrigramDocumentCountJsonModel;
import de.mindscan.latentiron.index.trigram.model.TrigramIndexJsonModel;

/**
 * 
 */
public class MetadataTrigramReferencesToHFBFilterBuilder implements FileContentIndexer {

    private static final String TRIGRAM_INVERSE_METADATA_INDEX = "inverseMetadataTrigram.index";
    private static final String TRIGRAM_REFERENCE_SUFFIX = ".reference";

    private static final int MAX_INDEX_REFERENCES = 4096;

    private LabelDataDatabaseIndex index;
    private Path searchMetadataTrigramsPath;

    public void buildIndex( Deque<Path> filesToBeIndexed, Path crawlFolder, Path indexFolder ) {
        setIndex( new LabelDataDatabaseIndex( indexFolder ) );
        setIndexFolder( indexFolder );

        HFBFilterBankCompiler compiler = new HFBFilterBankCompiler();

        for (Path referenceCountFile : filesToBeIndexed) {
            TrigramOccurrence trigramOccurrence = loadTrigramOccurrence( referenceCountFile );
            if (trigramOccurrence == null) {
                // TODO: log that shit.. this can be problematic, if a filter does not exist.
                continue;
            }

            // use SearchMetadataTrigramIndex load all documentids for a trigram  
            Collection<String> documentIdsForTrigram = getDocumentIdsForTrigram( trigramOccurrence.getTrigram() );

            // do some consistency check first...
            if (trigramOccurrence.getOccurrenceCount() == documentIdsForTrigram.size()) {
                System.out.println( String.format( "Compiling filter for '%s' with %d elements", trigramOccurrence.getTrigram(),
                                trigramOccurrence.getOccurrenceCount() ) );

                // compile each documentid list into a HFB Filter
                HFBFilterBank compiledFilter = compiler.compileFilterHex( documentIdsForTrigram );

                index.getInverseMetadataHFBFilterIndex().addHFBFilterForMetadata( trigramOccurrence.getTrigram(), compiledFilter );
            }
            else {
                System.out.println( String.format( "'%s' is _not_ consistent; %d vs %d", trigramOccurrence.getTrigram(), trigramOccurrence.getOccurrenceCount(),
                                documentIdsForTrigram.size() ) );
            }
        }
    }

    /**
     * @param indexFolder
     */
    private void setIndexFolder( Path indexFolder ) {
        this.searchMetadataTrigramsPath = indexFolder.resolve( TRIGRAM_INVERSE_METADATA_INDEX );
    }

    TrigramOccurrence loadTrigramOccurrence( Path pathForTrigramCount ) {
        Gson gson = new Gson();

        try (Reader json = Files.newBufferedReader( pathForTrigramCount )) {
            TrigramDocumentCountJsonModel fromJson = gson.fromJson( json, TrigramDocumentCountJsonModel.class );
            long occurence = fromJson.getRelatedDocumentsCount();
            String trigram = fromJson.getTrigram();
            return new TrigramOccurrence( trigram, occurence );
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Collection<String> getDocumentIdsForTrigram( String trigram ) {
        return loadFromDisk( trigram );
    }

    private Set<String> loadFromDisk( String trigram ) {
        Set<String> result = new TreeSet<>();

        for (int counter = 0; counter < MAX_INDEX_REFERENCES; counter++) {
            Path pathForTrigrams = TrigramSubPathCalculator.getPathForTrigram( searchMetadataTrigramsPath, trigram, "." + counter + TRIGRAM_REFERENCE_SUFFIX );

            if (Files.exists( pathForTrigrams, LinkOption.NOFOLLOW_LINKS )) {
                Gson gson = new Gson();
                try (Reader json = Files.newBufferedReader( pathForTrigrams )) {
                    TrigramIndexJsonModel fromJson = gson.fromJson( json, TrigramIndexJsonModel.class );
                    result.addAll( fromJson.getRelatedDocuments() );
                }
                catch (Exception e) {
                    break;
                }
            }
            else {
                break;
            }
        }

        return result;
    }

    private void setIndex( LabelDataDatabaseIndex index ) {
        this.index = index;
    }

}
