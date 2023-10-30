package com.sisp;

import com.sisp.beans.HttpResponseEntity;
import com.sisp.controller.UserController;
import com.sisp.dao.entity.UserEntity;
import com.sisp.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testUserLoginSuccess() {
        // Arrange
        UserEntity userEntity = new UserEntity();
        List<UserEntity> results = new ArrayList<>();
        results.add(new UserEntity());
        when(userService.selectUserInfo(userEntity)).thenReturn(results);

        // Act
        HttpResponseEntity httpResponseEntity = userController.userLogin(userEntity);

        // Assert
        assertEquals("666", httpResponseEntity.getCode());
        assertEquals("登录成功", httpResponseEntity.getMessage());
        assertEquals(results, httpResponseEntity.getData());
    }

    @Test
    public void testUserLoginFailure() {
        // Arrange
        UserEntity userEntity = new UserEntity();
        List<UserEntity> results = new ArrayList<>();
        when(userService.selectUserInfo(userEntity)).thenReturn(results);

        // Act
        HttpResponseEntity httpResponseEntity = userController.userLogin(userEntity);

        // Assert
        assertEquals("0", httpResponseEntity.getCode());
        assertEquals(null, httpResponseEntity.getMessage());
        assertEquals(null, httpResponseEntity.getData());
    }

    @Test
    public void testUserLoginException() {
        // Arrange
        UserEntity userEntity = new UserEntity();
        when(userService.selectUserInfo(userEntity)).thenThrow(new RuntimeException());

        // Act
        HttpResponseEntity httpResponseEntity = userController.userLogin(userEntity);

        // Assert
        assertEquals(null, httpResponseEntity.getCode());
        assertEquals(null, httpResponseEntity.getMessage());
        assertEquals(null, httpResponseEntity.getData());
    }

    @Test
    public void testQueryUserList() {
        // Arrange
        UserEntity userEntity = new UserEntity();
        List<UserEntity> results = new ArrayList<>();
        results.add(new UserEntity());
        when(userService.queryUserList(userEntity)).thenReturn(results);

        // Act
        HttpResponseEntity httpResponseEntity = userController.queryUserList(userEntity);

        // Assert
        assertEquals("666", httpResponseEntity.getCode());
        assertEquals(results, httpResponseEntity.getData());
    }

    @Test
    public void testQueryUserListEmpty() {
        // Arrange
        UserEntity userEntity = new UserEntity();
        List<UserEntity> results = new ArrayList<>();
        when(userService.queryUserList(userEntity)).thenReturn(results);

        // Act
        HttpResponseEntity httpResponseEntity = userController.queryUserList(userEntity);

        // Assert
        assertEquals("0", httpResponseEntity.getCode());
        assertEquals(null, httpResponseEntity.getMessage());
        assertEquals(null, httpResponseEntity.getData());
    }

    @Test
    public void testQueryUserListException() {
        // Arrange
        UserEntity userEntity = new UserEntity();
        when(userService.queryUserList(userEntity)).thenThrow(new RuntimeException());

        // Act
        HttpResponseEntity httpResponseEntity = userController.queryUserList(userEntity);

        // Assert
        assertEquals(null, httpResponseEntity.getCode());
        assertEquals(null, httpResponseEntity.getMessage());
        assertEquals(null, httpResponseEntity.getData());
    }
}