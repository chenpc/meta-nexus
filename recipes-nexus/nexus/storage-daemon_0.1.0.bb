SUMMARY = "Nexus Storage Daemon"
HOMEPAGE = "https://github.com/chenpc/storage-daemon"
LICENSE = "CLOSED"
EDITION="2024"

# If this is git based prefer versioned ones if they exist
# DEFAULT_PREFERENCE = "-1"

# how to get storage-daemon could be as easy as but default to a git checkout:
# SRC_URI += "crate://crates.io/storage-daemon/0.1.0"
SRC_URI = "git://github.com/chenpc/storage-daemon.git;protocol=https;branch=master \
        git://github.com/chenpc/libnexus.git;protocol=https;branch=master;name=libnexus;destsuffix=libnexus;type=git-dependency"
SRCREV = "336976f8623271af2b2064c176cd95912160438c"

SRCREV_FORMAT .= "_libnexus"
SRCREV_libnexus = "adb49f9c5433fba980b41c6efb04f13b73587bab"
do_compile[network] = "1"

S = "${WORKDIR}/git"

DEPENDS = "protobuf-native"

#SYSTEMD_SERVICE:${PN} = "storage-daemon.service"
#SYSTEMD_AUTO_ENABLE = "enable"
#FILES:${PN} += "${systemd_system_unitdir}/storage-daemon.service"

inherit cargo_bin

# includes this file if it exists but does not fail
# this is useful for anything you may want to override from
# what cargo-bitbake generates.
include storage-daemon-crates.inc
