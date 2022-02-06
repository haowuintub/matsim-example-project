package org.matsim.advancedMATSim2122.juice;

import com.google.inject.Module;
import com.google.inject.*;

class RunSimulationWithJuice_CircularDependency {

    public static void main(String[] args) {

        Module module = new AbstractModule() {
            @Override
            protected void configure() {
                this.bind(Simulation.class).to( SimulationDefaultImpl.class );
                this.bind(Helper.class).to( HelperDefaultImpl.class );
                this.bind(Helper2.class).to( HelperDefaultImpl2.class );
            }
        };
        Injector injector = Guice.createInjector( module );
        Simulation simulation = injector.getInstance( SimulationDefaultImpl.class);

        simulation.doStep();
    }
    interface Helper{
        void help();
    }
    interface Helper2{
        void help();
    }
    interface Simulation{
        void doStep();
    }
    static class HelperDefaultImpl implements Helper{
        private Helper2 helper2;
        @Inject HelperDefaultImpl(Helper2 helper2){
            helper2.help();
            this.helper2 = helper2;
        }
        public void help(){
            System.out.println( this.getClass().getSimpleName() + "is helping");
        }
    }
    static class HelperDefaultImpl2 implements Helper2{
        private Helper helper;
        @Inject HelperDefaultImpl2(Helper helper){
            helper.help();
            this.helper = helper;
        }
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
