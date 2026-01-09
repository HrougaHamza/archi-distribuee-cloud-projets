package agents;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class ResourceAgent extends Agent {

    protected void setup() {
        System.out.println(getLocalName() + " lancé");

        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage msg = receive();
                if (msg != null) {
                    if (msg.getContent().equals("PING")) {
                        ACLMessage reply = msg.createReply();
                        reply.setContent("PONG");
                        send(reply);
                    }
                } else {
                    block();
                }
            }
        });
    }

    protected void takeDown() {
        System.out.println(getLocalName() + " arrêté (panne simulée)");
    }
}
