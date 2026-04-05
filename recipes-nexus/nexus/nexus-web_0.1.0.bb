SUMMARY = "Nexus Web UI"
HOMEPAGE = "https://github.com/chenpc/nexus-nas"
LICENSE = "CLOSED"
EDITION="2024"
inherit cargo_bin cargo-update-recipe-crates systemd useradd

# Git-based source
SRC_URI = "git://github.com/chenpc/nexus-nas.git;protocol=https;branch=master \
        git://github.com/chenpc/libnexus.git;protocol=https;branch=master;name=libnexus;destsuffix=git/libnexus;type=git-dependency \
        file://nexus-web.service \
        "
SRCREV = "e563751b99b0a335d72ac2cac195f8a3c64da54a"

SRCREV_FORMAT .= "_libnexus"
SRCREV_libnexus = "880e4665aaccb327d8289bc39235dca007d161f4"
do_compile[network] = "1"

# Cargo workspace setup
CARGO_BUILD_FLAGS += "-p nexus-web"

do_install:append() {
    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/nexus-web.service ${D}${systemd_system_unitdir}/
}

S = "${WORKDIR}/git"

DEPENDS = "protobuf-native openssl"

SYSTEMD_SERVICE:${PN} = "nexus-web.service"

# Create nexus-web user in storage-daemon group for socket access
USERADD_PACKAGES = "${PN}"
GROUPADD_PARAM:${PN} = "-r storage-daemon"
USERADD_PARAM:${PN} = "-r -s /bin/false -g storage-daemon nexus-web"
FILES:${PN} += "${systemd_system_unitdir}/nexus-web.service"

# Include crates file if it exists
include nexus-web-crates.inc
