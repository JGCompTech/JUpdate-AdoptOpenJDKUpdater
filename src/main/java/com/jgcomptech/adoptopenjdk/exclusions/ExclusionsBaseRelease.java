package com.jgcomptech.adoptopenjdk.exclusions;

public class ExclusionsBaseRelease {
    protected ExclusionsSubRelease JDKHotspot = new ExclusionsSubRelease();

    protected ExclusionsSubRelease JREHotspot = new ExclusionsSubRelease();

    protected ExclusionsSubRelease JDKOpenJ9 = new ExclusionsSubRelease();

    protected ExclusionsSubRelease JREOpenJ9 = new ExclusionsSubRelease();

    public ExclusionsSubRelease getJDKHotspot() {
        return JDKHotspot;
    }

    public void setJDKHotspot(ExclusionsSubRelease JDKHotspot) {
        this.JDKHotspot = JDKHotspot;
    }

    public ExclusionsSubRelease getJREHotspot() {
        return JREHotspot;
    }

    public void setJREHotspot(ExclusionsSubRelease JREHotspot) {
        this.JREHotspot = JREHotspot;
    }

    public ExclusionsSubRelease getJDKOpenJ9() {
        return JDKOpenJ9;
    }

    public void setJDKOpenJ9(ExclusionsSubRelease JDKOpenJ9) {
        this.JDKOpenJ9 = JDKOpenJ9;
    }

    public ExclusionsSubRelease getJREOpenJ9() {
        return JREOpenJ9;
    }

    public void setJREOpenJ9(ExclusionsSubRelease JREOpenJ9) {
        this.JREOpenJ9 = JREOpenJ9;
    }
}
