SUMMARY = "Mount VirtioFS in development environment"
LICENSE = "CLOSED"
LIC_FILES_CHKSUM = ""

SRC_URI = "file://devfs.service"
inherit systemd
SYSTEMD_SERVICE:${PN} = "devfs.service"
SYSTEMD_PACKAGES = "${PN}"
FILES:${PN} += "${systemd_system_unitdir}/devfs.service"

do_install () {
	install -d -m 755 ${D}${systemd_unitdir}/system
        install -m 0644 ${WORKDIR}/devfs.service ${D}${systemd_unitdir}/system/devfs.service
}
