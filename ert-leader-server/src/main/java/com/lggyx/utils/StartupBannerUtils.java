package com.lggyx.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class StartupBannerUtils implements ApplicationRunner {
    @Value("${server.port:8080}")  // 从yml读取，默认值8080
    private String port;

    @Override
    public void run(ApplicationArguments args) {
        String buildSuccess = """
                                                                                                                       \s
                     mmmmmmmm  mmmmmm    mmmmmmmm            mm                                  mm                    \s
                     ##""\"""\"  ##""\""##  ""\"##""\"            ##                                  ##                    \s
                     ##        ##    ##     ##               ##         m####m    m#####m   m###m##   m####m    ##m####\s
                     #######   #######      ##               ##        ##mmmm##   " mmm##  ##"  "##  ##mmmm##   ##"    \s
                     ##        ##  "##m     ##      #####    ##        ##""\"""\"  m##""\"##  ##    ##  ##""\"""\"   ##     \s
                     ##mmmmmm  ##    ##     ##               ##mmmmmm  "##mmmm#  ##mmm###  "##mm###  "##mmmm#   ##     \s
                     ""\"""\"""  ""    ""\"    ""               ""\"""\"""    ""\"""    ""\"" ""    ""\" ""    ""\"""    "" \s
                     项目启动成功，接口文档：http://localhost:%s/doc.html
                """.formatted(port);
        System.out.println(buildSuccess);
    }
}
