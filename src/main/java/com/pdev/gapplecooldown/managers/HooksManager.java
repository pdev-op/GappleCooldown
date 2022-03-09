package com.pdev.gapplecooldown.managers;

import java.util.HashMap;

import com.pdev.gapplecooldown.GappleCooldown;
import com.pdev.gapplecooldown.api.Hook;

public class HooksManager {
    private HashMap<String, Hook> hooks;

    public HooksManager(GappleCooldown plugin) {
        hooks = new HashMap<>();
    }

    public Hook getHook(String name) {
        if (hooks.containsKey(name)) {
            return hooks.get(name);
        }

        return null;
    }

    public void addHook(Hook hook) {
        if (hook.load()) {
            hooks.put(hook.getName(), hook);
        }
    }
}
