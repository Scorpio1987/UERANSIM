/*
 * Generated by asn1c-0.9.29 (http://lionet.info/asn1c)
 * From ASN.1 module "NGAP-IEs"
 * 	found in "asn/NGAP-IEs.asn"
 * 	`asn1c -fcompound-names -findirect-choice -fno-include-deps -gen-PER -no-gen-OER -no-gen-example -D ngap -pdu=all`
 */

#ifndef	_NGAP_AMF_UE_NGAP_ID_H_
#define	_NGAP_AMF_UE_NGAP_ID_H_


#include <asn_application.h>

/* Including external dependencies */
#include <INTEGER.h>

#ifdef __cplusplus
extern "C" {
#endif

/* NGAP_AMF-UE-NGAP-ID */
typedef INTEGER_t	 NGAP_AMF_UE_NGAP_ID_t;

/* Implementation */
extern asn_per_constraints_t asn_PER_type_NGAP_AMF_UE_NGAP_ID_constr_1;
extern asn_TYPE_descriptor_t asn_DEF_NGAP_AMF_UE_NGAP_ID;
asn_struct_free_f NGAP_AMF_UE_NGAP_ID_free;
asn_struct_print_f NGAP_AMF_UE_NGAP_ID_print;
asn_constr_check_f NGAP_AMF_UE_NGAP_ID_constraint;
ber_type_decoder_f NGAP_AMF_UE_NGAP_ID_decode_ber;
der_type_encoder_f NGAP_AMF_UE_NGAP_ID_encode_der;
xer_type_decoder_f NGAP_AMF_UE_NGAP_ID_decode_xer;
xer_type_encoder_f NGAP_AMF_UE_NGAP_ID_encode_xer;
per_type_decoder_f NGAP_AMF_UE_NGAP_ID_decode_uper;
per_type_encoder_f NGAP_AMF_UE_NGAP_ID_encode_uper;
per_type_decoder_f NGAP_AMF_UE_NGAP_ID_decode_aper;
per_type_encoder_f NGAP_AMF_UE_NGAP_ID_encode_aper;

#ifdef __cplusplus
}
#endif

#endif	/* _NGAP_AMF_UE_NGAP_ID_H_ */
#include <asn_internal.h>
