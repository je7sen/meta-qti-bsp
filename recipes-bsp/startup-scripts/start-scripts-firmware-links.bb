DESCRIPTION = "Start up script for firmware links"
HOMEPAGE = "http://codeaurora.org"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/BSD;md5=3775480a712fc46a69647678acb234cb"
LICENSE = "BSD"

SRC_URI +="file://${BASEMACHINE}/firmware-links.sh"
SRC_URI +="file://firmware-links.service"

S = "${WORKDIR}/${BASEMACHINE}"

PR = "r5"

inherit update-rc.d

INITSCRIPT_NAME   = "firmware-links.sh"
INITSCRIPT_PARAMS = "start 37 S ."

do_install() {
    install -d ${D}/lib/firmware
    ln -s /firmware/image ${D}/lib/firmware/image
    install -m 0755 ${WORKDIR}/${BASEMACHINE}/firmware-links.sh -D ${D}${sysconfdir}/init.d/firmware-links.sh
    if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}; then
       install -d ${D}${systemd_unitdir}/system/
       install -m 0644 ${WORKDIR}/firmware-links.service -D ${D}${systemd_unitdir}/system/firmware-links.service
       install -d ${D}${systemd_unitdir}/system/sysinit.target.wants/
       # enable the service for sysinit.target
       ln -sf ${systemd_unitdir}/system/firmware-links.service \
            ${D}${systemd_unitdir}/system/sysinit.target.wants/firmware-links.service
    fi
}

pkg_postinst_${PN} () {
        update-alternatives --install ${sysconfdir}/init.d/firmware-links.sh firmware-links.sh firmware-links.sh 60
        [ -n "$D" ] && OPT="-r $D" || OPT="-s"
        # remove all rc.d-links potentially created from alternatives
        update-rc.d $OPT -f firmware-links.sh remove
        update-rc.d $OPT firmware-links.sh multiuser
}

FILES_${PN} += "/lib/firmware/*"
FILES_${PN} += "${systemd_unitdir}/system/"
