package com.repsy.package_manager.dto;

import java.util.List;

import lombok.Data;

@Data
public class MetaJsonDTO {
    private String name;
    private String version;
    private String author;
    private List<Dependency> dependencies;

    @Data
    public static class Dependency {
        private String packageName;
        private String version;
    }
}
