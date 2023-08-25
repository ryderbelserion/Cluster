package com.ryderbelserion.ruby.commands.args.types;

import com.ryderbelserion.ruby.commands.args.ArgumentType;

import java.util.List;

public class BooleanArgument extends ArgumentType {

    @Override
    public List<String> getPossibleValues() {
        return List.of("true", "false");
    }
}