DESCRIPTION = "hardware libhardware headers"
HOMEPAGE = "http://codeaurora.org/"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://NOTICE;md5=9645f39e9db895a4aa6e02cb57294595"

PR = "r3"

SRC_URI = "file://${WORKSPACE}/hardware/libhardware/"
SRC_URI += "file://autotools.patch"

S = "${WORKDIR}/${PN}"

DEPENDS = "system-core"

inherit autotools

do_install_append () {
        install -d ${D}${includedir}
        install -m 0644 ${S}/include/hardware/gps.h -D ${D}${includedir}/hardware/gps.h
        install -m 0644 ${S}/include/hardware/hardware.h -D ${D}${includedir}/hardware/hardware.h
        install -m 0644 ${S}/include/hardware/gralloc.h -D ${D}${includedir}/hardware/gralloc.h
}