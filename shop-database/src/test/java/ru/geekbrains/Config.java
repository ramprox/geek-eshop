package ru.geekbrains;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(ConfigLog4jdbc.class)
public class Config {
}
