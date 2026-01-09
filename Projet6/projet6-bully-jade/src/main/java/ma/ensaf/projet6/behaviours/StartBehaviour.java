package ma.ensaf.projet6.behaviours;

import jade.core.behaviours.WakerBehaviour;
import ma.ensaf.projet6.agents.ElectionAgent;

public class StartBehaviour extends WakerBehaviour {
    public StartBehaviour(ElectionAgent a, long timeout) {
        super(a, timeout);
    }

    @Override
    protected void onWake() {
        ((ElectionAgent) myAgent).startElection();
    }
}
