/* Copyright (c) 2008 Seva Safris
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * You should have received a copy of The MIT License (MIT) along with this
 * program. If not, see <http://opensource.org/licenses/MIT/>.
 */

package org.safris.commons.dbcp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.sql.DataSource;

import org.junit.Ignore;
import org.junit.Test;
import org.safris.commons.lang.Resources;
import org.safris.commons.test.LoggableTest;
import org.safris.xml.generator.compiler.runtime.Bindings;
import org.xml.sax.InputSource;

public class DataSourcesTest extends LoggableTest {
  @Test
  @Ignore("Need to have an embedded DB to test against.")
  public void testJNDIDataSource() throws Exception {
    final dbcp_dbcp dbcp = (dbcp_dbcp)Bindings.parse(new InputSource(Resources.getResource("dbcp.xml").getURL().openStream()));
    final DataSource dataSource = DataSources.createDataSource(dbcp);
    try (final Connection connection = dataSource.getConnection()) {
      if (connection != null) {
        try (
          final Statement statement = connection.createStatement();
          final ResultSet resultSet = statement.executeQuery("SELECT 1");
        ) {
          while (resultSet.next()) {
            final String string = resultSet.getString(1);
            log("C : " + string);
          }
        }
      }
    }
  }
}