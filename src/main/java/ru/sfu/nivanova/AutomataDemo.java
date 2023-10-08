package ru.sfu.nivanova;

import ru.sfu.nivanova.automata.Automata;
import ru.sfu.nivanova.automata.AutomataExecutionProtocol;

public class AutomataDemo {
    public static void main(String[] args) {
        AutomataExecutionProtocol protocol = new DemoProtocol();

        Automata automata = new Automata("Z");
        automata.addState("q0", true, true);
        automata.addState("q1", false, false);
        automata.addState("q2", false, true);
        automata.addState("q3", false, false);
        automata.addState("q4", false, false);

        automata.addTransition("q0", "q1", 'a', "Z", "aZ");
        automata.addTransition("q0", "q2", 'd', "", "aaaaaa");
        automata.addTransition("q0", "q4", 'b', "Z", "");

        automata.addTransition("q1", "q1", 'a', "a", "aa");
        automata.addTransition("q1", "q2", 'b', "a", "");

        automata.addTransition("q2", "q0", null, "Z", "Z");
        automata.addTransition("q2", "q2", 'b', "a", "");
        automata.addTransition("q2", "q3", 'r', "", "");

        automata.addTransition("q3", "q0", null, "Z", "Z");
        automata.addTransition("q3", "q3", null, "a", "");


        automata.execute("", protocol);
        automata.execute("a", protocol);
        automata.execute("b", protocol);
        automata.execute("d", protocol);
        automata.execute("r", protocol);
        automata.execute("ab", protocol);
        automata.execute("abd", protocol);
        automata.execute("abr", protocol);
        automata.execute("dbbbbb", protocol);
        automata.execute("dbbbbbbb", protocol);
        automata.execute("dr", protocol);
        automata.execute("", protocol);
        automata.execute("ar", protocol);
        automata.execute("br", protocol);
        automata.execute("abbbbr", protocol);
        automata.execute("aabbr", protocol);
        automata.execute("aaabbr", protocol);
        automata.execute("brrr", protocol);
        automata.execute("aabrdr", protocol);
        automata.execute("aabrd", protocol);
    }
}