package com.runsim.backend.nas.impl.ies;

import com.runsim.backend.nas.core.ProtocolEnum;
import com.runsim.backend.nas.core.ies.InformationElement1;
import com.runsim.backend.utils.bits.Bit4;

public class IESmsIndication extends InformationElement1 {
    public ESmsAvailabilityIndication sai;

    @Override
    public IESmsIndication decodeIE1(Bit4 value) {
        var res = new IESmsIndication();
        res.sai = ESmsAvailabilityIndication.fromValue(value.getBitI(0));
        return res;
    }

    @Override
    public int encodeIE1() {
        return sai.intValue();
    }

    public static class ESmsAvailabilityIndication extends ProtocolEnum {
        public static final ESmsAvailabilityIndication NOT_AVAILABLE
                = new ESmsAvailabilityIndication(0b0, "SMS over NAS not available");
        public static final ESmsAvailabilityIndication AVAILABLE
                = new ESmsAvailabilityIndication(0b1, "SMS over NAS available");

        private ESmsAvailabilityIndication(int value, String name) {
            super(value, name);
        }

        public static ESmsAvailabilityIndication fromValue(int value) {
            return fromValueGeneric(ESmsAvailabilityIndication.class, value);
        }
    }
}
