/*
 * Generated by asn1c-0.9.29 (http://lionet.info/asn1c)
 * From ASN.1 module "NR-RRC-Definitions"
 * 	found in "asn/nr-rrc-15.6.0.asn1"
 * 	`asn1c -fcompound-names -pdu=all -findirect-choice -fno-include-deps -gen-PER -no-gen-OER -no-gen-example -D rrc`
 */

#ifndef	_ASN_RRC_PUCCH_FormatConfig_H_
#define	_ASN_RRC_PUCCH_FormatConfig_H_


#include <asn_application.h>

/* Including external dependencies */
#include <NativeEnumerated.h>
#include "ASN_RRC_PUCCH-MaxCodeRate.h"
#include <constr_SEQUENCE.h>

#ifdef __cplusplus
extern "C" {
#endif

/* Dependencies */
typedef enum ASN_RRC_PUCCH_FormatConfig__interslotFrequencyHopping {
	ASN_RRC_PUCCH_FormatConfig__interslotFrequencyHopping_enabled	= 0
} e_ASN_RRC_PUCCH_FormatConfig__interslotFrequencyHopping;
typedef enum ASN_RRC_PUCCH_FormatConfig__additionalDMRS {
	ASN_RRC_PUCCH_FormatConfig__additionalDMRS_true	= 0
} e_ASN_RRC_PUCCH_FormatConfig__additionalDMRS;
typedef enum ASN_RRC_PUCCH_FormatConfig__nrofSlots {
	ASN_RRC_PUCCH_FormatConfig__nrofSlots_n2	= 0,
	ASN_RRC_PUCCH_FormatConfig__nrofSlots_n4	= 1,
	ASN_RRC_PUCCH_FormatConfig__nrofSlots_n8	= 2
} e_ASN_RRC_PUCCH_FormatConfig__nrofSlots;
typedef enum ASN_RRC_PUCCH_FormatConfig__pi2BPSK {
	ASN_RRC_PUCCH_FormatConfig__pi2BPSK_enabled	= 0
} e_ASN_RRC_PUCCH_FormatConfig__pi2BPSK;
typedef enum ASN_RRC_PUCCH_FormatConfig__simultaneousHARQ_ACK_CSI {
	ASN_RRC_PUCCH_FormatConfig__simultaneousHARQ_ACK_CSI_true	= 0
} e_ASN_RRC_PUCCH_FormatConfig__simultaneousHARQ_ACK_CSI;

/* ASN_RRC_PUCCH-FormatConfig */
typedef struct ASN_RRC_PUCCH_FormatConfig {
	long	*interslotFrequencyHopping;	/* OPTIONAL */
	long	*additionalDMRS;	/* OPTIONAL */
	ASN_RRC_PUCCH_MaxCodeRate_t	*maxCodeRate;	/* OPTIONAL */
	long	*nrofSlots;	/* OPTIONAL */
	long	*pi2BPSK;	/* OPTIONAL */
	long	*simultaneousHARQ_ACK_CSI;	/* OPTIONAL */
	
	/* Context for parsing across buffer boundaries */
	asn_struct_ctx_t _asn_ctx;
} ASN_RRC_PUCCH_FormatConfig_t;

/* Implementation */
/* extern asn_TYPE_descriptor_t asn_DEF_ASN_RRC_interslotFrequencyHopping_2;	// (Use -fall-defs-global to expose) */
/* extern asn_TYPE_descriptor_t asn_DEF_ASN_RRC_additionalDMRS_4;	// (Use -fall-defs-global to expose) */
/* extern asn_TYPE_descriptor_t asn_DEF_ASN_RRC_nrofSlots_7;	// (Use -fall-defs-global to expose) */
/* extern asn_TYPE_descriptor_t asn_DEF_ASN_RRC_pi2BPSK_11;	// (Use -fall-defs-global to expose) */
/* extern asn_TYPE_descriptor_t asn_DEF_ASN_RRC_simultaneousHARQ_ACK_CSI_13;	// (Use -fall-defs-global to expose) */
extern asn_TYPE_descriptor_t asn_DEF_ASN_RRC_PUCCH_FormatConfig;
extern asn_SEQUENCE_specifics_t asn_SPC_ASN_RRC_PUCCH_FormatConfig_specs_1;
extern asn_TYPE_member_t asn_MBR_ASN_RRC_PUCCH_FormatConfig_1[6];

#ifdef __cplusplus
}
#endif

#endif	/* _ASN_RRC_PUCCH_FormatConfig_H_ */
#include <asn_internal.h>
