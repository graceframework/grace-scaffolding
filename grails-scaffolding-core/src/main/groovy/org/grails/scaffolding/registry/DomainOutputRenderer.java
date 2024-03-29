package org.grails.scaffolding.registry;

import groovy.lang.Closure;
import groovy.xml.MarkupBuilder;

import org.grails.scaffolding.model.property.DomainProperty;

/**
 * Used to render markup that represents how single domain class property will be displayed
 *
 * @author James Kleeh
 */
public interface DomainOutputRenderer extends DomainRenderer {

    /**
     * Defines how a given domain class property will be rendered in the context of a list of domains class instances
     *
     * @param property The domain property to be rendered
     * @return The closure to be passed to an instance of {@link MarkupBuilder}
     */
    Closure renderListOutput(DomainProperty property);

    /**
     * Defines how a given domain class property will be rendered in the context of a single domain class instance
     *
     * @param property The domain property to be rendered
     * @return The closure to be passed to an instance of {@link MarkupBuilder}
     */
    Closure renderOutput(DomainProperty property);

}
