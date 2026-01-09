package agents;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import java.util.PriorityQueue;
import java.util.Comparator;

public class ResourceAgent extends Agent {
    private int clock = 0;    
    private boolean available = true;

    private PriorityQueue<ACLMessage> queue = new PriorityQueue<>(Comparator.comparingInt(
        m -> Integer.parseInt(m.getUserDefinedParameter("clock"))));

    protected void setup() {
        System.out.println("--- Agent " + getLocalName() + " (Lamport) prêt ---");
        addBehaviour(new CyclicBehaviour() {
            public void action() {
                ACLMessage msg = receive();
                if (msg != null) {
                    int msgClock = Integer.parseInt(msg.getUserDefinedParameter("clock"));
                    clock = Math.max(clock, msgClock) + 1;
                    System.out.println("Clock locale: " + clock + " | Recu de " + msg.getSender().getLocalName());

                    if (msg.getContent().equals("RESERVE")) {
                        handleReserve(msg);
                    } else if (msg.getContent().equals("RELEASE")) {
                        handleRelease();
                    }
                } else { 
                    block(); 
                }
            }

            private void handleReserve(ACLMessage msg) {
                if (available) {
                    available = false;
                    sendReply(msg, "ACCEPTED");
                } else {
                    queue.add(msg); 
                    System.out.println("Mis en file d'attente Lamport: " + msg.getSender().getLocalName());
                }
            }

            private void handleRelease() {
                if (!queue.isEmpty()) {
                    ACLMessage nextMsg = queue.poll();
                    sendReply(nextMsg, "ACCEPTED");
                } else {
                    available = true;
                }
            }

            private void sendReply(ACLMessage msg, String text) {
                clock++; 
                ACLMessage r = msg.createReply();
                r.setContent(text);
                r.addUserDefinedParameter("clock", String.valueOf(clock));
                send(r);
            }
        });
    }
}