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

package com.akiban.sql.optimizer;

import com.akiban.sql.compiler.ASTTransformTestBase;
import com.akiban.sql.compiler.BooleanNormalizer;
import com.akiban.sql.optimizer.AISBinder;
import com.akiban.sql.optimizer.AISTypeComputer;
import com.akiban.sql.optimizer.BindingNodeFactory;
import com.akiban.sql.optimizer.BoundNodeToString;
import com.akiban.sql.optimizer.Grouper;
import com.akiban.sql.optimizer.SubqueryFlattener;
import com.akiban.sql.parser.SQLParser;
import com.akiban.sql.parser.StatementNode;
import com.akiban.sql.views.ViewDefinition;

import com.akiban.ais.ddl.SchemaDef;
import com.akiban.ais.ddl.SchemaDefToAis;
import com.akiban.ais.model.AkibanInformationSchema;
import com.akiban.ais.model.GroupIndex;
import com.akiban.server.util.GroupIndexCreator;

import org.junit.Before;
import org.junit.Ignore;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.FileReader;
import java.io.Reader;

@Ignore
public class OptimizerTestBase extends ASTTransformTestBase
{
    protected OptimizerTestBase(String caseName, String sql, 
                                String expected, String error) {
        super(caseName, sql, expected, error);
    }

    public static final File RESOURCE_DIR = 
        new File("src/test/resources/"
                 + OptimizerTestBase.class.getPackage().getName().replace('.', '/'));

    // Base class has all possible transformers for convenience.
    protected AISBinder binder;
    protected AISTypeComputer typeComputer;
    protected BooleanNormalizer booleanNormalizer;
    protected SubqueryFlattener subqueryFlattener;
    protected Grouper grouper;

    @Before
    public void makeTransformers() throws Exception {
        parser = new SQLParser();
        parser.setNodeFactory(new BindingNodeFactory(parser.getNodeFactory()));
        unparser = new BoundNodeToString();
        typeComputer = new AISTypeComputer();
        booleanNormalizer = new BooleanNormalizer(parser);
        subqueryFlattener = new SubqueryFlattener(parser);
        grouper = new Grouper(parser);
    }

    protected static AkibanInformationSchema parseSchema(File schema) throws Exception {
        String sql = fileContents(schema);
        SchemaDef schemaDef = SchemaDef.parseSchema("use user; " + sql);
        SchemaDefToAis toAis = new SchemaDefToAis(schemaDef, false);
        return toAis.getAis();
    }

    protected AkibanInformationSchema loadSchema(File schema) throws Exception {
        AkibanInformationSchema ais = parseSchema(schema);
        binder = new AISBinder(ais, "user");
        return ais;
    }

    protected void loadView(File view) throws Exception {
        String sql = fileContents(view);
        binder.addView(new ViewDefinition(sql, parser));
    }

    protected static void loadGroupIndexes(AkibanInformationSchema ais, File file) 
            throws Exception {
        Reader rdr = null;
        try {
            rdr = new FileReader(file);
            BufferedReader brdr = new BufferedReader(rdr);
            while (true) {
                String line = brdr.readLine();
                if (line == null) break;
                String defn[] = line.split("\t");
                GroupIndex index = GroupIndexCreator.createIndex(ais,
                                                                 defn[0], 
                                                                 defn[1],
                                                                 defn[2]);
                index.getGroup().addIndex(index);
            }
        }
        finally {
            if (rdr != null) {
                try {
                    rdr.close();
                }
                catch (IOException ex) {
                }
            }
        }
    }

}
