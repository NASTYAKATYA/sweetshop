package ru.mirea.work;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * Главный класс веб-приложения
 * @author Бирюкова Екатерина
 */
@SpringBootApplication
public class WorkApplication {

    /**
     * Входная точка веб-приложения
     * @param args Массив аргументов переданных при запуске веб-приложения
     */
    public static void main(String[] args) {
        SpringApplication.run(WorkApplication.class, args);
    }

}
