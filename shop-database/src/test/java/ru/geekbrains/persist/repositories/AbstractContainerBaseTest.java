package ru.geekbrains.persist.repositories;

import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

public abstract class AbstractContainerBaseTest {

    public static MySQLContainer<?> container = new MySQLContainer<>(
            DockerImageName.parse("mysql:latest")
    )
            .withDatabaseName("geek_eshop")
            .withUsername("spring")
            .withPassword("spring")
            .withEnv("MYSQL_ROOT_PASSWORD", "spring")
            .withExposedPorts(3306)
            .withCreateContainerCmdModifier(
                    createContainerCmd -> createContainerCmd
                            .withHostConfig(new HostConfig().withPortBindings(
                                    new PortBinding(Ports.Binding.bindPort(3306), new ExposedPort(3306)))
                            ));

    static {
        container.start();
    }
}
