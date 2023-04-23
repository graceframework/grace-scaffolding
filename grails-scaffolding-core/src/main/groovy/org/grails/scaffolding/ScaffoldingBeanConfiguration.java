package org.grails.scaffolding;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

import org.grails.scaffolding.markup.ContextMarkupRenderer;
import org.grails.scaffolding.markup.ContextMarkupRendererImpl;
import org.grails.scaffolding.markup.DomainMarkupRenderer;
import org.grails.scaffolding.markup.DomainMarkupRendererImpl;
import org.grails.scaffolding.markup.PropertyMarkupRenderer;
import org.grails.scaffolding.markup.PropertyMarkupRendererImpl;
import org.grails.scaffolding.model.DomainModelService;
import org.grails.scaffolding.model.DomainModelServiceImpl;
import org.grails.scaffolding.model.property.DomainPropertyFactory;
import org.grails.scaffolding.model.property.DomainPropertyFactoryImpl;
import org.grails.scaffolding.registry.DomainInputRendererRegistry;
import org.grails.scaffolding.registry.DomainOutputRendererRegistry;
import org.grails.scaffolding.registry.DomainRendererRegisterer;

@AutoConfiguration
public class ScaffoldingBeanConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public ContextMarkupRenderer contextMarkupRenderer() {
        return new ContextMarkupRendererImpl();
    }

    @Bean
    @ConditionalOnMissingBean
    public DomainMarkupRenderer domainMarkupRenderer() {
        return new DomainMarkupRendererImpl();
    }

    @Bean
    @ConditionalOnMissingBean
    public PropertyMarkupRenderer propertyMarkupRenderer() {
        return new PropertyMarkupRendererImpl();
    }

    @Bean
    @ConditionalOnMissingBean
    public DomainPropertyFactory domainPropertyFactory() {
        return new DomainPropertyFactoryImpl();
    }

    @Bean
    @ConditionalOnMissingBean
    public DomainModelService domainModelService() {
        return new DomainModelServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean
    public DomainInputRendererRegistry domainInputRendererRegistry() {
        return new DomainInputRendererRegistry();
    }

    @Bean
    @ConditionalOnMissingBean
    public DomainOutputRendererRegistry domainOutputRendererRegistry() {
        return new DomainOutputRendererRegistry();
    }

    @Bean
    @ConditionalOnMissingBean
    public DomainRendererRegisterer domainRendererRegisterer() {
        return new DomainRendererRegisterer();
    }

}
