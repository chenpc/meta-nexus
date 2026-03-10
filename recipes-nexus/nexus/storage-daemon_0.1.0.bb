SUMMARY = "Nexus Storage Daemon"
HOMEPAGE = "https://github.com/chenpc/storage-daemon"
LICENSE = "CLOSED"
EDITION="2024"
inherit cargo_bin cargo-update-recipe-crates systemd

# If this is git based prefer versioned ones if they exist
# DEFAULT_PREFERENCE = "-1"

# how to get storage-daemon could be as easy as but default to a git checkout:
# SRC_URI += "crate://crates.io/storage-daemon/0.1.0"
SRC_URI = "git://github.com/chenpc/storage-daemon.git;protocol=https;branch=master \
        git://github.com/chenpc/libnexus.git;protocol=https;branch=master;name=libnexus;destsuffix=libnexus;type=git-dependency \
        file://storage-daemon.service \
        file://nexus-test.service \
        "
SRCREV = "b719afdc598dc692195d0b82ac131025988b2d80"

SRCREV_FORMAT .= "_libnexus"
SRCREV_libnexus = "880e4665aaccb327d8289bc39235dca007d161f4"
do_compile[network] = "1"

do_install:append() {
    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/storage-daemon.service ${D}${systemd_system_unitdir}/
    install -m 0644 ${WORKDIR}/nexus-test.service ${D}${systemd_system_unitdir}/
}

S = "${WORKDIR}/git"

DEPENDS = "protobuf-native"

PACKAGES =+ "nexus-test"
FILES:nexus-test = "${bindir}/nexus-test ${systemd_system_unitdir}/nexus-test.service"

SYSTEMD_PACKAGES = "${PN} nexus-test"
SYSTEMD_SERVICE:${PN} = "storage-daemon.service"
SYSTEMD_SERVICE:nexus-test = "nexus-test.service"
SYSTEMD_AUTO_ENABLE:nexus-test ?= "disable"
FILES:${PN} += "${systemd_system_unitdir}/storage-daemon.service"


# includes this file if it exists but does not fail
# this is useful for anything you may want to override from
# what cargo-bitbake generates.
include storage-daemon-crates.inc
