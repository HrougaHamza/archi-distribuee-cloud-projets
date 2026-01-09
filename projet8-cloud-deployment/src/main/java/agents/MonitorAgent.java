package agents;

import jade.core.Agent;

public class MonitorAgent extends Agent {
    @Override
    protected void setup() {
        System.out.println(getLocalName() + " lancé. Il surveille le système JADE.");
    }
}
