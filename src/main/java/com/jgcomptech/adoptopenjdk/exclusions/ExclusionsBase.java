package com.jgcomptech.adoptopenjdk.exclusions;

import com.jgcomptech.adoptopenjdk.JavaName;

import java.util.LinkedHashMap;
import java.util.Map;

public class ExclusionsBase {
    protected Map<String, ExclusionsBaseRelease> releases = new LinkedHashMap<>();

    public ExclusionsBase() {
        releases.put(JavaName.Java8.getName(), new ExclusionsBaseRelease());
        releases.put(JavaName.Java9.getName(), new ExclusionsBaseRelease());
        releases.put(JavaName.Java10.getName(), new ExclusionsBaseRelease());
        releases.put(JavaName.Java11.getName(), new ExclusionsBaseRelease());
        releases.put(JavaName.Java12.getName(), new ExclusionsBaseRelease());
        releases.put(JavaName.Java13.getName(), new ExclusionsBaseRelease());
        releases.put(JavaName.Java14.getName(), new ExclusionsBaseRelease());
        releases.put(JavaName.Java15.getName(), new ExclusionsBaseRelease());
    }

    public Map<String, ExclusionsBaseRelease> getReleases() {
        return releases;
    }

    public void setReleases(Map<String, ExclusionsBaseRelease> releases) {
        this.releases = releases;
    }
}
