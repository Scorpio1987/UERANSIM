/*
 * Generated by asn1c-0.9.29 (http://lionet.info/asn1c)
 * From ASN.1 module "NR-RRC-Definitions"
 * 	found in "asn/nr-rrc-15.6.0.asn1"
 * 	`asn1c -fcompound-names -pdu=all -findirect-choice -fno-include-deps -gen-PER -no-gen-OER -no-gen-example -D rrc`
 */

#ifndef	_ASN_RRC_PhysicalCellGroupConfig_H_
#define	_ASN_RRC_PhysicalCellGroupConfig_H_


#include <asn_application.h>

/* Including external dependencies */
#include <NativeEnumerated.h>
#include "ASN_RRC_P-Max.h"
#include "ASN_RRC_RNTI-Value.h"
#include <constr_SEQUENCE.h>

#ifdef __cplusplus
extern "C" {
#endif

/* Dependencies */
typedef enum ASN_RRC_PhysicalCellGroupConfig__harq_ACK_SpatialBundlingPUCCH {
	ASN_RRC_PhysicalCellGroupConfig__harq_ACK_SpatialBundlingPUCCH_true	= 0
} e_ASN_RRC_PhysicalCellGroupConfig__harq_ACK_SpatialBundlingPUCCH;
typedef enum ASN_RRC_PhysicalCellGroupConfig__harq_ACK_SpatialBundlingPUSCH {
	ASN_RRC_PhysicalCellGroupConfig__harq_ACK_SpatialBundlingPUSCH_true	= 0
} e_ASN_RRC_PhysicalCellGroupConfig__harq_ACK_SpatialBundlingPUSCH;
typedef enum ASN_RRC_PhysicalCellGroupConfig__pdsch_HARQ_ACK_Codebook {
	ASN_RRC_PhysicalCellGroupConfig__pdsch_HARQ_ACK_Codebook_semiStatic	= 0,
	ASN_RRC_PhysicalCellGroupConfig__pdsch_HARQ_ACK_Codebook_dynamic	= 1
} e_ASN_RRC_PhysicalCellGroupConfig__pdsch_HARQ_ACK_Codebook;
typedef enum ASN_RRC_PhysicalCellGroupConfig__ext2__xScale {
	ASN_RRC_PhysicalCellGroupConfig__ext2__xScale_dB0	= 0,
	ASN_RRC_PhysicalCellGroupConfig__ext2__xScale_dB6	= 1,
	ASN_RRC_PhysicalCellGroupConfig__ext2__xScale_spare2	= 2,
	ASN_RRC_PhysicalCellGroupConfig__ext2__xScale_spare1	= 3
} e_ASN_RRC_PhysicalCellGroupConfig__ext2__xScale;

/* Forward declarations */
struct ASN_RRC_SetupRelease_RNTI_Value;
struct ASN_RRC_SetupRelease_PDCCH_BlindDetection;

/* ASN_RRC_PhysicalCellGroupConfig */
typedef struct ASN_RRC_PhysicalCellGroupConfig {
	long	*harq_ACK_SpatialBundlingPUCCH;	/* OPTIONAL */
	long	*harq_ACK_SpatialBundlingPUSCH;	/* OPTIONAL */
	ASN_RRC_P_Max_t	*p_NR_FR1;	/* OPTIONAL */
	long	 pdsch_HARQ_ACK_Codebook;
	ASN_RRC_RNTI_Value_t	*tpc_SRS_RNTI;	/* OPTIONAL */
	ASN_RRC_RNTI_Value_t	*tpc_PUCCH_RNTI;	/* OPTIONAL */
	ASN_RRC_RNTI_Value_t	*tpc_PUSCH_RNTI;	/* OPTIONAL */
	ASN_RRC_RNTI_Value_t	*sp_CSI_RNTI;	/* OPTIONAL */
	struct ASN_RRC_SetupRelease_RNTI_Value	*cs_RNTI;	/* OPTIONAL */
	/*
	 * This type is extensible,
	 * possible extensions are below.
	 */
	struct ASN_RRC_PhysicalCellGroupConfig__ext1 {
		ASN_RRC_RNTI_Value_t	*mcs_C_RNTI;	/* OPTIONAL */
		ASN_RRC_P_Max_t	*p_UE_FR1;	/* OPTIONAL */
		
		/* Context for parsing across buffer boundaries */
		asn_struct_ctx_t _asn_ctx;
	} *ext1;
	struct ASN_RRC_PhysicalCellGroupConfig__ext2 {
		long	*xScale;	/* OPTIONAL */
		
		/* Context for parsing across buffer boundaries */
		asn_struct_ctx_t _asn_ctx;
	} *ext2;
	struct ASN_RRC_PhysicalCellGroupConfig__ext3 {
		struct ASN_RRC_SetupRelease_PDCCH_BlindDetection	*pdcch_BlindDetection;	/* OPTIONAL */
		
		/* Context for parsing across buffer boundaries */
		asn_struct_ctx_t _asn_ctx;
	} *ext3;
	
	/* Context for parsing across buffer boundaries */
	asn_struct_ctx_t _asn_ctx;
} ASN_RRC_PhysicalCellGroupConfig_t;

/* Implementation */
/* extern asn_TYPE_descriptor_t asn_DEF_ASN_RRC_harq_ACK_SpatialBundlingPUCCH_2;	// (Use -fall-defs-global to expose) */
/* extern asn_TYPE_descriptor_t asn_DEF_ASN_RRC_harq_ACK_SpatialBundlingPUSCH_4;	// (Use -fall-defs-global to expose) */
/* extern asn_TYPE_descriptor_t asn_DEF_ASN_RRC_pdsch_HARQ_ACK_Codebook_7;	// (Use -fall-defs-global to expose) */
/* extern asn_TYPE_descriptor_t asn_DEF_ASN_RRC_xScale_20;	// (Use -fall-defs-global to expose) */
extern asn_TYPE_descriptor_t asn_DEF_ASN_RRC_PhysicalCellGroupConfig;
extern asn_SEQUENCE_specifics_t asn_SPC_ASN_RRC_PhysicalCellGroupConfig_specs_1;
extern asn_TYPE_member_t asn_MBR_ASN_RRC_PhysicalCellGroupConfig_1[12];

#ifdef __cplusplus
}
#endif

#endif	/* _ASN_RRC_PhysicalCellGroupConfig_H_ */
#include <asn_internal.h>
