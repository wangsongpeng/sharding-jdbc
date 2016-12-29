package com.alibaba.druid.sql.dialect.sqlserver.parser;

import com.alibaba.druid.sql.ast.statement.SQLUpdateStatement;
import com.alibaba.druid.sql.dialect.sqlserver.ast.SQLServerOutput;
import com.alibaba.druid.sql.dialect.sqlserver.ast.SQLServerTop;
import com.alibaba.druid.sql.dialect.sqlserver.ast.stmt.SQLServerUpdateStatement;
import com.alibaba.druid.sql.lexer.Token;
import com.alibaba.druid.sql.parser.AbstractUpdateParser;
import com.alibaba.druid.sql.parser.SQLExprParser;

/**
 * SQLServer Update语句解析器.
 *
 * @author zhangliang
 */
public final class SQLServerUpdateParser extends AbstractUpdateParser {
    
    public SQLServerUpdateParser(final SQLExprParser exprParser) {
        super(exprParser);
    }
    
    @Override
    protected SQLServerUpdateStatement createUpdateStatement() {
        return new SQLServerUpdateStatement();
    }
    
    protected void parseCustomizedParserBetweenUpdateAndTable(final SQLUpdateStatement updateStatement) {
        SQLServerTop top = ((SQLServerExprParser) getExprParser()).parseTop();
        if (null != top) {
            ((SQLServerUpdateStatement) updateStatement).setTop(top);
        }
    }
    
    @Override
    protected void parseCustomizedParserBetweenSetAndWhere(final SQLUpdateStatement updateStatement) {
        SQLServerOutput output = ((SQLServerExprParser) getExprParser()).parserOutput();
        if (null != output) {
            ((SQLServerUpdateStatement) updateStatement).setOutput(output);
        }
        if (getLexer().equalToken(Token.FROM)) {
            getLexer().nextToken();
            ((SQLServerUpdateStatement) updateStatement).setFrom(getExprParser().createSelectParser().parseTableSource());
        }
    }
}