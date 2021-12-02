package com.iscas.common.tools.mapstruct;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-11-30T09:47:02+0800",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.8 (Oracle Corporation)"
)
public class TestUserConverterImpl implements TestUserConverter {

    @Override
    public TestUserVO1 toTestUserVO1(TestUserDTO testUser) {
        if ( testUser == null ) {
            return null;
        }

        TestUserVO1 testUserVO1 = new TestUserVO1();

        testUserVO1.setId( testUser.getId() );
        testUserVO1.setRealName( testUser.getRealName() );

        return testUserVO1;
    }

    @Override
    public TestUserVO2 toTestUserVO2(TestUserDTO testUser) {
        if ( testUser == null ) {
            return null;
        }

        TestUserVO2 testUserVO2 = new TestUserVO2();

        List<TestRoleDTO> list = testUser.getRoles();
        if ( list != null ) {
            testUserVO2.setRoleList( new ArrayList<TestRoleDTO>( list ) );
        }
        if ( testUser.getCreateTime() != null ) {
            testUserVO2.setCreateTimeStr( new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" ).format( testUser.getCreateTime() ) );
        }
        testUserVO2.setId( testUser.getId() );
        testUserVO2.setUsername( testUser.getUsername() );
        testUserVO2.setRealName( testUser.getRealName() );
        testUserVO2.setEmail( testUser.getEmail() );
        testUserVO2.setPhone( testUser.getPhone() );
        testUserVO2.setCreateTime( testUser.getCreateTime() );

        testUserVO2.setCreateTimeExpression( org.apache.commons.lang3.time.DateFormatUtils.format(testUser.getCreateTime(), "yyyy-MM-dd") );

        return testUserVO2;
    }

    @Override
    public TestUserVO3 toTestUserVO3(TestUserDTO user, TestRoleDTO role) {
        if ( user == null && role == null ) {
            return null;
        }

        TestUserVO3 testUserVO3 = new TestUserVO3();

        if ( user != null ) {
            testUserVO3.setRealName( user.getRealName() );
        }
        if ( role != null ) {
            testUserVO3.setRole( role );
        }

        return testUserVO3;
    }

    @Override
    public void updateTestUserVO3(TestUserDTO user, TestRoleDTO role, TestUserVO3 testUserVO3) {
        if ( user == null && role == null ) {
            return;
        }

        if ( user != null ) {
            testUserVO3.setRealName( user.getRealName() );
        }
        if ( role != null ) {
            testUserVO3.setRole( role );
        }
    }
}
