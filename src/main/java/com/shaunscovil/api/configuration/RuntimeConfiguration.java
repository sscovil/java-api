package com.shaunscovil.api.configuration;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.Option;

import java.util.ArrayList;
import java.util.List;

public class RuntimeConfiguration {

    @Option(name = "-config", usage = "Relative path to the API config file")
    public String apiConfigFilePath = "configuration/dev.api.config.yaml";

    @Argument
    public List<String> arguments = new ArrayList<>();

}
