/**
 * Copyright (C) 2011 Akiban Technologies Inc.
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License, version 3,
 * as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses.
 */

package com.akiban.sql.optimizer.rule.range;

enum ComparisonResult {
    LT("<"),
    LT_BARELY("~<"),
    GT(">"),
    GT_BARELY("~>"),
    EQ("=="),
    INVALID("??")
    ;

    public ComparisonResult normalize() {
        switch (this) {
        case LT_BARELY: return LT;
        case GT_BARELY: return GT;
        default: return this;
        }
    }

    public String describe() {
        return description;
    }

    private ComparisonResult(String description) {
        this.description = description;
    }

    private final String description;
}