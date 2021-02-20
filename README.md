# interpreter
> 
## Grammar
> QUERY: KEYWORD_SELECT (PROJECTION)+ 
>> KEYWORD_FROM TABLE_NAME ( (KEYWORD_WHERE SELECTION EOQ)|EOQ )

> PROJECTION: PROJECT ','?

> PROJECT: COLUMN_NAME RANGE? PREDICATE? 'NONE'

> RANGE: ('[' (TOKEN_T ':' TOKEN_T?)|(':' TOKEN_T) ']')

> PREDICATE: OPERATOR TOKEN_T

> OPERATOR: '='|'!='|'<'|'>'

> TOKEN_T: any kind of number or string or '*'

> TABLE_NAME: valid table name

> COLUMN_NAME: valid column name	 //TABLE_NAME and COLUMN_NAME considered as IDENTIFIERS

> SELECTION: SELECT_DISJUNCTION //if KEYWORD_WHERE check SELECTION

> SELECT_DISJUNCTION: SELECT_CONJUNCTION ('OR' SELECT_CONJUNCTION)*

> SELECT_CONJUNCTION: SELECT ('AND' SELECT)*

> SELECT: HOLD PROJECT | '(' SELECTION ')'

> HOLD: 'ALL'|'ANY'

> EOQ: ';'

## Parsing algorithm
> *recursive-descent parser (top-down)*  