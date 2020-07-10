package com.jgcomptech.adoptopenjdk.api;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/** Returns the full version of the specified release. */
public final class Version {
    final String versionString;
    final String major;
    final String minor;
    final String revision;
    final String buildMajor;
    final String buildMinor;

    public Version(final String version) {
        this(version, false);
    }

    public Version(final String version, final boolean basic) {
        this(version, basic, false);
    }

    public Version(final String version, final boolean basic, final boolean programFiles) {
        if(version.contains("jdk8u")) {
            String temp = version.replace("jdk8u-", "").replace("jdk8u", "");

            if (temp.contains("_")) {
                temp = temp.substring(0, temp.indexOf("_"));
            }

            versionString = temp;

            if (temp.length() - temp.replaceAll("-", "").length() == 4) {
                major = versionString.substring(0, versionString.indexOf("-"));
                temp = versionString.substring(major.length() + 1);
                minor = temp.substring(0, temp.indexOf("-"));
                temp = temp.substring(minor.length() + 1);
                revision = temp.substring(0, temp.indexOf("-"));
                temp = temp.substring(revision.length() + 1);
                buildMajor = temp.substring(0, temp.indexOf("-"));
                temp = temp.substring(buildMajor.length() + 1);
                buildMinor = temp;
            } else {
                major = "8";
                minor = "0";
                revision = temp.substring(0, 3);
                temp = temp.substring(5);
                if (temp.contains(".")) {
                    buildMajor = temp.substring(0, temp.indexOf("."));
                    buildMinor = temp.substring(temp.indexOf(".") + 1);
                } else {
                    buildMajor = temp;
                    buildMinor = "0";
                }
            }
        } else if (basic) {
            versionString = version;
            String temp;
            major = versionString.substring(0, versionString.indexOf("."));
            temp = versionString.substring(major.length() + 1);
            minor = temp.substring(0, temp.indexOf("."));
            temp = temp.substring(minor.length() + 1);
            revision = temp.substring(0, temp.indexOf("."));
            temp = temp.substring(revision.length() + 1);
            if(temp.contains(".")) {
                buildMajor = temp.substring(0, temp.indexOf("."));
                buildMinor = temp.substring(temp.indexOf(".") + 1);
            } else {
                buildMajor = temp;
                buildMinor = "0";
            }
        } else if (programFiles) {
            versionString = version;
            String temp = version.replace("jdk-", "")
                    .replace("jre-", "")
                    .replace("-hotspot", "")
                    .replace("-openj9", "");
            major = temp.substring(0, temp.indexOf("."));
            temp = temp.substring(major.length() + 1);
            minor = temp.substring(0, temp.indexOf("."));
            temp = temp.substring(minor.length() + 1);
            revision = temp.substring(0, temp.indexOf("."));
            temp = temp.substring(revision.length() + 1);
            if(temp.contains(".")) {
                buildMajor = temp.substring(0, temp.indexOf("."));
                buildMinor = temp.substring(temp.indexOf(".") + 1);
            } else {
                buildMajor = temp;
                buildMinor = "0";
            }
        } else {
            String temp = version.substring(version.indexOf("-") + 1);

            if (temp.contains("_")) {
                temp = temp.substring(0, temp.indexOf("_"));
            }

            versionString = temp;

            if (temp.length() - temp.replaceAll("-", "").length() == 4) {
                major = versionString.substring(0, versionString.indexOf("-"));
                temp = versionString.substring(major.length() + 1);
                minor = temp.substring(0, temp.indexOf("-"));
                temp = temp.substring(minor.length() + 1);
                revision = temp.substring(0, temp.indexOf("-"));
                temp = temp.substring(revision.length() + 1);
                buildMajor = temp.substring(0, temp.indexOf("-"));
                temp = temp.substring(buildMajor.length() + 1);
                buildMinor = temp;
            } else {
                major = versionString.substring(0, versionString.indexOf("."));
                temp = versionString.substring(major.length() + 1);
                minor = temp.substring(0, temp.indexOf("."));
                temp = temp.substring(minor.length() + 1);
                revision = temp.substring(0, temp.indexOf("+"));
                temp = temp.substring(revision.length() + 1);
                if(temp.contains(".")) {
                    buildMajor = temp.substring(0, temp.indexOf("."));
                    buildMinor = temp.substring(temp.indexOf(".") + 1);
                } else {
                    buildMajor = temp;
                    buildMinor = "0";
                }
            }
        }
    }

    /**
     * Returns the full version of the specified release.
     * @return Full version as string
     */
    public String getMain() {
        return getVersionInfo(Type.Main);
    }

    /**
     * Returns the basic version of the specified release using the format "0.0.0.0.0".
     * @return Basic version as string
     */
    public String getBasic() {
        return major + '.' + minor + '.' + revision + '.' + buildMajor + '.' + buildMinor;
    }

    /**
     * Returns the major version of the specified release.
     * @return Major version as int
     */
    public int getMajor() { return Integer.parseInt(getVersionInfo(Type.Major)); }

    /**
     * Returns the minor version of the specified release.
     * @return Minor version as int
     */
    public int getMinor() { return Integer.parseInt(getVersionInfo(Type.Minor)); }

    /**
     * Returns the revision version of the specified release.
     * @return Build Revision as int
     */
    public int getRevision() { return Integer.parseInt(getVersionInfo(Type.Revision)); }

    /**
     * Returns the build major version of the specified release.
     * @return Build major version as int
     */
    public int getBuildMajor() { return Integer.parseInt(getVersionInfo(Type.BuildMajor)); }

    /**
     * Returns the build minor version of the specified release.
     * @return Build minor version as int
     */
    public int getBuildMinor() {
        return Integer.parseInt(getVersionInfo(Type.BuildMinor)); }

    private String getVersionInfo(final Type type) {
        switch(type) {
            case Main:
                return versionString;
            case Major:
                return major;
            case Minor:
                return minor;
            case Revision:
                return revision;
            case BuildMajor:
                return buildMajor;
            case BuildMinor:
                return buildMinor;
            default:
                return "0";
        }
    }

    public boolean isEqualTo(final Version second) {
        return getMajor() == second.getMajor()
                && getMinor() == second.getMinor()
                && getRevision() == second.getRevision()
                && getBuildMajor() == second.getBuildMajor();
    }

    public boolean isNewerThen(final Version second) {
        return isNewerThen(this, second);
    }

    public static boolean isNewerThen(final Version first, final Version second) {
        if(first.getMajor() > second.getMajor()) return true;
        else if(first.getMajor() == second.getMajor()
                && first.getMinor() > second.getMinor()) return true;
        else if(first.getMajor() == second.getMajor()
                && first.getMinor() == second.getMinor()
                && first.getRevision() > second.getRevision()) return true;
        else return first.getMajor() == second.getMajor()
                && first.getMinor() == second.getMinor()
                && first.getRevision() == second.getRevision()
                && first.getBuildMajor() > second.getBuildMajor();
        //TODO: Fix issue with BuildMinor not showing in filename or registry
//        else if(first.getMajor() == second.getMajor()
//                && first.getMinor() == second.getMinor()
//                && first.getRevision() == second.getRevision()
//                && first.getBuildMajor() > second.getBuildMajor()) return true;
//        else return first.getMajor() == second.getMajor()
//                    && first.getMinor() == second.getMinor()
//                    && first.getRevision() == second.getRevision()
//                    && first.getBuildMajor() == second.getBuildMajor()
//                    && first.getBuildMinor() > second.getBuildMinor();
    }

    public enum Type {
        Main,
        Major,
        Minor,
        Revision,
        BuildMajor,
        BuildMinor
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Version version = (Version) o;

        return new EqualsBuilder()
                .append(versionString, version.versionString)
                .append(major, version.major)
                .append(minor, version.minor)
                .append(revision, version.revision)
                .append(buildMajor, version.buildMajor)
                .append(buildMinor, version.buildMinor)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(versionString)
                .append(major)
                .append(minor)
                .append(revision)
                .append(buildMajor)
                .append(buildMinor)
                .toHashCode();
    }
}
