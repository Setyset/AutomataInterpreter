package ru.sfu.nivanova.automata;

import java.util.LinkedList;
import java.util.List;
import java.util.StringJoiner;

public class AutomataStack {
    private final LinkedList<Character> stackBody = new LinkedList<>();
    private final String initialStack;

    public AutomataStack(String initialStack) {
        this.initialStack = initialStack;
    }

    public void initStack() {
        stackBody.clear();
        push(initialStack);
    }

    public boolean comparePeek(String string) {
        if (string.length() > stackBody.size()) {
            return false;
        }
        for (int i = 0; i < string.length(); i++) {
            if (stackBody.get(i) != (string.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public String currentStack() {
        StringBuilder stringBuilder = new StringBuilder();
        stackBody.forEach(stringBuilder::append);
        return stringBuilder.toString();
    }

    private void pop(String pop) {
        for (int i = 0; i < pop.length(); i++) {
            stackBody.removeFirst();
        }
    }

    private void push(String push) {
        for (int i = push.length() - 1; i >= 0; i--) {
            stackBody.addFirst(push.charAt(i));
        }
    }

    /**
     * Performs pushdown automata stack operation.
     * @param pop string on top of the stack
     * @param push string to put in the stack instead of pop
     * @return true if pop was actually in top of the stack and operation is complete, false otherwise
     */
    public boolean stackOperation(String pop, String push) {
        if (!this.comparePeek(pop)) {
            return false;
        }
        this.pop(pop);
        this.push(push);
        return true;
    }
}
