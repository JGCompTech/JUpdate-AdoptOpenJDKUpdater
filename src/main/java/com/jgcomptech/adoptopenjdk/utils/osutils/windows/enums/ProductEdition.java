package com.jgcomptech.adoptopenjdk.utils.osutils.windows.enums;

import com.jgcomptech.adoptopenjdk.utils.info.enums.BaseEnum;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Arrays;

/**
 * A list of Product Editions according to
 * <a href="http://msdn.microsoft.com/en-us/library/ms724358(VS.85).aspx">Microsoft Documentation</a>.
 */
public enum ProductEdition implements BaseEnum {
    /** Business. */
    Business(6, "Business"),
    /** BusinessN. */
    BusinessN(16, "Business N"),
    /** ClusterServer. */
    ClusterServer(18, "Cluster Server"),
    /** DatacenterServer. */
    DatacenterServer(8, "Datacenter"),
    /** DatacenterServerCore. */
    DatacenterServerCore(12, "Datacenter (Core installation)"),
    /** DatacenterServerCoreV. */
    DatacenterServerCoreV(39, "Datacenter without Hyper-V (Core installation)"),
    /** DatacenterServerV. */
    DatacenterServerV(37, "Datacenter without Hyper-V"),

    //DeveloperPreview(74),
    /** Enterprise. */
    Enterprise(4, "Enterprise"),

    /** EnterpriseE. */
    EnterpriseE(70, "Enterprise E"),
    /** EnterpriseN. */
    EnterpriseN(27, "Enterprise N"),
    /** EnterpriseServer. */
    EnterpriseServer(10, "Enterprise Server"),
    /** EnterpriseServerCore. */
    EnterpriseServerCore(14, "Enterprise (Core installation)"),
    /** EnterpriseServerCoreV. */
    EnterpriseServerCoreV(41, "Enterprise without Hyper-V (Core installation)"),
    /** EnterpriseServerIA64. */
    EnterpriseServerIA64(15, "Enterprise For Itanium-based Systems"),
    /** EnterpriseServerV. */
    EnterpriseServerV(38, "Enterprise without Hyper-V"),
    /** HomeBasic. */
    HomeBasic(2, "Home Basic"),
    /** HomeBasicE. */
    HomeBasicE(67, "Home Basic E"),
    /** HomeBasicN. */
    HomeBasicN(5, "Home Basic N"),
    /** HomePremium. */
    HomePremium(3, "Home Premium"),
    /** HomePremiumE. */
    HomePremiumE(68, "Home Premium E"),
    /** HomePremiumN. */
    HomePremiumN(26, "Home Premium N"),

    //HomePremiumServer(34),
    //HyperV(42, "HyperV"),
    /** MediumBusinessServerManagement. */
    MediumBusinessServerManagement(30, "Windows Essential Business Management"),

    /** MediumBusinessServerMessaging. */
    MediumBusinessServerMessaging(32, "Windows Essential Business Messaging"),
    /** MediumBusinessServerSecurity. */
    MediumBusinessServerSecurity(31, "Windows Essential Business Security"),
    /** Professional. */
    Professional(48, "Professional"),
    /** ProfessionalE. */
    ProfessionalE(69, "Professional E"),
    /** ProfessionalN. */
    ProfessionalN(49, "Professional N"),

    //SBSolutionServer(50),
    /** ServerForSmallBusiness. */
    ServerForSmallBusiness(24, "Windows Essential Server Solutions"),

    /** ServerForSmallBusinessV. */
    ServerForSmallBusinessV(35, "Windows Essential Server Solutions without Hyper-V"),

    //ServerFoundation(33),
    /** SmallBusinessServer. */
    SmallBusinessServer(9, "Small Business Server"),

    //SmallBusinessServerPremium(25),
    //SolutionEmbeddedServer(56),
    /** StandardServer. */
    StandardServer(7, "Standard"),

    /** StandardServerCore. */
    StandardServerCore(13, "Standard (Core installation)"),
    /** StandardServerCoreV. */
    StandardServerCoreV(40, "Standard without Hyper-V (Core installation)"),
    /** StandardServerV. */
    StandardServerV(36, "Standard without Hyper-V"),
    /** Starter. */
    Starter(11, "Starter"),
    /** StarterE. */
    StarterE(66, "Starter E"),
    /** StarterN. */
    StarterN(47, "Starter N"),
    /** StorageEnterpriseServer. */
    StorageEnterpriseServer(23, "Storage Enterprise"),
    /** StorageExpressServer. */
    StorageExpressServer(20, "Storage Express"),
    /** StorageStandardServer. */
    StorageStandardServer(21, "Storage Standard"),
    /** StorageWorkgroupServer. */
    StorageWorkgroupServer(22, "Storage Workgroup"),
    /** Undefined. */
    Undefined(0,"Unknown Product"),
    /** Ultimate. */
    Ultimate(1,"Ultimate"),
    /** UltimateE. */
    UltimateE(71,"Ultimate E"),
    /** UltimateN. */
    UltimateN(28,"Ultimate N"),
    /** WebServer. */
    WebServer(17, "Web Server"),
    /** WebServerCore. */
    WebServerCore(29, "Web (Core installation)");

    private final int value;
    private final String fullName;

    ProductEdition(final int value, final String fullName) {
        this.fullName = fullName;
        this.value = value;
    }

    public String getFullName() {
        return fullName;
    }

    public static ProductEdition parse(final int value) {
        return Arrays.stream(ProductEdition.values())
                .filter(type -> type.value == value)
                .findFirst()
                .orElse(Undefined);
    }

    public static ProductEdition parseString(final String name) {
        return Arrays.stream(ProductEdition.values())
                .filter(type -> type.fullName.equalsIgnoreCase(name))
                .findFirst()
                .orElse(Undefined);
    }

    @Override
    public int getValue() { return value; }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("value", value)
                .append("fullName", fullName)
                .toString();
    }
}
