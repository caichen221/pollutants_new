package com.iscas.common.docker.tools.utils;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerCmd;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.*;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.jaxrs.JerseyDockerHttpClient;
import com.github.dockerjava.transport.DockerHttpClient;
import com.iscas.common.docker.tools.model.CreateContainerReq;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * <p>docker工具<p/>
 * <p>需要开启远程访问，可参见：https://blog.csdn.net/u011943534/article/details/112134818<p/>
 *
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/9/14 15:58
 * @since jdk1.8
 */
@Slf4j
public class DockerClientUtils {
    /**api的版本号，可以在远程docker服务上运行docker version看到*/
    private static final String API_VERSION = "1.40";

    /**
     * docker httpclient连接信息
     */
    private static final int READ_TIMEOUT = 1000;
    private static final int CONNECT_TIMEOUT = 1000;
    private static final int MAX_TOTAL_CONNECTIONS = 100;
    private static final int MAX_PER_ROUTE_CONNECTIONS = 10;


    private DockerClientUtils() {}

    /**
     * 连接到远程的docker
     * @version 1.0
     * @since jdk1.8
     * @date 2021/9/14
     * @param host 连接host信息，如tcp://172.16.10.163:2375
     * @param apiVersion 使用的API版本，可以在远程docker服务上运行docker version看到
     * @throws
     * @return com.github.dockerjava.api.DockerClient
     */
    public static DockerClient connectDocker(String host, String apiVersion) throws URISyntaxException {
        DefaultDockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder()
                /*.withDockerHost(host)*/.withApiVersion(apiVersion).build();
        DockerHttpClient httpClient = new JerseyDockerHttpClient.Builder()
                .readTimeout(READ_TIMEOUT)
                .connectTimeout(CONNECT_TIMEOUT)
                .maxPerRouteConnections(MAX_PER_ROUTE_CONNECTIONS)
                .maxTotalConnections(MAX_TOTAL_CONNECTIONS)
                .dockerHost(new URI(host)).build();
        DockerClient dockerClient = DockerClientImpl.getInstance(config, httpClient);
        Info info = dockerClient.infoCmd().exec();
        LogUtils.debug(log, "docker信息:-->" + info.toString());
        return dockerClient;
    }

    /**
     * 连接到远程的docker,apiVersion使用类上的配置
     * @version 1.0
     * @since jdk1.8
     * @date 2021/9/14
     * @param host 连接host信息，如tcp://172.16.10.163:2375
     * @throws
     * @return com.github.dockerjava.api.DockerClient
     */
    public static DockerClient connectDocker(String host) throws URISyntaxException {
        return connectDocker(host, API_VERSION);
    }

    public static CreateContainerResponse createContainer(DockerClient client, CreateContainerReq createContainerReq) {
        CreateContainerCmd containerCmd = client.createContainerCmd(createContainerReq.getImageName());

        //设置名字
        Optional.ofNullable(createContainerReq.getContainerName()).ifPresent(containerName -> containerCmd.withName(containerName));

        //设置端口绑定
        if (createContainerReq.getPorts() != null) {
            List<PortBinding> portBindings = new ArrayList<>();
            for (CreateContainerReq.Port port : createContainerReq.getPorts()) {
                Integer exposedPort = port.getExposedPort();
                Integer bindPort = port.getBindPort();
                Ports.Binding binding = bindPort == null ? Ports.Binding.empty() : Ports.Binding.bindPort(bindPort);
                PortBinding portBinding = new PortBinding(binding, new ExposedPort(exposedPort));
                portBindings.add(portBinding);
            }
            containerCmd.getHostConfig().withPortBindings(portBindings);
        }

        //设置环境变量
        Optional.ofNullable(createContainerReq.getEnvs()).ifPresent(envs -> containerCmd.withEnv(envs));

        //设置entrypiont
        Optional.ofNullable(createContainerReq.getEntrypoints()).ifPresent(entryPoints -> containerCmd.withEntrypoint(entryPoints));

        //设置挂载
        if (createContainerReq.getBindVolumes() != null) {
            List<Bind> binds = new ArrayList<>();
            for (CreateContainerReq.BindVolume bindVolume : createContainerReq.getBindVolumes()) {
                binds.add(new Bind(bindVolume.getBind(), new Volume(bindVolume.getVol()), bindVolume.getAccessMode()));
            }
            containerCmd.getHostConfig().withBinds(binds);
        }

        //创建
        return containerCmd.exec();
    }

}
