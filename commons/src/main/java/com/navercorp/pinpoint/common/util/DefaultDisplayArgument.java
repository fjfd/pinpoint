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

package com.navercorp.pinpoint.common.util;

import com.navercorp.pinpoint.common.AnnotationKey;
import com.navercorp.pinpoint.common.AnnotationKeyMatcher;
import com.navercorp.pinpoint.common.ServiceType;

import static com.navercorp.pinpoint.common.AnnotationKeyMatcher.ARGS_MATCHER;

/**
 * @author emeroad
 */
public class DefaultDisplayArgument {

    public static final DisplayArgumentMatcher UNKNOWN_DB_MATCHER = createArgumentMatcher(ServiceType.UNKNOWN, AnnotationKey.ARGS0);
    public static final DisplayArgumentMatcher UNKNOWN_DB_EXECUTE_QUERY_MATCHER = createArgumentMatcher(ServiceType.UNKNOWN_DB_EXECUTE_QUERY, AnnotationKey.ARGS0);

    public static final DisplayArgumentMatcher MYSQL_MATCHER = createArgumentMatcher(ServiceType.MYSQL, AnnotationKey.ARGS0);
    public static final DisplayArgumentMatcher MYSQL_EXECUTE_QUERY_MATCHER = createArgumentMatcher(ServiceType.MYSQL_EXECUTE_QUERY, AnnotationKey.ARGS0);

    public static final DisplayArgumentMatcher MSSQL_MATCHER = createArgumentMatcher(ServiceType.MSSQL, AnnotationKey.ARGS0);
    public static final DisplayArgumentMatcher MSSQL_EXECUTE_QUERY_MATCHER = createArgumentMatcher(ServiceType.MSSQL_EXECUTE_QUERY, AnnotationKey.ARGS0);

    public static final DisplayArgumentMatcher ORACLE_MATCHER = createArgumentMatcher(ServiceType.ORACLE, AnnotationKey.ARGS0);
    public static final DisplayArgumentMatcher ORACLE_EXECUTE_QUERY_MATCHER = createArgumentMatcher(ServiceType.ORACLE_EXECUTE_QUERY, AnnotationKey.ARGS0);

    public static final DisplayArgumentMatcher CUBRID_MATCHER = createArgumentMatcher(ServiceType.CUBRID, AnnotationKey.ARGS0);
    public static final DisplayArgumentMatcher CUBRID_EXECUTE_QUERY_MATCHER = createArgumentMatcher(ServiceType.CUBRID_EXECUTE_QUERY, AnnotationKey.ARGS0);


    public static final DisplayArgumentMatcher SPRING_ORM_IBATIS_MATCHER = createArgumentMatcher(ServiceType.SPRING_ORM_IBATIS, AnnotationKey.ARGS0);


    public static final DisplayArgumentMatcher IBATIS_MATCHER = createArgumentMatcher(ServiceType.IBATIS, AnnotationKey.ARGS0);
    public static final DisplayArgumentMatcher MYBATIS_MATCHER = createArgumentMatcher(ServiceType.MYBATIS, AnnotationKey.ARGS0);

    public static final DisplayArgumentMatcher MEMCACHED_MATCHER = createArgumentMatcher(ServiceType.MEMCACHED, ARGS_MATCHER);
    
    public static final DisplayArgumentMatcher HTTP_CLIENT_MATCHER = createArgumentMatcher(ServiceType.HTTP_CLIENT, AnnotationKey.HTTP_URL);
    public static final DisplayArgumentMatcher HTTP_CLIENT_INTERNAL_MATCHER = createArgumentMatcher(ServiceType.HTTP_CLIENT_INTERNAL, AnnotationKey.HTTP_CALL_RETRY_COUNT);
    public static final DisplayArgumentMatcher JDK_HTTPURLCONNECTOR_MATCHER = createArgumentMatcher(ServiceType.JDK_HTTPURLCONNECTOR, AnnotationKey.HTTP_URL);



    private static AnnotationKeyMatcher createMatcher(AnnotationKey displayArgument) {
        return new AnnotationKeyMatcher.ExactMatcher(displayArgument);
    }

    private static DisplayArgumentMatcher createArgumentMatcher(ServiceType serviceType, AnnotationKey annotationKey) {
        return createArgumentMatcher(serviceType, createMatcher(annotationKey));
    }

    private static DisplayArgumentMatcher createArgumentMatcher(ServiceType serviceType, AnnotationKeyMatcher annotationKeyMatcher) {
        return new DisplayArgumentMatcher(serviceType, annotationKeyMatcher);
    }
}
