package com.ryderbelserion.ruby.other.builder.commands.args.types;

import com.ryderbelserion.ruby.other.builder.commands.args.ArgumentType;
import java.util.List;

public class BooleanArgument extends ArgumentType {

    @Override
    public List<String> getPossibleValues() {
        return List.of("true", "false");
    }
}