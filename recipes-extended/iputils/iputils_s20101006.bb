SUMMARY = "Network monitoring tools"
DESCRIPTION = "Utilities for the IP protocol, including traceroute6, \
tracepath, tracepath6, ping, ping6 and arping."
HOMEPAGE = "http://www.skbuff.net/iputils"
SECTION = "console/network"

LICENSE = "BSD & GPLv2+"

LIC_FILES_CHKSUM = "file://ping.c;beginline=1;endline=35;md5=f9ceb201733e9a6cf8f00766dd278d82 \
                    file://tracepath.c;beginline=1;endline=10;md5=0ecea2bf60bff2f3d840096d87647f3d \
                    file://arping.c;beginline=1;endline=10;md5=ada2a6d06acc90f943bddf40d15e0541 \
                    file://tftpd.c;beginline=1;endline=32;md5=28834bf8a91a5b8a92755dbee709ef96 "

DEPENDS = "openssl docbook-utils-native sgmlspl-native"

PR = "r6"

SRC_URI = "http://www.skbuff.net/iputils/${BPN}-${PV}.tar.bz2 \
           file://debian/fix-dead-host-ping-stats.diff \
           file://debian/add-icmp-return-codes.diff \
           file://debian/use_gethostbyname2.diff \
           file://debian/targets.diff \
           file://debian/fix-arping-timeouts.diff \
           file://nsgmls-path-fix.patch \
           file://arping-break-libsysfs-dependency.patch \
          "

SRC_URI[md5sum] = "a36c25e9ec17e48be514dc0485e7376c"
SRC_URI[sha256sum] = "fd3af46c80ebb99607c2ca1f2a3608b6fe828e25bbec6e54f2afd25f6ddb6ee7"

do_compile () {
	oe_runmake 'CC=${CC} -D_GNU_SOURCE' VPATH="${STAGING_LIBDIR}:${STAGING_DIR_HOST}/${base_libdir}" all man
}

do_install () {
	install -m 0755 -d ${D}${base_bindir} ${D}${mandir}/man8
	# SUID root programs
	install -m 4555 ping ${D}${base_bindir}/ping
	install -m 4555 ping6 ${D}${base_bindir}/ping6
	# Manual pages for things we build packages for
	for i in ping.8; do
	  install -m 0644 doc/$i ${D}${mandir}/man8/ || true
	done
}

inherit update-alternatives

ALTERNATIVE_PRIORITY = "100"

ALTERNATIVE_${PN}-ping = "ping"
ALTERNATIVE_LINK_NAME[ping] = "${base_bindir}/ping"

ALTERNATIVE_${PN}-ping6 = "ping6"
ALTERNATIVE_LINK_NAME[ping6] = "${base_bindir}/ping6"

PACKAGES += "${PN}-ping ${PN}-ping6"

ALLOW_EMPTY_${PN} = "1"
RDEPENDS_${PN} += "${PN}-ping ${PN}-ping6"

FILES_${PN}	= ""
FILES_${PN}-ping = "${base_bindir}/ping.${BPN}"
FILES_${PN}-ping6 = "${base_bindir}/ping6.${BPN}"
FILES_${PN}-doc	= "${mandir}/man8"
