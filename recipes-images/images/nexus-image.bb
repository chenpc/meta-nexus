SUMMARY = "Nexus Test Image"
DESCRIPTION = "Minimal image with storage-daemon and integration tests for QEMU testing"

inherit core-image

IMAGE_INSTALL:append = " \
    storage-daemon \
    devfs \
    zfs \
    util-linux-lsblk \
"

IMAGE_FEATURES += "ssh-server-openssh"
#EXTRA_IMAGE_FEATURES += "debug-tweaks"
QB_OPT_APPEND += "-chardev socket,id=char0,path=/tmp/vm001-vhost-fs.sock -device vhost-user-fs-pci,chardev=char0,tag=myfs -object memory-backend-memfd,id=mem,size=256M,share=on -numa node,memdev=mem"
QB_SETUP_CMD += "/usr/lib/qemu/virtiofsd --socket-path=/tmp/vm001-vhost-fs.sock --shared-dir `pwd` --tag myfs  > /dev/null 2>&1 &"
