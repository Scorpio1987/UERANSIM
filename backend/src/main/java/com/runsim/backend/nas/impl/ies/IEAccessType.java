package com.runsim.backend.nas.impl.ies;

import com.runsim.backend.nas.core.ProtocolEnum;
import com.runsim.backend.nas.core.ies.InformationElement1;
import com.runsim.backend.utils.bits.Bit4;

public class IEAccessType extends InformationElement1 {
    public EAccessType accessType;

    @Override
    public IEAccessType decodeIE1(Bit4 value) {
        var res = new IEAccessType();
        res.accessType = EAccessType.fromValue(value.intValue());
        return res;
    }

    @Override
    public int encodeIE1() {
        return accessType.intValue();
    }

    public static class EAccessType extends ProtocolEnum {
        public static final EAccessType THREEGPP_ACCESS
                = new EAccessType(0b0, "3GPP access");
        public static final EAccessType NON_THREEGPP_ACCESS
                = new EAccessType(0b1, "Non-3GPP access");

        private EAccessType(int value, String name) {
            super(value, name);
        }

        public static EAccessType fromValue(int value) {
            return fromValueGeneric(EAccessType.class, value);
        }
    }
}
