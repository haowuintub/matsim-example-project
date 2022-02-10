package org.matsim.advancedMATSim2122.matsimJuice;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.matsim.api.core.v01.Scenario;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.controler.Controler;
import org.matsim.core.events.EventsUtils;
import org.matsim.core.scenario.ScenarioUtils;
import org.matsim.core.utils.io.IOUtils;
import org.matsim.examples.ExamplesUtils;
import org.matsim.testcases.MatsimTestUtils;
import org.matsim.utils.eventsfilecomparison.EventsFileComparator;

import java.net.URL;

import static org.junit.Assert.*;

public class RunMatsimTest {
    @Rule
    public MatsimTestUtils utils = new MatsimTestUtils() ;

    @Test
    public void testMain() {
        try {
            //Assert.fail("failing");

            //Config config = ConfigUtils.createConfig();
            // create a config using the path of test input as context
            //Config config = utils.createConfigWithTestInputFilePathAsContext();

            // using material from ExamplesUtils
            URL url = ExamplesUtils.getTestScenarioURL("equil");
            URL url1 = IOUtils.extendUrl(url, "config.xml");
            Config config = ConfigUtils.loadConfig(url1);

            config.controler().setLastIteration(1);

            // using your own input files(put your own input ﬁles into utils.getXxxInputDirectory()).
            //config.controler().setOutputDirectory(utils.getOutputDirectory());
            //config.network().setInputFile(utils.getInputDirectory() + "/network.xml");

            Scenario scenario = ScenarioUtils.loadScenario(config);

            Controler controler = new Controler(scenario);

            controler.run();

            {
                // check whether the new event remains the same as before
                String eventsFileReference = utils.getInputDirectory() + "/output_event.xml.gz";
                String eventsFileActual = utils.getOutputDirectory() + "/output_events.xml.gz";
                EventsFileComparator.Result result = EventsUtils.compareEventsFiles(eventsFileReference, eventsFileActual);
                Assert.assertEquals(EventsFileComparator.Result.FILES_ARE_EQUAL, result);
            }
        } catch (Exception ee) {
            ee.printStackTrace();
            Assert.fail();
        }

    }
}