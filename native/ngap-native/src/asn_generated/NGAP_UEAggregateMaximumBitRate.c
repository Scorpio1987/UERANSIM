/*
 * Generated by asn1c-0.9.29 (http://lionet.info/asn1c)
 * From ASN.1 module "NGAP-IEs"
 * 	found in "asn/NGAP-IEs.asn"
 * 	`asn1c -fcompound-names -findirect-choice -fno-include-deps -gen-PER -no-gen-OER -no-gen-example -D ngap -pdu=all`
 */

#include "NGAP_UEAggregateMaximumBitRate.h"

#include "NGAP_ProtocolExtensionContainer.h"
asn_TYPE_member_t asn_MBR_NGAP_UEAggregateMaximumBitRate_1[] = {
	{ ATF_NOFLAGS, 0, offsetof(struct NGAP_UEAggregateMaximumBitRate, uEAggregateMaximumBitRateDL),
		(ASN_TAG_CLASS_CONTEXT | (0 << 2)),
		-1,	/* IMPLICIT tag at current level */
		&asn_DEF_NGAP_BitRate,
		0,
		{ 0, 0, 0 },
		0, 0, /* No default value */
		"uEAggregateMaximumBitRateDL"
		},
	{ ATF_NOFLAGS, 0, offsetof(struct NGAP_UEAggregateMaximumBitRate, uEAggregateMaximumBitRateUL),
		(ASN_TAG_CLASS_CONTEXT | (1 << 2)),
		-1,	/* IMPLICIT tag at current level */
		&asn_DEF_NGAP_BitRate,
		0,
		{ 0, 0, 0 },
		0, 0, /* No default value */
		"uEAggregateMaximumBitRateUL"
		},
	{ ATF_POINTER, 1, offsetof(struct NGAP_UEAggregateMaximumBitRate, iE_Extensions),
		(ASN_TAG_CLASS_CONTEXT | (2 << 2)),
		-1,	/* IMPLICIT tag at current level */
		&asn_DEF_NGAP_ProtocolExtensionContainer_176P171,
		0,
		{ 0, 0, 0 },
		0, 0, /* No default value */
		"iE-Extensions"
		},
};
static const int asn_MAP_NGAP_UEAggregateMaximumBitRate_oms_1[] = { 2 };
static const ber_tlv_tag_t asn_DEF_NGAP_UEAggregateMaximumBitRate_tags_1[] = {
	(ASN_TAG_CLASS_UNIVERSAL | (16 << 2))
};
static const asn_TYPE_tag2member_t asn_MAP_NGAP_UEAggregateMaximumBitRate_tag2el_1[] = {
    { (ASN_TAG_CLASS_CONTEXT | (0 << 2)), 0, 0, 0 }, /* uEAggregateMaximumBitRateDL */
    { (ASN_TAG_CLASS_CONTEXT | (1 << 2)), 1, 0, 0 }, /* uEAggregateMaximumBitRateUL */
    { (ASN_TAG_CLASS_CONTEXT | (2 << 2)), 2, 0, 0 } /* iE-Extensions */
};
asn_SEQUENCE_specifics_t asn_SPC_NGAP_UEAggregateMaximumBitRate_specs_1 = {
	sizeof(struct NGAP_UEAggregateMaximumBitRate),
	offsetof(struct NGAP_UEAggregateMaximumBitRate, _asn_ctx),
	asn_MAP_NGAP_UEAggregateMaximumBitRate_tag2el_1,
	3,	/* Count of tags in the map */
	asn_MAP_NGAP_UEAggregateMaximumBitRate_oms_1,	/* Optional members */
	1, 0,	/* Root/Additions */
	3,	/* First extension addition */
};
asn_TYPE_descriptor_t asn_DEF_NGAP_UEAggregateMaximumBitRate = {
	"UEAggregateMaximumBitRate",
	"UEAggregateMaximumBitRate",
	&asn_OP_SEQUENCE,
	asn_DEF_NGAP_UEAggregateMaximumBitRate_tags_1,
	sizeof(asn_DEF_NGAP_UEAggregateMaximumBitRate_tags_1)
		/sizeof(asn_DEF_NGAP_UEAggregateMaximumBitRate_tags_1[0]), /* 1 */
	asn_DEF_NGAP_UEAggregateMaximumBitRate_tags_1,	/* Same as above */
	sizeof(asn_DEF_NGAP_UEAggregateMaximumBitRate_tags_1)
		/sizeof(asn_DEF_NGAP_UEAggregateMaximumBitRate_tags_1[0]), /* 1 */
	{ 0, 0, SEQUENCE_constraint },
	asn_MBR_NGAP_UEAggregateMaximumBitRate_1,
	3,	/* Elements count */
	&asn_SPC_NGAP_UEAggregateMaximumBitRate_specs_1	/* Additional specs */
};

