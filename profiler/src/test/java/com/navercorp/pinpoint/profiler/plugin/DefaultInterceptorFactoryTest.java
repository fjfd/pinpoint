/*
 * Copyright 2014 NAVER Corp.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.navercorp.pinpoint.profiler.plugin;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.Test;

import com.navercorp.pinpoint.bootstrap.context.TraceContext;
import com.navercorp.pinpoint.bootstrap.instrument.ByteCodeInstrumentor;
import com.navercorp.pinpoint.bootstrap.instrument.InstrumentClass;
import com.navercorp.pinpoint.bootstrap.instrument.MethodInfo;
import com.navercorp.pinpoint.bootstrap.interceptor.Interceptor;
import com.navercorp.pinpoint.bootstrap.interceptor.MethodDescriptor;
import com.navercorp.pinpoint.exception.PinpointException;
import com.navercorp.pinpoint.profiler.plugin.DefaultProfilerPluginContext;
import com.navercorp.pinpoint.profiler.plugin.TestInterceptors.TestInterceptor0;
import com.navercorp.pinpoint.profiler.plugin.TestInterceptors.TestInterceptor1;
import com.navercorp.pinpoint.profiler.plugin.TestInterceptors.TestInterceptor2;
import com.navercorp.pinpoint.profiler.plugin.interceptor.AnnotatedInterceptorFactory;

public class DefaultInterceptorFactoryTest {
    private final DefaultProfilerPluginContext pluginContext = mock(DefaultProfilerPluginContext.class);
    private final ByteCodeInstrumentor instrumentor = mock(ByteCodeInstrumentor.class);
    private final TraceContext traceContext = mock(TraceContext.class);
    private final InstrumentClass aClass = mock(InstrumentClass.class);
    private final MethodInfo aMethod = mock(MethodInfo.class);
    private final MethodDescriptor descriptor = mock(MethodDescriptor.class);
    
    @Before
    public void setUp() {
        reset(instrumentor, traceContext, aClass, aMethod);
        when(aMethod.getDescriptor()).thenReturn(descriptor);
    }

    @Test
    public void test0() throws Exception {
        AnnotatedInterceptorFactory factory = new AnnotatedInterceptorFactory(traceContext, pluginContext, instrumentor, TestInterceptors.TestInterceptor0.class, null);
        Interceptor interceptor = factory.getInterceptor(getClass().getClassLoader(), aClass, aMethod);
        
        assertEquals(TestInterceptor0.class, interceptor.getClass());
    }
    
    @Test
    public void test1() throws Exception {
        Object[] args = new Object[] { "arg0" };
        
        AnnotatedInterceptorFactory factory = new AnnotatedInterceptorFactory(traceContext, pluginContext, instrumentor, TestInterceptors.TestInterceptor0.class, args);
        Interceptor interceptor = factory.getInterceptor(getClass().getClassLoader(), aClass, aMethod);
        
        assertEquals(TestInterceptor0.class, interceptor.getClass());
        assertEquals(args[0], getField(interceptor, "field0"));
    }
    
    @Test(expected = PinpointException.class)
    public void test2() throws Exception {
        Object[] args = new Object[] { 1 };
        
        AnnotatedInterceptorFactory factory = new AnnotatedInterceptorFactory(traceContext, pluginContext, instrumentor, TestInterceptors.TestInterceptor0.class, args);
        factory.getInterceptor(getClass().getClassLoader(), aClass, aMethod);
    }

    @Test
    public void test3() throws Exception {
        Object[] args = new Object[] { "arg0", (byte)1, (short)2, (float)3.0 };
        
        AnnotatedInterceptorFactory factory = new AnnotatedInterceptorFactory(traceContext, pluginContext, instrumentor, TestInterceptors.TestInterceptor1.class, args);
        Interceptor interceptor = factory.getInterceptor(getClass().getClassLoader(), aClass, aMethod);
        
        assertEquals(TestInterceptor1.class, interceptor.getClass());
        assertEquals(args[0], getField(interceptor, "field0"));
        assertEquals(args[1], getField(interceptor, "field1"));
        assertEquals(args[2], getField(interceptor, "field2"));
        assertEquals(args[3], getField(interceptor, "field3"));
    }

    @Test
    public void test4() throws Exception {
        Object[] args = new Object[] { (byte)1, (short)2, (float)3.0, "arg0" };
        
        AnnotatedInterceptorFactory factory = new AnnotatedInterceptorFactory(traceContext, pluginContext, instrumentor, TestInterceptors.TestInterceptor1.class, args);
        Interceptor interceptor = factory.getInterceptor(getClass().getClassLoader(), aClass, aMethod);
        
        assertEquals(TestInterceptor1.class, interceptor.getClass());
        assertEquals(args[3], getField(interceptor, "field0"));
        assertEquals(args[0], getField(interceptor, "field1"));
        assertEquals(args[1], getField(interceptor, "field2"));
        assertEquals(args[2], getField(interceptor, "field3"));
    }

    @Test
    public void test5() throws Exception {
        Object[] args = new Object[] { (short)2, (float)3.0, "arg0", (byte)1 };
        
        AnnotatedInterceptorFactory factory = new AnnotatedInterceptorFactory(traceContext, pluginContext, instrumentor, TestInterceptors.TestInterceptor1.class, args);
        Interceptor interceptor = factory.getInterceptor(getClass().getClassLoader(), aClass, aMethod);
        
        assertEquals(TestInterceptor1.class, interceptor.getClass());
        assertEquals(args[2], getField(interceptor, "field0"));
        assertEquals(args[3], getField(interceptor, "field1"));
        assertEquals(args[0], getField(interceptor, "field2"));
        assertEquals(args[1], getField(interceptor, "field3"));
    }
    
    @Test
    public void test6() throws Exception {
        Object[] args = new Object[] { (float)3.0, (short)2, (byte)1, "arg0" };
        
        AnnotatedInterceptorFactory factory = new AnnotatedInterceptorFactory(traceContext, pluginContext, instrumentor, TestInterceptors.TestInterceptor1.class, args);
        Interceptor interceptor = factory.getInterceptor(getClass().getClassLoader(), aClass, aMethod);
        
        assertEquals(TestInterceptor1.class, interceptor.getClass());
        assertEquals(args[3], getField(interceptor, "field0"));
        assertEquals(args[2], getField(interceptor, "field1"));
        assertEquals(args[1], getField(interceptor, "field2"));
        assertEquals(args[0], getField(interceptor, "field3"));
    }

    @Test(expected=PinpointException.class)
    public void test7() throws Exception {
        AnnotatedInterceptorFactory factory = new AnnotatedInterceptorFactory(traceContext, pluginContext, instrumentor, TestInterceptors.TestInterceptor1.class, null);
        factory.getInterceptor(getClass().getClassLoader(), aClass, aMethod);
    }

    @Test(expected=PinpointException.class)
    public void test8() throws Exception {
        AnnotatedInterceptorFactory factory = new AnnotatedInterceptorFactory(traceContext, pluginContext, instrumentor, TestInterceptors.TestInterceptor1.class, null);
        factory.getInterceptor(getClass().getClassLoader(), aClass, aMethod);
    }
    
    @Test
    public void test9() throws Exception {
        Object[] args = new Object[] { "arg0", 1, 2.0, true, 3L };
        
        AnnotatedInterceptorFactory factory = new AnnotatedInterceptorFactory(traceContext, pluginContext, instrumentor, TestInterceptors.TestInterceptor2.class, args);
        Interceptor interceptor = factory.getInterceptor(getClass().getClassLoader(), aClass, aMethod);
        
        assertEquals(TestInterceptor2.class, interceptor.getClass());
        assertEquals(args[0], getField(interceptor, "field0"));
        assertEquals(args[1], getField(interceptor, "field1"));
        assertEquals(args[2], getField(interceptor, "field2"));
        assertEquals(args[3], getField(interceptor, "field3"));
        assertEquals(args[4], getField(interceptor, "field4"));
        
        assertSame(descriptor, getField(interceptor, "descriptor"));
        assertSame(aClass, getField(interceptor, "targetClass"));
        assertSame(aMethod, getField(interceptor, "targetMethod"));
    }

    @Test
    public void test10() throws Exception {
        Object[] args = new Object[] { "arg0", 1, 2.0 };
        
        AnnotatedInterceptorFactory factory = new AnnotatedInterceptorFactory(traceContext, pluginContext, instrumentor, TestInterceptors.TestInterceptor2.class, args);
        Interceptor interceptor = factory.getInterceptor(getClass().getClassLoader(), aClass, aMethod);
        
        assertEquals(TestInterceptor2.class, interceptor.getClass());
        assertEquals(args[0], getField(interceptor, "field0"));
        assertEquals(args[1], getField(interceptor, "field1"));
        assertEquals(args[2], getField(interceptor, "field2"));
        assertEquals(false, getField(interceptor, "field3"));
        assertEquals(0L, getField(interceptor, "field4"));
        
        assertSame(descriptor, getField(interceptor, "descriptor"));
        assertSame(aClass, getField(interceptor, "targetClass"));
        assertSame(aMethod, getField(interceptor, "targetMethod"));
    }

    @Test
    public void test11() throws Exception {
        Object[] args = new Object[] { "arg0", 1 };
        
        AnnotatedInterceptorFactory factory = new AnnotatedInterceptorFactory(traceContext, pluginContext, instrumentor, TestInterceptors.TestInterceptor2.class, args);
        Interceptor interceptor = factory.getInterceptor(getClass().getClassLoader(), aClass, aMethod);
        
        assertEquals(TestInterceptor2.class, interceptor.getClass());
        assertEquals(args[0], getField(interceptor, "field0"));
        assertEquals(args[1], getField(interceptor, "field1"));
        assertEquals(0.0, getField(interceptor, "field2"));
        assertEquals(false, getField(interceptor, "field3"));
        assertEquals(0L, getField(interceptor, "field4"));
        
        assertSame(descriptor, getField(interceptor, "descriptor"));
        assertSame(aClass, getField(interceptor, "targetClass"));
        assertSame(aMethod, getField(interceptor, "targetMethod"));
    }
    
    @Test
    public void test12() throws Exception {
        AnnotatedInterceptorFactory factory = new AnnotatedInterceptorFactory(traceContext, pluginContext, instrumentor, TestInterceptors.TestInterceptor2.class, null);
        Interceptor interceptor = factory.getInterceptor(getClass().getClassLoader(), aClass, aMethod);
        
        assertEquals(TestInterceptor2.class, interceptor.getClass());
        assertEquals(null, getField(interceptor, "field0"));
        assertEquals(0, getField(interceptor, "field1"));
        assertEquals(0.0, getField(interceptor, "field2"));
        assertEquals(false, getField(interceptor, "field3"));
        assertEquals(0L, getField(interceptor, "field4"));
        
        assertSame(descriptor, getField(interceptor, "descriptor"));
        assertSame(aClass, getField(interceptor, "targetClass"));
        assertSame(aMethod, getField(interceptor, "targetMethod"));
    }

    @Test
    public void test13() throws Exception {
        Object[] args = new Object[] { "arg0" };
        
        AnnotatedInterceptorFactory factory = new AnnotatedInterceptorFactory(traceContext, pluginContext, instrumentor, TestInterceptors.TestInterceptor2.class, args);
        Interceptor interceptor = factory.getInterceptor(getClass().getClassLoader(), aClass, aMethod);
        
        assertEquals(TestInterceptor2.class, interceptor.getClass());
        assertEquals(args[0], getField(interceptor, "field0"));
        assertEquals(0, getField(interceptor, "field1"));
        assertEquals(0.0, getField(interceptor, "field2"));
        assertEquals(false, getField(interceptor, "field3"));
        assertEquals(0L, getField(interceptor, "field4"));
        
        assertSame(descriptor, getField(interceptor, "descriptor"));
        assertSame(aClass, getField(interceptor, "targetClass"));
        assertNull(getField(interceptor, "targetMethod"));
    }

    
    private Object getField(Object object, String name) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        String methodName = "get" + Character.toUpperCase(name.charAt(0)) + name.substring(1);
        Method method = object.getClass().getMethod(methodName);
        return method.invoke(object);
    }
}
