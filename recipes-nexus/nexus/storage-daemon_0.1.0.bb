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
        file://storage-daemon.service"
SRCREV = "7056e4bc9c5d90b4a5af3a0e856b04f7a22b7d9a"

SRCREV_FORMAT .= "_libnexus"
SRCREV_libnexus = "13de53b3698289eba57ef5436a46bab015b8df97"
do_compile[network] = "1"

do_install:append() {
    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/storage-daemon.service ${D}${systemd_system_unitdir}/
}

S = "${WORKDIR}/git"

DEPENDS = "protobuf-native"

SYSTEMD_PACKAGES = "storage-daemon"
SYSTEMD_SERVICE:${PN} = "storage-daemon.service"
FILES:${PN} += "${systemd_system_unitdir}/storage-daemon.service"


# includes this file if it exists but does not fail
# this is useful for anything you may want to override from
# what cargo-bitbake generates.
include storage-daemon-crates.inc
