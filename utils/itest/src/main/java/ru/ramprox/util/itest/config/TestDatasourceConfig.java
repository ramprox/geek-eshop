package ru.ramprox.util.itest.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import javax.sql.DataSource;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

@TestConfiguration
@Import({ TestContainersConfig.class, DataSourceConfig.class })
public class TestDatasourceConfig {

    private final TestContainersConfig testContainersConfig;

    private final DataSourceConfig dataSourceConfig;

    @Autowired
    public TestDatasourceConfig(TestContainersConfig testContainersConfig,
                                DataSourceConfig dataSourceConfig) {
        this.testContainersConfig = testContainersConfig;
        this.dataSourceConfig = dataSourceConfig;
    }

    @Bean
    public DataSource dataSource() {
        DataSourceBuilder<?> builder = DataSourceBuilder.create()
                .driverClassName(dataSourceConfig.getDriverClassName());
        try{
            return connectToStartedDbServer(builder);
        } catch (Exception ex) {
            return startTestContainer(builder);
        }
    }

    private DataSource connectToStartedDbServer(DataSourceBuilder<?> builder) throws IOException {
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress(
                testContainersConfig.getHost(),
                testContainersConfig.getPort()));
        socket.close();
        return builder
                .username(dataSourceConfig.getUsername())
                .password(dataSourceConfig.getPassword())
                .url(dataSourceConfig.getUrl())
                .build();
    }

    private DataSource startTestContainer(DataSourceBuilder<?> builder) {
        PostgreSQLContainer<?> container = new PostgreSQLContainer<>(DockerImageName.parse(testContainersConfig.getImageName()))
                .withDatabaseName(testContainersConfig.getDbName())
                .withUsername(dataSourceConfig.getUsername())
                .withPassword(dataSourceConfig.getPassword())
                .withEnv("POSTGRES_PASSWORD", dataSourceConfig.getPassword())
                .withExposedPorts(testContainersConfig.getPort());
        container.start();
        return builder.username(container.getUsername())
                .password(container.getPassword())
                .url(container.getJdbcUrl())
                .build();
    }

}
