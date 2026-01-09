package agents;

import jade.core.Agent;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;

public class MonitorAgent extends Agent {

    private boolean resourceAlive = true;

    protected void setup() {
        System.out.println(getLocalName() + " lancé (monitor)");

        addBehaviour(new TickerBehaviour(this, 3000) {
            @Override
            protected void onTick() {
                ACLMessage ping = new ACLMessage(ACLMessage.REQUEST);
                ping.setContent("PING");
                ping.addReceiver(new AID("resource", AID.ISLOCALNAME));
                send(ping);

                if (!resourceAlive) {
                    System.out.println("ResourceAgent en panne !");
                    startBackup();
                    stop();
                }

                resourceAlive = false;
            }
        });

        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage msg = receive();
                if (msg != null && msg.getContent().equals("PONG")) {
                    System.out.println("Heartbeat reçu");
                    resourceAlive = true;
                } else {
                    block();
                }
            }
        });
    }

    private void startBackup() {
        try {
            getContainerController()
                .createNewAgent("backup", "agents.BackupAgent", null)
                .start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
