package com.gbps.webex.util;

import com.sunsharing.share.common.base.annotation.Nullable;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ExecutionException;

import static org.springframework.core.NestedExceptionUtils.getRootCause;

public class ExceptionUtil {
    private static final StackTraceElement[] EMPTY_STACK_TRACE = new StackTraceElement[0];

    public ExceptionUtil() {
    }

    public static RuntimeException unchecked(Throwable t) {
        if (t instanceof RuntimeException) {
            throw (RuntimeException)t;
        } else if (t instanceof Error) {
            throw (Error)t;
        } else {
            throw new ExceptionUtil.UncheckedException(t);
        }
    }

    public static Throwable unwrap(Throwable t) {
        return !(t instanceof ExecutionException) && !(t instanceof InvocationTargetException) && !(t instanceof ExceptionUtil.UncheckedException) ? t : t.getCause();
    }

    public static RuntimeException uncheckedAndWrap(Throwable t) {
        Throwable unwrapped = unwrap(t);
        return unchecked(unwrapped);
    }



    public static boolean isCausedBy(Throwable t, Class<? extends Exception>... causeExceptionClasses) {
        for(Throwable cause = t; cause != null; cause = cause.getCause()) {
            Class[] var3 = causeExceptionClasses;
            int var4 = causeExceptionClasses.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                Class<? extends Exception> causeClass = var3[var5];
                if (causeClass.isInstance(cause)) {
                    return true;
                }
            }
        }

        return false;
    }

    public static String toStringWithShortName(@Nullable Throwable t) {
        return ExceptionUtils.getMessage(t);
    }

    public static String toStringWithRootCause(@Nullable Throwable t) {
        if (t == null) {
            return "";
        } else {
            String clsName = ClassUtils.getShortClassName(t, (String)null);
            String message = StringUtils.defaultString(t.getMessage());
            Throwable cause = getRootCause(t);
            StringBuilder sb = (new StringBuilder(128)).append(clsName).append(": ").append(message);
            if (cause != t) {
                sb.append("; <---").append(toStringWithShortName(cause));
            }

            return sb.toString();
        }
    }

    public static <T extends Throwable> T setStackTrace(T exception, Class<?> throwClass, String throwClazz) {
        exception.setStackTrace(new StackTraceElement[]{new StackTraceElement(throwClass.getName(), throwClazz, (String)null, -1)});
        return exception;
    }

    public static <T extends Throwable> T clearStackTrace(T exception) {
        for(Throwable cause = exception; cause != null; cause = cause.getCause()) {
            cause.setStackTrace(EMPTY_STACK_TRACE);
        }

        return exception;
    }

    public static RuntimeException unwrapAndUnchecked(@Nullable Throwable t) {
        throw unchecked(unwrap(t));
    }

    public static class UncheckedException extends RuntimeException {
        private static final long serialVersionUID = 4140223302171577501L;

        public UncheckedException(Throwable cause) {
            super(cause);
        }
        @Override
        public String getMessage() {
            return super.getCause().getMessage();
        }
    }

    public static class CloneableRuntimeException extends RuntimeException implements Cloneable {
        private static final long serialVersionUID = 3984796576627959400L;
        protected String message;

        public CloneableRuntimeException() {
            super((Throwable)null);
        }

        public CloneableRuntimeException(String message) {
            super((Throwable)null);
            this.message = message;
        }

        public CloneableRuntimeException(String message, Throwable cause) {
            super(cause);
            this.message = message;
        }
        @Override
        public ExceptionUtil.CloneableRuntimeException clone() {
            try {
                return (ExceptionUtil.CloneableRuntimeException)super.clone();
            } catch (CloneNotSupportedException var2) {
                return null;
            }
        }

        public ExceptionUtil.CloneableRuntimeException clone(String message) {
            ExceptionUtil.CloneableRuntimeException newException = this.clone();
            newException.setMessage(message);
            return newException;
        }
        @Override
        public String getMessage() {
            return this.message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public ExceptionUtil.CloneableRuntimeException setStackTrace(Class<?> throwClazz, String throwMethod) {
            ExceptionUtil.setStackTrace(this, throwClazz, throwMethod);
            return this;
        }
    }

    public static class CloneableException extends Exception implements Cloneable {
        private static final long serialVersionUID = -6270471689928560417L;
        protected String message;

        public CloneableException() {
            super((Throwable)null);
        }

        public CloneableException(String message) {
            super((Throwable)null);
            this.message = message;
        }

        public CloneableException(String message, Throwable cause) {
            super(cause);
            this.message = message;
        }
        @Override
        public ExceptionUtil.CloneableException clone() {
            try {
                return (ExceptionUtil.CloneableException)super.clone();
            } catch (CloneNotSupportedException var2) {
                return null;
            }
        }

        public ExceptionUtil.CloneableException clone(String message) {
            ExceptionUtil.CloneableException newException = this.clone();
            newException.setMessage(message);
            return newException;
        }
        @Override
        public String getMessage() {
            return this.message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public ExceptionUtil.CloneableException setStackTrace(Class<?> throwClazz, String throwMethod) {
            ExceptionUtil.setStackTrace(this, throwClazz, throwMethod);
            return this;
        }
    }
}
