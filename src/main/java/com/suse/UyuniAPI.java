package com.suse;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * This class offers mock implementations for the APIs in Uyuni. It does not provide real data or
 * perform any logic. When working on the integration with Uyuni, it is necessary to substitute calls to this class
 * with calls to the real APIs.
 */
public class UyuniAPI {

    private static Stream<CVEPatchStatus> listSystemsByPatchStatus(User user, String cveIdentifier) {

        return Stream.<CVEPatchStatus>builder().build();
    }


    public static class CVEPatchStatus {

        private final long systemId;
        private final String systemName;
        private final Optional<Long> errataId;
        private final String errataAdvisory;
        private final Optional<Long> packageId;
        private final Optional<String> packageName;
        private final Optional<PackageEvr> packageEvr;
        private final boolean packageInstalled;
        private final Optional<Long> channelId;
        private final String channelName;
        private final String channelLabel;
        private final boolean channelAssigned;
        private final Optional<Long> channelRank;

        CVEPatchStatus(long systemIdIn, String systemNameIn,
                       Optional<Long> errataIdIn, String errataAdvisoryIn,
                       Optional<Long> packageIdIn, Optional<String> packageNameIn,
                       Optional<PackageEvr> evrIn, boolean packageInstalledIn,
                       Optional<Long> channelIdIn, String channelNameIn,
                       String channelLabelIn, boolean channelAssignedIn,
                       Optional<Long> channelRankIn) {
            this.systemId = systemIdIn;
            this.systemName = systemNameIn;
            this.errataId = errataIdIn;
            this.errataAdvisory = errataAdvisoryIn;
            this.packageId = packageIdIn;
            this.packageName = packageNameIn;
            this.packageInstalled = packageInstalledIn;
            this.channelId = channelIdIn;
            this.channelName = channelNameIn;
            this.channelLabel = channelLabelIn;
            this.channelAssigned = channelAssignedIn;
            this.channelRank = channelRankIn;
            this.packageEvr = evrIn;
        }

        public long getSystemId() {
            return systemId;
        }

        public String getSystemName() {
            return systemName;
        }

        public Optional<Long> getErrataId() {
            return errataId;
        }

        public String getErrataAdvisory() {
            return errataAdvisory;
        }

        public Optional<Long> getPackageId() {
            return packageId;
        }

        public Optional<String> getPackageName() {
            return packageName;
        }

        public Optional<PackageEvr> getPackageEvr() {
            return packageEvr;
        }

        public boolean isPackageInstalled() {
            return packageInstalled;
        }

        public boolean isChannelAssigned() {
            return channelAssigned;
        }

        public Optional<Long> getChannelId() {
            return channelId;
        }

        public String getChannelName() {
            return channelName;
        }

        public String getChannelLabel() {
            return channelLabel;
        }

        public Optional<Long> getChannelRank() {
            return channelRank;
        }
    }

    public static class PackageEvr {

        private Long id;
        private String epoch;
        private String version;
        private String release;
        private String type;


        public PackageEvr(String epochIn, String versionIn, String releaseIn, String typeIn) {
            id = null;
            epoch = epochIn;
            version = versionIn;
            release = releaseIn;
            type = typeIn;
        }


        public String getEpoch() {
            return epoch;
        }

        public void setEpoch(String e) {
            this.epoch = e;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long i) {
            this.id = i;
        }

        public String getRelease() {
            return release;
        }

        public String getType() {
            return type;
        }

        public void setType(String t) {
            this.type = t;
        }

        public void setRelease(String r) {
            this.release = r;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String v) {
            this.version = v;
        }
    }

    public static class User {
        private static final User INSTANCE = new User();
    }

    public static enum PatchStatus {

        // Values sorted by seriousness
        AFFECTED_PATCH_INAPPLICABLE("Affected, patch available in unassigned channel", 0),
        AFFECTED_PATCH_APPLICABLE("Affected, patch available in assigned channel", 1),
        NOT_AFFECTED("Not affected", 2),
        PATCHED("Patched", 3),
        AFFECTED_PATCH_INAPPLICABLE_SUCCESSOR_PRODUCT("Affected, patch available in a Product Migration target", 4);

        /**
         * The lower the more severe
         */
        private int rank;
        private String description;

        PatchStatus(String descriptionIn, int rankIn) {
            this.description = descriptionIn;
            this.rank = rankIn;
        }

        public String getDescription() {
            return description;
        }

        public int getRank() {
            return rank;
        }
    }
}
