package ma.ensaf.projet6.agents;

import jade.core.Agent;
import jade.core.AID;
import jade.lang.acl.ACLMessage;

import ma.ensaf.projet6.behaviours.ReceiveBehaviour;
import ma.ensaf.projet6.behaviours.StartBehaviour;

import java.util.*;

public class ElectionAgent extends Agent {

    private int id;
    private Integer leaderId = null;

    // Simple TP assumption: agents know possible IDs (exchange IDs concept)
    private final List<Integer> knownIds = Arrays.asList(1,2,3,4,5);

    private volatile boolean electionInProgress = false;
    private volatile boolean gotOkFromHigher = false;

    @Override
    protected void setup() {
        this.id = (int) getArguments()[0];
        System.out.println(getLocalName() + " started with ID=" + id);

        addBehaviour(new ReceiveBehaviour(this));

        // Start election after a small random delay (looks realistic)
        addBehaviour(new StartBehaviour(this, 1000 + new Random().nextInt(1500)));
    }

    private AID aidOf(int otherId) {
        return new AID("A" + otherId, AID.ISLOCALNAME);
    }

    private void sendMsg(int toId, String content) {
        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
        msg.addReceiver(aidOf(toId));
        msg.setContent(content);
        send(msg);
    }

    private void broadcast(String content) {
        for (int otherId : knownIds) {
            if (otherId != id) sendMsg(otherId, content);
        }
    }

    // =================== Bully Algorithm ===================

    public void startElection() {
        if (electionInProgress) return;

        electionInProgress = true;
        gotOkFromHigher = false;

        System.out.println(getLocalName() + " -> starts ELECTION");

        // 1) Send ELECTION to higher IDs
        for (int otherId : knownIds) {
            if (otherId > id) {
                sendMsg(otherId, "ELECTION:" + id);
            }
        }

        // 2) Wait; if no OK => I become leader
        addBehaviour(new jade.core.behaviours.WakerBehaviour(this, 1200) {
            @Override
            protected void onWake() {
                if (!gotOkFromHigher) {
                    declareLeader();
                } else {
                    System.out.println(getLocalName() + " -> OK received, waiting leader...");
                    electionInProgress = false;
                }
            }
        });
    }

    private void declareLeader() {
        leaderId = id;
        electionInProgress = false;

        System.out.println("***** " + getLocalName()
                + " declares itself LEADER (ID=" + id + ") *****");

        // 3) Inform everyone
        broadcast("LEADER:" + id);
    }

    // lower ID asks election => respond OK and start own election
    public void onElectionRequest(int fromId) {
        System.out.println(getLocalName() + " -> replies OK to A" + fromId);
        sendMsg(fromId, "OK:" + id);
        startElection();
    }

    public void onOkFromHigher(int fromId) {
        gotOkFromHigher = true;
    }

    public void onLeaderAnnouncement(int leader) {
        leaderId = leader;
        electionInProgress = false;
        System.out.println(getLocalName() + " -> Leader is A" + leaderId);
    }
}
