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

/**
 * This file contains the description of the database.
 * 
 * This database description should be laodable and the search and labelling can be
 * done using this description file.
 */
public class LabelDataDatabaseDescription {

    private String databaseDescription;
    private String databaseName;
    private String databaseVersion;
    private String dataFolder;
    private String indexFolder;
    private String databaseAuthor;

    // private String createdDate;
    // private String createdTime;
    // private String createdTimestamp;

    /**
     * @param indexFolder
     */
    public LabelDataDatabaseDescription( String indexFolder ) {
        this.indexFolder = indexFolder;
        // actually the indexfolder is the one where we opened the database
        // when we load it from disk it may or may not be at this position, when the index was last created.
        // in case of a load operation, we may need to override with the constructor value.
    }

    public void setDatabaseDescription( String databaseDescription ) {
        this.databaseDescription = databaseDescription;
    }

    public String getDatabaseDescription() {
        return databaseDescription;
    }

    public void setDatabaseName( String databaseName ) {
        this.databaseName = databaseName;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void save() {
        // TODO save operation will store to index/label folder.
        // probably use gson
    }

    public void load() {
        // TODO load operation 
        // probably use gson
    }

    public void loadUpdateIndexFoder() {
        // probably use gson
    }
}
