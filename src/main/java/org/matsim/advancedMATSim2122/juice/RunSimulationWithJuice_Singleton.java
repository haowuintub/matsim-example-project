package org.matsim.advancedMATSim2122.juice;

import com.google.inject.Module;
import com.google.inject.*;

class RunSimulationWithJuice_Singleton {

    public static void main(String[] args) {

        Module module = new AbstractModule() {
            @Override
            protected void configure() {
                this.bind(Simulation.class).to( SimulationDefaultImpl.class );
                //bind to class
                this.bind(Helper.class).to( HelperDefaultImpl.class );
                //bind to singleton instance
                this.bind(Helper.class).to( HelperDefaultImpl.class ).asEagerSingleton(); //also add 'eager' attribute
                this.bind(Helper.class).to( HelperDefaultImpl.class ).in(Singleton.class);
                //bind to instance
                Helper instance = new HelperDefaultImpl();
                this.bind(Helper.class).toInstance(instance);
            }
        };
        Injector injector = Guice.createInjector( module );
        Simulation simulation = injector.getInstance( SimulationDefaultImpl.class);

        simulation.doStep();
    }
    interface Helper{
        void help();
    }
    interface Simulation{
        void doStep();
    }
    @Singleton //designer decides this class must to be a 'singleton' class
    static class HelperDefaultImpl implements Helper{
        public void help(){
            System.out.println( this.getClass().getSimpleName() + "is helping");
        }
    }
    static class SimulationDefaultImpl implements Simulation{
        private final Helper helper;
        @Inject SimulationDefaultImpl( Helper helper){
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
