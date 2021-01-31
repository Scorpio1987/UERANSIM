/*
 * Generated by asn1c-0.9.29 (http://lionet.info/asn1c)
 * From ASN.1 module "NR-RRC-Definitions"
 * 	found in "asn/nr-rrc-15.6.0.asn1"
 * 	`asn1c -fcompound-names -pdu=all -findirect-choice -fno-include-deps -gen-PER -no-gen-OER -no-gen-example -D rrc`
 */

#ifndef	_ASN_RRC_PTRS_DensityRecommendationDL_H_
#define	_ASN_RRC_PTRS_DensityRecommendationDL_H_


#include <asn_application.h>

/* Including external dependencies */
#include <NativeInteger.h>
#include <constr_SEQUENCE.h>

#ifdef __cplusplus
extern "C" {
#endif

/* ASN_RRC_PTRS-DensityRecommendationDL */
typedef struct ASN_RRC_PTRS_DensityRecommendationDL {
	long	 frequencyDensity1;
	long	 frequencyDensity2;
	long	 timeDensity1;
	long	 timeDensity2;
	long	 timeDensity3;
	
	/* Context for parsing across buffer boundaries */
	asn_struct_ctx_t _asn_ctx;
} ASN_RRC_PTRS_DensityRecommendationDL_t;

/* Implementation */
extern asn_TYPE_descriptor_t asn_DEF_ASN_RRC_PTRS_DensityRecommendationDL;
extern asn_SEQUENCE_specifics_t asn_SPC_ASN_RRC_PTRS_DensityRecommendationDL_specs_1;
extern asn_TYPE_member_t asn_MBR_ASN_RRC_PTRS_DensityRecommendationDL_1[5];

#ifdef __cplusplus
}
#endif

#endif	/* _ASN_RRC_PTRS_DensityRecommendationDL_H_ */
#include <asn_internal.h>