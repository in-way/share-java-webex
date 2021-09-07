

package com.gbps.webex.core;

import com.gbps.webex.entity.ResponseCode;
import com.sunsharing.share.common.base.ExceptionUtil;

public class ExceptionTrans<T extends Throwable> {

    private final boolean dumpStackIfDev;
    private final CodeCreator<T> codeCreator;
    private final DetailCreator<T> innerDetailCreator;

    public ExceptionTrans(boolean dumpStackIfDev,
                           CodeCreator<T> codeCreator,
                           DetailCreator<T> innerDetailCreator) {
        this.codeCreator = codeCreator;
        this.dumpStackIfDev = dumpStackIfDev;
        this.innerDetailCreator = innerDetailCreator;
    }

    public ResponseCode getResponseCode(T ex) {
        return codeCreator.getResponseCode(ex);
    }

    public Object createDetail(T ex, boolean dev) {
        if (dumpStackIfDev && dev) {
            return DUMP_STACK_DETAIL_CREATOR.createDetail(ex);
        }

        return innerDetailCreator.createDetail(ex);
    }

    @FunctionalInterface
    public interface DetailCreator<T extends Throwable> {
        Object createDetail(T ex);
    }

    @FunctionalInterface
    public interface CodeCreator<T extends Throwable> {
        ResponseCode getResponseCode(T ex);
    }

    private static final DetailCreator<Throwable> DUMP_STACK_DETAIL_CREATOR = (ex) -> ExceptionUtil.stackTraceText(ex);
}

