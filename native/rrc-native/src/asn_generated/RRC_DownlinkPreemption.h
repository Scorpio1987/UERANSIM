/*
 * Generated by asn1c-0.9.29 (http://lionet.info/asn1c)
 * From ASN.1 module "NR-RRC-Definitions"
 * 	found in "asn/nr-rrc-15.6.0.asn1"
 * 	`asn1c -fcompound-names -pdu=all -findirect-choice -fno-include-deps -gen-PER -no-gen-OER -no-gen-example -D rrc`
 */

#ifndef	_RRC_DownlinkPreemption_H_
#define	_RRC_DownlinkPreemption_H_


#include <asn_application.h>

/* Including external dependencies */
#include "RRC_RNTI-Value.h"
#include <NativeEnumerated.h>
#include <NativeInteger.h>
#include <asn_SEQUENCE_OF.h>
#include <constr_SEQUENCE_OF.h>
#include <constr_SEQUENCE.h>

#ifdef __cplusplus
extern "C" {
#endif

/* Dependencies */
typedef enum RRC_DownlinkPreemption__timeFrequencySet {
	RRC_DownlinkPreemption__timeFrequencySet_set0	= 0,
	RRC_DownlinkPreemption__timeFrequencySet_set1	= 1
} e_RRC_DownlinkPreemption__timeFrequencySet;

/* Forward declarations */
struct RRC_INT_ConfigurationPerServingCell;

/* RRC_DownlinkPreemption */
typedef struct RRC_DownlinkPreemption {
	RRC_RNTI_Value_t	 int_RNTI;
	long	 timeFrequencySet;
	long	 dci_PayloadSize;
	struct RRC_DownlinkPreemption__int_ConfigurationPerServingCell {
		A_SEQUENCE_OF(struct RRC_INT_ConfigurationPerServingCell) list;
		
		/* Context for parsing across buffer boundaries */
		asn_struct_ctx_t _asn_ctx;
	} int_ConfigurationPerServingCell;
	/*
	 * This type is extensible,
	 * possible extensions are below.
	 */
	
	/* Context for parsing across buffer boundaries */
	asn_struct_ctx_t _asn_ctx;
} RRC_DownlinkPreemption_t;

/* Implementation */
/* extern asn_TYPE_descriptor_t asn_DEF_RRC_timeFrequencySet_3;	// (Use -fall-defs-global to expose) */
extern asn_TYPE_descriptor_t asn_DEF_RRC_DownlinkPreemption;
extern asn_SEQUENCE_specifics_t asn_SPC_RRC_DownlinkPreemption_specs_1;
extern asn_TYPE_member_t asn_MBR_RRC_DownlinkPreemption_1[4];

#ifdef __cplusplus
}
#endif

#endif	/* _RRC_DownlinkPreemption_H_ */
#include <asn_internal.h>