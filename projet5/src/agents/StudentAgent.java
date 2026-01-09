package agents;

import jade.core.Agent;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class StudentAgent extends Agent {
    private int clock = (int)(Math.random() * 10); 

    protected void setup() {
        addBehaviour(new OneShotBehaviour() {
            public void action() {
                clock++; 
                ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
                msg.addReceiver(new AID("resource", AID.ISLOCALNAME));
                msg.setContent("RESERVE");
                
                msg.addUserDefinedParameter("clock", String.valueOf(clock));
                
                send(msg);
                System.out.println(getLocalName() + " envoie RESERVE avec Clock=" + clock);
            }
        });
    }
}