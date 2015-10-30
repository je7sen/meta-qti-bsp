FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
SRC_URI += "\
    file://index.html.lighttpd \
    file://lighttpd \
    file://lighttpd.conf \
    file://lighttpd.user \
    file://openssl.cnf \
    file://lighttpd.service \
"
DEPENDS += " openssl"
RDEPENDS_${PN} += " \
               lighttpd-module-alias \
               lighttpd-module-compress \
               lighttpd-module-cgi \
               lighttpd-module-auth \
               lighttpd-module-redirect \
               lighttpd-module-evasive \
"
EXTRA_OECONF += " \
             --with-openssl \
             --with-openssl-libs=${STAGING_LIBDIR} \
"
do_install_append() {
    install -m 0755 ${WORKDIR}/openssl.cnf ${D}${sysconfdir}
    install -m 0770 ${WORKDIR}/lighttpd.user ${D}/www/lighttpd.user
}
