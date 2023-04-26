package ca.bc.gov.open.scss.configuration;

import ca.bc.gov.open.scss.models.serializers.InstantDeserializer;
import ca.bc.gov.open.scss.models.serializers.InstantSerializer;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import javax.xml.soap.SOAPMessage;
import org.apache.catalina.webresources.StandardRoot;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.soap.SoapVersion;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
public class SoapConfig extends WsConfigurerAdapter {

    public static final String SOAP_NAMESPACE =
            "http://brooks/SCSS.Source.CeisScss.ws.provider:CeisScss";

    @Value("${scss.username}")
    private String username;

    @Value("${scss.password}")
    private String password;

    @Value("${scss.ords-read-timeout}")
    private String ordsReadTimeout;

    @Bean
    public WebServerFactoryCustomizer prodTomcatCustomizer() {
        return (WebServerFactoryCustomizer<TomcatServletWebServerFactory>)
                factory ->
                        factory.addContextCustomizers(
                                context -> {
                                    final int cacheSize = 100 * 1024;
                                    StandardRoot standardRoot = new StandardRoot(context);
                                    standardRoot.setCacheMaxSize(cacheSize);
                                    context.setResources(standardRoot);
                                });
    }

    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(
            ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean<>(servlet, "/ws/*");
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        var restTemplate =
                restTemplateBuilder
                        .basicAuthentication(username, password)
                        .setReadTimeout(Duration.ofSeconds(Integer.parseInt(ordsReadTimeout)))
                        .build();
        restTemplate.getMessageConverters().add(0, createMappingJacksonHttpMessageConverter());
        return restTemplate;
    }

    private MappingJackson2HttpMessageConverter createMappingJacksonHttpMessageConverter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(objectMapper());
        return converter;
    }

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Instant.class, new InstantDeserializer());
        module.addSerializer(Instant.class, new InstantSerializer());
        objectMapper.registerModule(module);
        return objectMapper;
    }

    @Bean
    public SaajSoapMessageFactory messageFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(SOAPMessage.WRITE_XML_DECLARATION, "true");
        SaajSoapMessageFactory messageFactory = new SaajSoapMessageFactory();
        messageFactory.setMessageProperties(props);
        messageFactory.setSoapVersion(SoapVersion.SOAP_11);
        return messageFactory;
    }

    @Bean(name = "SCSS.Source.CeisScss.ws.provider:CeisScss")
    public DefaultWsdl11Definition Wsdl11Definition() {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("scss");
        wsdl11Definition.setCreateSoap11Binding(true);
        wsdl11Definition.setLocationUri("/ws");
        wsdl11Definition.setTargetNamespace(SoapConfig.SOAP_NAMESPACE);
        wsdl11Definition.setSchema(schema3());
        return wsdl11Definition;
    }

    @Bean(name = "schema3")
    public XsdSchema schema3() {
        return new SimpleXsdSchema(
                new ClassPathResource("xsdSchemas/RequestAndResponseObjects.xsd"));
    }
}
