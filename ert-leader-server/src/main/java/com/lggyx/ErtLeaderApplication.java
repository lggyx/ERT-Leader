package com.lggyx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ErtLeaderApplication {
    public static void main(String[] args) {
        SpringApplication.run(ErtLeaderApplication.class, args);
        String buildSuccess = """
                                                                                                                   \s
                 mmmmmmmm  mmmmmm    mmmmmmmm            mm                                  mm                    \s
                 ##""\"""\"  ##""\""##  ""\"##""\"            ##                                  ##                    \s
                 ##        ##    ##     ##               ##         m####m    m#####m   m###m##   m####m    ##m####\s
                 #######   #######      ##               ##        ##mmmm##   " mmm##  ##"  "##  ##mmmm##   ##"    \s
                 ##        ##  "##m     ##      #####    ##        ##""\"""\"  m##""\"##  ##    ##  ##""\"""\"   ##     \s
                 ##mmmmmm  ##    ##     ##               ##mmmmmm  "##mmmm#  ##mmm###  "##mm###  "##mmmm#   ##     \s
                 ""\"""\"""  ""    ""\"    ""               ""\"""\"""    ""\"""    ""\"" ""    ""\" ""    ""\"""    "" \s
                """;
        System.out.println(buildSuccess);
    }

}
