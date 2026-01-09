package agents;
import jade.core.Agent;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class StudentAgent extends Agent {
    protected void setup() {
        addBehaviour(new OneShotBehaviour() {
            public void action() {
                ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
                msg.addReceiver(new AID("resource", AID.ISLOCALNAME));
                msg.setContent("RESERVE");
                send(msg);
                System.out.println(getLocalName() + " a envoye RESERVE");
            }
        });
    }
}
