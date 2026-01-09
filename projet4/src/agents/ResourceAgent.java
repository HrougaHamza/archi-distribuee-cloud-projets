package agents;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import java.util.LinkedList;
import java.util.Queue;
public class ResourceAgent extends Agent {
    private boolean available = true;
    private Queue<ACLMessage> queue = new LinkedList<>();
    protected void setup() {
        System.out.println("--- Agent " + getLocalName() + " (Resource) pret ---");
        addBehaviour(new CyclicBehaviour() {
            public void action() {
                ACLMessage msg = receive();
                if (msg != null) {
                    String content = msg.getContent();
                    if (content.equals("RESERVE")) {
                        handleReserve(msg);
                    } else if (content.equals("RELEASE")) {
                        handleRelease();
                    }
                } else { block(); }
            }
            private void handleReserve(ACLMessage msg) {
                if (available) {
                    available = false;
                    reply(msg, "ACCEPTED");
                    System.out.println("[OK] Ressource louee a: " + msg.getSender().getLocalName());
                } else {
                    queue.add(msg); 
                    System.out.println("[WAIT] " + msg.getSender().getLocalName() + " mis en file d'attente.");
                }
            }
            private void handleRelease() {
                System.out.println("[INFO] Liberation de la ressource...");
                if (!queue.isEmpty()) {
                    ACLMessage nextMsg = queue.poll(); 
                    reply(nextMsg, "ACCEPTED");
                    System.out.println("[NEXT] Ressource passee a: " + nextMsg.getSender().getLocalName());
                } else {
                    available = true;
                    System.out.println("[FREE] Ressource totalement libre.");
                }
            }
            private void reply(ACLMessage msg, String text) {
                ACLMessage r = msg.createReply();
                r.setContent(text);
                send(r);
            }
        });
    }
}
