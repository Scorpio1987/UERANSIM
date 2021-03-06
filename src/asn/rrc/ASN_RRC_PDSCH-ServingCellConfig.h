/*
 * Generated by asn1c-0.9.29 (http://lionet.info/asn1c)
 * From ASN.1 module "NR-RRC-Definitions"
 * 	found in "asn/nr-rrc-15.6.0.asn1"
 * 	`asn1c -fcompound-names -pdu=all -findirect-choice -fno-include-deps -gen-PER -no-gen-OER -no-gen-example -D rrc`
 */

#ifndef	_ASN_RRC_PDSCH_ServingCellConfig_H_
#define	_ASN_RRC_PDSCH_ServingCellConfig_H_


#include <asn_application.h>

/* Including external dependencies */
#include <NativeEnumerated.h>
#include "ASN_RRC_ServCellIndex.h"
#include <NativeInteger.h>
#include <BOOLEAN.h>
#include <constr_SEQUENCE.h>

#ifdef __cplusplus
extern "C" {
#endif

/* Dependencies */
typedef enum ASN_RRC_PDSCH_ServingCellConfig__xOverhead {
	ASN_RRC_PDSCH_ServingCellConfig__xOverhead_xOh6	= 0,
	ASN_RRC_PDSCH_ServingCellConfig__xOverhead_xOh12	= 1,
	ASN_RRC_PDSCH_ServingCellConfig__xOverhead_xOh18	= 2
} e_ASN_RRC_PDSCH_ServingCellConfig__xOverhead;
typedef enum ASN_RRC_PDSCH_ServingCellConfig__nrofHARQ_ProcessesForPDSCH {
	ASN_RRC_PDSCH_ServingCellConfig__nrofHARQ_ProcessesForPDSCH_n2	= 0,
	ASN_RRC_PDSCH_ServingCellConfig__nrofHARQ_ProcessesForPDSCH_n4	= 1,
	ASN_RRC_PDSCH_ServingCellConfig__nrofHARQ_ProcessesForPDSCH_n6	= 2,
	ASN_RRC_PDSCH_ServingCellConfig__nrofHARQ_ProcessesForPDSCH_n10	= 3,
	ASN_RRC_PDSCH_ServingCellConfig__nrofHARQ_ProcessesForPDSCH_n12	= 4,
	ASN_RRC_PDSCH_ServingCellConfig__nrofHARQ_ProcessesForPDSCH_n16	= 5
} e_ASN_RRC_PDSCH_ServingCellConfig__nrofHARQ_ProcessesForPDSCH;

/* Forward declarations */
struct ASN_RRC_SetupRelease_PDSCH_CodeBlockGroupTransmission;

/* ASN_RRC_PDSCH-ServingCellConfig */
typedef struct ASN_RRC_PDSCH_ServingCellConfig {
	struct ASN_RRC_SetupRelease_PDSCH_CodeBlockGroupTransmission	*codeBlockGroupTransmission;	/* OPTIONAL */
	long	*xOverhead;	/* OPTIONAL */
	long	*nrofHARQ_ProcessesForPDSCH;	/* OPTIONAL */
	ASN_RRC_ServCellIndex_t	*pucch_Cell;	/* OPTIONAL */
	/*
	 * This type is extensible,
	 * possible extensions are below.
	 */
	struct ASN_RRC_PDSCH_ServingCellConfig__ext1 {
		long	*maxMIMO_Layers;	/* OPTIONAL */
		BOOLEAN_t	*processingType2Enabled;	/* OPTIONAL */
		
		/* Context for parsing across buffer boundaries */
		asn_struct_ctx_t _asn_ctx;
	} *ext1;
	
	/* Context for parsing across buffer boundaries */
	asn_struct_ctx_t _asn_ctx;
} ASN_RRC_PDSCH_ServingCellConfig_t;

/* Implementation */
/* extern asn_TYPE_descriptor_t asn_DEF_ASN_RRC_xOverhead_3;	// (Use -fall-defs-global to expose) */
/* extern asn_TYPE_descriptor_t asn_DEF_ASN_RRC_nrofHARQ_ProcessesForPDSCH_7;	// (Use -fall-defs-global to expose) */
extern asn_TYPE_descriptor_t asn_DEF_ASN_RRC_PDSCH_ServingCellConfig;
extern asn_SEQUENCE_specifics_t asn_SPC_ASN_RRC_PDSCH_ServingCellConfig_specs_1;
extern asn_TYPE_member_t asn_MBR_ASN_RRC_PDSCH_ServingCellConfig_1[5];

#ifdef __cplusplus
}
#endif

#endif	/* _ASN_RRC_PDSCH_ServingCellConfig_H_ */
#include <asn_internal.h>
