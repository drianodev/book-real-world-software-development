package br.com.drianodev;

@FunctionalInterface
public interface Action {
    void execute(Facts facts);
}
