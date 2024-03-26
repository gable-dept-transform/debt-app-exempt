package th.co.ais.mimo.debt;


import org.slf4j.MDC;

import io.jaegertracing.internal.JaegerSpanContext;
import io.opentracing.Scope;
import io.opentracing.Span;
import io.opentracing.util.ThreadLocalScopeManager;

public class MDCScopeManager extends ThreadLocalScopeManager {

    @Override
    public Scope activate(Span span) {
        Scope activate = super.activate(span);
        mdc(span);
        return activate;
    }


    public void mdc(Span span){
        JaegerSpanContext ctx = (JaegerSpanContext) span.context();
        String traceId = ctx.getTraceId();
        String spanId = Long.toHexString(ctx.getSpanId());
        String sampled = String.valueOf(ctx.isSampled());
        String parentSpanId = Long.toHexString(ctx.getParentId());
        replace("x-b3-traceid", traceId);
        replace("x-b3-spanid", spanId);
        replace("x-b3-parentspanid", parentSpanId);
        replace("x-b3-sampled", sampled);
    }

    private static String lookup(String key) {
        return MDC.get(key);
    }

    private static void replace(String key, String value) {
        if (value == null) {
            MDC.remove(key);
        } else {
            MDC.put(key, value);
        }
    }
}