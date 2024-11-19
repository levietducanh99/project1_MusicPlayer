package com.yourapp.MusicApp;

import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "com.yourapp.MusicApp") // Đảm bảo Spring quét đúng package
public class SpringBootJavaFXApplication extends Application {

    private static ApplicationContext springContext;

    // Khởi động Spring Boot và JavaFX
    public static void main(String[] args) {
        springContext = new SpringApplicationBuilder(SpringBootJavaFXApplication.class)
                .headless(false) // Đảm bảo Spring không chạy trong chế độ headless (không giao diện đồ họa)
                .run(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        // Khởi tạo JavaFX với Spring Boot context
        // Lấy đối tượng MusicAppApplication từ Spring context
        MusicAppApplication app = springContext.getBean(MusicAppApplication.class);

        // Khởi động JavaFX với đối tượng MusicAppApplication
        app.start(stage);  // Chuyển stage của JavaFX cho MusicAppApplication
    }
}
