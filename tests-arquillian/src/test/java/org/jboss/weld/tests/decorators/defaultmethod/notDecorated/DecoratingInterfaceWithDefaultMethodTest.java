/*
 * JBoss, Home of Professional Open Source
 * Copyright 2019, Red Hat, Inc., and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jboss.weld.tests.decorators.defaultmethod.notDecorated;

import jakarta.inject.Inject;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.BeanArchive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.weld.test.util.Utils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Decorator on an interface that only decorated non-default method. Contains a variant for standard and
 * abstract decorators.
 *
 * See also WELD-2647
 */
@RunWith(Arquillian.class)
public class DecoratingInterfaceWithDefaultMethodTest {
    @Deployment
    public static Archive<?> deploy() {
        return ShrinkWrap.create(BeanArchive.class, Utils.getDeploymentNameAsHash(DecoratingInterfaceWithDefaultMethodTest.class))
                .decorate(NonAbstractDecorator.class)
                .decorate(AbstractDecorator.class)
                .addPackage(DecoratingInterfaceWithDefaultMethodTest.class.getPackage());
    }

    @Inject
    FooBean bean;

    @Test
    public void testDecoratorOnInterfaceWithDefaultMethod() {
        Assert.assertEquals(InterfaceWithDefaultMethod.class.getSimpleName(), bean.defaultPing());
        Assert.assertEquals(AbstractDecorator.class.getSimpleName() + FooBean.class.getSimpleName() + NonAbstractDecorator.class.getSimpleName(), bean.decoratedMethod());
    }
}
