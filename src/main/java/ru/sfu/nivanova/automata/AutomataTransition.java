package ru.sfu.nivanova.automata;

import lombok.Data;

@Data
public class AutomataTransition {
    private final AutomataState stateFrom;
    private final AutomataState stateTo;
    private final Character tapeInput;
    private final String stackInput;
    private final String stackOutput;
}
