package agents;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class ResourceAgent extends Agent {
    private boolean available = true;

    @Override
    protected void setup() {
        System.out.println(getLocalName() + " lancé.");

        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage msg = receive();
                if (msg != null) {
                    if ("RESERVE".equals(msg.getContent())) {
                        ACLMessage reply = msg.createReply();
                        if (available) {
                            available = false;
                            reply.setContent("ACCEPTED");
                        } else {
                            reply.setContent("REFUSED");
                        }
                        send(reply);
                        System.out.println(getLocalName() + " répond à " + msg.getSender().getLocalName() + " : " + reply.getContent());
                    } else if ("RELEASE".equals(msg.getContent())) {
                        available = true;
                        System.out.println(getLocalName() + " : ressource libérée par " + msg.getSender().getLocalName());
                    }
                } else {
                    block();
                }
            }
        });
    }
}
