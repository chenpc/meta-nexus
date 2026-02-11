# Example content for linux-yocto_%.bbappend
SUMMARY = "Changes to the Linux kernel configuration."
FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI += "file://fragment.cfg"
