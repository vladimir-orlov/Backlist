package com.company;

import java.util.List;

public interface Command {
    void execute(List<String> params);
}
