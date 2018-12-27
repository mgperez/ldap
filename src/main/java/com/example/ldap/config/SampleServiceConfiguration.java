package com.example.ldap.config;

import com.example.ldap.service.SampleService;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Arrays;
import java.util.List;

/**
 * https://gist.github.com/ehdez73/b9ac35bf8d230c769309
 */
@Configuration
public class SampleServiceConfiguration implements ImportBeanDefinitionRegistrar {

    private List<String> names = Arrays.asList("s1", "s2", "s3");

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        names.stream().forEach(
                name -> register(registry, name)
        );
    }

    private void register(BeanDefinitionRegistry registry, String name){
        BeanDefinition bd = new RootBeanDefinition(SampleService.class);
        bd.getConstructorArgumentValues()
                .addGenericArgumentValue(name);
        registry.registerBeanDefinition(name, bd);
    }
}