/**
 * 
 * MIT License
 *
 * Copyright (c) 2021, 2022 Maxim Gansert, Mindscan
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
package de.mindscan.latentiron.framework.registry.impl;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

import de.mindscan.latentiron.framework.dispatcher.CommandDispatcher;
import de.mindscan.latentiron.framework.dispatcher.EventDispatcher;
import de.mindscan.latentiron.framework.dispatcher.impl.SimpleCommandDispatcher;
import de.mindscan.latentiron.framework.dispatcher.impl.SimpleEventDispatcher;
import de.mindscan.latentiron.framework.registry.FrameworkRegistry;
import de.mindscan.latentiron.framework.registry.FrameworkRegistryParticipant;

/**
 * 
 */
public class FrameworkRegistryImpl implements FrameworkRegistry {

    private EventDispatcher eventDispatcher;
    private CommandDispatcher commandDispatcher;
    private ConcurrentLinkedQueue<FrameworkRegistryParticipant> registeredParticipantsQueue = new ConcurrentLinkedQueue<>();

    public FrameworkRegistryImpl() {
        this( new SimpleEventDispatcher() );
    }

    public FrameworkRegistryImpl( EventDispatcher eventdispatcher ) {
        this( eventdispatcher, new SimpleCommandDispatcher( eventdispatcher ) );
    }

    public FrameworkRegistryImpl( EventDispatcher eventdispatcher, CommandDispatcher commandDispatcher ) {
        this.eventDispatcher = eventdispatcher;
        this.commandDispatcher = commandDispatcher;
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public CommandDispatcher getCommandDispatcher() {
        return commandDispatcher;
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public EventDispatcher getEventDispatcher() {
        return eventDispatcher;
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public void registerParticipant( FrameworkRegistryParticipant participant ) {
        registeredParticipantsQueue.add( participant );
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public void completeParticipantRegistration() {
        Set<FrameworkRegistryParticipant> alreadyRegistered = new HashSet<>();

        // TODO: Actually we want to run the injector on all ProjectRegistryParticipant instead of this approach
        // TODO: for the moment this is good enough...

        while (!registeredParticipantsQueue.isEmpty()) {
            FrameworkRegistryParticipant participant = registeredParticipantsQueue.poll();

            // avoid registraton loops
            if (!alreadyRegistered.contains( participant )) {
                alreadyRegistered.add( participant );
                participant.setFrameworkRegistry( this );
            }
        }

        alreadyRegistered.clear();
    }

    // TODO: work on a mechanism that if the loaded files change, then we want to react on that....
    // 
    // addProjectListener
    // removeProjectListener
}
