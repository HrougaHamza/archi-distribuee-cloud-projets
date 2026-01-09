package ma.ensaf.projet6;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;

public class Boot {
    public static void main(String[] args) throws Exception {
        Runtime rt = Runtime.instance();

        Profile profile = new ProfileImpl();
        profile.setParameter(Profile.GUI, "true"); // show JADE GUI

        ContainerController main = rt.createMainContainer(profile);

        // Create agents A1..A5 (highest ID should become leader)
        for (int id = 1; id <= 5; id++) {
            String name = "A" + id;
            Object[] agentArgs = new Object[]{ id };

            AgentController ac = main.createNewAgent(
                    name,
                    "ma.ensaf.projet6.agents.ElectionAgent",
                    agentArgs
            );
            ac.start();
        }
    }
}
