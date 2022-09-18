/**
 * 
 * MIT License
 *
 * Copyright (c) 2021 Maxim Gansert, Mindscan
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

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

import de.mindscan.latentiron.framework.command.BFCommand;
import de.mindscan.latentiron.framework.registry.FrameworkRegistry;
import de.mindscan.latentiron.framework.registry.impl.FrameworkRegistryImpl;
import de.mindscan.latentiron.system.startup.SystemServices;

/**
 * 
 */
public class LatentIronViewerMainApp {

    protected Shell shellBFViewerMainApp;
    private FrameworkRegistry frameworkRegistry;

    /**
     * Launch the application.
     * @param args
     */
    public static void main( String[] args ) {
        try {
            LatentIronViewerStartup startup = new LatentIronViewerStartup();
            startup.start();

            LatentIronViewerMainApp window = new LatentIronViewerMainApp();
            window.open();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Open the window.
     */
    public void open() {
        frameworkRegistry = getFrameworkRegistryInstance();

        Display display = Display.getDefault();
        createContents();
        shellBFViewerMainApp.open();
        shellBFViewerMainApp.layout();
        while (!shellBFViewerMainApp.isDisposed()) {
            try {
                if (!display.readAndDispatch()) {
                    display.sleep();
                }
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private FrameworkRegistry getFrameworkRegistryInstance() {
        FrameworkRegistry registry = SystemServices.getInstance().getFrameworkRegistry();

        frameworkRegistry = registry != null ? registry : new FrameworkRegistryImpl();

        return frameworkRegistry;
    }

    /**
     * Create contents of the window.
     */
    protected void createContents() {
        shellBFViewerMainApp = new Shell();
        shellBFViewerMainApp.setSize( 853, 642 );
        shellBFViewerMainApp.setText( "LatentIron-DataLabel-Viewer and Editor 0.0.1.M1" );
        shellBFViewerMainApp.setLayout( new FillLayout( SWT.HORIZONTAL ) );

        createMainMenu();

        Composite composite = new Composite( shellBFViewerMainApp, SWT.NONE );
        composite.setLayout( new FillLayout( SWT.HORIZONTAL ) );

        SashForm sashForm_1 = new SashForm( composite, SWT.NONE );

        SashForm sashForm_2 = new SashForm( sashForm_1, SWT.VERTICAL );

//        ProjectViewComposite projectViewComposite = new ProjectViewComposite( sashForm_2, SWT.NONE );
//
//        OutlineViewComposite outlineViewComposite = new OutlineViewComposite( sashForm_2, SWT.NONE );
//        sashForm_2.setWeights( new int[] { 375, 218 } );
//
//        SashForm sashForm = new SashForm( sashForm_1, SWT.VERTICAL );
//
//        MainProjectComposite mainProjectComposite = new MainProjectComposite( sashForm, SWT.NONE );
//
//        MultiViewComposite multiViewComposite = new MultiViewComposite( sashForm, SWT.NONE );
//        sashForm.setWeights( new int[] { 432, 161 } );
//        sashForm_1.setWeights( new int[] { 148, 694 } );

        // This is still not nice, but good enough for now
        // we might implement a patched classloader or some DependenyInjector mechanism, since the app is 
        // basically also a ProjectRegistryParticipant and should not provide the truth to everyone else top down.
//        frameworkRegistry.registerParticipant( projectViewComposite );
//        frameworkRegistry.registerParticipant( mainProjectComposite );
//        frameworkRegistry.registerParticipant( multiViewComposite );
//        frameworkRegistry.registerParticipant( outlineViewComposite );
//
        frameworkRegistry.completeParticipantRegistration();
    }

    private void createMainMenu() {
        Menu menu = new Menu( shellBFViewerMainApp, SWT.BAR );
        shellBFViewerMainApp.setMenuBar( menu );

        MenuItem mntmFile = new MenuItem( menu, SWT.CASCADE );
        mntmFile.setText( "File" );

        Menu menu_file = new Menu( mntmFile );
        mntmFile.setMenu( menu_file );

        MenuItem mntmLoadFile = new MenuItem( menu_file, SWT.NONE );
        mntmLoadFile.addSelectionListener( new SelectionAdapter() {
            @Override
            public void widgetSelected( SelectionEvent e ) {
            }
        } );
        mntmLoadFile.setText( "Open Database ..." );

        MenuItem mntmSpecialRawOption = new MenuItem( menu_file, SWT.NONE );
        mntmSpecialRawOption.addSelectionListener( new SelectionAdapter() {
            @Override
            public void widgetSelected( SelectionEvent e ) {
            }
        } );
        mntmSpecialRawOption.setText( "New Database ..." );

        MenuItem mntmLoadrxxFile = new MenuItem( menu_file, SWT.NONE );
        mntmLoadrxxFile.addSelectionListener( new SelectionAdapter() {
            @Override
            public void widgetSelected( SelectionEvent e ) {

            }
        } );
        mntmLoadrxxFile.setText( "Save Database..." );

        new MenuItem( menu_file, SWT.SEPARATOR );

        MenuItem mntmGarbageCollector = new MenuItem( menu_file, SWT.NONE );
        mntmGarbageCollector.addSelectionListener( new SelectionAdapter() {
            @Override
            public void widgetSelected( SelectionEvent e ) {
                // Don't like it... 
                System.gc();
            }
        } );
        mntmGarbageCollector.setText( "Garbage Collector (debug)" );

        new MenuItem( menu_file, SWT.SEPARATOR );

        MenuItem mntmExit = new MenuItem( menu_file, SWT.PUSH );
        mntmExit.addSelectionListener( new SelectionAdapter() {
            @Override
            public void widgetSelected( SelectionEvent e ) {
                shellBFViewerMainApp.close();
            }
        } );
        mntmExit.setText( "Exit" );

        MenuItem mntmWindow = new MenuItem( menu, SWT.CASCADE );
        mntmWindow.setText( "Window" );

        Menu menu_window = new Menu( mntmWindow );
        mntmWindow.setMenu( menu_window );

        MenuItem mntmSearchTools = new MenuItem( menu_window, SWT.CHECK );
        mntmSearchTools.addSelectionListener( new SelectionAdapter() {
            @Override
            public void widgetSelected( SelectionEvent e ) {
            }
        } );
        mntmSearchTools.setText( "Search Tools" );

        MenuItem mntmTools = new MenuItem( menu, SWT.CASCADE );
        mntmTools.setText( "Tools" );

        Menu menu_tools = new Menu( mntmTools );
        mntmTools.setMenu( menu_tools );

        MenuItem mntmExtract = new MenuItem( menu_tools, SWT.NONE );
        mntmExtract.addSelectionListener( new SelectionAdapter() {
            @Override
            public void widgetSelected( SelectionEvent e ) {
            }
        } );
        mntmExtract.setText( "Build Labeled Dataset" );

        MenuItem mntmHelp = new MenuItem( menu, SWT.NONE );
        mntmHelp.setText( "Help" );
    }

    public void dispatchCommand( BFCommand command ) {
        if (frameworkRegistry != null) {
            frameworkRegistry.getCommandDispatcher().dispatchCommand( command );
        }
    }
}
