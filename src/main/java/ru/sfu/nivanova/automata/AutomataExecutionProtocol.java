package ru.sfu.nivanova.automata;

public interface AutomataExecutionProtocol {
    void protocol(String stack, String currentState, String tapeCommand, AutomataTransition nextTransition);
    void result(boolean result);
    void init(String stack, String tape, String state);
}
