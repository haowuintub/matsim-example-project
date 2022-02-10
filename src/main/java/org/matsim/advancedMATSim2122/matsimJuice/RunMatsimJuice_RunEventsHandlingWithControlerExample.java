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
import org.matsim.api.core.v01.events.LinkEnterEvent;
import org.matsim.api.core.v01.events.handler.LinkEnterEventHandler;
import org.matsim.api.core.v01.network.Network;
import org.matsim.api.core.v01.population.Person;
import org.matsim.contrib.otfvis.OTFVisLiveModule;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.config.groups.QSimConfigGroup;
import org.matsim.core.controler.AbstractModule;
import org.matsim.core.controler.Controler;
import org.matsim.core.controler.OutputDirectoryHierarchy.OverwriteFileSetting;
import org.matsim.core.events.handler.EventHandler;
import org.matsim.core.scenario.ScenarioUtils;
import org.matsim.core.scoring.ScoringFunction;
import org.matsim.core.scoring.ScoringFunctionFactory;
import org.matsim.core.scoring.SumScoringFunction;
import org.matsim.core.scoring.functions.*;

/**
 * @author nagel
 *
 */
public class RunMatsimJuice_RunEventsHandlingWithControlerExample {

	public static void main(String[] args) {

		Config config;
		if ( args==null || args.length==0 || args[0]==null ){
			config = ConfigUtils.loadConfig( "scenarios/equil/config.xml" );
		} else {
			config = ConfigUtils.loadConfig( args );
		}

		config.controler().setOverwriteFileSetting( OverwriteFileSetting.deleteDirectoryIfExists );
		config.controler().setLastIteration(0);

		config.qsim().setSnapshotStyle(QSimConfigGroup.SnapshotStyle.kinematicWaves);
		config.qsim().setTrafficDynamics(QSimConfigGroup.TrafficDynamics.kinematicWaves);

		// possibly modify config here

		// ---
		
		Scenario scenario = ScenarioUtils.loadScenario(config) ;

		// possibly modify scenario here
		
		// ---
		
		Controler controler = new Controler( scenario ) ;

		// possibly modify controler here
		controler.addOverridingModule(new AbstractModule() {
			@Override
			public void install() {
/*				//bind to instance
				this.addEventHandlerBinding().toInstance(new MyEventHandler1(scenario.getNetwork()));*/
				//bind to class
				this.addEventHandlerBinding().to(MyEventHandler1.class);
			}
		});

		controler.addOverridingModule( new OTFVisLiveModule() ) ;

		// ---
		
		controler.run();
	}

	private static class MyEventHandler1 implements LinkEnterEventHandler {
/*		//if class is bound to instance
		public MyEventHandler1(Network network) {
		}*/
		//if class is bound to class
		@Inject Network network;

		@Override
		public void reset(int iteration) {
			System.out.println("reset...");
		}

		@Override
		public void handleEvent(LinkEnterEvent event) {
			System.out.println("LinkEnterEvent");
			System.out.println("Time: " + event.getTime());
			System.out.println("LinkId: " + event.getLinkId());
			System.out.println("VehicleId: " + event.getVehicleId());
		}
	}
}
