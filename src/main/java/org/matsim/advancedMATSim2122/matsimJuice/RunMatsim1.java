/* *********************************************************************** *
 * project: org.matsim.*												   *
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 * copyright       : (C) 2008 by the members listed in the COPYING,        *
 *                   LICENSE and WARRANTY file.                            *
 * email           : info at matsim dot org                                *
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *   See also COPYING, LICENSE and WARRANTY file                           *
 *                                                                         *
 * *********************************************************************** */
package org.matsim.advancedMATSim2122.matsimJuice;

import com.google.inject.Inject;
import org.matsim.api.core.v01.Scenario;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.controler.AbstractModule;
import org.matsim.core.controler.Controler;
import org.matsim.core.controler.OutputDirectoryHierarchy.OverwriteFileSetting;
import org.matsim.core.scenario.ScenarioUtils;

/**
 * @author nagel
 *
 */
public class RunMatsim1 {

	public static void main(String[] args) {

		Config config;
		if ( args==null || args.length==0 || args[0]==null ){
			config = ConfigUtils.loadConfig( "scenarios/equil/config.xml" );
		} else {
			config = ConfigUtils.loadConfig( args );
		}

		config.controler().setOverwriteFileSetting( OverwriteFileSetting.deleteDirectoryIfExists );

		// possibly modify config here

		// ---
		
		Scenario scenario = ScenarioUtils.loadScenario(config) ;

		// possibly modify scenario here
		
		// ---
		
		Controler controler = new Controler( scenario ) ;

		controler.addOverridingModule(new MyAbstractModule());
		
		// possibly modify controler here

//		controler.addOverridingModule( new OTFVisLiveModule() ) ;

		
		// ---
		
		controler.run();
	}
	// the new defined AbstractModule can also be packaged and move it to other places
	private static class MyAbstractModule extends AbstractModule {
		@Override
		public void install() {
			this.bind(Simulation.class).to( SimulationDefaultImpl.class );
			this.bind(Helper.class).to( HelperDefaultImpl.class );
		}
	}

	interface Helper{
		void help();
	}
	interface Simulation{
		void doStep();
	}
	static class HelperDefaultImpl implements Helper{
		public void help(){
			System.out.println( this.getClass().getSimpleName() + "is helping");
		}
	}
	static class SimulationDefaultImpl implements Simulation{
		private final Helper helper;
		@Inject
		SimulationDefaultImpl(Helper helper){
			this.helper = helper;
		}
		//@Inject private Helper helper;

		public void doStep(){
			System.out.println("entering" + this.getClass().getSimpleName());
			helper.help();
			System.out.println("leaving" + this.getClass().getSimpleName());
		}
	}

}
