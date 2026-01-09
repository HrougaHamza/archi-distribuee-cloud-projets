package ma.ensaf.projet6.behaviours;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import ma.ensaf.projet6.agents.ElectionAgent;

public class ReceiveBehaviour extends CyclicBehaviour {

    public ReceiveBehaviour(ElectionAgent a) {
        super(a);
    }

    @Override
    public void action() {
        ElectionAgent a = (ElectionAgent) myAgent;
        ACLMessage msg = a.receive();

        if (msg == null) {
            block();
            return;
        }

        String content = msg.getContent();
        if (content == null) return;

        if (content.startsWith("ELECTION:")) {
            int fromId = Integer.parseInt(content.split(":")[1]);
            System.out.println(a.getLocalName() + " <- ELECTION from A" + fromId);
            a.onElectionRequest(fromId);

        } else if (content.startsWith("OK:")) {
            int fromId = Integer.parseInt(content.split(":")[1]);
            System.out.println(a.getLocalName() + " <- OK from A" + fromId);
            a.onOkFromHigher(fromId);

        } else if (content.startsWith("LEADER:")) {
            int leaderId = Integer.parseInt(content.split(":")[1]);
            System.out.println(a.getLocalName() + " <- LEADER A" + leaderId);
            a.onLeaderAnnouncement(leaderId);
        }
    }
}
