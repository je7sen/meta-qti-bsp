inherit autotools-brokensep pkgconfig

DESCRIPTION = "Bluetooth Fluoride Stack"
HOMEPAGE = "http://codeaurora.org/"
LICENSE = "Apache-2.0"

LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/\
${LICENSE};md5=89aea4e17d99a7cacdbeed46a0096b10"

DEPENDS = "common zlib libhardware libbt-vendor"

FILESPATH =+ "${WORKSPACE}:"
SRC_URI = "file://system/bt/"

S = "${WORKDIR}/system/bt/"

FILES_${PN} += "${libdir}"
INSANE_SKIP_${PN} = "dev-so"

CFLAGS_append = " -DHCI_USE_MCT"

EXTRA_OECONF = " \
                --with-zlib \
                --with-common-includes="${WORKSPACE}/hardware/libhardware/include" \
                --with-lib-path=${STAGING_LIBDIR} \
               "
do_install_append() {

	install -d ${D}${sysconfdir}/bluetooth/

	cd  ${D}/${libdir}/ && ln -s libbluetoothdefault.so.0 bluetooth.default.so

	if [ -f ${S}conf/auto_pair_devlist.conf ]; then
	   install -m 0660 ${S}conf/auto_pair_devlist.conf ${D}${sysconfdir}/bluetooth/
	fi

	if [ -f ${S}conf/bt_did.conf ]; then
	   install -m 0660 ${S}conf/bt_did.conf ${D}${sysconfdir}/bluetooth/
	fi

	if [ -f ${S}conf/bt_stack.conf ]; then
	   install -m 0660 ${S}conf/bt_stack.conf ${D}${sysconfdir}/bluetooth/
	fi

	if [ -f ${S}conf/iot_devlist.conf ]; then
	   install -m 0660 ${S}conf/iot_devlist.conf ${D}${sysconfdir}/bluetooth/
	fi
}
