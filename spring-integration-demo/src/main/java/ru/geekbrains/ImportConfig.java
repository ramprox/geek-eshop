package ru.geekbrains;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.aopalliance.aop.Advice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.integration.handler.advice.ExpressionEvaluatingRequestHandlerAdvice;
import org.springframework.integration.jpa.dsl.Jpa;
import org.springframework.integration.jpa.dsl.JpaUpdatingOutboundEndpointSpec;
import org.springframework.integration.jpa.support.PersistMode;
import org.springframework.integration.message.AdviceMessage;
import ru.geekbrains.persist.model.Product;
import ru.geekbrains.persist.repositories.BrandRepository;
import ru.geekbrains.persist.repositories.CategoryRepository;

import javax.persistence.EntityManagerFactory;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class ImportConfig {

    private static final Logger logger = LoggerFactory.getLogger(ImportConfig.class);

    @Value("${source.directory.path}")
    private String sourceDirPath;

    @Value("${dest.directory.path}")
    private String destDirPath;

    @Value("${failure.directory.path}")
    private String failureDirPath;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Bean
    public MessageSource<File> sourceDirectory() {
        FileReadingMessageSource messageSource = new FileReadingMessageSource();
        messageSource.setDirectory(new File(sourceDirPath));
        messageSource.setAutoCreateDirectory(true);
        return messageSource;
    }

    @Bean
    public IntegrationFlow fileMoveFlow() {
        return IntegrationFlows.from(sourceDirectory(), conf -> conf.poller(Pollers.fixedDelay(2000)))
                .filter(msg -> ((File) msg).getName().endsWith(".csv"))
                .transform(this::fileToListProductCsvBean)
                .transform(this::listProductCsvBeanToListProduct)
                .handle(jpaPersistHandler(), e -> e.transactional().advice(afterPersistAdvice()))
                .get();
    }

    private List<ProductCsvBean> fileToListProductCsvBean(File file) {
        List<ProductCsvBean> result = null;
        try (Reader reader = Files.newBufferedReader(file.toPath())) {
            CsvToBean<ProductCsvBean> cb = new CsvToBeanBuilder<ProductCsvBean>(reader)
                    .withType(ProductCsvBean.class)
                    .build();
            result = cb.parse();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    private List<Product> listProductCsvBeanToListProduct(List<ProductCsvBean> productCsvBeans) {
        return productCsvBeans.stream()
                .map(productCsvBean -> {
                    Product product = new Product();
                    product.setName(productCsvBean.getName());
                    product.setCost(productCsvBean.getCost());
                    product.setCategory(categoryRepository.findByName(productCsvBean.getCategoryName()));
                    product.setBrand(brandRepository.findByName(productCsvBean.getBrandName()));
                    product.setShortDescription(productCsvBean.getShortDescription());
                    product.setDescription(productCsvBean.getDescription());
                    return product;
                })
                .collect(Collectors.toList());
    }

    @Bean
    public JpaUpdatingOutboundEndpointSpec jpaPersistHandler() {
        return Jpa.outboundAdapter(this.entityManagerFactory)
                .entityClass(Product.class)
                .persistMode(PersistMode.PERSIST);
    }

    @Bean
    public Advice afterPersistAdvice() {
        ExpressionEvaluatingRequestHandlerAdvice advice = new ExpressionEvaluatingRequestHandlerAdvice();
        advice.setSuccessChannelName("success.input");
        advice.setFailureChannelName("failure.input");
        advice.setTrapException(true);
        return advice;
    }

    @Bean
    public IntegrationFlow success() {
        return getIntegrationFlow(destDirPath);
    }

    @Bean
    public IntegrationFlow failure() {
        return getIntegrationFlow(failureDirPath);
    }

    private IntegrationFlow getIntegrationFlow(String handledDir) {
        return f -> f.handle(msg -> {
            String fileName = (String)((AdviceMessage<?>)msg).getInputMessage().getHeaders().get("file_name");
            try {
                Path handledDirPath = Paths.get(handledDir);
                if(!Files.exists(handledDirPath)) {
                    Files.createDirectory(handledDirPath);
                }
                Files.move(Paths.get(sourceDirPath, fileName), Paths.get(handledDir, fileName));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
