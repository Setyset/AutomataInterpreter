package ru.sfu.nivanova.automata;

import java.util.ArrayList;
import java.util.List;

public class Automata {
    private final List<AutomataState> states = new ArrayList<>();
    private final List<AutomataTransition> transitions = new ArrayList<>();
    private final AutomataStack stack;
    private AutomataState initialState = null;
    private AutomataState currentState = null;

    public Automata(String initialStack) {
        this.stack = new AutomataStack(initialStack);
    }

    public void addState(String stateName, boolean isInitial, boolean isFinal) {
        if (this.states.stream().anyMatch(state ->
                state.getName().equals(stateName) || isInitial && this.initialState != null)) {
            throw new RuntimeException("State with such name is present!");
        }
        AutomataState newState = new AutomataState(stateName, isInitial, isFinal);
        this.states.add(newState);
        if (newState.isInitial()) {
            this.initialState = newState;
        }

    }

    public void addTransition(String stateFromName, String stateToName, Character tapeInput,
                              String stackInput, String stackOutput) {
        AutomataState stateFrom = this.states.stream()
                .filter(state -> state.getName().equals(stateFromName)).findFirst()
                .orElseThrow(() -> new RuntimeException("No state with name " + stateFromName));
        AutomataState stateTo = this.states.stream()
                .filter(state -> state.getName().equals(stateToName)).findFirst()
                .orElseThrow(() -> new RuntimeException("No state with name" + stateToName));
        this.transitions.add(new AutomataTransition(stateFrom, stateTo, tapeInput, stackInput,
                stackOutput));
    }

    public boolean execute(String tape, AutomataExecutionProtocol protocol) {
        if (initialState == null) {
            throw new RuntimeException("No initial state!");
        }
        currentState = initialState;
        stack.initStack();
        int commandIndex = 0;
        if (protocol != null) {
            protocol.init(stack.currentStack(), tape, currentState.getName());
        }

        boolean lambdaTransiction = false;

        do {

            Character command = commandIndex < tape.toCharArray().length
                    ? tape.toCharArray()[commandIndex]
                    : null;
            List<AutomataTransition> possibleTransitions = transitions.stream()
                    .filter(transition -> transition.getStateFrom() == currentState
                            && (transition.getTapeInput() == null || transition.getTapeInput() == command)
                            && stack.comparePeek(transition.getStackInput())
                    ).toList();

            if (possibleTransitions.size() > 1) {
                possibleTransitions = possibleTransitions.stream()
                        .filter(transition -> transition.getTapeInput() == null)
                        .toList();
                if (possibleTransitions.size() > 1) {
                    throw new RuntimeException("More than one possible transition, automata is supposed to be determined!");
                }
            }
            if (possibleTransitions.isEmpty()) {
                break;
            }
            AutomataTransition next = possibleTransitions.get(0);
            if (next.getTapeInput() == null) {
                lambdaTransiction = true;
            }
            if (protocol != null) {
                protocol.protocol(stack.currentStack(), currentState.getName(), String.valueOf(command), next);
            }
            stack.stackOperation(next.getStackInput(), next.getStackOutput());
            currentState = next.getStateTo();
            commandIndex++;

        } while (lambdaTransiction || commandIndex < tape.length() ||
                transitions.stream().anyMatch(transitions ->
                        transitions.getTapeInput() == null && transitions.getStateFrom() == currentState && stack.comparePeek(transitions.getStackInput())));
        boolean result = currentState.isFinal() && commandIndex >= tape.length();

        if (protocol != null) {
            protocol.result(result);
        }
        return result;
    }
}
