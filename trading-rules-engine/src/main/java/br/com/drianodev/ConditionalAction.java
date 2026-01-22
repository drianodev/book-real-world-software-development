package br.com.drianodev;

public interface ConditionalAction {
    void perform(Facts facts);
    boolean evaluate(Facts facts);
}
