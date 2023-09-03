package com.ryderbelserion.cluster.api.commands.args.types;

import com.ryderbelserion.cluster.api.commands.args.ArgumentType;
import java.util.List;

public class CustomArgument extends ArgumentType {

    private final List<String> args;

    public CustomArgument(List<String> args) {
        this.args = args;
    }

    @Override
    public List<String> getPossibleValues() {
        return this.args;
    }
}