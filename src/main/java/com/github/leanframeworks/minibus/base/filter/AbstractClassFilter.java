/*
 * Copyright (c) 2017, MiniBus Authors
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.github.leanframeworks.minibus.base.filter;

import com.github.leanframeworks.minibus.api.EventFilter;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public abstract class AbstractClassFilter<C> implements EventFilter<C> {

    private final Set<Class<?>> classes = new HashSet<>();

    private final boolean acceptSubClasses;

    private final boolean acceptNull;

    public AbstractClassFilter(Class<?>... classes) {
        this(false, classes);
    }

    public AbstractClassFilter(boolean acceptSubClasses, Class<?>... classes) {
        this(acceptSubClasses, false, classes);
    }

    public AbstractClassFilter(boolean acceptSubClasses, boolean acceptNull, Class<?>... classes) {
        this.acceptSubClasses = acceptSubClasses;
        this.acceptNull = acceptNull;
        Collections.addAll(this.classes, classes);
    }

    public void addClass(Class<?> clazz) {
        classes.add(clazz);
    }

    public void removeClass(Class<?> clazz) {
        classes.remove(clazz);
    }

    protected boolean accept(Class<?> testedClass) {
        boolean accept;

        if (testedClass == null) {
            accept = acceptNull;
        } else if (acceptSubClasses) {
            accept = false;
            for (Class<?> clazz : classes) {
                if (clazz.isAssignableFrom(testedClass)) {
                    accept = true;
                    break;
                }
            }
        } else {
            accept = classes.contains(testedClass);
        }

        return accept;
    }
}
