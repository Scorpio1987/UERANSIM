/*
 * Generated by asn1c-0.9.29 (http://lionet.info/asn1c)
 * From ASN.1 module "NGAP-PDU-Contents"
 * 	found in "asn/NGAP-PDU-Contents.asn"
 * 	`asn1c -fcompound-names -findirect-choice -fno-include-deps -gen-PER -no-gen-OER -no-gen-example -D ngap -pdu=all`
 */

#ifndef	_NGAP_HandoverPreparationFailure_H_
#define	_NGAP_HandoverPreparationFailure_H_


#include <asn_application.h>

/* Including external dependencies */
#include "NGAP_ProtocolIE-Container.h"
#include <constr_SEQUENCE.h>

#ifdef __cplusplus
extern "C" {
#endif

/* NGAP_HandoverPreparationFailure */
typedef struct NGAP_HandoverPreparationFailure {
	NGAP_ProtocolIE_Container_125P23_t	 protocolIEs;
	/*
	 * This type is extensible,
	 * possible extensions are below.
	 */
	
	/* Context for parsing across buffer boundaries */
	asn_struct_ctx_t _asn_ctx;
} NGAP_HandoverPreparationFailure_t;

/* Implementation */
extern asn_TYPE_descriptor_t asn_DEF_NGAP_HandoverPreparationFailure;
extern asn_SEQUENCE_specifics_t asn_SPC_NGAP_HandoverPreparationFailure_specs_1;
extern asn_TYPE_member_t asn_MBR_NGAP_HandoverPreparationFailure_1[1];

#ifdef __cplusplus
}
#endif

#endif	/* _NGAP_HandoverPreparationFailure_H_ */
#include <asn_internal.h>
