package com.leighton;

import java.util.HashMap;
import java.util.Map;

public enum Commands {
    mode,
    addtimerevent,
    removetimerevent,
    startrunback,
    stoprunback;

    private static final Map<String, Commands> nameIndex =
            new HashMap<>(Commands.values().length);
    static {
        for (Commands command : Commands.values()) {
            nameIndex.put(command.name(), command);
        }
    }
    public static Commands lookupByName(String name) {
        return nameIndex.get(name);
    }

}
