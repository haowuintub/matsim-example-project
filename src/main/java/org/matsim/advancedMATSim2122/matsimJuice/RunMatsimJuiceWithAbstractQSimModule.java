package org.matsim.advancedMATSim2122.matsimJuice;

import com.google.inject.Injector;
import com.google.inject.Module;
import org.matsim.api.core.v01.Scenario;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.controler.*;
import org.matsim.core.controler.corelisteners.ControlerDefaultCoreListenersModule;
import org.matsim.core.mobsim.qsim.AbstractQSimModule;
import org.matsim.core.router.TripRouter;
import org.matsim.core.scenario.ScenarioByInstanceModule;
import org.matsim.core.scenario.ScenarioUtils;

class RunMatsimJuiceWithAbstractQSimModule {

    public static void main(String[] args) {
        Config config = ConfigUtils.createConfig();
        config.controler().setOverwriteFileSetting(OutputDirectoryHierarchy.OverwriteFileSetting.deleteDirectoryIfExists);
        Scenario scenario = ScenarioUtils.loadScenario(config);

        Controler controler = new Controler(scenario);

        // the below installed 'infrastructures' are only can be accessed in QSim level (/within the iterations) and be removed after the end of this iteration!
        // Also note: in QSim level, the 'infrastructures' that installed in upper level(controler.addOverridingModule(new AbstractModule());) can also be accessed.
        controler.addOverridingQSimModule(new AbstractQSimModule() {
            @Override
            protected void configureQSim() {
                //for example, we could install some 'infrastructures' here if we want to do something like 'agents replan as they go'.
            }
        });

    }
}
