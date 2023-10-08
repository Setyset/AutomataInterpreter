package ru.sfu.nivanova;

import ru.sfu.nivanova.automata.AutomataExecutionProtocol;
import ru.sfu.nivanova.automata.AutomataTransition;

import java.util.StringJoiner;

public class DemoProtocol implements AutomataExecutionProtocol {
    private StringJoiner statesString = new StringJoiner(" -> ");
    private StringBuilder resultString = new StringBuilder();

    @Override
    public void protocol(String stack, String currentState, String tapeCommand, AutomataTransition nextTransition) {
        statesString.add(currentState);
    }

    @Override
    public void result(boolean result) {
        resultString.append(statesString.toString()).append('\n').append(result ? "ACCEPT" : "REJECT");
        System.out.println(resultString);
        statesString = new StringJoiner(" -> ");
        resultString = new StringBuilder();
    }

    @Override
    public void init(String stack, String tape, String state) {
        resultString.append("TAPE: ").append(tape).append('\n');
    }
}
