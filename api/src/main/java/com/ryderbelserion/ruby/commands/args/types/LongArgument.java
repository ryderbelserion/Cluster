package com.ryderbelserion.ruby.commands.args.types;

import com.ryderbelserion.ruby.commands.args.ArgumentType;

import java.util.ArrayList;
import java.util.List;

public class LongArgument extends ArgumentType {

    private final long cap;

    public LongArgument(long cap) {
        if (cap <= 0L) {
            this.cap = 100L;
            return;
        }

        this.cap = cap;
    }

    @Override
    public List<String> getPossibleValues() {
        List<String> numbers = new ArrayList<>();

        for (float value = 1L; value <= this.cap; value += 1L) {
            numbers.add(String.valueOf(value));
        }

        return numbers;
    }
}