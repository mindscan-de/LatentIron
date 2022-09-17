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
package de.mindscan.latentiron.persistence;

import java.nio.file.Path;

import de.mindscan.latentiron.persistence.impl.PersistenceModuleImpl;
import de.mindscan.latentiron.persistence.io.PersistenceModuleReaderImpl;

/**
 * This will create initialized instances of PersistenceModule Implementations
 */
public class PersistenceModuleFactory {

    public static PersistenceModule createModuleInstance( Path persistenceBasePath2, String persistenceNamespaceName ) {
        Path fullFilePath = persistenceBasePath2.resolve( persistenceNamespaceName + ".persisted" );

        // either create+load or get PersistenceModule from Runtime
        PersistenceModuleImpl persistenceModule = new PersistenceModuleImpl( 0, persistenceNamespaceName );

        PersistenceModuleReaderImpl reader = new PersistenceModuleReaderImpl();
        reader.loadFile( persistenceModule, fullFilePath );

        return persistenceModule;
    }

}
