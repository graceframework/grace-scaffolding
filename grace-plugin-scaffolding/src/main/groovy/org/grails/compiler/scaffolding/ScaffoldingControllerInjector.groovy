/*
 * Copyright 2015-2024 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.grails.compiler.scaffolding

import groovy.transform.CompileStatic
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.GenericsType
import org.codehaus.groovy.ast.expr.ClassExpression
import org.codehaus.groovy.ast.tools.GenericsUtils
import org.codehaus.groovy.classgen.GeneratorContext
import org.codehaus.groovy.control.SourceUnit

import grails.compiler.ast.AstTransformer
import grails.compiler.ast.GrailsArtefactClassInjector
import grails.rest.RestfulController
import org.grails.compiler.injection.GrailsASTUtils
import org.grails.compiler.web.ControllerActionTransformer
import org.grails.core.artefact.ControllerArtefactHandler
import org.grails.plugins.web.rest.transform.ResourceTransform

/**
 * Transformation that turns a controller into a scaffolding controller at compile time,
 * when 'static scaffold = Foo' is specified
 *
 * @author Graeme Rocher
 * @author Michael Yan
 * @since 3.1
 */
@AstTransformer
@CompileStatic
class ScaffoldingControllerInjector implements GrailsArtefactClassInjector {

    public static final String PROPERTY_SCAFFOLD = "scaffold"

    final String[] artefactTypes = [ControllerArtefactHandler.TYPE] as String[]

    @Override
    void performInjection(SourceUnit source, GeneratorContext context, ClassNode classNode) {
        performInjectionOnAnnotatedClass(source, classNode)
    }

    @Override
    void performInjection(SourceUnit source, ClassNode classNode) {
        performInjectionOnAnnotatedClass(source, classNode)
    }

    @Override
    void performInjectionOnAnnotatedClass(SourceUnit source, ClassNode classNode) {
        def propertyNode = classNode.getProperty(PROPERTY_SCAFFOLD)

        def expression = propertyNode?.getInitialExpression()
        if (expression instanceof ClassExpression) {
            ClassNode superClassNode = GenericsUtils.makeClassSafe(RestfulController)
            def currentSuperClass = classNode.getSuperClass()
            if (currentSuperClass.equals(GrailsASTUtils.OBJECT_CLASS_NODE)) {
                def domainClass = ((ClassExpression) expression).getType()
                superClassNode.setGenericsTypes(new GenericsType(domainClass))
                classNode.setUsingGenerics(true)
                classNode.setSuperClass(superClassNode)
                new ResourceTransform().addConstructor(classNode, domainClass, false)
            }
            else if(!currentSuperClass.isDerivedFrom(superClassNode)) {
               GrailsASTUtils.error(source, classNode, "Scaffolded controllers (${classNode.name})" +
                       " cannot extend other classes: ${currentSuperClass.getName()}", true)
            }
        }
        else if (propertyNode != null) {
            GrailsASTUtils.error(source, propertyNode, "The 'scaffold' property must refer to a domain class.", true)
        }
    }

    @Override
    boolean shouldInject(URL url) {
        return url != null && ControllerActionTransformer.CONTROLLER_PATTERN.matcher(url.getFile()).find()
    }

}
