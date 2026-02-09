SUMMARY = "Nexus Test Image"
DESCRIPTION = "Minimal image with storage-daemon and integration tests for QEMU testing"

inherit core-image

IMAGE_INSTALL:append = " \
    storage-daemon \
"

IMAGE_FEATURES += "ssh-server-openssh"
EXTRA_IMAGE_FEATURES += "debug-tweaks"
