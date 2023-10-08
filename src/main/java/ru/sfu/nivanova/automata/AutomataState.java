package ru.sfu.nivanova.automata;

import lombok.Data;

@Data
public class AutomataState {
    private final String name;
    private final boolean isInitial;
    private final boolean isFinal;
}
