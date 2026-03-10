SUMMARY = "Nexus Test Image"
DESCRIPTION = "Minimal image with storage-daemon and integration tests for QEMU testing"

inherit core-image

set_root_password () {
    echo "root:admin" | chpasswd -R ${IMAGE_ROOTFS}
}
ROOTFS_POSTPROCESS_COMMAND += "set_root_password; "

do_fix_samba() {
    cat > ${IMAGE_ROOTFS}/etc/samba/smb.conf << 'EOF'
[global]
   workgroup = NEXUS
   server string = Nexus NAS
   security = user
   map to guest = bad user
   restrict anonymous = 0
   passdb backend = tdbsam
   log level = 1
   max log size = 1000
   dns proxy = no
   load printers = no

include = /etc/samba/shares/*.conf
EOF
    mkdir -p ${IMAGE_ROOTFS}/etc/samba/shares/
}
ROOTFS_POSTPROCESS_COMMAND += "do_fix_samba;"


IMAGE_INSTALL:append = " \
    storage-daemon \
    nexus-web \
    devfs \
    zfs \
    samba \
    util-linux-lsblk \
"

IMAGE_FEATURES += "ssh-server-openssh"
#EXTRA_IMAGE_FEATURES += "debug-tweaks"
QB_OPT_APPEND += "-chardev socket,id=char0,path=/tmp/vm001-vhost-fs.sock -device vhost-user-fs-pci,chardev=char0,tag=myfs -object memory-backend-memfd,id=mem,size=256M,share=on -numa node,memdev=mem"
QB_SETUP_CMD += "/usr/lib/qemu/virtiofsd --socket-path=/tmp/vm001-vhost-fs.sock --shared-dir `pwd` --tag myfs  > /dev/null 2>&1 &"
