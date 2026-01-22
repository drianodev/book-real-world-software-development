package br.com.drianodev;

@FunctionalInterface
public interface Condition {
    boolean evaluate(Facts facts);
}