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
package de.mindscan.latentiron.viewer.main;

import java.lang.reflect.InvocationTargetException;

import de.mindscan.latentiron.framework.exceptions.NotYetImplemetedException;
import de.mindscan.latentiron.system.activators.EarlyPersistenceActivator;
import de.mindscan.latentiron.system.activators.FrameworkRegistryActivator;
import de.mindscan.latentiron.system.startup.StartupParticipant;
import de.mindscan.latentiron.system.startup.SystemServices;

/**
 * 
 */
public class LatentIronViewerStartup {

    /**
     * TODO: 
     * 
     * refactor this, to retrieve the startup configuration from early Persistence and then execute the 
     * startup configuration. But for now this is quite good enough. Also I want to registerServices and
     * getServices using Interfaces instead of specialized setters and getters in the systemServices to 
     * be able to have a plugin system. 
     *   
     */
    public void start() {

        // -----------------------
        // Phase 1 : early startup
        // -----------------------

        // Prepare System Services
        SystemServices systemServices = SystemServices.getInstance();

        // Early Persistence data (avoid hard coded dependencies)
        startActivator( EarlyPersistenceActivator.class, systemServices );

        // --------------------------------------
        // Phase 2 : other components and plugins
        // --------------------------------------        
        // TODO: get early startup configuration from early persistence configuration
        // TODO: start these configured components as individual bundles

        // Startup the Project registry 
        startActivator( FrameworkRegistryActivator.class, systemServices );

        // -------------------------------
        // Phase X : finish registtrations
        // -------------------------------

        // STARTUP : Complete Project Participant registration
        finishProjectParticipantRegistration( systemServices );
    }

    public void finishProjectParticipantRegistration( SystemServices systemServices ) {
        // Complete the Registration of the Startup-Items Startup-Steps
        if (systemServices.getFrameworkRegistry() != null) {
            systemServices.getFrameworkRegistry().completeParticipantRegistration();
        }
        else {
            throw new NotYetImplemetedException( "Startup could not complete, because project registry is not available." );
        }
    }

    private <T extends StartupParticipant> void startActivator( Class<T> clazz, SystemServices systemServices ) {
        try {
            T startupParticipant = clazz.getDeclaredConstructor().newInstance();
            startupParticipant.start( systemServices );
        }
        catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
                        | SecurityException e) {
            e.printStackTrace();
            throw new NotYetImplemetedException( "May be we should improve on the startActivator" );
        }
    }

}
