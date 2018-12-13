package com.example.ahmed.wasetco.events;

import com.example.ahmed.wasetco.data.models.AgentModel;

public class EventAgents {
    private AgentModel agentModel;

    public EventAgents(AgentModel agentModel) {
        this.agentModel = agentModel;
    }

    public AgentModel getAgentModel() {
        return agentModel;
    }
}
