package top.ivyxo.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /**
     * 创建API
     */
    @Bean
    public Docket createRestApi() {
        ParameterBuilder ticketPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<Parameter>();

        ticketPar.name("user_id")
                .description("用户id，登录之后需要设置上去")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .defaultValue("0")
                .required(true)
                .build();
        pars.add(ticketPar.build());

        ticketPar = new ParameterBuilder();
        ticketPar.name("user_session")
                .description("用户session，登录之后需要设置上去")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .defaultValue("user_session")
                .required(true)
                .build();
        pars.add(ticketPar.build());

        return new Docket(DocumentationType.SWAGGER_2)
                // 详细定制
                .apiInfo(apiInfo())
                .select()
                // 指定当前包路径
                .apis(RequestHandlerSelectors.basePackage("top.ivyxo"))
                // 扫描所有 .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(pars);
    }

    /**
     * 添加摘要信息
     */
    private ApiInfo apiInfo() {
        // 用ApiInfoBuilder进行定制
        return new ApiInfoBuilder()
                .title("IVYXO项目接口文档")
                .description("用于个人项目ivyxo")
                .contact(new Contact("Richard", "http://new.ivyxo.top:9999", "617130179@qq.com"))
                .version("0.0.3")
                .build();
    }

}
