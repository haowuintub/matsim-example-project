package org.matsim.advancedMATSim2122.matsimJuice;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Module;
import org.matsim.api.core.v01.Scenario;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.controler.*;
import org.matsim.core.controler.corelisteners.ControlerDefaultCoreListenersModule;
import org.matsim.core.router.TripRouter;
import org.matsim.core.scenario.ScenarioByInstanceModule;
import org.matsim.core.scenario.ScenarioUtils;

class RunMatsimJuice {

    public static void main(String[] args) {
        Config config = ConfigUtils.createConfig();
        config.controler().setOverwriteFileSetting(OutputDirectoryHierarchy.OverwriteFileSetting.deleteDirectoryIfExists);
        Scenario scenario = ScenarioUtils.loadScenario(config);

        Module module = new AbstractModule() {
            @Override
            public void install() {
                install( new NewControlerModule() );
                install( new ControlerDefaultCoreListenersModule() );
                install( new ControlerDefaultsModule() );
                install( new ScenarioByInstanceModule( scenario ) ) ;
            }
        };
        Injector injector = org.matsim.core.controler.Injector.createInjector(config, module);
/*        // can also createInjector without module as following:
        Injector injector = org.matsim.core.controler.Injector.createInjector(config);*/

/*        // the above block can be replaced by one following line:
        Injector injector = ControlerUtils.createAdhocInjector(config, scenario);*/

        TripRouter tripRouter = injector.getInstance( TripRouter.class ) ;

    }
}
