package com.iscas.common.docker.tools.utils;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.AccessMode;
import com.github.dockerjava.api.model.Volume;
import com.iscas.common.docker.tools.model.CreateContainerReq;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;
import java.util.Arrays;

/**
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/9/14 16:25
 * @since jdk1.8
 */
public class DockerClientUtilsTest {
    private DockerClient dockerClient = null;

    @BeforeEach
    public void init() throws URISyntaxException {
        dockerClient = DockerClientUtils.connectDocker("tcp://172.16.10.163:2375");
    }

    /**
     * 测试获取连接
     */
    @Test
    public void testDockerClient() throws URISyntaxException {
        DockerClient dockerClient = DockerClientUtils.connectDocker("tcp://172.16.10.163:2375", "1.40");
        Assertions.assertNotNull(dockerClient);
    }

    /**
     * 测试创建容器
     */
    @Test
    public void testCreateDocker() {
        CreateContainerResponse containerRes = DockerClientUtils.createContainer(dockerClient,
                new CreateContainerReq()
                        .setImageName("nginx:latest")
                        .setPorts(Arrays.asList(new CreateContainerReq.Port().setBindPort(8080).setExposedPort(80)))
                        .setContainerName("nginx-test")
                        .setEnvs(Arrays.asList("key1=val1", "key2=val2"))
//                        .setEntrypoints(Arrays.asList("java -jar xxx.jar"))
                .setBindVolumes(Arrays.asList(new CreateContainerReq.BindVolume().setBind("/tmp").setVol("/tmp").setAccessMode(AccessMode.DEFAULT)))
        );
        System.out.println(containerRes);
    }


}